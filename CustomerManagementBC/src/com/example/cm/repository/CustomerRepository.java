package com.example.cm.repository;

import java.util.Optional;

import com.example.cm.domain.Customer;
import com.example.cm.domain.CustomerID;
import com.example.hexagonal.helper.Port;
import com.example.hexagonal.helper.PortType;

@Port(PortType.DRIVEN_PORT)
public interface CustomerRepository {

	Customer persist(Customer customer);

	Optional<Customer> findCustomerById(CustomerID customerId);

	Customer updateCustomer(Customer cust);

}
