package com.example.db1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBHelper helper;
    SQLiteDatabase myDB;
    Cursor cursor;
    EditText txtFname, txtLname, txtCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DBHelper(this);
        myDB = helper.getWritableDatabase();
        cursor = helper.selectRecords();
        cursor.moveToFirst();
        showData();
    }

    public void addRecord(View v) {
        String fname = txtFname.getText().toString();
        String lname = txtLname.getText().toString();
        String course = txtCourse.getText().toString();
        long isInserted = helper.insertData(fname, lname, course);
        if(isInserted == -1) {
            Toast.makeText(this, "data not saved", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "successfully saved data", Toast.LENGTH_LONG).show();
        }
    }

    public void goFirst(View v) {
        Cursor cursor = helper.selectRecords();
        cursor.moveToFirst();
        cursor.getString(1);
        txtFname.setText(cursor.getString(0));
        txtLname.setText(cursor.getString(1));
        txtCourse.setText(cursor.getString(2));
        helper.close();
    }

    public void clearEntries(View v) {
        txtFname.setText("");
        txtLname.setText("");
        txtCourse.setText("");
        txtFname.requestFocus();
    }

    public void showData() {
        txtFname.setText(cursor.getString(1));
        txtLname.setText(cursor.getString(2));
        txtCourse.setText(cursor.getString(3));
    }

    public void moveNext(View v) {
        if(cursor.getPosition() == cursor.getCount()-1){
            cursor.moveToLast();
            Toast.makeText(this, "Last record", Toast.LENGTH_LONG).show();
            }
        else {
            cursor.moveToNext();
            showData();
        }
    }

    public void movePrevious(View v) {
        if(cursor.getPosition() == 0) {
            cursor.moveToFirst();
            showData();
            Toast.makeText(this, "First Record", Toast.LENGTH_SHORT).show();
        }
        else {
            cursor.moveToPrevious();
            showData();
        }
    }

    public void moveLast(View v) {
        cursor.moveToLast();
        showData();
    }

    public void editRecord (View v) {
        int id = cursor.getInt(0);
        String fn = txtFname.getText().toString();
        String ln = txtLname.getText().toString();
        String course = txtCourse.getText().toString();

        int isUpdated = helper.updateRecord("" + id, fn, ln, course);
        if(isUpdated != -1) {
            Toast.makeText(this, "Successfully updated", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_LONG).show();
        }
    }
}