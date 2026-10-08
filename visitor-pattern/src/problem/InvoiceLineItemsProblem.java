package problem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class InvoiceLineItemsProblem {

    public static void main(String[] args) {
        List<Object> invoice = List.of(
                new Goods("Laptop stand", new BigDecimal("120.00"), 2),
                new Service("On-site installation", new BigDecimal("300.00")),
                new Discount("Loyalty 10%", new BigDecimal("54.00")),
                new Shipping("Express courier", new BigDecimal("35.00")),
                new Deposit("Advance payment", new BigDecimal("100.00")));

        System.out.println("tax total    : " + new TaxCalculator().totalTax(invoice));
        System.out.println("  correct    : 118.20   (VAT is due on an advance payment)");
        System.out.println();
        System.out.println("ledger postings:");
        new LedgerPoster().post(invoice);
        System.out.println("  the deposit never reached the ledger: no branch matched it");
    }
}

// ---------------------------------------------------------------- the model
record Goods(String description, BigDecimal unitPrice, int quantity) {
}

record Service(String description, BigDecimal fee) {
}

record Discount(String description, BigDecimal amount) {
}

record Shipping(String description, BigDecimal amount) {
}

/** Added last. Three of the four instanceof chains silently ignore it. */
record Deposit(String description, BigDecimal amount) {
}

// ---------------------------------------------------------------- operation #1
final class TaxCalculator {

    private static final BigDecimal STANDARD_RATE = new BigDecimal("0.20");
    private static final BigDecimal SERVICE_RATE = new BigDecimal("0.18");

    BigDecimal totalTax(List<Object> lines) {
        BigDecimal total = BigDecimal.ZERO;
        for (Object line : lines) {
            total = total.add(taxOf(line));
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal taxOf(Object line) {
        if (line instanceof Goods goods) {
            return goods.unitPrice().multiply(BigDecimal.valueOf(goods.quantity())).multiply(STANDARD_RATE);
        }
        if (line instanceof Service service) {
            return service.fee().multiply(SERVICE_RATE);
        }
        if (line instanceof Discount discount) {
            return discount.amount().multiply(STANDARD_RATE).negate();
        }
        if (line instanceof Shipping shipping) {
            return shipping.amount().multiply(STANDARD_RATE);
        }
        // Deposit falls through here and is taxed at zero. VAT is in fact due on an
        // advance payment, so every invoice carrying a deposit under-declares tax.
        return BigDecimal.ZERO;
    }
}

// ---------------------------------------------------------------- operation #2
final class LedgerPoster {

    void post(List<Object> lines) {
        for (Object line : lines) {
            if (line instanceof Goods goods) {
                System.out.println("  600100 Revenue-Goods    "
                        + goods.unitPrice().multiply(BigDecimal.valueOf(goods.quantity())));
            } else if (line instanceof Service service) {
                System.out.println("  600200 Revenue-Services  " + service.fee());
            } else if (line instanceof Discount discount) {
                System.out.println("  610000 Discounts        -" + discount.amount());
            } else if (line instanceof Shipping shipping) {
                System.out.println("  600300 Freight recovered " + shipping.amount());
            }
            // no branch for Deposit: the line is silently dropped
        }
    }
}
