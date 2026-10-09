package com.example.cm.adapter;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.cm.document.CustomerContactDocument;
import com.example.cm.document.CustomerDocument;
import com.example.cm.document.CustomerProfileDocument;
import com.example.cm.domain.Customer;
import com.example.cm.domain.CustomerID;
import com.example.cm.repository.CustomerDocumentRepository;
import com.example.cm.repository.CustomerRepository;
import com.example.hexagonal.helper.Adapter;

@Adapter(port = CustomerRepository.class)
@Repository
public class CustomerRepositoryMongoAdapter implements CustomerRepository {
	private final CustomerDocumentRepository customerDocumentRepository;

	public CustomerRepositoryMongoAdapter(CustomerDocumentRepository customerDocumentRepository) {
		this.customerDocumentRepository = customerDocumentRepository;
	}

	@Override
	@Transactional
	public Customer persist(Customer customer) {
		var customerDocument = mapCustomerToDocument(customer);
		var savedCustomerDocument = customerDocumentRepository.save(customerDocument);
		return mapDocumentToCustomer(savedCustomerDocument);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Customer> findCustomerById(CustomerID customerId) {
		Objects.requireNonNull(customerId, "customerId cannot be null");
		return customerDocumentRepository.findById(customerId.getValue()).map(this::mapDocumentToCustomer);
	}

	@Transactional
	@Override
	public Customer updateCustomer(Customer customer) {
		var customerDocument = mapCustomerToDocument(customer);
		var updatedCustomerDocument = customerDocumentRepository.save(customerDocument);
		return mapDocumentToCustomer(updatedCustomerDocument);
	}

	private CustomerDocument mapCustomerToDocument(Customer customer) {
		Objects.requireNonNull(customer, "customer cannot be null");

		var customerDocument = new CustomerDocument();
		customerDocument.setCustomerId(customer.getIdentity().getValue());
		customerDocument.setFirstName(customer.getFullName().firstName());
		customerDocument.setLastName(customer.getFullName().lastName());
		customerDocument.setVerified(customer.getVerified());
		customerDocument.setCustomerProfile(CustomerProfileDocument.fromCustomerProfile(customer.getCustomerProfile()));
		customerDocument.setCustomerContact(CustomerContactDocument.fromCustomer(customer.getCustomerContact()));
		return customerDocument;
	}

	private Customer mapDocumentToCustomer(CustomerDocument customerDocument) {
		Objects.requireNonNull(customerDocument, "customerDocument cannot be null");
		return customerDocument.toCustomer();
	}
}
