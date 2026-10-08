package solution;

import java.util.List;
import java.util.Map;

public class CloudResourcesSolution {
	public static void main(String[] args) {
		var cloudPlatforms = Map.of(
			"aws", new AwsPlatform(),
			"azure", new AzurePlatform(),
			"gcp", new GoogleCloudPlatform(),
			"on-premise", new OnPremiseCloudPlatform()
		);
		for (var region : List.of("aws", "azure", "gcp", "on-premise")) {
			var cloudPlatform = cloudPlatforms.get(region);
			var service = new ArchiveService(cloudPlatform);
			service.archive(region, "doc-42", "secret document content");
		}
	}
}

interface BlobStore {
	String put(String key, String content);
}

interface MessageQueue {
	void publish(String uri);
}

interface SecretStore {
	String signKey();
}

interface CloudPlatform {
	String name();

	BlobStore blobStore();

	MessageQueue messageQueue();

	SecretStore secretStore();
}

final class AwsPlatform implements CloudPlatform {
	@Override
	public String name() {
		return "aws";
	}

	@Override
	public BlobStore blobStore() {
		return (key, _) -> {
			System.out.println("    [s3]          put " + key);
			return "s3://archive-bucket/" + key;
		};
	}

	@Override
	public MessageQueue messageQueue() {
		return uri -> {
			if (!uri.startsWith("s3://")) {
				throw new IllegalArgumentException("SQS consumer cannot resolve " + uri);
			}
			System.out.println("    [sqs]         published " + uri);
		};
	}

	@Override
	public SecretStore secretStore() {
		return () -> {
			System.out.println("    [secrets/aws] read archive-signing-key");
			return "aws-key-01";
		};
	}
}

final class AzurePlatform implements CloudPlatform {

	@Override
	public String name() {
		return "azure";
	}

	@Override
	public BlobStore blobStore() {
		return (key, _) -> {
			System.out.println("    [azblob]      put " + key);
			return "https://archive.blob.core.windows.net/" + key;
		};
	}

	@Override
	public MessageQueue messageQueue() {
		return uri -> {
			if (!uri.startsWith("https://")) {
				throw new IllegalArgumentException("Service Bus consumer cannot resolve " + uri);
			}
			System.out.println("    [servicebus]  published " + uri);
		};
	}

	@Override
	public SecretStore secretStore() {
		return () -> {
			System.out.println("    [keyvault]    read archive-signing-key");
			return "az-key-01";
		};
	}
}

final class GoogleCloudPlatform implements CloudPlatform {

	@Override
	public String name() {
		return "gcp";
	}

	@Override
	public BlobStore blobStore() {
		return (key, _) -> {
			System.out.println("    [azblob]      put " + key);
			return "https://archive.blob.core.google.net/" + key;
		};
	}

	@Override
	public MessageQueue messageQueue() {
		return uri -> {
			if (!uri.startsWith("https://")) {
				throw new IllegalArgumentException("Service Bus consumer cannot resolve " + uri);
			}
			System.out.println("    [servicebus]  published " + uri);
		};
	}

	@Override
	public SecretStore secretStore() {
		return () -> {
			System.out.println("    [keyvault]    read archive-signing-key");
			return "gcp-key-01";
		};
	}
}

final class OnPremiseCloudPlatform implements CloudPlatform {
	@Override
	public String name() {
		return "on-premise";
	}

	@Override
	public BlobStore blobStore() {
		return (key, _) -> {
			System.out.println("    [mem]         put " + key);
			return "mem://" + key;
		};
	}

	@Override
	public MessageQueue messageQueue() {
		return uri -> System.out.println("    [mem-queue]   published " + uri);
	}

	@Override
	public SecretStore secretStore() {
		return () -> "test-key";
	}
}

//---------------------------------------------------------------- the service
@SuppressWarnings("unused")
final class ArchiveService {
	private final BlobStore blobStore;
	private final MessageQueue messageQueue;
	private final SecretStore secretStore;
	private final String platformName;

	public ArchiveService(CloudPlatform cloudPlatform) {
		this.blobStore = cloudPlatform.blobStore();
		this.messageQueue = cloudPlatform.messageQueue();
		this.secretStore = cloudPlatform.secretStore();
		this.platformName = cloudPlatform.name();
	}

	void archive(String provider, String documentId, String content) {
		String uri;
		uri = blobStore.put(documentId, content);

		String key;
		key = secretStore.signKey();

		messageQueue.publish(uri);

		System.out.println("  archived " + documentId + " signed with " + key);
	}

}
