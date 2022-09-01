package com.svalero.aa1pmdmrecu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addClient(View view) {
        Intent intent = new Intent(this, AddClientActivity.class);
        startActivity(intent);
    }

    public void addCar(View view) {
        Intent intent = new Intent(this, AddCarActivity.class);
        startActivity(intent);
    }

    public void addOrder(View view) {
        Intent intent = new Intent(this, AddOrderActivity.class);
        startActivity(intent);
    }

    public void viewCar(View view) {
        Intent intent = new Intent(this, ViewCarActivity.class);
        startActivity(intent);
    }
    public void viewOrder(View view) {
        Intent intent = new Intent(this, ViewOrderActivity.class);
        startActivity(intent);
    }
    public void viewClient(View view) {
        Intent intent = new Intent(this, ViewClientActivity.class);
        startActivity(intent);
    }





}