package com.example.mad.finalexam.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseDataManager {

    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private PojoDAO pojoDAO;

    public DatabaseDataManager(Context context) {
        this.mContext = context;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        pojoDAO = new PojoDAO(db);
    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }

    public PojoDAO getPojoDAO() {
        return this.pojoDAO;
    }

    public long savePojoForDB(PojoForDB pojo) {
        return this.pojoDAO.save(pojo);
    }

    public boolean updatePojoForDB(PojoForDB pojo) {
        return this.pojoDAO.update(pojo);
    }

    public boolean delete(PojoForDB pojo) {
        return this.pojoDAO.delete(pojo);
    }

    public PojoForDB getPojo(long id) {
        return this.pojoDAO.get(id);
    }

    public ArrayList getALL() {
        return this.pojoDAO.getAll();
    }

    public PojoForDB getPojo(String var1, String var2) {
        return this.pojoDAO.get(var1, var2);
    }
}