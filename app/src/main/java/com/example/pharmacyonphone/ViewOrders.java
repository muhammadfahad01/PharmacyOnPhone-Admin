package com.example.pharmacyonphone;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class ViewOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_orders);
    }
}
