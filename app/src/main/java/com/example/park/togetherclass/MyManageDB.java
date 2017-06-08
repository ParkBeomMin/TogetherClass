package com.example.park.togetherclass;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Park on 2017-06-08.
 */


public class MyManageDB {
    private static MySQLiteDatabase database = null;
    private static SQLiteDatabase myDB2 = null;
    private static MyManageDB mInstance = null;

    public final static MyManageDB getInstance(Context context) {
        if (mInstance == null) mInstance = new MyManageDB(context);
        return mInstance;
    }

    private MyManageDB(Context context) {
        database = new MySQLiteDatabase(context, "myDB2", null, 1);
        myDB2 = database.getWritableDatabase();
    }

    public Cursor execSELECTStudent(String sql) {
        Cursor cursor = myDB2.rawQuery(sql, null);
        return cursor;
    }

    public void execINSERTProfessor(String Id, String Name, String Pw, String subject) {
        String sql = "INSERT INTO professor values ( null, '" + Id + "','" + Name + "','" + Pw + "','" +subject+"')";
        myDB2.execSQL(sql);
    }
}