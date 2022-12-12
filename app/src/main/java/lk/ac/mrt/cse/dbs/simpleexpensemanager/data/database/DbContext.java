package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.DatabaseNotInitializedException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.DbHelper;

public class DbContext {
    private static SQLiteDatabase db = null;
    public static SQLiteDatabase getDb() throws DatabaseNotInitializedException {
        if (db == null)
            throw new DatabaseNotInitializedException("Database Not Initialized");
        return db;
    }
    public static void initDb(DbHelper dbHelper) {
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
    }
}
