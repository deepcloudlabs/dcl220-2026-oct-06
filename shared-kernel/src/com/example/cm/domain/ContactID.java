package com.example.cm.domain;

import com.example.ddd.helper.Identity;
import com.example.ddd.helper.ValueObject;

@ValueObject
@Identity
public record ContactID(int id) {

}
