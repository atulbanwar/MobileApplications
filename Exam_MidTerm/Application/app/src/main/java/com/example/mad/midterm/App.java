package com.example.mad.midterm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by atulb on 10/24/2016.
 */

public class App {
    private String name;
    private String imageUrl;
    private double price;

    public App() {

    }

    public App(String name, String imageUrl, int price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "App{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                '}';
    }

    public static App getApp(JSONObject appArrayJSONObject) throws JSONException {
        App app = new App();
        JSONObject nameObj = appArrayJSONObject.getJSONObject("im:name");
        app.setName(nameObj.getString("label"));

        JSONArray imageArray = appArrayJSONObject.getJSONArray("im:image");
        app.setImageUrl(imageArray.getJSONObject(2).getString("label"));

        app.setPrice(Double.parseDouble(appArrayJSONObject.getJSONObject("im:price").getJSONObject("attributes").getString("amount")));
        return app;
    }
}
