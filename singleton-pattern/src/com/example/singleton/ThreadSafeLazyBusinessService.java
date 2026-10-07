package com.example.singleton;

import static java.util.Objects.isNull;

// ThreadSafe
// Not Cache Coherent
public class ThreadSafeLazyBusinessService {
    private static ThreadSafeLazyBusinessService instance ;

    private ThreadSafeLazyBusinessService() {
        System.err.println("LazyBusinessService()");
    }
    public void fun(){
        System.err.println("Have fun...");
    }

    public static synchronized ThreadSafeLazyBusinessService getInstance() {
        if (isNull(instance))
            instance = new ThreadSafeLazyBusinessService(); // Once
        return instance;
    }
}
