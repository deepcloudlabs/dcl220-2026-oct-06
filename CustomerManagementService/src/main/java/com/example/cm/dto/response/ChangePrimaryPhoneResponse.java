package com.example.cm.dto.response;

import com.example.hexagonal.helper.DataTransferObject;
import com.example.hexagonal.helper.Direction;

@DataTransferObject(Direction.OUTBOUND)
public record ChangePrimaryPhoneResponse(String customerId, String primaryPhone, String status) {
}
