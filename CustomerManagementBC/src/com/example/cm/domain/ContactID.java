package com.example.cm.domain;

import com.example.ddd.helper.Identity;
import com.example.ddd.helper.ValueObject;

@ValueObject
@Identity(entity = CustomerContact.class)
public record ContactID(int id) {

}
