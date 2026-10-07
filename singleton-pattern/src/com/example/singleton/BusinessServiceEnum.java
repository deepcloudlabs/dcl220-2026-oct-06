package com.example.singleton;

// ThreadSafe
// Cache Coherent
// JLS, JVM Spec. -> Class Loader
public enum BusinessServiceEnum implements BusinessService {
	INSTANCE;

    private BusinessServiceEnum() {
        System.err.println("Creating BusinessServiceEnum...");
    }

    public void fun(){
        System.err.println("Have fun...");
    }
    
    public static BusinessServiceEnum getInstance() {
        return INSTANCE;
    }
}
