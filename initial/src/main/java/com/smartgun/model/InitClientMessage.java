package com.smartgun.model;

public class InitClientMessage {

    private String name;

    public InitClientMessage() {
    }

    public InitClientMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
