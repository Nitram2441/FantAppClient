package com.example.fantapp.ui;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fantapp.Listing;
import com.example.fantapp.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public String getToken(){
        return token;
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
    //might have to make ListingList or figure out a way to just use Listing

    public void loadListings(Callback<List<Listing>> onPostExecute, Response.ErrorListener onError){
        //System.out.println("1");
        requestQueue.add(new SecuredJsonArrayRequest(Request.Method.GET, "http://169.254.8.28:8080/WebappTwo/api/listings/getlistings", null,
                response -> {
                                List<Listing> result = new ArrayList<>();
                                try{
                                    for(int i = 0; i < response.length(); i++){
                                        Listing listing = new Listing(response.getJSONObject(i));
                                        System.out.println(listing.getTitle());
                                        result.add(new Listing(response.getJSONObject(i)));
                                    }
                                } catch (JSONException e){
                                    onError.onErrorResponse(new VolleyError(e));
                                }
                                onPostExecute.onPostExecute(result);

                },onError));
    }


    public void addToRequestQueue(StringRequest request){
        requestQueue.add(request);
    }

    protected Map<String, String> getHeaders(){
        HashMap<String, String> result = new HashMap<>();
        System.out.println("Token: " + token);
        result.put("Authorization", "Bearer " + token);
        return result;
    }

    public interface Callback<Result>{
        void onPostExecute(Result result);
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


    class SecuredJsonArrayRequest extends JsonArrayRequest{
        public SecuredJsonArrayRequest(int method, String url, @Nullable JSONArray jsonRequest,
                                        Response.Listener<JSONArray> listener, @Nullable Response.ErrorListener errorListener){
            super(method, url, jsonRequest, listener, errorListener);
        }

        @Override
        public Map<String, String> getHeaders(){
            return FantService.this.getHeaders();
        }
    }
}
