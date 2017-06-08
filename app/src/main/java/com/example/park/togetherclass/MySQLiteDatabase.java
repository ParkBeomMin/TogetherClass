package com.example.park.togetherclass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Park on 2017-06-08.
 */

public class MySQLiteDatabase extends SQLiteOpenHelper {
    public MySQLiteDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table if not exists professor (" +
                "num integer primary key autoincrement," +
                "id text not null," +
                "name text not null," +
                "password text not null," +
                "subject text);" ;
//        String sql = "drop table if exists professor";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists professor";
//        db.execSQL(sql);
        onCreate(db);
    }


}