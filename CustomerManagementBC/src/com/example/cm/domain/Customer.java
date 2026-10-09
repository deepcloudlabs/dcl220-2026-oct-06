package com.example.cm.domain;

import java.time.Instant;
import java.util.Objects;

import com.example.cm.domain.exception.DomainInvariantViolation;
import com.example.ddd.helper.Entity;

// Class, Object, Inheritance, OO Design
// Ubiquitous Language -> Bounded Context
// Customer, CustomerID, FullName, BirthYear, Email, AddressList, PhoneList,... 
// Entity: 1) persistence ii) identity
@Entity(identity = "customerId", aggregate = true)
public class Customer extends DomainEntity<CustomerID> {
	private FullName fullName;
	private CustomerVerified verified;
	private CustomerProfile customerProfile;
	private CustomerContact customerContact;

	private Customer(Builder builder) {
		super(builder.customerId);

		this.fullName = Objects.requireNonNull(builder.fullName, "fullName cannot be null");
		this.customerProfile = Objects.requireNonNull(builder.customerProfile, "customerProfile cannot be null");
		this.customerContact = Objects.requireNonNull(builder.customerContact, "customerContact cannot be null");
		this.verified = builder.verified;
		enforceInvariants();
	}

	public FullName getFullName() {
		return fullName;
	}

	public void setFullName(FullName fullName) {
		this.fullName = fullName;
	}

	public CustomerVerified getVerified() {
		return verified;
	}

	public void setVerified(CustomerVerified verified) {
		this.verified = verified;
	}

	public CustomerProfile getCustomerProfile() {
		return customerProfile;
	}

	public void setCustomerProfile(CustomerProfile customerProfile) {
		this.customerProfile = customerProfile;
	}

	public CustomerContact getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(CustomerContact customerContact) {
		this.customerContact = customerContact;
	}

	public static Builder builder() {
		return new Builder();
	}

	public CustomerID customerId() {
		return this.getIdentity();
	}

	public FullName fullName() {
		return fullName;
	}

	public CustomerVerified verified() {
		return verified;
	}

	public CustomerProfile customerProfile() {
		return customerProfile;
	}

	public CustomerContact customerContact() {
		return customerContact;
	}

	public boolean isVerified() {
		return verified == CustomerVerified.VERIFIED;
	}

	public void changeFullName(FullName fullName) {
		this.fullName = Objects.requireNonNull(fullName, "fullName cannot be null");
		enforceInvariants();
	}

	public void changeEmail(Email email) {
		this.customerContact.changeEmail(email);
		this.verified = CustomerVerified.UNVERIFIED;
		enforceInvariants();
	}

	public void addAddress(Address address) {
		this.customerContact.addAddress(address);
		enforceInvariants();
	}

	public void removeAddress(Address address) {
		this.customerContact.removeAddress(address);
		enforceInvariants();
	}

	public void addPhone(Phone phone) {
		this.customerContact.addPhone(phone);
		enforceInvariants();
	}

	public void removePhone(Phone phone) {
		this.customerContact.removePhone(phone);
		enforceInvariants();
	}

	public void makePrimaryPhone(Phone phone) {
		this.customerContact.makePrimaryPhone(phone);
		enforceInvariants();
	}

	public void verify(Instant verifiedAt) {
		if (!customerContact.hasEmail()) {
			throw new DomainInvariantViolation(10, "Customer cannot be verified without an email.");
		}

		if (!customerContact.hasAtLeastOneContactChannel()) {
			throw new DomainInvariantViolation(20, "Customer cannot be verified without a contact channel.");
		}
		this.verified = CustomerVerified.VERIFIED;

		enforceInvariants();
	}

	public void unverify() {
		this.verified = CustomerVerified.UNVERIFIED;
		enforceInvariants();
	}

	private void enforceInvariants() {
		if (this.customerId() == null) {
			throw new DomainInvariantViolation(30, "Customer identity is required.");
		}

		if (fullName == null) {
			throw new DomainInvariantViolation(40, "Customer full name is required.");
		}

		if (customerProfile == null) {
			throw new DomainInvariantViolation(50, "Customer profile is required.");
		}

		if (customerContact == null) {
			throw new DomainInvariantViolation(60, "Customer contact is required.");
		}

		if (verified == null) {
			throw new DomainInvariantViolation(70, "Customer verification status is required.");
		}

		if (verified == CustomerVerified.UNVERIFIED && !customerContact.hasEmail()) {
			throw new DomainInvariantViolation(80, "Verified customer must have an email.");
		}
	}

	public static final class Builder {

		private CustomerID customerId;
		private FullName fullName;
		private CustomerVerified verified = CustomerVerified.UNVERIFIED;
		private CustomerProfile customerProfile;
		private CustomerContact customerContact;

		public Builder customerId(String customerId) {
			Objects.requireNonNull(customerId, "customerId cannot be null");
			this.customerId = CustomerID.of(customerId);
			return this;
		}

		public Builder fullName(String firstName, String lastName) {
			this.fullName = new FullName(firstName, lastName);
			return this;
		}

		public Builder profile(CustomerProfile customerProfile) {
			this.customerProfile = Objects.requireNonNull(customerProfile, "customerProfile cannot be null");
			return this;
		}

		public Builder contact(CustomerContact customerContact) {
			this.customerContact = Objects.requireNonNull(customerContact, "customerContact cannot be null");
			return this;
		}

		public Builder verified(CustomerVerified verified) {
			this.verified = Objects.requireNonNull(verified, "verified cannot be null");
			return this;
		}

		public Customer build() {
			return new Customer(this);
		}
	}

	public Phone getPrimaryPhone() {
		for (var phone : customerContact.getPhoneList().getPhones()) {
			if (phone.defaultPhone())
				return phone;
		}
		throw new IllegalStateException("Customer should have exactly one primary phone");
	}
}
