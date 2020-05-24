package com.example.apparkinglot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class LoginActivity extends AppCompatActivity {

    CreateUserFragment createUserFragment = new CreateUserFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, VolleyTutorialActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                CreateUserFragment fragment = new CreateUserFragment();

                fm.beginTransaction().add(R.id.container , fragment).commit();

//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.createUser_fragment_container, createUserFragment)
//                        .commit();
               // Intent intent = new Intent(LoginActivity.this, VolleyTutorialActivity.class);
               // startActivity(intent);
            }
        });
    }
}
