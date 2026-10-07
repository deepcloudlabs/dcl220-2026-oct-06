package quiz;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import quiz.external.PdfBuilder;
import quiz.external.S3Storage;
import quiz.external.SmtpMailer;

public class Exercise03 {

	public static void main(String[] args) {

	}

}

enum ReportFormat {
	CSV, PDF
}

record Sale(String product, int qty, BigDecimal amount) {
}


// Used in dev so engineers don't need AWS access
class LocalDiskStorage implements FileStorage {
	private final Path root;

	public LocalDiskStorage(Path root) {
		this.root = root;
	}

	public Path pathFor(String key) {
		return root.resolve(key);
	}

	@Override
	public String presignedUrl(String key, Duration ttl) {
		return null; // local disk has no URLs
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
}

class MonthlyReportService {

	private final DataSource dataSource;
	private final FileStorage storage;
	private final SmtpMailer mailer;

	public MonthlyReportService(DataSource dataSource) {
		this.dataSource = dataSource;
		this.storage = "prod".equals(System.getenv("APP_ENV")) ? new S3Storage("acme-reports")
				: new LocalDiskStorage(Path.of("/tmp/reports"));
		this.mailer = new SmtpMailer("smtp.acme.com", 587);
	}

	public void sendMonthlyReport(String customerId, String email, ReportFormat format, YearMonth month)
			throws SQLException, UnsupportedEncodingException {

		// 1. Load sales
		List<Sale> sales = new ArrayList<>();
		String sql = "SELECT product, qty, amount FROM sales" + " WHERE customer_id = ? AND month = ?";
		try (Connection c = dataSource.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, customerId);
			ps.setString(2, month.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sales.add(new Sale(rs.getString("product"), rs.getInt("qty"), rs.getBigDecimal("amount")));
			}
		}

		// 2. Render file
		byte[] file;
		String ext;
		switch (format) {
		case CSV -> {
			StringBuilder sb = new StringBuilder("product,qty,amount\n");
			for (Sale s : sales) {
				sb.append(s.product()).append(',').append(s.qty()).append(',').append(s.amount()).append('\n');
			}
			file = sb.toString().getBytes("UTF_8");
			ext = "csv";
		}
		case PDF -> {
			file = new PdfBuilder().table(sales).build();
			ext = "pdf";
		}
		default -> throw new IllegalArgumentException("Unsupported: " + format);
		}

		// 3. Upload and get a link
		String key = customerId + "/" + month + "." + ext;
		storage.upload(key, file);

		String link;
		if (storage instanceof LocalDiskStorage local) {
			link = local.pathFor(key).toUri().toString();
		} else {
			link = storage.presignedUrl(key, Duration.ofDays(7));
		}

		// 4. Email
		String html = "<h1>Your " + month + " report</h1>" + "<a href=\"" + link + "\">Download</a>";
		mailer.send(email, "Your monthly report", html);
	}
}