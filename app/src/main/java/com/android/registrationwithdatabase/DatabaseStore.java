package com.android.registrationwithdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseStore extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "registration.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USER = "user_store";

    public static final String COLUMN_FULL_NAME = "user_full_name";
    public static final String COLUMN_EMAIL = "user_email";
    public static final String COLUMN_PASSWORD = "user_password";

    Context context;

    public DatabaseStore(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USER + "(" +
                COLUMN_FULL_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT , " +
                COLUMN_PASSWORD + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }
}
