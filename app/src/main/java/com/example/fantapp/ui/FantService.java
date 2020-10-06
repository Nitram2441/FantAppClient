package com.example.fantapp.ui;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fantapp.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FantService implements Response.ErrorListener {
    static FantService SINGELTON;

    User user;

    String token;
    RequestQueue requestQueue;

    public static FantService initialize(Context context, String token){
        SINGELTON = new FantService(context, token);
        return SINGELTON;
    }

    public static FantService getInstance(){
        return SINGELTON;
    }

    public FantService(Context context, String token){
        this.token = token;
        this.requestQueue = Volley.newRequestQueue(context);
        loadUser();
    }

    @Override
    public void onErrorResponse(VolleyError error){
        System.out.println("Error: " + error);
    }

    public User getUser(){
        return user;
    }

    public void loadUser(){
        requestQueue.add(new SecuredJsonObjectRequest(Request.Method.GET, "http://169.254.8.28:8080/WebappTwo/api/auth/currentuser", null,
                response -> {
                    try{
                        user = new User(response);
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                }, this));
    }

    protected Map<String, String> getHeaders(){
        HashMap<String, String> result = new HashMap<>();
        result.put("Authorization", "Bearer " + token);
        return result;
    }

    class SecuredJsonObjectRequest extends JsonObjectRequest{
        public SecuredJsonObjectRequest(int method, String url, @Nullable JSONObject jsonRequest,
                                        Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener){
            super(method, url, jsonRequest, listener, errorListener);
        }

        @Override
        public Map<String, String> getHeaders(){
            return FantService.this.getHeaders();
        }
    }
}
