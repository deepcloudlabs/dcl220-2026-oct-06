package com.example.cm.document;

import java.util.Objects;

public class PhoneDocument {
	private String phone;
	private boolean primaryPhone;

	public PhoneDocument() {
	}

	public PhoneDocument(String phone, boolean primaryPhone) {
		this.phone = phone;
		this.primaryPhone = primaryPhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(boolean primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhoneDocument other = (PhoneDocument) obj;
		return Objects.equals(phone, other.phone);
	}

	@Override
	public String toString() {
		return "PhoneDocument [phone=" + phone + ", primaryPhone=" + primaryPhone + "]";
	}

}
