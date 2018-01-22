package com.example.mad.inclassassgn09;

import java.util.ArrayList;

/**
 * Created by atulb on 10/31/2016.
 */

public class MessagesResponse {
    ArrayList<Message> messages;
    String status;

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
