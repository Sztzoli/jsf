package com.example.empapp;

import javax.enterprise.inject.Model;

@Model
public class SimpleHelloController {

    public String getMessage() {
        return "Hello JSF";
    }
}
