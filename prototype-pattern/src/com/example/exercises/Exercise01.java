package com.example.exercises;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Exercise01 {

	public static void main(String[] args) throws NoSuchAlgorithmException, CloneNotSupportedException {
		var commonHeader = "header".getBytes();
		var payloadA = "part a".getBytes();
		var payloadB = "part b".getBytes();
		MessageDigest base = MessageDigest.getInstance("SHA-256");
		System.out.println(base.getClass());
		base.update(commonHeader);
		// prototype: base
		MessageDigest a = (MessageDigest) base.clone();
		a.update(payloadA); // digest of header + payloadA
		MessageDigest b = (MessageDigest) base.clone();
		b.update(payloadB); // digest of header + payloadB, prefix hashed only once
	}

}
