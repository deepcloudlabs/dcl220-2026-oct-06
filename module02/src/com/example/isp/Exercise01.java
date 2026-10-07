package com.example.isp;

interface Machine {
	void print();
	void colorPrint();
	void copy();
	void colorCopy();
	void sendFax();
	void receiveFax();
	void scanToUsb();
	void scanToGoogleDrive();
	void scanToEmail();
}
class ColorPrinter implements Machine {

	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void colorPrint() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void colorCopy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendFax() {
		throw new UnsupportedOperationException("Cannot send fax");
	}

	@Override
	public void receiveFax() {
		throw new UnsupportedOperationException("Cannot receive fax");
	}

	@Override
	public void scanToUsb() {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void scanToGoogleDrive() {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void scanToEmail() {
		throw new UnsupportedOperationException();		
	}
	
}
class OfficePrinter implements Machine {

	@Override
	public void print() {
	}

	@Override
	public void colorPrint() {
	}

	@Override
	public void copy() {
	}

	@Override
	public void colorCopy() {
	}

	@Override
	public void sendFax() {
	}

	@Override
	public void receiveFax() {
	}

	@Override
	public void scanToUsb() {
	}

	@Override
	public void scanToGoogleDrive() {
	}

	@Override
	public void scanToEmail() {
	}
	
}

public class Exercise01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
