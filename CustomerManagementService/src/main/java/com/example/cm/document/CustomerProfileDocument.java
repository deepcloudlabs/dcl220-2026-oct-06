package com.example.cm.document;

import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Field;

import com.example.cm.domain.CustomerCategory;
import com.example.cm.domain.CustomerProfile;
import com.example.cm.domain.ProfileID;

public class CustomerProfileDocument {
	@Field("profile_id")
	private int profileId;

	@Field("birth_date")
	private BirthDateDocument birthDate;
	@Field("category")
	private CustomerCategory customerCategory;

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public BirthDateDocument getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(BirthDateDocument birthDate) {
		this.birthDate = birthDate;
	}

	public CustomerCategory getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(CustomerCategory customerCategory) {
		this.customerCategory = customerCategory;
	}

	public static CustomerProfileDocument fromCustomerProfile(CustomerProfile customerProfile) {
		var profileDocument = new CustomerProfileDocument();
		profileDocument.setProfileId(customerProfile.getIdentity().id());

		var birthDate = new BirthDateDocument();
		birthDate.setDay(customerProfile.getBirthDate().day());
		birthDate.setMonth(customerProfile.getBirthDate().month());
		birthDate.setYear(customerProfile.getBirthDate().year());
		profileDocument.setBirthDate(birthDate);

		profileDocument.setCustomerCategory(customerProfile.getCustomerCategory());
		return profileDocument;
	}

	public static CustomerProfile toCustomerProfile(CustomerProfileDocument customerProfileDocument) {
		Objects.requireNonNull(customerProfileDocument, "customerProfileDocument cannot be null");
		Objects.requireNonNull(customerProfileDocument.getBirthDate(), "birthDate cannot be null");
		Objects.requireNonNull(customerProfileDocument.getCustomerCategory(), "customerCategory cannot be null");

		var birthDate = customerProfileDocument.getBirthDate();
		return new CustomerProfile.Builder(new ProfileID(customerProfileDocument.getProfileId()))
				.birthDate(birthDate.getDay(), birthDate.getMonth(), birthDate.getYear())
				.customerCategory(customerProfileDocument.getCustomerCategory().name())
				.build();
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthDate, customerCategory, Integer.valueOf(profileId));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerProfileDocument other = (CustomerProfileDocument) obj;
		return Objects.equals(birthDate, other.birthDate) && customerCategory == other.customerCategory
				&& profileId == other.profileId;
	}

	@Override
	public String toString() {
		return "CustomerProfileDocument [profileId=" + profileId + ", birthDate=" + birthDate + ", customerCategory="
				+ customerCategory + "]";
	}

}
