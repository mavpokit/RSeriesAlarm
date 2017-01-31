package com.mavpokit.rseriesalarm.data.source.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Alex on 31.01.2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Objects.db";

    public static final String TABLE_OBJECTS = "objects";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_CODE = "code";

    private static final String SQL_CREATE_SQRIPT =
            "CREATE TABLE " + TABLE_OBJECTS + " (" +
                    COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_NUMBER + " TEXT," +
                    COLUMN_CODE + " TEXT" +
                    ")";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SQRIPT);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
