package problem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MarketDataFeedProblem {

    private static final int INSTRUMENTS = 3_000;
    private static final int QUOTES = 400_000;

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        settle(runtime);
        long before = runtime.totalMemory() - runtime.freeMemory();

        List<Quote> book = new ArrayList<>(QUOTES);
        var random = ThreadLocalRandom.current();
        for (int i = 0; i < QUOTES; i++) {
            int instrument = i % INSTRUMENTS;
            // Exactly what a wire parser does: a fresh String per field, per message.
            book.add(new Quote(
                    new String("SYM" + instrument),
                    new String("TR000000" + String.format("%04d", instrument)),
                    new String(instrument % 2 == 0 ? "XIST" : "XETR"),
                    new String(instrument % 2 == 0 ? "TRY" : "EUR"),
                    new String(sectorOf(instrument)),
                    0.01d,
                    1,
                    random.nextDouble(10, 500),
                    random.nextDouble(10, 500),
                    random.nextInt(1, 5_000),
                    random.nextInt(1, 5_000),
                    System.nanoTime()));
        }

        settle(runtime);
        long after = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("quotes held      : %,d%n", book.size());
        System.out.printf("instruments      : %,d%n", INSTRUMENTS);
        System.out.printf("retained heap    : %,d KB%n", (after - before) / 1024);
        System.out.printf("bytes per quote  : ~%d%n", (after - before) / book.size());

        Quote a = book.get(0);
        Quote b = book.get(INSTRUMENTS);            // same instrument, later tick
        System.out.println("same instrument? symbol equal = " + a.symbol().equals(b.symbol())
                + ", but the descriptions are two separate objects: " + (a.symbol() == b.symbol()));
    }

    private static String sectorOf(int instrument) {
        return switch (instrument % 4) {
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

/** Instrument description and tick data fused into one wide object, stored per tick. */
record Quote(String symbol, String isin, String venueMic, String currency, String sector,
             double tickSize, int lotSize,
             double bid, double ask, int bidSize, int askSize, long timestampNanos) {
}