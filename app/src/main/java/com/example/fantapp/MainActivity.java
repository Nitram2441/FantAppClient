package com.example.fantapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.fantapp.ui.FantService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    ListingAdapter adapter = new ListingAdapter();
    RecyclerView recyclerView;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->{
            //startActivity(new Intent(this, CreateActivity.class));
            startActivity(new Intent(this, TestActivity.class));
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();

        });



        adapter.setOnClickListener(position -> System.out.println("Open Listing #" + position));
        recyclerView = findViewById(R.id.listings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        FantService.getInstance().loadListings(adapter::setListings, System.out::println);

    }


}