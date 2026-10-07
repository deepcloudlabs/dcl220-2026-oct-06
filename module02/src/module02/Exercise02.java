package module02;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

enum ShippingMethod {
	STANDARD, EXPRESS, OVERNIGHT, DRONE
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Policy {
	ShippingMethod value();
}

interface ShippingPolicy {
	BigDecimal fee(BigDecimal orderTotal);
}

@Policy(ShippingMethod.STANDARD)
class StandardShippingPolicy implements ShippingPolicy {

	@Override
	public BigDecimal fee(BigDecimal orderTotal) {
		return new BigDecimal("5.00");
	}

}

@Policy(ShippingMethod.DRONE)
class DroneShippingPolicy implements ShippingPolicy {

	@Override
	public BigDecimal fee(BigDecimal orderTotal) {
		return orderTotal.multiply(new BigDecimal("0.20")).add(new BigDecimal("30.00"));
	}

}

@Policy(ShippingMethod.EXPRESS)
class ExpressShippingPolicy implements ShippingPolicy {

	@Override
	public BigDecimal fee(BigDecimal orderTotal) {
		return orderTotal.multiply(new BigDecimal("0.10"));
	}

}

@Policy(ShippingMethod.OVERNIGHT)
class OvernightShippingPolicy implements ShippingPolicy {

	@Override
	public BigDecimal fee(BigDecimal orderTotal) {
		return new BigDecimal("30.00");
	}

}

class ShippingCalculator {
	private final Map<ShippingMethod, ShippingPolicy> shippingPolicies;

	public ShippingCalculator(Map<ShippingMethod, ShippingPolicy> shippingPolicies) {
		this.shippingPolicies = shippingPolicies;
	}

	BigDecimal calculate(ShippingMethod method, BigDecimal orderTotal) {
		var shippingPolicy = shippingPolicies.get(method);
		return shippingPolicy.fee(orderTotal);
	}
}

public class Exercise02 {
	private final static Map<ShippingMethod, ShippingPolicy> ShippingPolicies = loadPolicies("module02");

	public static void main(String[] args) {
		ShippingCalculator goodCalculator = new ShippingCalculator(ShippingPolicies);
		for (var shippingMethod : ShippingMethod.values()) {
			System.out.println("%9s fee: %f".formatted(shippingMethod.name(),
					goodCalculator.calculate(shippingMethod, BigDecimal.valueOf(240L))));

		}
	}

	private static Map<ShippingMethod, ShippingPolicy> loadPolicies(String packageName) {

		try {
			String packagePath = packageName.replace('.', '/');
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			URL resource = classLoader.getResource(packagePath);
			if (resource == null) {
				throw new IllegalStateException("Package not found: " + packageName);
			}
			File directory = new File(resource.toURI());
			File[] classFiles = directory.listFiles(file -> file.getName().endsWith(".class") && !file.getName().contains("$"));
			if (classFiles == null) {
				return Map.of();
			}
			return Arrays.stream(classFiles)
					.map(file -> packageName + "." + file.getName().replace(".class", ""))
					.map(Exercise02::loadClass)
					.filter(ShippingPolicy.class::isAssignableFrom)
					.filter(clazz -> clazz != ShippingPolicy.class)
					.filter(clazz -> clazz.isAnnotationPresent(Policy.class))
					.collect(Collectors.toUnmodifiableMap(
							clazz -> clazz.getAnnotation(Policy.class).value(),
							Exercise02::createPolicy));
		} catch (Exception e) {
			throw new IllegalStateException("Could not scan shipping policies", e);
		}
	}

	private static Class<?> loadClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	private static ShippingPolicy createPolicy(Class<?> clazz) {
		try {
			return (ShippingPolicy) clazz.getDeclaredConstructor().newInstance();

		} catch (ReflectiveOperationException e) {
			throw new IllegalStateException("Cannot instantiate " + clazz.getName(), e);
		}
	}
}