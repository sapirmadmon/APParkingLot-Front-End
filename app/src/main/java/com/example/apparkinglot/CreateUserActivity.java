package com.example.apparkinglot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apparkinglot.logic.Boundaries.Element.ElementBoundary;
import com.example.apparkinglot.logic.Boundaries.Element.ElementIdBoundary;
import com.example.apparkinglot.logic.Boundaries.Element.Location;
import com.example.apparkinglot.logic.Boundaries.User.NewUserDetailsBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserIdBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserRole;
import com.example.apparkinglot.logic.JsonPlaceHolderApi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateUserActivity extends AppCompatActivity {

    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private static UserBoundary userBoundary;

    private EditText email;
    private EditText username;
    private EditText avatar;
    private EditText role;
    private EditText numberCar;
    private EditText nameCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        textViewResult = findViewById(R.id.textView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.254.101:8092/acs/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        findViewById(R.id.bottomCreateUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createUserAndCarElement();


                Intent intent = new Intent(CreateUserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }



    private void createUserAndCarElement() {

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

        createUser(newUser);


        }



    private void updateUserRole(String domain, String email, UserBoundary userB) {

        Call<Void> call = jsonPlaceHolderApi.updateUserDetails(domain, email, userB);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("ON_RESPONSE_UPDATE", "Code:" + response.code());
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }
        });
    }

    private void createUser(NewUserDetailsBoundary newUser) {

        if(newUser.getRole().equals(UserRole.PLAYER)) {
            newUser.setRole(UserRole.MANAGER);
        }
        Call<UserBoundary> call = jsonPlaceHolderApi.CreateNewUser(newUser);


        call.enqueue(new Callback<UserBoundary>() {
            @Override
            public void onResponse(Call<UserBoundary> call, Response<UserBoundary> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("code: " + response.code());
                    return;
                }

                UserBoundary UserBoundaryResponse = response.body();
                String content = "";
                content += "code: " + response.code() + "\n";
                content += "domain: " + UserBoundaryResponse.getUserId().getDomain() + "\n";
                content += "email: " + UserBoundaryResponse.getUserId().getEmail() + "\n";
                content += "role: " + UserBoundaryResponse.getRole() + "\n";
                content += "user name: " + UserBoundaryResponse.getUsername() + "\n";
                content += "avatar: " + UserBoundaryResponse.getAvatar() + "\n";

                textViewResult.setText(content);

                //if(UserBoundaryResponse.getRole().equals(UserRole.PLAYER)) {

                    // TODO (Player->manager) -> craete Element car -> (manager->player)
                    //player -> manager
                    //UserBoundaryResponse.setRole(UserRole.MANAGER);
                    //updateUserRole(UserBoundaryResponse.getUserId().getDomain(), UserBoundaryResponse.getUserId().getEmail(),UserBoundaryResponse);

                    //TODO - Not Work to call this method
                    createCarElement(UserBoundaryResponse.getUserId().getDomain(), UserBoundaryResponse.getUserId().getEmail());

                    //TODO - Not Work manager -> player
                   UserBoundaryResponse.setRole(UserRole.PLAYER);
                   updateUserRole(UserBoundaryResponse.getUserId().getDomain(), UserBoundaryResponse.getUserId().getEmail(),UserBoundaryResponse);

              //  }

            }

            @Override
            public void onFailure(Call<UserBoundary> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }


    private void createCarElement(String domain, String email) {
        String type = "car";

        numberCar = findViewById(R.id.editTextNumberCar);
        nameCar = findViewById(R.id.editTextCarName);


        final HashMap<String, UserIdBoundary> myMap= new HashMap<>();
        UserIdBoundary userId = new UserIdBoundary(domain, email);
        myMap.put("userId", userId);


        //UserBoundary ub = new UserBoundary(userId, UserRole.MANAGER,null, null);
        //updateUserRole(domain, email, ub);


        //Log.d("ROLE TEST UPDATE", ub.getRole().toString());

        final HashMap<String, Object> myMapElementsAttributes= new HashMap<>();
        myMapElementsAttributes.put("car_id", numberCar.getText().toString());

        ElementBoundary elementBoundary = new ElementBoundary(new ElementIdBoundary(), type, nameCar.getText().toString(), Boolean.TRUE, null,
                new Location(0.00, 0.00), myMapElementsAttributes, myMap);


        Call<ElementBoundary> call = jsonPlaceHolderApi.CreateNewElement(domain, email, elementBoundary);

        call.enqueue(new Callback<ElementBoundary>() {
            @Override
            public void onResponse(Call<ElementBoundary> call, Response<ElementBoundary> response) {

                if (!response.isSuccessful()) {
                    //result.setText("code: " + response.code());
                    Log.d("CODE", response.code()+"");
                    return;
                }

                ElementBoundary ElementBoundaryResponse = response.body();
                String content = "";
                content += "code: " + response.code() + "\n";
                content += "domain: " + ElementBoundaryResponse.getElementId().getDomain() + "\n";
                content += "id: " + ElementBoundaryResponse.getElementId().getId() + "\n";
                content += "type: " + ElementBoundaryResponse.getType() + "\n";
                content += "name: " + ElementBoundaryResponse.getName() + "\n";
                content += "active: " + ElementBoundaryResponse.getActive() + "\n";
                content += "time: " + ElementBoundaryResponse.getCreatedTimestamp() + "\n";
                for (Map.Entry<String, UserIdBoundary> entry : myMap.entrySet()) {
                    content += "createdBy: " + entry.getKey() + ": " + entry.getValue().getDomain() + ", " + entry.getValue().getEmail() + "\n";
                }
                content += "location: " + ElementBoundaryResponse.getLocation().getLat() + "," + ElementBoundaryResponse.getLocation().getLng() + "\n";

                for (Map.Entry<String, Object> entry : myMapElementsAttributes.entrySet()) {
                    content += "Attributes: " + entry.getKey() + ": " + entry.getValue() + "\n";
                }



                Log.d("ELEMENT CAR BOUNDARY", "******************"+content);
                //result.setText(content);
            }


            @Override
            public void onFailure(Call<ElementBoundary> call, Throwable t) {
                //result.setText(t.getMessage());
                Log.d("ON FAILURE", t.getMessage());
            }
        });
    }
}
