package com.example.apparkinglot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class Map2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);


        findViewById(R.id.buttonUpdate).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("BUTTON","****** click on button record list ******");

                FragmentManager fm = getSupportFragmentManager();
                UpdateDetailsFragment fragment = new UpdateDetailsFragment();

                fm.beginTransaction().add(R.id.container2 , fragment).commit();
            }
        });


    }
}
