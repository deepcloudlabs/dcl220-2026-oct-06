package module02;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

record InvoiceLine(String description, BigDecimal amount) {
}

record Invoice(String id, String customerEmail, List<InvoiceLine> lines) {
}

interface InvoiceCalculator {
	BigDecimal calculateTotal(Invoice invoice);
}

interface InvoiceRepository {
	void save(Invoice invoice, BigDecimal total);
}

interface InvoiceNotifier {
	void send(Invoice invoice, BigDecimal total);
}

interface InvoiceLogger {
	void invoiceProcessed(Invoice invoice, BigDecimal total);
}

class StandardInvoiceCalculator implements InvoiceCalculator {

	@Override
	public BigDecimal calculateTotal(Invoice invoice) {
		return invoice.lines().stream().map(InvoiceLine::amount).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}

class InMemoryInvoiceRepository implements InvoiceRepository {

	@Override
	public void save(Invoice invoice, BigDecimal total) {
		System.out.printf("SAVE invoice=%s total=%s%n", invoice.id(), total);
	}

}

class EmailInvoiceNotifier implements InvoiceNotifier {

	@Override
	public void send(Invoice invoice, BigDecimal total) {
		System.out.printf("EMAIL %s total=%s%n", invoice.customerEmail(), total);
	}

}

class SmsInvoiceNotifier implements InvoiceNotifier {

	@Override
	public void send(Invoice invoice, BigDecimal total) {
		System.out.printf("SMS %s total=%s%n", invoice.customerEmail(), total);
	}

}

class WhatsappInvoiceNotifier implements InvoiceNotifier {

	@Override
	public void send(Invoice invoice, BigDecimal total) {
		System.out.printf("WHATSAPP %s total=%s%n", invoice.customerEmail(), total);
	}

}

class MultichannelInvoiceNotifer implements InvoiceNotifier {
	private List<InvoiceNotifier> channelNotifiers = new ArrayList<>();

	public void addChannel(InvoiceNotifier channel) {
		channelNotifiers.add(channel);
	}

	@Override
	public void send(Invoice invoice, BigDecimal total) {
		channelNotifiers.forEach(invoiceNotifier -> invoiceNotifier.send(invoice, total));
	}

}

class AuditLogger implements InvoiceLogger {

	@Override
	public void invoiceProcessed(Invoice invoice, BigDecimal total) {
		System.out.printf("AUDIT invoice.processed id=%s%n", invoice.id());
	}

}

class InvoiceService {
	private final InvoiceCalculator invoiceCalculator;
	private final InvoiceRepository invoiceRepository;
	private final InvoiceNotifier invoiceNotifier;
	private final InvoiceLogger invoiceLogger;

	public InvoiceService(InvoiceCalculator invoiceCalculator, InvoiceRepository invoiceRepository,
			InvoiceNotifier invoiceNotifier, InvoiceLogger invoiceLogger) {
		this.invoiceCalculator = invoiceCalculator;
		this.invoiceRepository = invoiceRepository;
		this.invoiceNotifier = invoiceNotifier;
		this.invoiceLogger = invoiceLogger;
	}

	public void process(Invoice invoice) {
		BigDecimal total = invoiceCalculator.calculateTotal(invoice);
		invoiceRepository.save(invoice, total);
		invoiceNotifier.send(invoice, total);
		invoiceLogger.invoiceProcessed(invoice, total);
	}
}

public class Exercise01 {

	public static void main(String[] args) {
		var invoiceCalculator = new StandardInvoiceCalculator();
		var invoiceRepository = new InMemoryInvoiceRepository();
		MultichannelInvoiceNotifer invoiceNotifier = new MultichannelInvoiceNotifer();
		invoiceNotifier.addChannel(new SmsInvoiceNotifier());
		invoiceNotifier.addChannel(new WhatsappInvoiceNotifier());
		var invoiceLogger = new AuditLogger();
		InvoiceService good = new InvoiceService(invoiceCalculator, invoiceRepository, invoiceNotifier, invoiceLogger);
		good.process(new Invoice("INV-1001", "customer@example.com",
				List.of(new InvoiceLine("Consulting", new BigDecimal("1200.00")),
						new InvoiceLine("Support", new BigDecimal("300.00")))));

	}

}
