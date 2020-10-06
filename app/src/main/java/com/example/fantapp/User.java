package com.example.fantapp;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    String userId;
    String eMail;

    public User(){

    }

    public User(JSONObject jsonObject) throws JSONException{
        setUserId(jsonObject.getString("userid"));
        if (jsonObject.has("email")){
            seteMail(jsonObject.getString("email"));
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
