package com.example.cm.dto.request;

import com.example.cm.dto.CustomerContactDto;
import com.example.cm.dto.CustomerProfileDto;
import com.example.hexagonal.helper.DataTransferObject;
import com.example.hexagonal.helper.Direction;

@DataTransferObject(Direction.INBOUND)
public record RegisterCustomerRequest(
		String customerId,
		String firstName,
		String lastName,
		CustomerProfileDto profile,
		CustomerContactDto contact) {
}
