package com.example.apparkinglot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.apparkinglot.logic.Boundaries.User.UserBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserIdBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserRole;
import com.google.gson.Gson;


public class VolleyTutorialActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_tutorial);

        Gson gson = new Gson();
        UserIdBoundary userId = new UserIdBoundary("domainSapir","sapir@gmail.com");
        UserBoundary userB = new UserBoundary(userId, UserRole.PLAYER, "sapir", ":)");

        String json = gson.toJson(userB);

        findViewById(R.id.buttonMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolleyTutorialActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }
}
