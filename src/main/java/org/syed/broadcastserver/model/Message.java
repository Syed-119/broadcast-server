package org.syed.broadcastserver.model;

import java.time.LocalDateTime;

public class Message {
    private String sender;
    private String message;
    private String receiver;
    private LocalDateTime timestamp;

    public Message(String sender, String message, String receiver, LocalDateTime timestamp) {
        this.sender = sender;
        this.message = message;
        this.receiver = receiver;
        this.timestamp = timestamp;

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

