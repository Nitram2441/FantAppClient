package com.example.fantapp;

import org.json.JSONException;
import org.json.JSONObject;

public class Listing {
    int listingId;
    String title;
    String description;
    int price;


    public Listing(JSONObject jsonObject) throws JSONException{
        setId(jsonObject.getInt("id"));
        if (jsonObject.has("title")){
            setTitle(jsonObject.getString("title"));
        }
        if (jsonObject.has("description")){
            setDescription(jsonObject.getString("description"));
        }
        if(jsonObject.has("price")){
            setPrice(jsonObject.getInt("price"));
        }
    }

    public Listing(){

    }

    public int getId() {
        return listingId;
    }

    public void setId(int id) {
        this.listingId = listingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
