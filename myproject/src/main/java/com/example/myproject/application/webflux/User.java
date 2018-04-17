package com.example.myproject.application.webflux;

public class User {
    private long userSeq;
    private String name;

    public User(long userSeq) {
        this.userSeq = userSeq;
    }

    public long getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(long userSeq) {
        this.userSeq = userSeq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
