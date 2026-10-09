package com.example.cm.domain;

import com.example.ddd.helper.Identity;
import com.example.ddd.helper.ValueObject;

@ValueObject
@Identity(entity = AddressList.class)
public record AddressListID(int id) {
}
