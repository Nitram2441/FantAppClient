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

import com.example.fantapp.ui.FantService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

public class CreateActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }




}
