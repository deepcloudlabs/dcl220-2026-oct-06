package solution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DocumentVaultSolution {

    public static void main(String[] args) {
        var clerk = new User("clerk-7", Classification.INTERNAL);
        var vault = new DocumentVault();

        System.out.println("-- rendering a search result page (titles only) --");
        ObjectStorage.bytesTransferred.set(0);
        List<Document> page = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            page.add(vault.open("DOC-" + i, "Supply agreement " + i,
                    i == 3 ? Classification.RESTRICTED : Classification.INTERNAL, 8_000_000, clerk));
        }
        for (Document document : page) {
            System.out.println("  " + document.id() + "  " + document.title());
        }
        System.out.println("  transferred " + ObjectStorage.bytesTransferred.get()
                + " bytes to print " + page.size() + " titles");

        System.out.println();
        System.out.println("-- opening one document actually fetches it, once --");
        Document internal = page.get(0);
        System.out.println("  read " + internal.content().length + " bytes");
        System.out.println("  read " + internal.content().length + " bytes again (no second GET)");

        System.out.println();
        System.out.println("-- every caller now hits the same rule, and cannot skip it --");
        Document restricted = page.get(2);
        new DocumentApiController().download(restricted);
        new ExportJob().export(restricted);
        new PrintSpooler().print(restricted);
    }
}

// ---------------------------------------------------------------- subject interface
interface Document {

    String id();

    String title();

    Classification classification();

    /** @throws AccessDeniedException when the caller may not read this document. */
    byte[] content();
}

@SuppressWarnings("serial")
class AccessDeniedException extends RuntimeException {
    AccessDeniedException(String message) {
        super(message);
    }
}

// ---------------------------------------------------------------- real subject
final class StoredDocument implements Document {

    private final String id;
    private final String title;
    private final Classification classification;
    private final int sizeBytes;

    StoredDocument(String id, String title, Classification classification, int sizeBytes) {
        this.id = id;
        this.title = title;
        this.classification = classification;
        this.sizeBytes = sizeBytes;
    }

    @Override
    public String id() { return id; }

    @Override
    public String title() { return title; }

    @Override
    public Classification classification() { return classification; }

    @Override
    public byte[] content() {
        return ObjectStorage.fetch(id, sizeBytes);
    }
}

// ---------------------------------------------------------------- virtual proxy
final class LazyDocument implements Document {

    private final Document delegate;
    private byte[] cached;

    LazyDocument(Document delegate) {
        this.delegate = delegate;
    }

    // Metadata is answered without any I/O: this is what makes listings cheap.
    @Override
    public String id() { return delegate.id(); }

    @Override
    public String title() { return delegate.title(); }

    @Override
    public Classification classification() { return delegate.classification(); }

    @Override
    public byte[] content() {
        if (cached == null) {
            cached = delegate.content();
        }
        return cached;
    }
}

// ---------------------------------------------------------------- protection proxy
final class AccessControlledDocument implements Document {

    private final Document delegate;
    private final User user;

    AccessControlledDocument(Document delegate, User user) {
        this.delegate = delegate;
        this.user = user;
    }

    @Override
    public String id() { return delegate.id(); }

    @Override
    public String title() { return delegate.title(); }

    @Override
    public Classification classification() { return delegate.classification(); }

    /** The one and only clearance rule in the system. */
    @Override
    public byte[] content() {
        if (user.clearance().level() < delegate.classification().level()) {
            throw new AccessDeniedException(user.id() + " is not cleared for "
                    + delegate.classification() + " document " + delegate.id());
        }
        return delegate.content();
    }
}

// ---------------------------------------------------------------- the only way in
final class DocumentVault {

    /** Callers never see a StoredDocument; they always get it wrapped. */
    Document open(String id, String title, Classification classification, int sizeBytes, User user) {
        return new AccessControlledDocument(
                new LazyDocument(new StoredDocument(id, title, classification, sizeBytes)), user);
    }
}

// ---------------------------------------------------------------- callers: no checks left
final class DocumentApiController {

    void download(Document document) {
        try {
            document.content();
            System.out.println("  [api]     served " + document.id());
        } catch (AccessDeniedException e) {
            System.out.println("  [api]     denied: " + e.getMessage());
        }
    }
}

final class ExportJob {

    void export(Document document) {
        try {
            document.content();
            System.out.println("  [export]  exported " + document.id());
        } catch (AccessDeniedException e) {
            System.out.println("  [export]  denied: " + e.getMessage());
        }
    }
}

final class PrintSpooler {

    /** Still has no security code, and is now correct anyway. */
    void print(Document document) {
        try {
            document.content();
            System.out.println("  [print]   printed " + document.id());
        } catch (AccessDeniedException e) {
            System.out.println("  [print]   denied: " + e.getMessage());
        }
    }
}

// ---------------------------------------------------------------- supporting types
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
        return new byte[Math.min(sizeBytes, 16)];
    }
}
