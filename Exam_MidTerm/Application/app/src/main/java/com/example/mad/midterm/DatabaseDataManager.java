package com.example.mad.midterm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by atulb on 10/24/2016.
 */

public class DatabaseDataManager {

    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private AppDao appDAO;

    public DatabaseDataManager(Context context) {
        this.mContext = context;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        appDAO = new AppDao(db);
    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }

    public AppDao getAppDao() {
        return this.appDAO;
    }

    public long saveApp(App app) {
        return this.appDAO.save(app);
    }

    public boolean delete(App app) {
        return this.appDAO.delete(app);
    }

    public List<App> getALL() {
        return this.appDAO.getAll();
    }
}
