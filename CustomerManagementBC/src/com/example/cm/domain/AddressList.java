package com.example.cm.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.cm.domain.exception.BusinessConstraint;
import com.example.cm.domain.exception.BusinessRule;
import com.example.ddd.helper.Entity;

@Entity(identity = "id")
public class AddressList extends DomainEntity<AddressListID> {

	public static final int MAX_ADDRESS_SIZE = 5;
	private List<Address> addresses;

	private AddressList(Builder builder) {
		super(builder.addressListID);
		this.addresses = builder.addresses;
	}

	public List<Address> getAddresses() {
		return List.copyOf(this.addresses);
	}

	public List<Address> addAddress(Address newAddress) {
		if (this.addresses.size() == MAX_ADDRESS_SIZE)
			throw new BusinessConstraint(13, "Customer cannot have more than %d addresses".formatted(MAX_ADDRESS_SIZE));
		this.addresses.add(newAddress);
		return List.copyOf(this.addresses);
	}

	public List<Address> removeAddress(Address address) {
		if (this.addresses.size() == 1)
			throw new BusinessRule(18, "Customer should have at least one address");
		this.addresses.remove(address);
		return List.copyOf(this.addresses);
	}

	public static class Builder {
		private AddressListID addressListID;
		private List<Address> addresses;

		public Builder(int id) {
			this.addressListID = new AddressListID(id);
		}

		public Builder addresses(Address... addressesReceived) {
			this.addresses = new ArrayList<>();
			for (var address : addressesReceived) {
				// TODO: validation
				addresses.add(address);
			}
			return this;
		}

		public AddressList build() {
			// Consistency Boundary:
			// 1. Business Rule
			// 2. Validation Rule
			// 3. Invariant
			// 4. Business Constraint
			// 5. Regulations
			// 6. Standards
			if (this.addresses.size() > MAX_ADDRESS_SIZE)
				throw new BusinessConstraint(13,
						"Customer cannot have more than %d addresses".formatted(MAX_ADDRESS_SIZE));
			if (this.addresses.isEmpty())
				throw new BusinessRule(18, "Customer should have at least one address");
			return new AddressList(this);
		}
	}
}
