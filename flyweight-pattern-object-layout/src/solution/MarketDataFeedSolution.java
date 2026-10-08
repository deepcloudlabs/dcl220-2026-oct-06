package solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
/*
quotes held      : 400,000
instruments      : 3,000
retained heap    : 105,107 KB
bytes per quote  : ~269

quotes held      : 400,000
instruments held : 3,000  (flyweights)
retained heap    : 21,870 KB
bytes per quote  : ~55
 */
public class MarketDataFeedSolution {
	private static final int INSTRUMENTS = 3_000;
    private static final int QUOTES = 2_000_000;

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        settle(runtime);
        long before = runtime.totalMemory() - runtime.freeMemory();

        var registry = new InstrumentRegistry();
        List<Quote> book = new ArrayList<>(QUOTES);
        var random = ThreadLocalRandom.current();
        for (int i = 0; i < QUOTES; i++) {
            int id = i % INSTRUMENTS;
            Instrument instrument = registry.intern(
                    new String(id % 2 == 0 ? "XIST" : "XETR"),
                    new String("SYM" + id),
                    () -> new Instrument(
                            id % 2 == 0 ? "XIST" : "XETR",
                            "SYM" + id,
                            "TR000000" + String.format("%04d", id),
                            id % 2 == 0 ? "TRY" : "EUR",
                            sectorOf(id),
                            0.01d,
                            1));
            book.add(new Quote(instrument,
                    random.nextDouble(10, 500), random.nextDouble(10, 500),
                    random.nextInt(1, 5_000), random.nextInt(1, 5_000),
                    System.nanoTime()));
        }

        settle(runtime);
        long after = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("quotes held      : %,d%n", book.size());
        System.out.printf("instruments held : %,d  (flyweights)%n", registry.size());
        System.out.printf("retained heap    : %,d KB%n", (after - before) / 1024);
        System.out.printf("bytes per quote  : ~%d%n", (after - before) / book.size());

        Quote a = book.get(0);
        Quote b = book.get(INSTRUMENTS);
        System.out.println("same instrument? reference equality = " + (a.instrument() == b.instrument())
                + "  (one comparison, and it is correct across venues)");
    }

    private static String sectorOf(int id) {
        return switch (id % 4) {
            case 0 -> "FINANCIALS";
            case 1 -> "ENERGY";
            case 2 -> "TECHNOLOGY";
            default -> "INDUSTRIALS";
        };
    }

    private static void settle(Runtime runtime) {
        for (int i = 0; i < 3; i++) {
            System.gc();
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

record Quote(Instrument instrument,double bid, double ask, int bidSize, int askSize, long timestampNanos) {

}

record Instrument(String symbol, String isin, String venueMic, String currency, String sector,
        double tickSize, int lotSize) {
	
	Instrument {
        // Immutability is what makes sharing safe; a record gives it for these fields.
        if (venueMic == null || symbol == null) {
            throw new IllegalArgumentException("venueMic and symbol identify the flyweight");
        }
    }

    @Override
    public String toString() {
        return symbol + "@" + venueMic;
    }
}

final class InstrumentRegistry { // object pool

    private final Map<String, Instrument> pool = new ConcurrentHashMap<>();

    /**
     * @param supplier builds the instrument only if it is not already pooled, so the
     *                 hot path allocates nothing once the universe is warm.
     */
    Instrument intern(String venueMic, String symbol, java.util.function.Supplier<Instrument> supplier) {
        return pool.computeIfAbsent(key(venueMic, symbol), _ -> supplier.get());
    }

    Instrument find(String venueMic, String symbol) {
        return pool.get(key(venueMic, symbol));
    }

    int size() {
        return pool.size();
    }

    /** MIC first: the same ticker on two venues is two instruments, not one. */
    private static String key(String venueMic, String symbol) {
        return venueMic + '|' + symbol;
    }
}