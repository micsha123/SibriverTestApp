package com.sibriver.testapp.sibrivertestapp.data;

import android.content.Context;

import com.sibriver.testapp.sibrivertestapp.model.Request;

import java.util.ArrayList;

public class Requests{

    private static Requests instance;
    private Context context;

    private ArrayList<Request> requests;
    private SQLDatabaseHelper dbHelper;

    private Requests(Context context) {

        requests = new ArrayList<Request>();
        this.context = context;
        this.dbHelper = new SQLDatabaseHelper(context);
//
//        requests.add(new Request(1, "xcvdd", 0, "asdsdsg", "13.12312", "12.354353", "123.asdda"));
//        requests.add(new Request(1, "nater", 1, "rewrwe", "13.12312", "12.354353", "a123s.asdda"));
//        requests.add(new Request(1, "iopiop", 2, "hsgsdg", "13.12312", "12.354353", "a44s.asdda"));
//        requests.add(new Request(1, "sdfgdfg", 3, "djjfsfsfs0", "13.12312", "12.354353", "as.asdda"));
//        requests.add(new Request(1, "eddt", 2, "uuuufsfs0", "13.12312", "12.354353", "67.asdda"));
//        requests.add(new Request(1, "xcvdd", 0, "asdsdsg", "13.12312", "12.354353", "123.asdda"));
//        requests.add(new Request(1, "nater", 1, "rewrwe", "13.12312", "12.354353", "a123s.asdda"));
//        requests.add(new Request(1, "iopiop", 2, "hsgsdg", "13.12312", "12.354353", "a44s.asdda"));
//        requests.add(new Request(1, "sdfgdfg", 3, "djjfsfsfs0", "13.12312", "12.354353", "as.asdda"));
//        requests.add(new Request(1, "eddt", 2, "uuuufsfs0", "13.12312", "12.354353", "67.asdda"));
    }

    public static synchronized Requests getInstance(Context context) {
        if (instance == null) {
            instance = new Requests(context);
        }
        return instance;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests.addAll(requests);
    }

    public void removeRequest(Request request){
        requests.remove(request);
    }

    public void saveRequestsToDB(){
        for(Request request : requests){
            dbHelper.insertRequest(request.getId(),
                    request.getName(),
                    request.getAddress(),
                    request.getStatus(),
                    request.getCreated(),
                    request.getLat(),
                    request.getLon());
        }
    }

//    public void loadRequestsFromDB(){
//        Cursor cursor = dbHelper.getRequests();
//        cursor.moveToFirst();
//        while(!cursor.isAfterLast()) {
//            requests.add(new Request(cursor.getInt(cursor.getColumnIndex(SQLDatabaseHelper.COLUMN_ID)),
//                    cursor.getString(cursor.getColumnIndex(SQLDatabaseHelper.COLUMN_NAME)),
//                    cursor.getInt(cursor.getColumnIndex(SQLDatabaseHelper.COLUMN_STATUS)),
//                    cursor.getString(cursor.getColumnIndex(SQLDatabaseHelper.COLUMN_ADDRESS)),
//                    cursor.getString(cursor.getColumnIndex(SQLDatabaseHelper.COLUMN_LAT)),
//                    cursor.getString(cursor.getColumnIndex(SQLDatabaseHelper.COLUMN_LONG)),
//                    cursor.getString(cursor.getColumnIndex(SQLDatabaseHelper.COLUMN_CREATED))));
//            cursor.moveToNext();
//        }
//    }

}
