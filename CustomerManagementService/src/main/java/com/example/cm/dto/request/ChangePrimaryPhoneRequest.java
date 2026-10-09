package com.example.cm.dto.request;

import com.example.hexagonal.helper.DataTransferObject;
import com.example.hexagonal.helper.Direction;

@DataTransferObject(Direction.INBOUND)
public record ChangePrimaryPhoneRequest(String phone) {
}
