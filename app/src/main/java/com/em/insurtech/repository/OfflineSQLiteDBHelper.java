package com.em.insurtech.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OfflineSQLiteDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "insurtech_db";
    public static final String CLAIM_TABLE_NAME = "claim";
    public static final String CLAIM_COLUMN_ID = "_id";
    public static final String CLAIM_COLUMN_NAME = "name";
    public static final String CLAIM_COLUMN_AMOUNT= "amount";
    public static final String CLAIM_COLUMN_DEPENDENT = "dependent";

    public OfflineSQLiteDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + CLAIM_TABLE_NAME + " (" +
                CLAIM_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CLAIM_COLUMN_NAME + " TEXT, " +
                CLAIM_COLUMN_AMOUNT + " INT, " +
                CLAIM_COLUMN_DEPENDENT + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addNewCourse(String courseName, String courseDuration, String courseDescription, String courseTracks) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(CLAIM_COLUMN_ID, courseName);
        values.put(CLAIM_COLUMN_NAME, courseDuration);
        values.put(CLAIM_COLUMN_AMOUNT, courseDescription);
        values.put(CLAIM_COLUMN_DEPENDENT, courseTracks);

        // after adding all values we are passing
        // content values to our table.
        db.insert(CLAIM_TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

}
