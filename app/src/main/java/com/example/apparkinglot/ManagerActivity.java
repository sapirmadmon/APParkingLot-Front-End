package com.example.apparkinglot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apparkinglot.logic.Boundaries.Element.ElementBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserRole;
import com.example.apparkinglot.logic.JsonPlaceHolderApi;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManagerActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);


        findViewById(R.id.buttonUpdateDetailsManager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                UpdateDetailsFragment fragment = new UpdateDetailsFragment();
                fm.beginTransaction().replace(R.id.containerM, fragment).commit();
            }
        });


        findViewById(R.id.buttonCreateParkingLot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                CreateParkingLotFragment fragment = new CreateParkingLotFragment();
                fm.beginTransaction().replace(R.id.containerM, fragment).commit();
            }
        });

        findViewById(R.id.buttonGetAllElements).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                GetElementsFragment fragment = new GetElementsFragment();
                fm.beginTransaction().replace(R.id.containerM, fragment).commit();
            }
        });

        ImageView buttonLogout= (ImageView)findViewById(R.id.logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }



}

