package com.example.cm.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cm.application.CustomerManagementApplication;
import com.example.cm.domain.Address;
import com.example.cm.domain.AddressList;
import com.example.cm.domain.Customer;
import com.example.cm.domain.CustomerContact;
import com.example.cm.domain.CustomerID;
import com.example.cm.domain.CustomerProfile;
import com.example.cm.domain.Email;
import com.example.cm.domain.Phone;
import com.example.cm.domain.PhoneList;
import com.example.cm.domain.ProfileID;
import com.example.cm.dto.AddressDto;
import com.example.cm.dto.BirthDateDto;
import com.example.cm.dto.CustomerContactDto;
import com.example.cm.dto.CustomerProfileDto;
import com.example.cm.dto.PhoneDto;
import com.example.cm.dto.request.ChangeEmailRequest;
import com.example.cm.dto.request.ChangePrimaryPhoneRequest;
import com.example.cm.dto.request.RegisterCustomerRequest;
import com.example.cm.dto.response.ChangeEmailResponse;
import com.example.cm.dto.response.ChangePrimaryPhoneResponse;
import com.example.cm.dto.response.CustomerResponse;
import com.example.cm.dto.response.RegisterCustomerResponse;

@Service
public class CustomerManagementService {
	private static final String SUCCESS = "SUCCESS";

	private final CustomerManagementApplication customerManagementApplication;

	public CustomerManagementService(CustomerManagementApplication customerManagementApplication) {
		this.customerManagementApplication = customerManagementApplication;
	}

	public CustomerResponse findCustomerById(String customerId) {
		var customer = customerManagementApplication.findCustomerById(CustomerID.of(customerId))
				.orElseThrow(() -> new IllegalArgumentException("Customer %s does not exist".formatted(customerId)));
		return toCustomerResponse(customer);
	}

	@Transactional
	public RegisterCustomerResponse register(RegisterCustomerRequest registerCustomerRequest) {
		var customer = toCustomer(registerCustomerRequest);
		customerManagementApplication.registerCustomer(customer);
		return new RegisterCustomerResponse(customer.getIdentity().getValue(), SUCCESS, toCustomerResponse(customer));
	}

	@Transactional
	public ChangeEmailResponse changeEmail(String customerId, ChangeEmailRequest changeEmailRequest) {
		Objects.requireNonNull(changeEmailRequest, "changeEmailRequest cannot be null");
		var updatedCustomer = customerManagementApplication
				.changeEmail(CustomerID.of(customerId), Email.of(changeEmailRequest.email()))
				.orElseThrow(() -> new IllegalArgumentException("Customer %s does not exist".formatted(customerId)));

		return new ChangeEmailResponse(
				updatedCustomer.getIdentity().getValue(),
				updatedCustomer.getCustomerContact().getEmail().value(),
				SUCCESS);
	}

	@Transactional
	public ChangePrimaryPhoneResponse changePrimaryPhone(String customerId,
			ChangePrimaryPhoneRequest changePrimaryPhoneRequest) {
		Objects.requireNonNull(changePrimaryPhoneRequest, "changePrimaryPhoneRequest cannot be null");
		var updatedCustomer = customerManagementApplication
				.changePrimaryPhone(CustomerID.of(customerId), new Phone(changePrimaryPhoneRequest.phone(), true))
				.orElseThrow(() -> new IllegalArgumentException("Customer %s does not exist".formatted(customerId)));

		return new ChangePrimaryPhoneResponse(
				updatedCustomer.getIdentity().getValue(),
				updatedCustomer.getPrimaryPhone().value(),
				SUCCESS);
	}

	private Customer toCustomer(RegisterCustomerRequest request) {
		Objects.requireNonNull(request, "registerCustomerRequest cannot be null");

		return new Customer.Builder()
				.customerId(request.customerId())
				.fullName(request.firstName(), request.lastName())
				.profile(toCustomerProfile(request.profile()))
				.contact(toCustomerContact(request.contact()))
				.build();
	}

	private CustomerProfile toCustomerProfile(CustomerProfileDto profileDto) {
		Objects.requireNonNull(profileDto, "profile cannot be null");
		Objects.requireNonNull(profileDto.birthDate(), "profile.birthDate cannot be null");

		return new CustomerProfile.Builder(new ProfileID(profileDto.profileId()))
				.birthDate(profileDto.birthDate().day(), profileDto.birthDate().month(), profileDto.birthDate().year())
				.customerCategory(profileDto.customerCategory())
				.build();
	}

	private CustomerContact toCustomerContact(CustomerContactDto contactDto) {
		Objects.requireNonNull(contactDto, "contact cannot be null");

		return new CustomerContact.Builder(contactDto.contactId())
				.email(contactDto.email())
				.addresses(toAddressList(contactDto))
				.phones(toPhoneList(contactDto))
				.build();
	}

	private AddressList toAddressList(CustomerContactDto contactDto) {
		var addressDtos = Objects.requireNonNullElse(contactDto.addresses(), List.<AddressDto>of());
		var addresses = addressDtos.stream()
				.map(this::toAddress)
				.toArray(Address[]::new);

		return new AddressList.Builder(contactDto.addressListId())
				.addresses(addresses)
				.build();
	}

	private Address toAddress(AddressDto addressDto) {
		Objects.requireNonNull(addressDto, "address cannot be null");
		var lines = Objects.requireNonNullElse(addressDto.lines(), List.<String>of()).toArray(String[]::new);
		return Address.valueOf(addressDto.country(), addressDto.city(), addressDto.primaryAddress(), lines);
	}

	private PhoneList toPhoneList(CustomerContactDto contactDto) {
		var phoneDtos = Objects.requireNonNullElse(contactDto.phones(), List.<PhoneDto>of());
		var phones = phoneDtos.stream()
				.map(phoneDto -> new Phone(phoneDto.phone(), phoneDto.primaryPhone()))
				.toArray(Phone[]::new);

		return new PhoneList.Builder(contactDto.phoneListId())
				.phones(phones)
				.build();
	}

	private CustomerResponse toCustomerResponse(Customer customer) {
		Objects.requireNonNull(customer, "customer cannot be null");

		return new CustomerResponse(
				customer.getIdentity().getValue(),
				customer.getFullName().firstName(),
				customer.getFullName().lastName(),
				customer.getVerified().name(),
				toCustomerProfileDto(customer.getCustomerProfile()),
				toCustomerContactDto(customer.getCustomerContact()));
	}

	private CustomerProfileDto toCustomerProfileDto(CustomerProfile customerProfile) {
		var birthDate = customerProfile.getBirthDate();
		return new CustomerProfileDto(
				customerProfile.getIdentity().id(),
				new BirthDateDto(birthDate.day(), birthDate.month(), birthDate.year()),
				customerProfile.getCustomerCategory().name());
	}

	private CustomerContactDto toCustomerContactDto(CustomerContact customerContact) {
		var addresses = customerContact.getAddressList().getAddresses().stream()
				.map(address -> new AddressDto(
						List.of(address.lines().values()),
						address.country().value(),
						address.city().value(),
						address.defaultAddress()))
				.toList();

		var phones = customerContact.getPhoneList().getPhones().stream()
				.map(phone -> new PhoneDto(phone.value(), phone.defaultPhone()))
				.toList();

		return new CustomerContactDto(
				customerContact.getIdentity().id(),
				customerContact.getEmail().value(),
				customerContact.getAddressList().getIdentity().id(),
				addresses,
				customerContact.getPhoneList().getIdentity().id(),
				phones);
	}
}
