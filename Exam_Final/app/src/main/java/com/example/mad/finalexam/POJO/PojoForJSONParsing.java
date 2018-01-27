package com.example.mad.finalexam.POJO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmObject;

// extends RealmObject
public class PojoForJSONParsing extends RealmObject implements Serializable {
    private int variable1;
    private String variable2;
    private String variable3;
    private Date variable4;

    public static PojoForJSONParsing getPojo(JSONObject obj) throws JSONException {
        PojoForJSONParsing pojo = new PojoForJSONParsing();

        if (obj.has("id")) {
            pojo.setVariable1(obj.getInt("id"));
        }

        if (obj.has("text")) {
            pojo.setVariable2(obj.getString("text"));
        }

        if (obj.has("image")) {
            pojo.setVariable3(obj.getString("image"));
        }

        pojo.setVariable4(Calendar.getInstance().getTime());

        return pojo;
    }

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

    public Date getVariable4() {
        return variable4;
    }

    public void setVariable4(Date variable4) {
        this.variable4 = variable4;
    }
}