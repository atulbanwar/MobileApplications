package com.example.mad.finalexam.POJO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmObject;

// extends RealmObject
public class Trip implements Serializable {
    private int variable1;
    private String variable2;
    private String variable3;
    private String variable4;
    private HashMap<String, Place> variable5;

    public static Trip getPojo(JSONObject obj) throws JSONException {
        Trip pojo = new Trip();

        /*if (obj.has("id")) {
            pojo.setVariable1(obj.getInt("id"));
        }*/

        if (obj.has("description")) {
            pojo.setVariable2(obj.getString("description"));
        }

        if (obj.has("place_id")) {
            pojo.setVariable3(obj.getString("place_id"));
        }

        if (obj.has("place_id")) {
            pojo.setVariable4(obj.getString("place_id"));
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

    public String getVariable4() {
        return variable4;
    }

    public void setVariable4(String variable4) {
        this.variable4 = variable4;
    }

    public HashMap<String, Place> getVariable5() {
        return variable5;
    }

    public void setVariable5(HashMap<String, Place> variable5) {
        this.variable5 = variable5;
    }
}