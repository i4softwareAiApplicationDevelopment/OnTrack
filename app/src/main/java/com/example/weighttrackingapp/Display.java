package com.example.weighttrackingapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Display extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> weightList, dateList;

    DatabaseManager dbManager;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize layout
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.edit);
        // Initialize database
        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        // Initialize arrays to store weight and date data
        weightList = new ArrayList<>();
        dateList = new ArrayList<>();
        // Initialize views
        recyclerView = findViewById(R.id.weightRecycler);
        adapter = new Adapter(this, weightList, dateList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Display data
        displayData();
    }

    private void displayData() {
        // Get database information of weights and dates
        Cursor cursor = dbManager.fetch();
        // If there is no data
        if (cursor.getCount()==0) {
            Toast.makeText(Display.this, "No data", Toast.LENGTH_SHORT).show();
        }
        else {
            // Move cursor to first item
            if (cursor.moveToFirst()) {
                do {
                    // Add weight and date to array
                    weightList.add(cursor.getString(1));
                    dateList.add(cursor.getString(2));

                } while(cursor.moveToNext()); // Move cursor to next in array
            }

        }

    }
}
