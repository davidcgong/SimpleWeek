package com.simpleweek.davidgong.simpleweek;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by David Gong on 1/19/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "monday_table";
    private static final String TABLE_NAME1 = "tuesday_table";
    private static final String TABLE_NAME2 = "wednesday_table";
    private static final String TABLE_NAME3 = "thursday_table";
    private static final String TABLE_NAME4 = "friday_table";
    private static final String COL1 = "task_name";
    private static final String COL2 = "task_description";
    private static final String COL3 = "start_time";
    private static final String COL4 = "end_time";
    private static final String COL0 = "ID";

    public DatabaseHelper(Context context) {
        super(context, TAG, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT);",
                TABLE_NAME, COL0, COL1, COL2, COL3, COL4);
        String createTable1 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT);",
                TABLE_NAME1, COL0, COL1, COL2, COL3, COL4);
        String createTable2 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT);",
                TABLE_NAME2, COL0, COL1, COL2, COL3, COL4);
        String createTable3 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT);",
                TABLE_NAME3, COL0, COL1, COL2, COL3, COL4);
        String createTable4 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT);",
                TABLE_NAME4, COL0, COL1, COL2, COL3, COL4);

        db.execSQL(createTable);
        db.execSQL(createTable1);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
        db.execSQL(createTable4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);

        onCreate(db);
    }


    public boolean addData(String data, String columnNumber, String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (columnNumber == "1") {
            contentValues.put(COL1, data);
        } else if (columnNumber == "2") {
            contentValues.put(COL2, data);
        } else if (columnNumber == "3") {
            contentValues.put(COL3, data);
        } else if (columnNumber == "4") {
            contentValues.put(COL4, data);
        }

        Log.d(TAG, "addData: Adding" + data + " to " + table_name);

        long result = db.insert(table_name, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + table_name;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void clearDatabase(String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM " + table_name;
        db.execSQL(clearDBQuery);
    }

//    public void editData(String table_name) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String edit
//    }

//    public Cursor getItemID(String data, String table_name) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT " + COL1 + " FROM " + table_name + " WHERE " +
//                COL2 + " = " + data + "'";
//        Cursor cursor = db.rawQuery(query, null);
//        return cursor;
//     }

//    public void deleteItem(long id, String table_name) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String theID = String.valueOf(id);
////        db.execSQL("DELETE FROM " + table_name + " WHERE _id = '" + theID + "'");
//        db.delete(table_name, theID, null);
//    }
}
