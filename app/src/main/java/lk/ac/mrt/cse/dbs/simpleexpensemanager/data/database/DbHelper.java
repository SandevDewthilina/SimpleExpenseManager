package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "200333C.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Account(accountNo TEXT PRIMARY KEY, bankName TEXT NOT NULL, accountHolderName TEXT NOT NULL, balance REAL DEFAULT 0);");
        db.execSQL("CREATE TABLE Transactions(Id INTEGER PRIMARY KEY AUTOINCREMENT, accountNo TEXT NOT NULL, expenseType TEXT NOT NULL, amount REAL DEFAULT 0, date DATE NOT " +
                "NULL,FOREIGN KEY " +
                "(accountNo)" +
                " REFERENCES Account(accountNo));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int upgradeTo = oldVersion;
        while (upgradeTo < newVersion) {
            switch (upgradeTo) {
                case 1:
                    // no incremental change yet
                    break;
            }
            upgradeTo++;
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no downgrade changes
    }
}
