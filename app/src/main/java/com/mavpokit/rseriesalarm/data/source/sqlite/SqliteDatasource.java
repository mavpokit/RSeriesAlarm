package com.mavpokit.rseriesalarm.data.source.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.data.source.IRepository;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by Alex on 31.01.2017.
 */

public class SqliteDatasource implements IRepository{
    private static SqliteDatasource INSTANCE;
    private DbHelper dbHelper;

    private SqliteDatasource(@NonNull Context context) {
        checkNotNull(context);
        dbHelper = new DbHelper(context);
    }

    public static SqliteDatasource getInstance(@NonNull Context context){
        if (INSTANCE==null) INSTANCE = new SqliteDatasource(context);
        return INSTANCE;
    }


    @Override
    public List<AlarmObject> getObjects() {
        List<AlarmObject> objects = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DbHelper.COLUMN_ID,
                DbHelper.COLUMN_NAME,
                DbHelper.COLUMN_NUMBER,
                DbHelper.COLUMN_CODE
        };

        Cursor c = db.query(
                DbHelper.TABLE_OBJECTS, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndexOrThrow(DbHelper.COLUMN_ID));
                String name = c.getString(c.getColumnIndexOrThrow(DbHelper.COLUMN_NAME));
                String number =c.getString(c.getColumnIndexOrThrow(DbHelper.COLUMN_NUMBER));
                String code =c.getString(c.getColumnIndexOrThrow(DbHelper.COLUMN_CODE));
                AlarmObject alarmObject = new AlarmObject(id,name,number,code);
                objects.add(alarmObject);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        return objects;
    }

    @Override
    public void insertObject(AlarmObject alarmObject) {
        checkNotNull(alarmObject);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        //ID column is not assigned, because it is autoincremented
        values.put(DbHelper.COLUMN_NAME, alarmObject.getName());
        values.put(DbHelper.COLUMN_NUMBER, alarmObject.getNumber());
        values.put(DbHelper.COLUMN_CODE, alarmObject.getCode());

        db.insert(dbHelper.TABLE_OBJECTS, null, values);

        db.close();

    }

    @Override
    public void updateObject(AlarmObject alarmObject) {
        checkNotNull(alarmObject);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DbHelper.COLUMN_NAME, alarmObject.getName());
        values.put(DbHelper.COLUMN_NUMBER, alarmObject.getNumber());
        values.put(DbHelper.COLUMN_CODE, alarmObject.getCode());

        String selection = DbHelper.COLUMN_ID+ " LIKE ?";
        String[] selectionArgs = { alarmObject.getId() };

        db.update(DbHelper.TABLE_OBJECTS, values, selection, selectionArgs);

        db.close();
    }

    @Override
    public void deleteObject(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = DbHelper.COLUMN_ID+ " LIKE ?";
        String[] selectionArgs = { id };

        db.delete(dbHelper.TABLE_OBJECTS, selection, selectionArgs);

        db.close();

    }

}
