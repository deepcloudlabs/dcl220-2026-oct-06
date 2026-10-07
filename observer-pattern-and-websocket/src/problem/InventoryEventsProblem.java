package problem;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * DCL-220 - Module 4: Responsibility Patterns - OBSERVER (problem)
 *
 * Scenario
 * --------
 * A warehouse system adjusts stock levels. Whenever a level changes, several unrelated
 * parts of the business must react:
 *   - reordering raises a purchase requisition when stock falls below the reorder point
 *   - the search index must be refreshed so out-of-stock items stop being sold
 *   - an immutable audit record must be written for the auditors
 *   - the merchandising team wants a notification when an item goes out of stock
 *
 * Symptoms
 * --------
 *  - InventoryService imports and calls all four collaborators. It is now the class
 *    with the most reasons to change in the whole codebase.
 *  - The reactions run inline and in a fixed order, so a failure in one aborts the
 *    rest. When the notifier's SMTP host was unreachable, no audit rows were written
 *    for two hours - the least important consumer took down the most important one.
 *  - Marketing now wants a fifth reaction (push a "back in stock" webhook). That is
 *    another constructor parameter and another call in two methods.
 *  - The service cannot be unit tested without stubbing four collaborators, even for
 *    a test that only cares about arithmetic.
 *  - reserve() forgot to refresh the search index. Sold-out items stayed purchasable.
 *
 */
public class InventoryEventsProblem {

    public static void main(String[] args) {
        var service = new InventoryService(
                new ReorderService(), new SearchIndexer(), new AuditLog(), new MerchandisingNotifier());

        service.receiveStock("SKU-1001", 40);
        System.out.println();
        service.reserve("SKU-1001", 38);      // crosses the reorder point AND hits zero
        System.out.println();
        System.out.println("now with a failing notifier:");
        var brittle = new InventoryService(
                new ReorderService(), new SearchIndexer(), new AuditLog(), new BrokenNotifier());
        try {
            brittle.reserve("SKU-1001", 2);
        } catch (RuntimeException e) {
            System.out.println("  stock update aborted by an unrelated consumer: " + e.getMessage());
        }
    }
}

// ---------------------------------------------------------------- the coupled service
final class InventoryService {

    private final Map<String, Integer> onHand = new HashMap<>();
    private final Map<String, Integer> reorderPoint = new HashMap<>(Map.of("SKU-1001", 5));

    private final ReorderService reorder;
    private final SearchIndexer searchIndexer;
    private final AuditLog auditLog;
    private final MerchandisingNotifier notifier;

    InventoryService(ReorderService reorder, SearchIndexer searchIndexer,
                     AuditLog auditLog, MerchandisingNotifier notifier) {
        this.reorder = reorder;
        this.searchIndexer = searchIndexer;
        this.auditLog = auditLog;
        this.notifier = notifier;
    }

    void receiveStock(String sku, int quantity) {
        int before = onHand.getOrDefault(sku, 0);
        int after = before + quantity;
        onHand.put(sku, after);
        System.out.println("received " + quantity + " of " + sku + " (" + before + " -> " + after + ")");

        auditLog.record(sku, before, after, "RECEIPT");
        searchIndexer.reindex(sku, after);
        if (before == 0 && after > 0) {
            notifier.backInStock(sku, after);
        }
    }

    void reserve(String sku, int quantity) {
        int before = onHand.getOrDefault(sku, 0);
        if (before < quantity) {
            throw new IllegalStateException("insufficient stock for " + sku);
        }
        int after = before - quantity;
        onHand.put(sku, after);
        System.out.println("reserved " + quantity + " of " + sku + " (" + before + " -> " + after + ")");

        // The order below is load-bearing and undocumented, and one consumer is missing.
        notifier.outOfStock(sku);                                  // fires even when after > 0? no...
        if (after <= reorderPoint.getOrDefault(sku, 0)) {
            reorder.raiseRequisition(sku, 100);
        }
        auditLog.record(sku, before, after, "RESERVATION");
        // BUG: searchIndexer.reindex(...) was never called here
    }
}

// ---------------------------------------------------------------- the four collaborators
final class ReorderService {
    void raiseRequisition(String sku, int quantity) {
        System.out.println("  [reorder]   requisition for " + quantity + " x " + sku);
    }
}

final class SearchIndexer {
    void reindex(String sku, int available) {
        System.out.println("  [search]    reindex " + sku + " available=" + available);
    }
}

final class AuditLog {
    void record(String sku, int before, int after, String reason) {
        System.out.println("  [audit]     " + Instant.EPOCH + " " + sku + " " + before + "->" + after + " " + reason);
    }
}

class MerchandisingNotifier {
    void outOfStock(String sku) {
        System.out.println("  [notify]    out of stock: " + sku);
    }

    void backInStock(String sku, int available) {
        System.out.println("  [notify]    back in stock: " + sku + " (" + available + ")");
    }
}

/** The SMTP host is down. Watch what it takes down with it. */
final class BrokenNotifier extends MerchandisingNotifier {
    @Override
    void outOfStock(String sku) {
        throw new RuntimeException("SMTP connect timeout");
    }
}