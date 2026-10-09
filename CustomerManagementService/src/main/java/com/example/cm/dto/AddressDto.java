package com.example.cm.dto;

import java.util.List;

import com.example.hexagonal.helper.DataTransferObject;
import com.example.hexagonal.helper.Direction;

@DataTransferObject(Direction.INBOUND)
public record AddressDto(List<String> lines, String country, String city, boolean primaryAddress) {
}
