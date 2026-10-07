package quiz;

public class Exercise01 {

	public static void main(String[] args) {
		// DIP -> Dependency Injection -> Factory/Framework (e.g., Spring)
		var notifier = new EmailNotifier();
		var orderRepository = new MySqlDatabase();
		var validator = new OrderValidator();
		var orderService = new OrderService(orderRepository,notifier,validator);
		orderService.placeOrder(new Order("1", "jack@example.com", new RegularDiscount(), 100), new GiftCardPayment());
		orderService.placeOrder(new Order("2", "kate@example.com", new VipDiscount(), 200), new Payment());
		orderService.placeOrder(new Order("3", "ben@example.com", new RegularDiscount(), 300), new Payment());
	}

}

enum CustomerType {
	REGULAR, VIP
}

// OCP
interface DiscountPolicy {
	double apply(double amount);
}

class RegularDiscount implements DiscountPolicy {

	@Override
	public double apply(double amount) {
		return amount * 0.9;
	}
}

class VipDiscount implements DiscountPolicy {

	@Override
	public double apply(double amount) {
		return amount * 0.75;
	}
}

record Order(String id, String email, DiscountPolicy discountPolicy, double amount) {

	public double total() {
		return this.discountPolicy.apply(this.amount);
	}

}

//ISP + LSP
interface EmailSender {
	void sendEmail(String to, String msg);
}  

interface SmsSender {
	void sendSms(String to, String msg);
}

interface WhatsappSender {
	void sendMessage(String to, String msg);
}  

interface MultiChannelNotifier extends EmailSender, SmsSender, WhatsappSender {}

class EmailNotifier implements EmailSender {
	public void sendEmail(String to, String msg) {
		System.out.println("Email to " + to + ": " + msg);
	}
}

class SmsNotifier implements SmsSender {

	@Override
	public void sendSms(String to, String msg) {
		System.out.println("Sending sms message to " + to + ": " + msg);		
	}
	
}

class MultiChannelNotifierService implements MultiChannelNotifier {

	@Override
	public void sendEmail(String to, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendSms(String to, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(String to, String msg) {
		// TODO Auto-generated method stub
		
	}
	
}
// LSP
interface Payable {
	void charge(double amount);

}

interface Refundable {
	void refund(double amount);
}

class Payment implements Payable, Refundable {
	@Override
	public void charge(double amount) {
		System.out.println("Charged " + amount);
	}

	@Override
	public void refund(double amount) {
		System.out.println("Refunded " + amount);
	}
}

class GiftCardPayment implements Payable {

	@Override
	public void charge(double amount) {
		System.out.println("Charged " + amount);
	}
}

class MySqlDatabase implements OrderRepository {
	public void save(Order order) {
		System.out.println("INSERT INTO orders " + order.id());
	}
}

class OrderValidator implements Validator<Order> {
	public void validate(Order order) {
		if (order.amount() <= 0) {
			throw new IllegalArgumentException("Bad amount");
		}		
	}
}

interface Validator<T> {
	public void validate(T t);
}

interface OrderRepository {
	void save(Order order);
}

// SRP -> High Cohesion -> Tightly Coupled -> Low Coupled
class OrderService {
	// DIP
	private final OrderRepository orderRepository;
	private final EmailSender emailNotifier;
	private final Validator<Order> orderValidator;
		
	public OrderService(OrderRepository orderRepository, EmailSender emailNotifier, Validator<Order> orderValidator) {
		this.orderRepository = orderRepository;
		this.emailNotifier = emailNotifier;
		this.orderValidator = orderValidator;
	}

	void placeOrder(Order order, Payable payment) {
		orderValidator.validate(order);
		payment.charge(order.total());
		orderRepository.save(order);
		emailNotifier.sendEmail(order.email(), "Order " + order.id() + " confirmed");
	}

	void cancelOrder(Order order, Refundable payment) {
		payment.refund(order.total());
	}

}