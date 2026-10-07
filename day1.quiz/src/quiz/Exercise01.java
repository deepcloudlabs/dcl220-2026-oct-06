package quiz;

public class Exercise01 {

	public static void main(String[] args) {
		OrderService orderService = new OrderService();
		orderService.placeOrder(new Order("1","jack@example.com", "REGULAR",100), new GiftCardPayment());
		orderService.placeOrder(new Order("2","kate@example.com", "VIP",200), new Payment());
		orderService.placeOrder(new Order("3","ben@example.com", "REGULAR",300), new Payment());		
	}

}

class Order {
	String id;
	String email;
	String customerType; // "REGULAR" or "VIP"
	double amount;

	Order(String id, String email, String customerType, double amount) {
		this.id = id;
		this.email = email;
		this.customerType = customerType;
		this.amount = amount;
	}
}

class EmailNotifier {
	public void sendEmail(String to, String msg) {
		System.out.println("Email to " + to + ": " + msg);
	}

	public void sendSms(String phone, String msg) {
		throw new UnsupportedOperationException();
	}
}

class Payment {
	void charge(double amount) {
		System.out.println("Charged " + amount);
	}

	void refund(double amount) {
		System.out.println("Refunded " + amount);
	}
}

class GiftCardPayment extends Payment {
	@Override
	void refund(double amount) {
		throw new UnsupportedOperationException("Gift cards can't be refunded");
	}
}

class MySqlDatabase {
	void save(Order order) {
		System.out.println("INSERT INTO orders " + order.id);
	}
}

class OrderService {
	private MySqlDatabase db = new MySqlDatabase();
	private EmailNotifier notifier = new EmailNotifier();

	void placeOrder(Order order, Payment payment) {
		if (order.amount <= 0) {
			throw new IllegalArgumentException("Bad amount");
		}
		payment.charge(calculateTotal(order));
		db.save(order);
		notifier.sendEmail(order.email, "Order " + order.id + " confirmed");
	}

	void cancelOrder(Order order, Payment payment) {
		payment.refund(calculateTotal(order));
	}

	double calculateTotal(Order order) {
		if (order.customerType.equals("VIP")) {
			return order.amount * 0.8;
		} else if (order.customerType.equals("REGULAR")) {
			return order.amount * 0.95;
		}
		return order.amount;
	}
}