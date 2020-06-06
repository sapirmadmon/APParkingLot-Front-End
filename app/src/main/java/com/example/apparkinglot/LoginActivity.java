package com.example.apparkinglot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apparkinglot.logic.Boundaries.Element.ElementBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserRole;
import com.example.apparkinglot.logic.JsonPlaceHolderApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    MyURL myUrl = new MyURL();

    public static EditText email;
    public static EditText domain;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        result = findViewById(R.id.textViewRes);
        email = findViewById(R.id.editTextEmail);
        domain =findViewById(R.id.editTextDomain);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myUrl.getBaseURL())
                //.baseUrl("http://172.16.254.101:8092/acs/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(domain.getText().toString(), email.getText().toString());

            }
        });

        findViewById(R.id.buttonSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateUserActivity.class);
                startActivity(intent);
            }
        });
    }



    private void loginElement() {


        Call<ElementBoundary> call = jsonPlaceHolderApi.RetreiveElement(domain.getText().toString() , email.getText().toString() ,
                null , null ); //element ID & element Domain

        Log.d("ERROR", email.getText().toString() + "********" + domain.getText().toString());

        call.enqueue(new Callback<ElementBoundary>() {
            @Override
            public void onResponse(Call<ElementBoundary> call, Response<ElementBoundary> response) {

                if(!response.isSuccessful()) {
                    result.setText("code: "+ response.code() + " " + response.message());
                    result.setTextColor(Color.RED);
                    return;
                }

                ElementBoundary ElementBoundaryResponse = response.body();
                String  content="";

                Log.d("ERROR", content);

                result.setText(content);


            }

            @Override
            public void onFailure(Call<ElementBoundary> call, Throwable t) {
                result.setText(t.getMessage());
                Log.d("RR", "********************************&&&#$");
            }
        });
    }


    private void login(String toString, String toString1) {


        Call<UserBoundary> call = jsonPlaceHolderApi.Login(domain.getText().toString(), email.getText().toString());
        Log.d("ERROR", email.getText().toString() + "********" + domain.getText().toString());

        call.enqueue(new Callback<UserBoundary>() {
            @Override
            public void onResponse(Call<UserBoundary> call, Response<UserBoundary> response) {

                if(!response.isSuccessful()) {
                    result.setText("code: "+ response.code() + " " + response.message());
                    result.setTextColor(Color.RED);
                    return;
                }

                UserBoundary UserBoundaryResponse = response.body();
                String  content="";
                content += "code: " + response.code() + "\n";

                Log.d("CONTENT", content);

                result.setText(content);

                if(UserBoundaryResponse.getRole().equals(UserRole.PLAYER)) {
                    Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(LoginActivity.this, ManagerActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<UserBoundary> call, Throwable t) {
                result.setText(t.getMessage());
                Log.d("ERROR", "on Failure");
            }
        });
    }
}

