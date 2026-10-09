package com.example.cm.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.cm.domain.exception.BusinessConstraint;
import com.example.cm.domain.exception.BusinessRule;
import com.example.ddd.helper.Entity;

@Entity(identity = "id")
public class PhoneList extends DomainEntity<PhoneListID> {
	public static final int MAX_PHONE_SIZE = 3;
	private List<Phone> phones;

	private PhoneList(Builder builder) {
		super(builder.phoneListID);
		this.phones = builder.phones;
	}

	public List<Phone> getPhones() {
		return List.copyOf(this.phones);
	}

	public List<Phone> addPhone(Phone newPhone) {
		if (this.phones.size() == MAX_PHONE_SIZE)
			throw new BusinessConstraint(13, "Customer cannot have more than %d addresses".formatted(MAX_PHONE_SIZE));
		this.phones.add(newPhone);
		return List.copyOf(this.phones);
	}

	public Phone setDefaultPhone(Phone defaultPhone) {
		var updatedPhones = new ArrayList<Phone>();
		var found = false;

		for (var phone : this.phones) {
			if (phone.value().equals(defaultPhone.value())) {
				updatedPhones.add(new Phone(phone.value(), true));
				found = true;
			} else {
				updatedPhones.add(new Phone(phone.value(), false));
			}
		}

		if (!found)
			throw new BusinessRule(29, "Default phone should be one of the customer's phones");

		this.phones = updatedPhones;
		return getDefaultPhone();
	}

	public Phone getDefaultPhone() {
		for (var phone : phones)
			if (phone.defaultPhone())
				return phone;
		throw new IllegalStateException("There is no default phone!");
	}

	public List<Phone> removePhone(Phone phone) {
		if (this.phones.size() == 1)
			throw new BusinessRule(18, "Customer should have at least one phone");
		if (phone.defaultPhone())
			throw new BusinessRule(28, "Customer should have at least one default phone");			
		this.phones.remove(phone);
		return List.copyOf(this.phones);
	}

	public static class Builder {
		private PhoneListID phoneListID;
		private List<Phone> phones;

		public Builder(int id) {
			this.phoneListID = new PhoneListID(id);
		}

		public Builder phones(String... phonesReceived) {
			this.phones = new ArrayList<>();
			for (var phone : phonesReceived) {
				// TODO: validation
				phones.add(new Phone(phone, false));
			}
			return this;
		}

		public Builder phones(Phone... phonesReceived) {
			this.phones = new ArrayList<>();
			for (var phone : phonesReceived) {
				// TODO: validation
				phones.add(phone);
			}
			return this;
		}

		public PhoneList build() {
			// Consistency Boundary:
			// 1. Business Rule
			// 2. Validation Rule
			// 3. Invariant
			// 4. Business Constraint
			// 5. Regulations
			// 6. Standards
			if (this.phones.size() > MAX_PHONE_SIZE)
				throw new BusinessConstraint(13, "Customer cannot have more than %d phones".formatted(MAX_PHONE_SIZE));
			if (this.phones.isEmpty())
				throw new BusinessRule(24, "Customer should have at least one phone");
			var numberOfDefaultPhones = 0;
			for (var phone : phones)
				if (phone.defaultPhone())
					numberOfDefaultPhones++;
			if (numberOfDefaultPhones < 1)
				throw new BusinessRule(28, "Customer should have at least one default phone");
			if (numberOfDefaultPhones > 1)
				throw new BusinessRule(25, "Customer should have at more than one default phone");
			return new PhoneList(this);
		}
	}
}
