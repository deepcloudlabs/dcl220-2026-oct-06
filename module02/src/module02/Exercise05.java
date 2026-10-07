package module02;

interface Machine extends ColorPrinter, Fax, CopyMachine, ScannerUSB, ScannerEmail, ScannerGoogleDrive {
}

interface BlackPrinter {
	void print();
}

interface ColorPrinter extends BlackPrinter {
	void printColor();
}

interface FaxSender {
	void sendFax();
}

interface FaxReceiver {
	void receiveFax();
}

interface Fax extends FaxSender, FaxReceiver {
}

interface CopyMachine {
	void copy();

	void copyColor();
}

interface ScannerUSB {
	void scanToUsb();
}

interface ScannerEmail {
	void scanToEmail();
}

interface ScannerGoogleDrive {
	void scanToGoogleDrive();
}

class OfficePrinter implements Machine {

	@Override
	public void print() {
		System.out.println("Printing in black...");
	}

	@Override
	public void printColor() {
		System.out.println("Printing in color...");
	}

	@Override
	public void sendFax() {
		System.out.println("Sending fax...");
	}

	@Override
	public void receiveFax() {
		System.out.println("Receiving fax...");
	}

	@Override
	public void copy() {
		System.out.println("Copying in black...");
	}

	@Override
	public void copyColor() {
		System.out.println("Copying in color...");
	}

	@Override
	public void scanToEmail() {
		System.out.println("Scanning and then send email...");
	}

	@Override
	public void scanToUsb() {
		System.out.println("Scanning and then copying to usb device...");
	}

	@Override
	public void scanToGoogleDrive() {
		System.out.println("Scanning and then copying to google drive...");
	}

}

class StandardPrinter implements ColorPrinter {

	@Override
	public void print() {
		System.out.println("Printing in black...");
	}

	@Override
	public void printColor() {
		System.out.println("Printing in color...");
	}

	
}

class SimplePrinter implements BlackPrinter {

	@Override
	public void print() {
		System.out.println("Printing in black...");
	}

}

record Document() {
}

@SuppressWarnings("unused")
public class Exercise05 {

	public static void generateReport(ColorPrinter machine) {
		Document colorfulDocument = /* generated */ new Document();
		machine.printColor(/* colorfulDocument */);
	}

	public static void main(String[] args) {
		var blackPrinter = new SimplePrinter();
		var standardPrinter = new StandardPrinter();
		generateReport(standardPrinter);
		// Error: Cannot call because blackPrinter does not implement ColorPrinter
		// generateReport(blackPrinter);

	}

}
