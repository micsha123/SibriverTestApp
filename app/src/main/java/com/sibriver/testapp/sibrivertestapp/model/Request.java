package com.sibriver.testapp.sibrivertestapp.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
/**
 * Model for parsing requests from JSON by LoganSquare
 * Contains getters and setters.
 * */
@JsonObject
public class Request{

    /** id field */
    @JsonField
    public int id;
    /** name field */
    @JsonField
    public String name;
    /** status field */
    @JsonField
    public int status;
    /** address field */
    @JsonField
    public String address;
    /** lat field */
    @JsonField
    public String lat;
    /** lon field */
    @JsonField
    public String lon;
    /** created field */
    @JsonField
    public String created;

    /**
     * Constructor for LoganSquare
     * */
    public Request(){}

    /**
     * Constructor for loading from DataBase
     * */
    public Request(int id, String name, int status, String address, String lat, String lon, String created) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.address = address;
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
