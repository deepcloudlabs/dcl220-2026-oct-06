package com.example.cm.domain;

import com.example.cm.domain.exception.BusinessRule;
import com.example.ddd.helper.Entity;

@Entity(identity = "profileId")
public class CustomerProfile extends DomainEntity<ProfileID> {
	private BirthDate birthDate;
	private CustomerCategory customerCategory;

	private CustomerProfile(ProfileID id, BirthDate birthDate, CustomerCategory customerCategory) {
		super(id);
		this.birthDate = birthDate;
		this.customerCategory = customerCategory;
	}

	public CustomerProfile(Builder builder) {
		super(builder.profileId);
		this.birthDate = builder.birthDate;
		this.customerCategory = builder.customerCategory;
	}

	public static class Builder {
		private ProfileID profileId;
		private BirthDate birthDate;
		private CustomerCategory customerCategory;

		public Builder(ProfileID profileId) {
			this.profileId = profileId;
		}

		public Builder birthDate(int day, int month, int year) {
			this.birthDate = BirthDate.of(day, month, year);
			return this;
		}

		public Builder customerCategory(String category) {
			this.customerCategory = CustomerCategory.valueOf(category);
			return this;
		}

		public CustomerProfile build() {
			// Consistency Boundary:
			// 1. Business Rule
			// 2. Validation Rule
			// 3. Invariant
			// 4. Business Constraint
			// 5. Regulations
			// 6. Standards
			return new CustomerProfile(this);
		}
	}

	public BirthDate getBirthDate() {
		return birthDate;
	}

	public CustomerCategory getCustomerCategory() {
		return customerCategory;
	}

	// business method => ubiquitous language
	public CustomerCategory changeCustomerCategory(CustomerCategory newCustomerCategory) {
		// 1. Business Rule
		// 2. Validation Rule
		// 3. Invariant
		// 4. Business Constraint
		// 5. Regulations
		// 6. Standards
		if (this.customerCategory.equals(CustomerCategory.CORPORATE_CUSTOMER))
			throw new BusinessRule(200, "CORPORATE Customers cannot be assigned to another category!");
		this.customerCategory = newCustomerCategory;
		return this.customerCategory;
	}
	
	public BirthDate changeBirthDate(BirthDate birthDate) {
		throw new BusinessRule(205, "Customer cannot change birth date");
	}

}
