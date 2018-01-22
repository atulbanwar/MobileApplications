package com.assgn.mad.weatherapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Homework 06
 * DatabaseDataManager.java
 * Sanket Patil
 * Atul Kumar Banwar
 */

public class DatabaseDataManager {

    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private CityDAO cityDAO;

    public DatabaseDataManager(Context context) {
        this.mContext = context;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        cityDAO = new CityDAO(db);
    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }

    public CityDAO getCityDAO() {
        return this.cityDAO;
    }

    public long saveCity(City city) {
        return this.cityDAO.save(city);
    }

    public boolean updateCity(City city) {
        return this.cityDAO.update(city);
    }

    public boolean delete(City city) {
        return this.cityDAO.delete(city);
    }

    public City getCity(long id) {
        return this.cityDAO.get(id);
    }

    public City getCity(String cityName, String country) {
        return this.cityDAO.get(cityName, country);
    }

    public List<City> getALL() {
        return this.cityDAO.getAll();
    }
}
