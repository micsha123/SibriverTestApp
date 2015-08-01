package com.sibriver.testapp.sibrivertestapp.model;


import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;

@JsonObject
public class Response {

    @JsonField
    public int code;

    @JsonField
    public ArrayList<Request> response;
}
