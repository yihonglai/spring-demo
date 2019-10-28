package com.example.demo;

class GreetingNotFoundException extends RuntimeException {

    GreetingNotFoundException(Long id) {
        super("Could not find greeting " + id);
    }
}
