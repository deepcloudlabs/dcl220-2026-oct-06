package com.example.cm.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.cm.document.CustomerDocument;

public interface CustomerDocumentRepository extends MongoRepository<CustomerDocument,String>{
	List<CustomerDocument>	findByCustomerContactAddressesCountry(String country);
}
