package com.sibriver.testapp.sibrivertestapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLDatabaseHelper {

    private static final String TAG = "SQLDatabaseHelper";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sibriver_testapp.db";

    private static final String TABLE_REQUESTS = "requests_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_CREATED = "created";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_LAT = "latitude";
    public static final String COLUMN_LONG = "longitude";

    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase database;

    public SQLDatabaseHelper(Context context) {
        openHelper = new DatabaseOpenHelper(context);
        database = openHelper.getWritableDatabase();
    }

    public boolean checkIfExist(){
        String buildSQL = "SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = '" + TABLE_REQUESTS + "'";
        Cursor cursor = database.rawQuery(buildSQL, null);
        if(cursor != null) {
            if(cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void insertRequest(int id, String name, String address, int status, String created,
                              String latitude, String longitude) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_STATUS, status);
        contentValues.put(COLUMN_CREATED, created);
        contentValues.put(COLUMN_LAT, latitude);
        contentValues.put(COLUMN_LONG, longitude);
        database.insert(TABLE_REQUESTS, null, contentValues);
    }

    public void deleteRequests()
    {
        database.delete(TABLE_REQUESTS, null, null);
    }

    public Cursor getRequests() {
        String buildSQL = "SELECT * FROM " + TABLE_REQUESTS;
        Log.d(TAG, "getMarkers SQL: " + buildSQL);
        return database.rawQuery(buildSQL, null);
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper {

        public DatabaseOpenHelper(Context aContext) {
            super(aContext, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String buildMarkersSQL = "CREATE TABLE " + TABLE_REQUESTS + "( " + COLUMN_ID + " INTEGER, " +
                    COLUMN_NAME + " TEXT, " + COLUMN_ADDRESS + " TEXT, "+ COLUMN_STATUS + " INTEGER, " +
                    COLUMN_CREATED + " TEXT, " + COLUMN_LAT + " REAL, " + COLUMN_LONG + " REAL )";

            Log.d(TAG, "onCreate SQL: " + buildMarkersSQL);

            sqLiteDatabase.execSQL(buildMarkersSQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

            String buildMarkersSQL = "DROP TABLE IF EXISTS " + TABLE_REQUESTS;

            Log.d(TAG, "onUpgrade SQL: " + buildMarkersSQL);

            sqLiteDatabase.execSQL(buildMarkersSQL);

            onCreate(sqLiteDatabase);
        }
    }

}
