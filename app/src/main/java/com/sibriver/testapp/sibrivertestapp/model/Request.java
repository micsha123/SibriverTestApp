package com.sibriver.testapp.sibrivertestapp.model;


public class Request {

    private int id;
    private String name;
    private int status;
    private String address;
    private String lat;
    private String lon;
    private String created;

    public Request(int id, String name, int status, String address, String lat,
                   String lon, String created){

        this.id = id;
        this.address = address;
        this.name = name;
        this.status = status;
        this.lat = lat;
        this.lon = lon;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getCreated() {
        return created;
    }
}
