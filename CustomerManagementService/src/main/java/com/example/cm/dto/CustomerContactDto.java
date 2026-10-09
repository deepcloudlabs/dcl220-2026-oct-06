package com.example.cm.dto;

import java.util.List;

import com.example.hexagonal.helper.DataTransferObject;
import com.example.hexagonal.helper.Direction;

@DataTransferObject(Direction.INBOUND)
public record CustomerContactDto(
		int contactId,
		String email,
		int addressListId,
		List<AddressDto> addresses,
		int phoneListId,
		List<PhoneDto> phones) {
}
