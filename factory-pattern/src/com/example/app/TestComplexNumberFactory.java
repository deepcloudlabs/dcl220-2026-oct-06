package com.example.app;

import static java.lang.Math.*;

import com.example.math.ComplexNumber;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class TestComplexNumberFactory {
	public static void main(String[] args) {
		ComplexNumber c1 = ComplexNumber.fromPolar(1.0, PI / 4.);
		ComplexNumber c2 = ComplexNumber.fromCartesian(2.0, 3.0);
		System.out.println("c1:= " + c1);
		System.out.println("c2:= " + c2);
	}
}
