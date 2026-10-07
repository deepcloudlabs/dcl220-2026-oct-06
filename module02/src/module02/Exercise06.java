package module02;

import java.math.BigDecimal;

record Order(String id, String customerEmail, BigDecimal total) {
}

final class LegacyPaymentClient implements PaymentClient {
	public String charge(BigDecimal amount) {
		String id = "PAY-LEGACY";
		System.out.printf("legacy payment %s amount=%s%n", id, amount);
		return id;
	}
}

final class PaypalPaymentClient implements PaymentClient {
	public String charge(BigDecimal amount) {
		String id = "PAYPAL";
		System.out.printf("paypal payment %s amount=%s%n", id, amount);
		return id;
	}
}

final class GarantiBBVAPaymentGateway implements PaymentClient {
	public String charge(BigDecimal amount) {
		String id = "GarantiBBVA";
		System.out.printf("Garanti BBVA Payment Gateway: payment %s amount=%s%n", id, amount);
		return id;
	}
}



final class LegacyOrderStore implements OrderStore {
	public void save(Order order, String paymentId) {
		System.out.printf("legacy store to mysql=%s%n", order);		
		/* simulated */ }

}


final class MongoOrderStore implements OrderStore {
	public void save(Order order, String paymentId) {
		System.out.printf("store to mongodb=%s%n", order);		
		/* simulated */ }

}
final class LegacyEmailClient implements EmailClient {
	public void send(String to, String text) {
		System.out.printf("legacy email to=%s%n", to);
	}
}

// ProblematicCheckoutService --- uses/depends --->  LegacyPaymentClient
//              |
//              |____uses/depends------> LegacyOrderStore
//              |____uses/depends------> LegacyEmailClient

interface PaymentClient {
	String charge(BigDecimal amount);
}

interface EmailClient {
	void send(String email, String message);
}

interface OrderStore {
	void save(Order order, String paymentId);
}

final class CheckoutService {
	private final PaymentClient payment ;
	private final OrderStore store ;
	private final EmailClient email ;
	
	public CheckoutService(PaymentClient payment, OrderStore store, EmailClient email) {
		this.payment = payment;
		this.store = store;
		this.email = email;
	}

	void checkout(Order order) {
		String paymentId = payment.charge(order.total());
		store.save(order, paymentId);
		email.send(order.customerEmail(), "Order confirmed: " + order.id());
	}
}

public class Exercise06 {

	public static void main(String[] args) {
		var order = new Order("1","jack@example.com",BigDecimal.valueOf(5_000));
		var paymentClient = new GarantiBBVAPaymentGateway();
		var emailClient = new LegacyEmailClient();
		var orderStore = new MongoOrderStore();
		var checkoutService = new CheckoutService(paymentClient,orderStore,emailClient);
		checkoutService.checkout(order);
	}

}
