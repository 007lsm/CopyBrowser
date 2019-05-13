package com.example.admin.myapplication.databean;

public class EventBusMessage {
    private String message;

    public EventBusMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
