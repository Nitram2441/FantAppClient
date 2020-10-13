package com.example.fantapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.fantapp.ui.FantService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class CreateActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //FantService.getInstance().testMe();
/*
        final EditText titleEditText = findViewById(R.id.editTextTextPersonName);
        String titleText = titleEditText.toString();
        final EditText descriptionEditText = findViewById(R.id.editTextTextMultiLine);
        String descriptionText = descriptionEditText.toString();
        final EditText priceEditText = findViewById(R.id.editTextNumber);
        String priceNumberString = priceEditText.toString();
        int priceNumber = Integer.parseInt(priceNumberString);
        final Button createListingButton = findViewById(R.id.button);

        Listing listing = new Listing();
        listing.setTitle(titleText);
        listing.setPrice(priceNumber);
        listing.setDescription(descriptionText);
*/

        //FantService.getInstance().addToRequestQueue(jsonObjRequest);
        Listing listing = new Listing();
        listing.setPrice(300);
        listing.setDescription("testDescription");
        listing.setTitle("testTitle");
        new PostMessageTask(listings -> {

            System.out.println(listing.getTitle());
        }, this::onException).execute(listing);
    }

    protected void onException(Throwable throwable){

    }

    public static final String BOUNDARY = "ghur9ehioghr9e84u9whforuig8e";
/*
    private String createPostBody(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            if (params.get(key) != null) {
                sb.append("\r\n" + "--" + BOUNDARY + "\r\n");
                sb.append("Content-Disposition: form-data; name=\""
                        + key + "\"" + "\r\n\r\n");
                sb.append(params.get(key) + "\n");

            }
        }

        System.out.println(sb.toString());
        return sb.toString();
    }
*/
    StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
            "http://169.254.8.28:8080/WebappTwo/api/listings/createwithpicture",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Log.i(TAG, "Success");
                }
            }, new Response.ErrorListener() {



        @Override
        public void onErrorResponse(VolleyError error) {
            VolleyLog.d("volley", "Error: " + error.getMessage());
            error.printStackTrace();
            //Log.e(TAG, "Success");
        }
    }) {


        @Override
        protected Map<String, String> getParams(){
            Map<String,String> params = new HashMap<>();
            params.put("title", "title");
            params.put("description", "description");
            params.put("price", "300");
            return params;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            final HashMap<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
            headers.put("Authorization", "Bearer " + FantService.getInstance().getToken());
            return headers;
        }
    };
}