package com.example.apparkinglot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apparkinglot.logic.Boundaries.User.NewUserDetailsBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserRole;
import com.example.apparkinglot.logic.JsonPlaceHolderApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateUserActivity extends AppCompatActivity {

    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private EditText email;
    private EditText username;
    private EditText avatar;
    private EditText role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        textViewResult = findViewById(R.id.textView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.14.20:8092/acs/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        findViewById(R.id.bottomCreateUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();

                Intent intent = new Intent(CreateUserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }



    private void createUser() {

        email = findViewById(R.id.editTextEmail);
        username = findViewById(R.id.editTextUserName);
        role = findViewById(R.id.editTextRole);
        avatar = findViewById(R.id.editTextAvatar);
        UserRole userRole = UserRole.ADMIN;

        if(role.getText().toString().equals(UserRole.PLAYER.name()))
            userRole = UserRole.PLAYER;
        if(role.getText().toString().equals(UserRole.ADMIN.name()))
            userRole = UserRole.ADMIN;
        if(role.getText().toString().equals(UserRole.MANAGER.name()))
            userRole = UserRole.MANAGER;
        //TODO exception if null
        //if(role.equals(null))
        //

        // NewUserDetailsBoundary newUser = new NewUserDetailsBoundary("email@gmail.com", UserRole.PLAYER, "username","avatar");
        NewUserDetailsBoundary newUser = new NewUserDetailsBoundary(email.getText().toString(), userRole, username.getText().toString() ,avatar.getText().toString());

        Log.d("ERROR", email.getText().toString() +"*****" + userRole +"****"+ username.getText().toString() + "****" + avatar.getText().toString());

        Call<UserBoundary> call = jsonPlaceHolderApi.CreateNewUser(newUser);

        call.enqueue(new Callback<UserBoundary>() {
            @Override
            public void onResponse(Call<UserBoundary> call, Response<UserBoundary> response) {

                if(!response.isSuccessful()) {
                    textViewResult.setText("code: "+ response.code());
                    return;
                }

                UserBoundary UserBoundaryResponse = response.body();
                String  content="";
                content += "code: " + response.code() + "\n";
                content += "domain: " + UserBoundaryResponse.getUserId().getDomain() + "\n";
                content += "email: " + UserBoundaryResponse.getUserId().getEmail() + "\n";
                content += "role: " + UserBoundaryResponse.getRole() + "\n";
                content += "user name: " + UserBoundaryResponse.getUsername() + "\n";
                content += "avatar: " + UserBoundaryResponse.getAvatar() + "\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<UserBoundary> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
