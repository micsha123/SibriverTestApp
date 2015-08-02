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

    public void setRequests(ArrayList<Request> requests) {
        this.requests.addAll(requests);
    }

    public void removeRequest(Request request){
        requests.remove(request);
    }

    private ArrayList<Request> getStatusRequests(int status){
        ArrayList<Request> statusRequests = new ArrayList<Request>();
        for(Request request : requests){
            if(request.getStatus() == status){
                statusRequests.add(request);
            }
        }
        return statusRequests;
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
}
