package com.sibriver.testapp.sibrivertestapp.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class Request{

    @JsonField
    public int id;
    @JsonField
    public String name;
    @JsonField
    public int status;
    @JsonField
    public String address;
    @JsonField
    public String lat;
    @JsonField
    public String lon;
    @JsonField
    public String created;

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
