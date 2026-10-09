package com.example.cm.dto.response;

import com.example.hexagonal.helper.DataTransferObject;
import com.example.hexagonal.helper.Direction;

@DataTransferObject(Direction.OUTBOUND)
public record RegisterCustomerResponse(String customerId, String status, CustomerResponse customer) {
	public RegisterCustomerResponse(String customerId, String status) {
		this(customerId, status, null);
	}
}
