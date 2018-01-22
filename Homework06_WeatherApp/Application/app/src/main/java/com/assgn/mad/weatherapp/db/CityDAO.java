package com.assgn.mad.weatherapp.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Homework 06
 * CityDAO.java
 * Sanket Patil
 * Atul Kumar Banwar
 */

public class CityDAO {
    private SQLiteDatabase db;

    public CityDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(City city) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CityTable.COLUMN_CITY_NAME, city.getCity());
        contentValues.put(CityTable.COLUMN_COUNTRY, city.getCountry());
        contentValues.put(CityTable.COLUMN_TEMERATURE, city.getTemperature());
        contentValues.put(CityTable.COLUMN_IS_FAVOURITE, city.isFavourite());
        contentValues.put(CityTable.COLUMN_DATE, city.getDate());
        return db.insert(CityTable.TABLE_NAME, null, contentValues);
    }

    public boolean update(City city) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CityTable.COLUMN_CITY_NAME, city.getCity());
        contentValues.put(CityTable.COLUMN_COUNTRY, city.getCountry());
        contentValues.put(CityTable.COLUMN_TEMERATURE, city.getTemperature());
        contentValues.put(CityTable.COLUMN_IS_FAVOURITE, city.isFavourite());
        contentValues.put(CityTable.COLUMN_DATE, city.getDate());
        return db.update(CityTable.TABLE_NAME, contentValues, CityTable.COLUMN_ID + "=?", new String[]{city.getId() + ""}) > 0;
    }

    public boolean delete(City city) {
        return db.delete(CityTable.TABLE_NAME, CityTable.COLUMN_ID + "=?", new String[]{city.getId() + ""}) > 0;
    }

    public City get(long id) {
        City city = null;
        Cursor c = db.query(true, CityTable.TABLE_NAME,
                new String[]{CityTable.COLUMN_ID, CityTable.COLUMN_CITY_NAME, CityTable.COLUMN_COUNTRY, CityTable.COLUMN_TEMERATURE, CityTable.COLUMN_IS_FAVOURITE, CityTable.COLUMN_DATE},
                CityTable.COLUMN_ID + "=?",
                new String[]{id + ""},
                null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            city = buildCityFromCursor(c);
            c.close();
        }
        return city;
    }

    public City get(String cityName, String country) {
        City city = null;
        Cursor c = db.query(true, CityTable.TABLE_NAME,
                new String[]{CityTable.COLUMN_ID, CityTable.COLUMN_CITY_NAME, CityTable.COLUMN_COUNTRY, CityTable.COLUMN_TEMERATURE, CityTable.COLUMN_IS_FAVOURITE, CityTable.COLUMN_DATE},
                CityTable.COLUMN_CITY_NAME + "=? AND " + CityTable.COLUMN_COUNTRY + "=?",
                new String[]{cityName, country},
                null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            city = buildCityFromCursor(c);
            c.close();
        }
        return city;
    }

    public List<City> getAll() {
        List<City> citys = new ArrayList<City>();
        Cursor c = db.query(CityTable.TABLE_NAME,
                new String[]{CityTable.COLUMN_ID, CityTable.COLUMN_CITY_NAME, CityTable.COLUMN_COUNTRY, CityTable.COLUMN_TEMERATURE, CityTable.COLUMN_IS_FAVOURITE, CityTable.COLUMN_DATE},
                null, null, null, null, null);

        if (c != null && c.moveToFirst()) {
            do {
                City city = buildCityFromCursor(c);
                if (city != null) {
                    citys.add(city);
                }
            } while (c.moveToNext());
        }
        return citys;
    }

    private City buildCityFromCursor(Cursor c) {
        City city = null;
        if (c != null) {
            city = new City();
            city.setId(c.getLong(0));
            city.setCity(c.getString(1));
            city.setCountry(c.getString(2));
            city.setTemperature(c.getInt(3));
            city.setFavourite(c.getInt(4) != 0);
            city.setDate(c.getString(5));
        }
        return city;
    }
}
