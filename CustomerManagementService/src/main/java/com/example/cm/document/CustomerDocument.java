package com.example.cm.document;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.example.cm.domain.Customer;
import com.example.cm.domain.CustomerVerified;

@Document(collection = "customers")
public class CustomerDocument {
	@Id
	private String customerId;
	@Field("fname")
	private String firstName;
	@Field("lname")
	private String lastName;
	private CustomerVerified verified;
	@Field("profile")
	private CustomerProfileDocument customerProfile;
	@Field("contact")
	private CustomerContactDocument customerContact;

	public CustomerDocument() {
	}

	public CustomerDocument(String customerId, String firstName, String lastName, CustomerVerified verified,
			CustomerProfileDocument customerProfile, CustomerContactDocument customerContact) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.verified = verified;
		this.customerProfile = customerProfile;
		this.customerContact = customerContact;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public CustomerVerified getVerified() {
		return verified;
	}

	public void setVerified(CustomerVerified verified) {
		this.verified = verified;
	}

	public CustomerProfileDocument getCustomerProfile() {
		return customerProfile;
	}

	public void setCustomerProfile(CustomerProfileDocument customerProfile) {
		this.customerProfile = customerProfile;
	}

	public CustomerContactDocument getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(CustomerContactDocument customerContact) {
		this.customerContact = customerContact;
	}

	public Customer toCustomer() {
		return new Customer.Builder()
				.customerId(this.customerId)
				.fullName(firstName, lastName)
				.verified(this.verified)
				.profile(CustomerProfileDocument.toCustomerProfile(this.customerProfile))
				.contact(CustomerContactDocument.toCustomerContact(this.customerContact))
				.build();
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerDocument other = (CustomerDocument) obj;
		return Objects.equals(customerId, other.customerId);
	}

	@Override
	public String toString() {
		return "CustomerDocument [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", verified=" + verified + ", customerProfile=" + customerProfile + ", customerContact="
				+ customerContact + "]";
	}

}
