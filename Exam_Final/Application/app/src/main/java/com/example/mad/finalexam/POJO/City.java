package com.example.mad.finalexam.POJO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmObject;

// extends RealmObject
public class City implements Serializable {
    private int variable1;
    private String variable2;
    private String variable3;

    public static City getPojo(JSONObject obj) throws JSONException {
        City pojo = new City();

        /*if (obj.has("id")) {
            pojo.setVariable1(obj.getInt("id"));
        }*/

        if (obj.has("description")) {
            pojo.setVariable2(obj.getString("description"));
        }

        if (obj.has("place_id")) {
            pojo.setVariable3(obj.getString("place_id"));
        }

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
}