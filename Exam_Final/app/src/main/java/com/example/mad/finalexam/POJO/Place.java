package com.example.mad.finalexam.POJO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import io.realm.RealmObject;

// extends RealmObject
public class Place implements Serializable {
    private String variable1;
    private String variable2;
    private String variable3;

    public static Place getPojo(JSONObject obj) throws JSONException {
        Place pojo = new Place();

        if (obj.has("id")) {
            pojo.setVariable1(obj.getString("id"));
        }

        if (obj.has("icon")) {
            pojo.setVariable2(obj.getString("icon"));
        }

        if (obj.has("name")) {
            pojo.setVariable3(obj.getString("name"));
        }

        return pojo;
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

    public String getVariable3() {
        return variable3;
    }

    public void setVariable3(String variable3) {
        this.variable3 = variable3;
    }
}