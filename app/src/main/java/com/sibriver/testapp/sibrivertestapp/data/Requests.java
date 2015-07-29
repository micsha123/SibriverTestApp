package com.sibriver.testapp.sibrivertestapp.data;

import com.sibriver.testapp.sibrivertestapp.model.Request;

import java.util.ArrayList;

public class Requests {

    private static Requests instance;
    private ArrayList<Request> requests;

    private Requests() {
        requests = new ArrayList<Request>();
        requests.add(new Request(1, "xcvdd", 0, "asdsdsg", "13.12312", "12.354353", "123.asdda"));
        requests.add(new Request(1, "nater", 1, "rewrwe", "13.12312", "12.354353", "a123s.asdda"));
        requests.add(new Request(1, "iopiop", 2, "hsgsdg", "13.12312", "12.354353", "a44s.asdda"));
        requests.add(new Request(1, "sdfgdfg", 3, "djjfsfsfs0", "13.12312", "12.354353", "as.asdda"));
        requests.add(new Request(1, "eddt", 2, "uuuufsfs0", "13.12312", "12.354353", "67.asdda"));
        requests.add(new Request(1, "xcvdd", 0, "asdsdsg", "13.12312", "12.354353", "123.asdda"));
        requests.add(new Request(1, "nater", 1, "rewrwe", "13.12312", "12.354353", "a123s.asdda"));
        requests.add(new Request(1, "iopiop", 2, "hsgsdg", "13.12312", "12.354353", "a44s.asdda"));
        requests.add(new Request(1, "sdfgdfg", 3, "djjfsfsfs0", "13.12312", "12.354353", "as.asdda"));
        requests.add(new Request(1, "eddt", 2, "uuuufsfs0", "13.12312", "12.354353", "67.asdda"));

    }

    public static synchronized Requests getInstance() {
        if (instance == null) {
            instance = new Requests();
        }
        return instance;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void removeRequest(Request request){
        requests.remove(request);
    }

}
