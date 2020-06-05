package com.example.apparkinglot;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.apparkinglot.logic.Boundaries.User.UserBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserIdBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserRole;
import com.example.apparkinglot.logic.JsonPlaceHolderApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateDetailsFragment extends Fragment {

    MyURL myUrl = new MyURL();

    private EditText updateUserName;
    private EditText updateRole;
    private EditText updateAvatar;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
  //  private TextView textViewResult;

    String email = LoginActivity.email.getText().toString();
    String domain = LoginActivity.domain.getText().toString();




    public UpdateDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_details, container, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myUrl.getBaseURL())
                //.baseUrl("http://172.16.254.101:8092/acs/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //click on "update user" button
        Button bUpdate = view.findViewById(R.id.bottomUpdateUser);
        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "update details BUTTON PRESSED");

                updateDetails();

                Toast.makeText(getActivity(),"Update Done!",Toast.LENGTH_SHORT).show();

                getFragmentManager().beginTransaction().remove(UpdateDetailsFragment.this).commit(); //out from the fragment
            }
        });

        //click on "exit" (x) button
        Button bExit = view.findViewById(R.id.buttonExit);
        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "exit BUTTON PRESSED");
                getFragmentManager().beginTransaction().remove(UpdateDetailsFragment.this).commit(); ////out from the fragment
            }
        });


        return view;
    }

    //private void updateDetails(String userDomain, String userEmail)
    private void updateDetails() {

        updateUserName = getView().findViewById(R.id.editTextUpdateUserName);
        updateRole = getView().findViewById(R.id.editTextUpdateRole);
        updateAvatar = getView().findViewById(R.id.editTextUpdateAvatar);
        UserRole userRole = null;

        if(updateRole.getText().toString().equals(UserRole.PLAYER.name()))
            userRole = UserRole.PLAYER;
        if(updateRole.getText().toString().equals(UserRole.ADMIN.name()))
            userRole = UserRole.ADMIN;
        if(updateRole.getText().toString().equals(UserRole.MANAGER.name()))
            userRole = UserRole.MANAGER;


        UserBoundary userUpdate = new UserBoundary(new UserIdBoundary(domain, email),
                userRole, updateUserName.getText().toString(), updateAvatar.getText().toString());

        Call<Void> call = jsonPlaceHolderApi.updateUserDetails(domain,email,userUpdate);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                //textViewResult.setText("Code: " + response.code());
                Log.d("ON_RESPONSE", "Code:" + response.code());
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
                Log.d("onFailure", t.getMessage());
            }
        });


    }


}
