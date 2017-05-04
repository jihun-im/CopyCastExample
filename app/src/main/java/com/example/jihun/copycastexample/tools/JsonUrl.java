package com.example.jihun.copycastexample.tools;

/**
 * Created by Jihun on 2017-05-04.
 */

public class JsonUrl {
    String id;
    String longUrl;

    public JsonUrl(String id, String longUrl) {
        this.id = id;
        this.longUrl = longUrl;
    }

    public JsonUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getId() {
        return id;
    }

    public String getLongUrl() {
        return longUrl;
    }
}
