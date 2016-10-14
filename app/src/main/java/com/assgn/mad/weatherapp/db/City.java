package com.assgn.mad.weatherapp.db;

/**
 * Homework 06
 * City.java
 * Sanket Patil
 * Atul Kumar Banwar
 */

public class City {
    private long id;
    private String city, country;
    private int temperature;
    private boolean isFavourite;

    public City() {
    }

    public City(String city, String country, int temperature, boolean isFavourite) {
        this.city = city;
        this.country = country;
        this.temperature = temperature;
        this.isFavourite = isFavourite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public String toString() {
        return "City{" +
                "city='" + city + '\'' +
                ", id=" + id +
                ", country='" + country + '\'' +
                ", temperature=" + temperature +
                ", isFavourite=" + isFavourite +
                '}';
    }
}
