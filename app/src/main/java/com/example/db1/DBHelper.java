package com.example.db1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "school.db";
    private static final int VER = 1;
    private static final String CREATE_TABLE = "CREATE TABLE student (_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "FIRST_NAME TEXT, LAST_NAME TEXT, COURSE TEXT)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS student";
    Context context;
    private static final String SELECT_ALL = "SELECT * FROM student";

    public DBHelper(Context context) {
        super(context, DBNAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertData(String fname, String lname, String course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("FIRST_NAME", fname);
        cv.put("LAST_NAME", lname);
        cv.put("COURSE", course);
        long result = db.insert("student", null, cv);
        db.close();
        return result;
    }

    public Cursor selectRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] cols = {"FIRST_NAME", "LAST_NAME", "COURSE"};
        Cursor records = db.rawQuery(SELECT_ALL, null);
        records.moveToFirst();
        db.close();
        return records;
    }

    public int updateRecord(String id, String fName, String lName, String course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_ID", id);
        values.put("FIRST_NAME", fName);
        values.put("LAST_NAME", lName);
        values.put("COURSE", course);
        db.update("student", values,"ID = ?", new String[]{id});
        db.close();
        return 0; //return result;
    }

}
