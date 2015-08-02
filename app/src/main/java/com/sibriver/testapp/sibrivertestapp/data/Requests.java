package com.sibriver.testapp.sibrivertestapp.data;

import android.content.Context;
import android.database.Cursor;

import com.sibriver.testapp.sibrivertestapp.model.Request;

import java.util.ArrayList;

public class Requests{

    private static Requests instance;
    private Context context;

    private ArrayList<Request> requests;
    private ArrayList<Integer> deletedIDs;
    private SQLDatabaseHelper dbHelper;

    private Requests(Context context) {
        this.context = context;
        this.dbHelper = new SQLDatabaseHelper(context);
        loadRequestsFromDB();
        loadDeletedIDsFromDB();
    }

    public static synchronized Requests getInstance(Context context) {
        if (instance == null) {
            instance = new Requests(context);
        }
        return instance;
    }

    public ArrayList<Request> getRequests(int status) {
        switch (status){
            case 0:
                return requests;
            case 1:
                return getStatusRequests(1);
            case 2:
                return getStatusRequests(0);
            case 3:
                return getStatusRequests(3);
            default:
                return getStatusRequests(2);
        }
    }

    public void removeRequest(Request request){
        dbHelper.deleteRequest(request.getId());
        dbHelper.insertDeleted(request.getId());
        requests.remove(request);
    }

    private ArrayList<Request> getStatusRequests(int status){
        ArrayList<Request> statusRequests = new ArrayList<Request>();
        for(Request request : requests) {
            if (request.getStatus() == status) {
                statusRequests.add(request);
            }
        }
        return statusRequests;
    }

    public void saveRequestsToDB(ArrayList<Request> requests){
        loadDeletedIDsFromDB();
        for(Request request : requests){
            dbHelper.insertRequest(request.getId(),
                    request.getName(),
                    request.getAddress(),
                    request.getStatus(),
                    request.getCreated(),
                    request.getLat(),
                    request.getLon());
        }
        for(Integer i: deletedIDs){
            dbHelper.deleteRequest(i);
        }
    }

    private void loadDeletedIDsFromDB(){
        deletedIDs = new ArrayList();
        Cursor cursor = dbHelper.getDeletedIDs();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            deletedIDs.add(cursor.getInt(cursor.getColumnIndex(SQLDatabaseHelper.COLUMN_ID)));
            cursor.moveToNext();
        }
    }

    public void loadRequestsFromDB(){
        requests = new ArrayList<>();
        Cursor cursor = dbHelper.getRequests();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            requests.add(new Request(
                    cursor.getInt(cursor.getColumnIndex(SQLDatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(SQLDatabaseHelper.COLUMN_NAME)),
                    cursor.getInt(cursor.getColumnIndex(SQLDatabaseHelper.COLUMN_STATUS)),
                    cursor.getString(cursor.getColumnIndex(SQLDatabaseHelper.COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(SQLDatabaseHelper.COLUMN_LAT)),
                    cursor.getString(cursor.getColumnIndex(SQLDatabaseHelper.COLUMN_LONG)),
                    cursor.getString(cursor.getColumnIndex(SQLDatabaseHelper.COLUMN_CREATED))));
            cursor.moveToNext();
        }
    }

    public void deleteDB(){
        dbHelper.deleteDBs();
    }
}

