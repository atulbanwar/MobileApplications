package com.example.mad.finalexam.POJO;

import java.io.Serializable;

public class SerializablePojoForIntent implements Serializable {
    private int variable1;
    private String variable2;
    private String variable3;

    public int getVariable1() {
        return variable1;
    }

    public void setVariable1(int variable1) {
        this.variable1 = variable1;
    }

    public String getVariable2() {
        return variable2;
    }

    public void setVariable2(String variable2) {
        this.variable2 = variable2;
    }

    public String getVariable3() {
        return variable3;
    }

    public void setVariable3(String variable3) {
        this.variable3 = variable3;
    }
}
