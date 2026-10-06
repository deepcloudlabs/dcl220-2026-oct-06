package com.example.catalog.app;

import module java.base;
import module com.example.catalog.api;

public final class Main {
	private Main() {
	}

	public static void main(String[] args) throws ReflectiveOperationException {
		var services = ServiceLoader.load(CatalogService.class).stream().map(ServiceLoader.Provider::get)
				.sorted(Comparator.comparing(CatalogService::providerName)).toList();
		if (services.isEmpty()) {
			throw new IllegalStateException("No CatalogService provider. Put the provider JAR on the module path.");
		}

		for (CatalogService service : services) {
			System.out.println("Provider: " + service.providerName());
			for (Product product : service.products()) {
				System.out.printf("%s | %s | price=%s | 10%% discount=%s%n", product.id(), product.name(),
						product.unitPrice(), service.discountedPrice(product, 10));
			}
			verify(service.findById("P-100").isPresent(), "Known product exists");
			verify(service.findById("missing").isEmpty(), "Unknown product is absent");
			verify(service.discountedPrice(service.findById("P-100").orElseThrow(), 10).equals(new BigDecimal("22.50")),
					"Discount calculation");
		}

		Module app = Main.class.getModule();
		Module model = Product.class.getModule();
		Module provider = services.getFirst().getClass().getModule();
		System.out.println("\nModule boundaries:");
		System.out.println("Application module: " + app.getName());
		System.out.println("App reads model transitively: " + app.canRead(model));
		System.out.println("App reads provider: " + app.canRead(provider));
		System.out.println("Model helper exported to app: " + model.isExported("edu.catalog.model.internal", app));
		System.out.println(
				"Model helper exported to provider: " + model.isExported("edu.catalog.model.internal", provider));
		System.out.println("Diagnostics opened to app: " + model.isOpen("edu.catalog.model.diagnostics", app));
		verify(app.canRead(model), "Transitive readability");
		verify(!app.canRead(provider), "App has no provider dependency");
		verify(!model.isExported("edu.catalog.model.internal", app), "Helper is hidden");
		verify(model.isExported("edu.catalog.model.internal", provider), "Qualified export");

		// No import of DiagnosticBox: its package is opened, not exported.
		Class<?> diagnostics = Class.forName("edu.catalog.model.diagnostics.DiagnosticBox");
		var constructor = diagnostics.getDeclaredConstructor();
		constructor.setAccessible(true);
		Object box = constructor.newInstance();
		var message = diagnostics.getDeclaredField("message");
		message.setAccessible(true);
		System.out.println("Reflection: " + message.get(box));

		// Loading a hidden class is possible; suppressing its constructor's
		// access checks is denied because its package is neither exported nor
		// opened to the application. ServiceLoader has its own access mechanism.
		var hiddenConstructor = services.getFirst().getClass().getConstructor();
		boolean canAccessHidden = hiddenConstructor.trySetAccessible();
		System.out.println("Provider constructor accessible to app: " + canAccessHidden);
		verify(!canAccessHidden, "Provider implementation is encapsulated");

		System.out.println("All checks passed.");

		// Compiler experiment: uncomment this line. The package is not
		// exported to the app, even though the app reads the model module.
		// edu.catalog.model.internal.PriceRules.applyDiscount(BigDecimal.TEN, 10);
		// Likewise, importing DiagnosticBox fails: opens is not exports.
	}

	private static void verify(boolean condition, String description) {
		if (!condition)
			throw new AssertionError(description);
	}
}
