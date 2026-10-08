package solution;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class InvoiceLineItemsSolution {

	public static void main(String[] args) {
		List<LineItem> invoice = List.of(new Goods("Laptop stand", new BigDecimal("120.00"), 2),
				new Service("On-site installation", new BigDecimal("300.00")),
				new Discount("Loyalty 10%", new BigDecimal("54.00")),
				new Shipping("Express courier", new BigDecimal("35.00")),
				new Deposit("Advance payment", new BigDecimal("100.00")));

		var tax = new TaxVisitor();
		BigDecimal total = BigDecimal.ZERO;
		for (LineItem line : invoice) {
			total = total.add(line.accept(tax));
		}
		System.out.println("tax total : " + total.setScale(2, RoundingMode.HALF_UP));

		System.out.println();
		System.out.println("ledger postings:");
		var ledger = new LedgerVisitor();
		invoice.forEach(line -> System.out.println("  " + line.accept(ledger)));

		System.out.println();
        System.out.println("-- a new operation, requested this quarter: one class, no model change --");
        var margin = new MarginContributionVisitor();
        BigDecimal contribution = BigDecimal.ZERO;
        for (LineItem line : invoice) {
            contribution = contribution.add(line.accept(margin));
        }
        System.out.println("  margin contribution: " + contribution.setScale(2, RoundingMode.HALF_UP));
	}

}

interface LineItemVisitor<R> {
	R visit(Goods goods);

	R visit(Service service);

	R visit(Discount discount);

	R visit(Shipping shipping);

	R visit(Deposit deposit);
}

interface LineItem {
	String description();

	<R> R accept(LineItemVisitor<R> visitor);
}

record Goods(String description, BigDecimal unitPrice, int quantity) implements LineItem {

	BigDecimal netAmount() {
		return unitPrice.multiply(BigDecimal.valueOf(quantity));
	}

	@Override
	public <R> R accept(LineItemVisitor<R> visitor) {
		return visitor.visit(this);
	}
}

record Service(String description, BigDecimal fee) implements LineItem {

	@Override
	public <R> R accept(LineItemVisitor<R> visitor) {
		return visitor.visit(this);
	}

}

record Discount(String description, BigDecimal amount) implements LineItem {

	@Override
	public <R> R accept(LineItemVisitor<R> visitor) {
		return visitor.visit(this);
	}
}

record Shipping(String description, BigDecimal amount) implements LineItem {

	@Override
	public <R> R accept(LineItemVisitor<R> visitor) {
		return visitor.visit(this);
	}
}

record Deposit(String description, BigDecimal amount) implements LineItem {

	@Override
	public <R> R accept(LineItemVisitor<R> visitor) {
		return visitor.visit(this);
	}
}

class TaxVisitor implements LineItemVisitor<BigDecimal> {

	private static final BigDecimal STANDARD_RATE = new BigDecimal("0.20");
	private static final BigDecimal SERVICE_RATE = new BigDecimal("0.18");

	@Override
	public BigDecimal visit(Goods goods) {
		return goods.netAmount().multiply(STANDARD_RATE);
	}

	@Override
	public BigDecimal visit(Service service) {
		return service.fee().multiply(SERVICE_RATE);
	}

	@Override
	public BigDecimal visit(Discount discount) {
		return discount.amount().multiply(STANDARD_RATE).negate();
	}

	@Override
	public BigDecimal visit(Shipping shipping) {
		return shipping.amount().multiply(STANDARD_RATE);
	}

	@Override
	public BigDecimal visit(Deposit deposit) {
		return deposit.amount().multiply(STANDARD_RATE);
	}
}

final class LedgerVisitor implements LineItemVisitor<String> {

	@Override
	public String visit(Goods goods) {
		return "600100 Revenue-Goods       " + goods.netAmount();
	}

	@Override
	public String visit(Service service) {
		return "600200 Revenue-Services    " + service.fee();
	}

	@Override
	public String visit(Discount discount) {
		return "610000 Discounts          -" + discount.amount();
	}

	@Override
	public String visit(Shipping shipping) {
		return "600300 Freight recovered   " + shipping.amount();
	}

	/** A deposit is a liability until the goods ship, not revenue. */
	@Override
	public String visit(Deposit deposit) {
		return "230000 Customer advances   " + deposit.amount() + "  (liability)";
	}
}

class MarginContributionVisitor implements LineItemVisitor<BigDecimal> {

	private static final BigDecimal GOODS_COST_RATIO = new BigDecimal("0.62");

	@Override
	public BigDecimal visit(Goods goods) {
		return goods.netAmount().multiply(BigDecimal.ONE.subtract(GOODS_COST_RATIO));
	}

	@Override
	public BigDecimal visit(Service service) {
		return service.fee(); // labour is already accounted for elsewhere
	}

	@Override
	public BigDecimal visit(Discount discount) {
		return discount.amount().negate();
	}

	@Override
	public BigDecimal visit(Shipping shipping) {
		return BigDecimal.ZERO; // recharged at cost
	}

	@Override
	public BigDecimal visit(Deposit deposit) {
		return BigDecimal.ZERO; // a payment, not a sale
	}
}