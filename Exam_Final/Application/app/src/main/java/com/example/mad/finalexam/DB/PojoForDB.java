package com.example.mad.finalexam.DB;

import java.io.Serializable;
import java.util.Date;

public class PojoForDB implements Serializable {
    private long id;
    private String variable1;
    private String variable2;

    public PojoForDB() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVariable1() {
        return variable1;
    }

    public void setVariable1(String variable1) {
        this.variable1 = variable1;
    }

    public String getVariable2() {
        return variable2;
    }

    public void setVariable2(String variable2) {
        this.variable2 = variable2;
    }
}
