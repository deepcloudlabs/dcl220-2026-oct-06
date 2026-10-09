package com.example.cm.domain;

import com.example.ddd.helper.ValueObject;

@ValueObject
public record Phone(String value,boolean defaultPhone) {

}
