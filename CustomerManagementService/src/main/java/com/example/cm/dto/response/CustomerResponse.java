package com.example.cm.dto.response;

import com.example.cm.dto.CustomerContactDto;
import com.example.cm.dto.CustomerProfileDto;
import com.example.hexagonal.helper.DataTransferObject;
import com.example.hexagonal.helper.Direction;

@DataTransferObject(Direction.OUTBOUND)
public record CustomerResponse(
		String customerId,
		String firstName,
		String lastName,
		String verified,
		CustomerProfileDto profile,
		CustomerContactDto contact) {
}
