package com.hw.mad.hw03;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Question.java
 * Homework 03
 * Sanket Patil
 * Atul Kumar Banwar
 */

public class Question implements Serializable {
    private int id;
    private String text;
    private String url;
    private ArrayList<String> choices;
    private int answer;

    public Question(int id, String text, String url, ArrayList<String> choices, int answer) {
        this.id = id;
        this.text = text;
        this.url = url;
        this.choices = choices;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
