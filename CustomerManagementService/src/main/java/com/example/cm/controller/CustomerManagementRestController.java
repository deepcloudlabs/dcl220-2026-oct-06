package com.example.cm.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.cm.application.CustomerManagementApplication;
import com.example.cm.dto.request.ChangeEmailRequest;
import com.example.cm.dto.request.ChangePrimaryPhoneRequest;
import com.example.cm.dto.request.RegisterCustomerRequest;
import com.example.cm.dto.response.ChangeEmailResponse;
import com.example.cm.dto.response.ChangePrimaryPhoneResponse;
import com.example.cm.dto.response.CustomerResponse;
import com.example.cm.dto.response.RegisterCustomerResponse;
import com.example.cm.service.CustomerManagementService;
import com.example.hexagonal.helper.Adapter;

@RestController
@RequestScope
@CrossOrigin
@Validated
@RequestMapping("/customers")
@Adapter(port = CustomerManagementApplication.class)
public class CustomerManagementRestController {
	private final CustomerManagementService customerManagementService;

	public CustomerManagementRestController(CustomerManagementService customerManagementService) {
		this.customerManagementService = customerManagementService;
	}

	@PostMapping
	public RegisterCustomerResponse registerCustomer(@RequestBody RegisterCustomerRequest registerCustomerRequest) {
		return customerManagementService.register(registerCustomerRequest);
	}

	@GetMapping("{customerId}")
	public CustomerResponse findCustomerById(@PathVariable String customerId) {
		return customerManagementService.findCustomerById(customerId);
	}

	@PutMapping("/{customerId}/email")
	public ChangeEmailResponse changeEmail(@PathVariable String customerId, @RequestBody ChangeEmailRequest changeEmailRequest) {
		return customerManagementService.changeEmail(customerId, changeEmailRequest);
	}

	@PutMapping("/{customerId}/phone")
	public ChangePrimaryPhoneResponse changePrimaryPhone(@PathVariable String customerId,
			@RequestBody ChangePrimaryPhoneRequest changePrimaryPhoneRequest) {
		return customerManagementService.changePrimaryPhone(customerId, changePrimaryPhoneRequest);
	}
}
