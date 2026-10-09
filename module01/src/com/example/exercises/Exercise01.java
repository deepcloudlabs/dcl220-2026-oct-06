package com.example.exercises;

// overloading 
// overriding
class A {
	// f(Number n) overloads f(Integer n)
    String f(Number n)  { return "A:Number"; }
    String f(Integer n) { return "A:Integer"; }
}

class B extends A {
    @Override
    String f(Number n) { return "B:Number"; }
    String f(Double n) { return "B:Double"; }
}

public class Exercise01 {

	public static void main(String[] args) {
		A p = new B(); // Heap: Header -> VT(B)
		System.out.println(p.f(1.)); // Offset: B::String f(Number n),A::f(Integer n),B::String f(Double n)
        // f(Long n) ->
		B q = new B();
		System.out.println(q.f(1.));
	}
   // A) B::Number
   // B) B::Double
   // C) A::Integer
   // D) A::Number
   // E) Compilation fails
}
