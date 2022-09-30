package com.em.insurtech.repository;

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

}
