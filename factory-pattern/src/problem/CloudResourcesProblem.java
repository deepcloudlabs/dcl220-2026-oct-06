package problem;

public class CloudResourcesProblem {

    public static void main(String[] args) {
        var service = new ArchiveService();

        System.out.println("-- correctly configured --");
        service.archive("aws", "DOC-1", "contract bytes");

        System.out.println();
        System.out.println("-- and this is what nothing prevents --");
        var mixed = new ArchiveService();
        mixed.archiveMixed("DOC-2", "contract bytes");
    }
}

// ---------------------------------------------------------------- AWS family
final class S3BlobStore {
    String put(String key, String content) {
        System.out.println("    [s3]          put " + key);
        return "s3://archive-bucket/" + key;
    }
}

final class SqsQueue {
    void publish(String uri) {
        if (!uri.startsWith("s3://")) {
            throw new IllegalArgumentException("SQS consumer cannot resolve " + uri);
        }
        System.out.println("    [sqs]         published " + uri);
    }
}

final class AwsSecretsManager {
    String signingKey() {
        System.out.println("    [secrets/aws] read archive-signing-key");
        return "aws-key-01";
    }
}

// ---------------------------------------------------------------- Azure family
final class AzureBlobStore {
    String put(String key, String content) {
        System.out.println("    [azblob]      put " + key);
        return "https://archive.blob.core.windows.net/" + key;
    }
}

final class ServiceBusQueue {
    void publish(String uri) {
        if (!uri.startsWith("https://")) {
            throw new IllegalArgumentException("Service Bus consumer cannot resolve " + uri);
        }
        System.out.println("    [servicebus]  published " + uri);
    }
}

final class AzureKeyVault {
    String signingKey() {
        System.out.println("    [keyvault]    read archive-signing-key");
        return "az-key-01";
    }
}

// ---------------------------------------------------------------- the service
final class ArchiveService {

    void archive(String provider, String documentId, String content) {
        // if/else #1: the blob store
        String uri;
        if ("aws".equals(provider)) {
            uri = new S3BlobStore().put(documentId, content);
        } else {
            uri = new AzureBlobStore().put(documentId, content);
        }

        // if/else #2: the secret store - a different chain, in a different method
        // shape, that could easily disagree with the one above
        String key;
        if ("aws".equals(provider)) {
            key = new AwsSecretsManager().signingKey();
        } else {
            key = new AzureKeyVault().signingKey();
        }

        // if/else #3: the queue
        if ("aws".equals(provider)) {
            new SqsQueue().publish(uri);
        } else {
            new ServiceBusQueue().publish(uri);
        }

        System.out.println("  archived " + documentId + " signed with " + key);
    }

    /** Nobody wrote this on purpose; three independent chains simply drifted apart. */
    void archiveMixed(String documentId, String content) {
        String uri = new S3BlobStore().put(documentId, content);
        String _ = new AzureKeyVault().signingKey();
        try {
            new ServiceBusQueue().publish(uri);
        } catch (IllegalArgumentException e) {
            System.out.println("  broken family: " + e.getMessage());
            System.out.println("  the document is stored but can never be acknowledged");
        }
    }
}
