package com.sibriver.testapp.sibrivertestapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** Helper class for managing app databases */
public class SQLDatabaseHelper {

    private static final String TAG = "SQLDatabaseHelper";
    /** Database name */
    private static final int DATABASE_VERSION = 1;
    /** Database version */
    private static final String DATABASE_NAME = "sibriver_testapp.db";

    /** Name of table with requests*/
    private static final String TABLE_REQUESTS = "requests_table";
    /** Column names */
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_CREATED = "created";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_LAT = "latitude";
    public static final String COLUMN_LONG = "longitude";

    /** Name of table consist of deleted IDs */
    private static final String TABLE_DELETED = "deleted_table";

    /** SQLHelper for managing database */
    private DatabaseOpenHelper openHelper;
    /** SQLite Database */
    private SQLiteDatabase database;

    public SQLDatabaseHelper(Context context) {
        openHelper = new DatabaseOpenHelper(context);
        database = openHelper.getWritableDatabase();
    }

    /** Method provides addin Request object to database */
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
    /** Method provides adding ID of deleted item to database */
    public void insertDeleted(int id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, id);
        database.insert(TABLE_DELETED, null, contentValues);
    }
    /** Method provides deleting Request object from database */
    public void deleteRequest(int id)
    {
        database.delete(TABLE_REQUESTS, COLUMN_ID + "=" + Integer.toString(id), null);
    }
    /** Method provides erasing databases for debugging */
    public void deleteDBs()
    {
        database.delete(TABLE_REQUESTS, null, null);
        database.delete(TABLE_DELETED, null, null);
    }
    /** Method returns cursor with all Request objects from database */
    public Cursor getRequests() {
        String buildSQL = "SELECT * FROM " + TABLE_REQUESTS;
        Log.d(TAG, "getRequests SQL: " + buildSQL);
        return database.rawQuery(buildSQL, null);
    }
    /** Method returns cursor with all Request objects from database */
    public Cursor getDeletedIDs() {
        String buildSQL = "SELECT * FROM " + TABLE_DELETED;
        Log.d(TAG, "getDeleted SQL: " + buildSQL);
        return database.rawQuery(buildSQL, null);
    }

    /** openhelper for creating and updating databases */
    private class DatabaseOpenHelper extends SQLiteOpenHelper {

        public DatabaseOpenHelper(Context aContext) {
            super(aContext, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            /** request for creating Request database */
            String buildRequestsSQL = "CREATE TABLE " + TABLE_REQUESTS + "( " + COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT, " + COLUMN_ADDRESS +
                    " TEXT, "+ COLUMN_STATUS + " INTEGER, " + COLUMN_CREATED + " TEXT, " +
                    COLUMN_LAT + " REAL, " + COLUMN_LONG + " REAL )";

            /** request for creating DeleteIDs database */
            String buildDeletedSQL = "CREATE TABLE " + TABLE_DELETED + "( " + COLUMN_ID + " INTEGER PRIMARY KEY)";

            Log.d(TAG, "onCreate SQL: " + buildRequestsSQL);

            sqLiteDatabase.execSQL(buildRequestsSQL);
            sqLiteDatabase.execSQL(buildDeletedSQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

            String buildRequestsSQL = "DROP TABLE IF EXISTS " + TABLE_REQUESTS;
            String buildDeletedSQL = "DROP TABLE IF EXISTS " + TABLE_DELETED;

            Log.d(TAG, "onUpgrade SQL: " + buildRequestsSQL);

            sqLiteDatabase.execSQL(buildRequestsSQL);
            sqLiteDatabase.execSQL(buildDeletedSQL);
            onCreate(sqLiteDatabase);
        }
    }

}
