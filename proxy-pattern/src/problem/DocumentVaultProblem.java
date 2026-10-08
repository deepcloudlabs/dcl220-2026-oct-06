package problem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * DCL-220 - Module 4: Responsibility Patterns - PROXY (problem)
 *
 * Scenario
 * --------
 * A document management system stores scanned contracts in object storage. A scan is
 * typically 8-40 MB. Two facts drive the design:
 *   - a search result page shows 50 documents but opens none of them
 *   - a document may only be opened by a user whose clearance meets its classification
 *
 * Symptoms
 * --------
 *  - ScannedDocument downloads the blob in its constructor. Rendering one page of
 *    search results therefore transfers ~1 GB and takes tens of seconds, to display
 *    titles. Memory follows the same curve.
 *  - The clearance check lives in the callers. DocumentApiController performs it,
 *    ExportJob performs a slightly different version of it, and PrintSpooler does not
 *    perform it at all - which is how RESTRICTED contracts reached an office printer.
 *  - Caching, audit and access control cannot be added to the document itself without
 *    editing ScannedDocument, which is generated from the storage schema.
 *
 */
public class DocumentVaultProblem {

    public static void main(String[] args) {
        System.out.println("-- rendering a search result page (titles only) --");
        ObjectStorage.bytesTransferred.set(0);
        List<ScannedDocument> page = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            page.add(new ScannedDocument("DOC-" + i, "Supply agreement " + i,
                    i == 3 ? Classification.RESTRICTED : Classification.INTERNAL, 8_000_000));
        }
        for (ScannedDocument document : page) {
            System.out.println("  " + document.getId() + "  " + document.getTitle());
        }
        System.out.println("  transferred " + ObjectStorage.bytesTransferred.get()
                + " bytes to print " + page.size() + " titles");

        System.out.println();
        System.out.println("-- three callers, three different opinions about clearance --");
        var restricted = page.get(2);
        var clerk = new User("clerk-7", Classification.INTERNAL);

        new DocumentApiController().download(restricted, clerk);
        new ExportJob().export(restricted, clerk);
        new PrintSpooler().print(restricted, clerk);
    }
}

enum Classification {
    PUBLIC(0), INTERNAL(1), CONFIDENTIAL(2), RESTRICTED(3);

    private final int level;

    Classification(int level) {
        this.level = level;
    }

    int level() {
        return level;
    }
}

record User(String id, Classification clearance) {
}

final class ObjectStorage {

    static final AtomicLong bytesTransferred = new AtomicLong();

    private ObjectStorage() {
    }

    static byte[] fetch(String key, int sizeBytes) {
        bytesTransferred.addAndGet(sizeBytes);
        System.out.println("    [storage] GET " + key + " (" + sizeBytes + " bytes)");
        return new byte[Math.min(sizeBytes, 16)];   // a stand-in for the real blob
    }
}

/** Generated from the storage schema. Downloads eagerly and knows nothing about users. */
final class ScannedDocument {

    private final String id;
    private final String title;
    private final Classification classification;
    private final byte[] content;

    ScannedDocument(String id, String title, Classification classification, int sizeBytes) {
        this.id = id;
        this.title = title;
        this.classification = classification;
        this.content = ObjectStorage.fetch(id, sizeBytes);   // paid for whether read or not
    }

    String getId() { return id; }
    String getTitle() { return title; }
    Classification getClassification() { return classification; }
    byte[] getContent() { return content; }
}

// ---------------------------------------------------------------- three callers
final class DocumentApiController {

    void download(ScannedDocument document, User user) {
        if (user.clearance().level() < document.getClassification().level()) {
            System.out.println("  [api]     denied for " + user.id());
            return;
        }
        System.out.println("  [api]     served " + document.getId());
    }
}

final class ExportJob {

    void export(ScannedDocument document, User user) {
        // Subtly different: uses != instead of a level comparison, so CONFIDENTIAL
        // documents are exported to users cleared only for INTERNAL.
        if (document.getClassification() == Classification.RESTRICTED
                && user.clearance() != Classification.RESTRICTED) {
            System.out.println("  [export]  denied for " + user.id());
            return;
        }
        System.out.println("  [export]  exported " + document.getId());
    }
}

final class PrintSpooler {

    void print(ScannedDocument document, User user) {
        // No check at all.
        System.out.println("  [print]   printed " + document.getId()
                + " (" + document.getClassification() + ") for " + user.id());
    }
}
