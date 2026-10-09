package com.example.cm.dto.fault;

import com.example.hexagonal.helper.DataTransferObject;
import com.example.hexagonal.helper.Direction;

@DataTransferObject(Direction.FAULT)
public record ErrorResponse(String message,String reason) {}