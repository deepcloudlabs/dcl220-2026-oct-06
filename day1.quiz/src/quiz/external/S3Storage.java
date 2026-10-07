package quiz.external;

import java.time.Duration;
import java.util.List;

import quiz.FileStorage;

public class S3Storage implements FileStorage {

	public S3Storage(String name) {
	}

	@Override
	public void upload(String key, byte[] data) {
	}

	@Override
	public byte[] download(String key) {
		return null;
	}

	@Override
	public void delete(String key) {
	}

	@Override
	public List<String> list(String prefix) {
		return null;
	}

	@Override
	public String presignedUrl(String key, Duration ttl) {
		return null;
	}

}
