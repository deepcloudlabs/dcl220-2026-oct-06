package com.example.singleton;

import static java.util.Objects.isNull;

// ThreadSafe
// Cache Coherent
public class ThreadSafeCacheCoherentLazyBusinessService {
    private static volatile ThreadSafeCacheCoherentLazyBusinessService instance ;

    private ThreadSafeCacheCoherentLazyBusinessService() {
        System.err.println("LazyBusinessService()");
    }
    public void fun(){
        System.err.println("Have fun...");
    }

    public static synchronized ThreadSafeCacheCoherentLazyBusinessService getInstance() {
        if (isNull(instance))
            instance = new ThreadSafeCacheCoherentLazyBusinessService(); // Once
        return instance;
    }
}
