package com.example.weighttrackingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Main extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 1;
    // Get variables for calender and its format
    Calendar calender = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
    private LineChart weightChart;

    DatabaseManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize layout and views
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.main);
        Button weightSubmit = (Button) findViewById(R.id.submitButton);
//        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        Button allowButton = (Button) findViewById(R.id.allowButton);
        EditText userWeight = (EditText) findViewById(R.id.userWeight);
        Button showWeights = (Button) findViewById(R.id.showWeights);
        // Initialize DATE and weight chart
        String DATE = simpleDateFormat.format(calender.getTime());
        weightChart = (LineChart) findViewById(R.id.weightChart);
        // Initialize database
        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        // Display weight chart
        weightChart.setScaleEnabled(false);
        ArrayList<Entry> yValues = new ArrayList<>();
        // Get database information
        Cursor cursor = dbManager.fetch();
        int counter = 0;
        // Loop through cursor and map it to graph
        if (cursor.moveToFirst()) {
            do {
                String ID = cursor.getString(0);
                float weight = Float.parseFloat(cursor.getString(1));
                String date = cursor.getString(2);
                yValues.add(new Entry(counter, weight));
                counter++;
            }while (cursor.moveToNext());
        }
        // Graph styles
        LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");
        set1.setFillAlpha(110);
        set1.setLabel("Weight lbs");
        set1.setLineWidth(3f);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        // Plot data onto the graph
        weightChart.setData(data);

        // Onclick for adding weight
        weightSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user inputted weight and call the addWeight database function
                dbManager.addWeight(1, Integer.parseInt(userWeight.getText().toString()), DATE);
                // After complete, refresh page
                finish();
                startActivity(getIntent());
            }
        });
        // Onclick for viewing weight
        showWeights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start display class
                Intent i = new Intent(getApplicationContext(), Display.class);
                startActivity(i);
            }
        });
//        // Onclick for delete button
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Use the delete database function that takes in weight being deleted
//                dbManager.delete(userWeight.getText().toString());
//                // Refresh page after
//                finish();
//                startActivity(getIntent());
//            }
//        });
        // Onclick for asking for permission
        allowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if permission has been asked
                checkPermission(Manifest.permission.SEND_SMS, SMS_PERMISSION_CODE);
            }
        });
    }
    public void checkPermission(String permission, int requestCode)
    {
        // If permission hasn't been granted
        if (ContextCompat.checkSelfPermission(Main.this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(Main.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(Main.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);
        // Response of asking for permission
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Main.this, "SMS Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(Main.this, "SMS Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
    }
}
