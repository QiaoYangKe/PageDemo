package com.example.pagedemo;

public class Session {

    private String name;

    private static class SessionInstance {
        private static final Session instance = new Session();
    }

    private Session() {
    }

    public static Session getInstance() {
        return SessionInstance.instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
