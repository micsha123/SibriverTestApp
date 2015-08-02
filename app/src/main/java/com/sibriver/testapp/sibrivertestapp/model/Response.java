package com.sibriver.testapp.sibrivertestapp.model;


import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;
/**
 * Model for parsing JSON from server by LoganSquare
 * */
@JsonObject
public class Response {

    /**
     * Request code fiend
     * */
    @JsonField
    public int code;

    /**
     * list of requests in JSON
     * */
    @JsonField
    public ArrayList<Request> response;
}
