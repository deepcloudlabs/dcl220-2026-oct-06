package quiz;

import java.time.Duration;
import java.util.List;

public interface FileStorage {
	void upload(String key, byte[] data);

	byte[] download(String key);

	void delete(String key);

	List<String> list(String prefix);

	String presignedUrl(String key, Duration ttl);
}
