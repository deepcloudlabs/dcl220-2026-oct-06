package com.example.cm.application;

import java.util.Optional;

import com.example.cm.domain.Customer;
import com.example.cm.domain.CustomerID;
import com.example.cm.domain.Email;
import com.example.cm.domain.Phone;
import com.example.ddd.helper.OpenHostService;
import com.example.hexagonal.helper.Port;
import com.example.hexagonal.helper.PortType;

@OpenHostService
@Port(PortType.DRIVING_PORT)
public interface CustomerManagementApplication {
	void registerCustomer(Customer customer);
	Optional<Customer> findCustomerById(CustomerID customerID);
	Optional<Customer> changeEmail(CustomerID customerId,Email email);
	Optional<Customer> changePrimaryPhone(CustomerID customerId,Phone phone);
}
