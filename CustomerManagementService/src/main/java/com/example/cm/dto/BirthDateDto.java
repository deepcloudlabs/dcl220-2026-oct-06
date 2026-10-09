package com.example.cm.dto;

import com.example.hexagonal.helper.DataTransferObject;
import com.example.hexagonal.helper.Direction;

@DataTransferObject(Direction.INBOUND)
public record BirthDateDto(int day, int month, int year) {
}
