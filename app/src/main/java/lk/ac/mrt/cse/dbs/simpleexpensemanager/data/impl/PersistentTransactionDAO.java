package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.DatabaseNotInitializedException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.DbContext;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO implements TransactionDAO {
    SQLiteDatabase db;

    public PersistentTransactionDAO() {
        try {
            db = DbContext.getDb();
        } catch (DatabaseNotInitializedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Object[] args = {null, accountNo, expenseType.toString(), amount, formatter.format(date)};
            db.execSQL("insert into Transactions values(?, ?, ?, ?, ?)", args);
        } catch (Exception e) {
            Log.e("Exception handled", e.getMessage());
        }
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactions = new ArrayList<>();
        Cursor mCursor = db.rawQuery("select * from Transactions", null);
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {

            String dateString = mCursor.getString(mCursor.getColumnIndex("date"));
            try {
                Date datetime = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);

                Transaction transaction = new Transaction(
                        datetime,
                        mCursor.getString(mCursor.getColumnIndex("accountNo")),
                        ExpenseType.valueOf(mCursor.getString(mCursor.getColumnIndex("expenseType"))),
                        mCursor.getDouble(mCursor.getColumnIndex("amount")));
                transactions.add(transaction);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        mCursor.close();
        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactions = new ArrayList<>();
        String[] args = {limit+""};
        Cursor mCursor = db.rawQuery("select * from Transactions where Id in (select Id from Transactions order by Id desc limit ?)", args);
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {

            String dateString = mCursor.getString(mCursor.getColumnIndex("date"));
            try {
                Date datetime = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);

                Transaction transaction = new Transaction(
                        datetime,
                        mCursor.getString(mCursor.getColumnIndex("accountNo")),
                        ExpenseType.valueOf(mCursor.getString(mCursor.getColumnIndex("expenseType"))),
                        mCursor.getDouble(mCursor.getColumnIndex("amount")));
                transactions.add(transaction);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        mCursor.close();
        return transactions;
    }
}
