package solution;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * DCL-220 - Module 4: Responsibility Patterns - OBSERVER (solution)
 *
 * InventoryService now knows one thing about the outside world: that something may be
 * listening. It publishes StockLevelChanged and moves on.
 *
 * Three decisions carry most of the value:
 *
 *  1. The event is an immutable value carrying BEFORE and AFTER. Subscribers derive
 *     their own conclusions ("did it cross the reorder point?", "did it hit zero?")
 *     instead of the publisher deciding for them. That is why adding the webhook
 *     subscriber needed no change to the event or the service.
 *
 *  2. Notification is isolated. A subscriber that throws is logged and skipped; the
 *     stock update and the other subscribers are unaffected. The publisher decides the
 *     failure policy once, in one place.
 *
 *  3. The listener list is a CopyOnWriteArrayList. Subscribers are registered rarely
 *     and notified constantly, and a listener may unsubscribe itself while being
 *     notified - which would be a ConcurrentModificationException with an ArrayList.
 *
 * Note on ordering: subscribers must not depend on each other's order. If two reactions
 * genuinely must happen in sequence, that is one subscriber containing both steps, not
 * two subscribers and a comment.
 */
public class InventoryEventsSolution {

    public static void main(String[] args) {
        var service = new InventoryService();
        service.subscribe(new ReorderSubscriber(Map.of("SKU-1001", 5)));
        service.subscribe(new SearchIndexSubscriber());
        service.subscribe(new AuditSubscriber());
        service.subscribe(new MerchandisingSubscriber());

        service.receiveStock("SKU-1001", 40);
        System.out.println();
        service.reserve("SKU-1001", 38);
        System.out.println();

        // Added later. The service is not touched, not recompiled, not retested.
        service.subscribe(new BackInStockWebhookSubscriber());
        service.receiveStock("SKU-1001", 10);
        System.out.println();

        System.out.println("now with a failing subscriber:");
        service.subscribe(new BrokenSubscriber());
        service.reserve("SKU-1001", 12);
        System.out.println("  stock update completed anyway");
    }
}

// ---------------------------------------------------------------- event and listener
record StockLevelChanged(String sku, int before, int after, StockChangeReason reason, Instant occurredAt) {

    boolean crossedDownTo(int threshold) {
        return before > threshold && after <= threshold;
    }

    boolean becameAvailable() {
        return before == 0 && after > 0;
    }

    boolean becameUnavailable() {
        return before > 0 && after == 0;
    }
}

enum StockChangeReason { RECEIPT, RESERVATION }

@FunctionalInterface
interface InventoryListener {

    void onStockLevelChanged(StockLevelChanged event);

    default String name() {
        return getClass().getSimpleName();
    }
}

// ---------------------------------------------------------------- the subject
final class InventoryService {

    private final Map<String, Integer> onHand = new HashMap<>();
    private final List<InventoryListener> listeners = new CopyOnWriteArrayList<>();

    void subscribe(InventoryListener listener) {
        listeners.add(listener);
    }

    void unsubscribe(InventoryListener listener) {
        listeners.remove(listener);
    }

    void receiveStock(String sku, int quantity) {
        int before = onHand.getOrDefault(sku, 0);
        int after = before + quantity;
        onHand.put(sku, after);
        System.out.println("received " + quantity + " of " + sku + " (" + before + " -> " + after + ")");
        publish(new StockLevelChanged(sku, before, after, StockChangeReason.RECEIPT, Instant.EPOCH));
    }

    void reserve(String sku, int quantity) {
        int before = onHand.getOrDefault(sku, 0);
        if (before < quantity) {
            throw new IllegalStateException("insufficient stock for " + sku);
        }
        int after = before - quantity;
        onHand.put(sku, after);
        System.out.println("reserved " + quantity + " of " + sku + " (" + before + " -> " + after + ")");
        publish(new StockLevelChanged(sku, before, after, StockChangeReason.RESERVATION, Instant.EPOCH));
    }

    /** One subscriber's failure is that subscriber's problem. */
    private void publish(StockLevelChanged event) {
        for (InventoryListener listener : listeners) {
            try {
                listener.onStockLevelChanged(event);
            } catch (RuntimeException e) {
                System.out.println("  [!] subscriber " + listener.name() + " failed: " + e.getMessage());
            }
        }
    }
}

// ---------------------------------------------------------------- subscribers
final class ReorderSubscriber implements InventoryListener {

    private final Map<String, Integer> reorderPoints;

    ReorderSubscriber(Map<String, Integer> reorderPoints) {
        this.reorderPoints = Map.copyOf(reorderPoints);
    }

    @Override
    public void onStockLevelChanged(StockLevelChanged event) {
        int point = reorderPoints.getOrDefault(event.sku(), 0);
        if (event.crossedDownTo(point)) {
            System.out.println("  [reorder]   requisition for 100 x " + event.sku());
        }
    }
}

final class SearchIndexSubscriber implements InventoryListener {

    /** Fires on every change, so the "reserve forgot to reindex" bug cannot recur. */
    @Override
    public void onStockLevelChanged(StockLevelChanged event) {
        System.out.println("  [search]    reindex " + event.sku() + " available=" + event.after());
    }
}

final class AuditSubscriber implements InventoryListener {

    @Override
    public void onStockLevelChanged(StockLevelChanged event) {
        System.out.println("  [audit]     " + event.occurredAt() + " " + event.sku() + " "
                + event.before() + "->" + event.after() + " " + event.reason());
    }
}

final class MerchandisingSubscriber implements InventoryListener {

    @Override
    public void onStockLevelChanged(StockLevelChanged event) {
        if (event.becameUnavailable()) {
            System.out.println("  [notify]    out of stock: " + event.sku());
        } else if (event.becameAvailable()) {
            System.out.println("  [notify]    back in stock: " + event.sku() + " (" + event.after() + ")");
        }
    }
}

/** The fifth reaction, added without opening InventoryService. */
final class BackInStockWebhookSubscriber implements InventoryListener {

    @Override
    public void onStockLevelChanged(StockLevelChanged event) {
        if (event.becameAvailable()) {
            System.out.println("  [webhook]   POST /back-in-stock " + event.sku());
        }
    }
}

final class BrokenSubscriber implements InventoryListener {

    @Override
    public void onStockLevelChanged(StockLevelChanged event) {
        throw new RuntimeException("SMTP connect timeout");
    }
}