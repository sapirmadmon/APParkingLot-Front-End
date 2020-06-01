package com.example.apparkinglot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apparkinglot.logic.Boundaries.Action.ActionBoundary;
import com.example.apparkinglot.logic.Boundaries.Action.ActionIdBoundary;
import com.example.apparkinglot.logic.Boundaries.Action.ElementOfAction;
import com.example.apparkinglot.logic.Boundaries.Action.InvokingUser;
import com.example.apparkinglot.logic.Boundaries.Element.ElementBoundary;
import com.example.apparkinglot.logic.Boundaries.Element.ElementIdBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserIdBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserRole;
import com.example.apparkinglot.logic.JsonPlaceHolderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    GoogleMap mapAPI;
    SupportMapFragment mapFragment;
    //private TextView result;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    String email = LoginActivity.email.getText().toString();
    String domain = LoginActivity.domain.getText().toString();


    private static double lng;
    private static double lat;

    private String elementCarId;
    private String elementCarDomain;

    //private Button bUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();


       // result = findViewById(R.id.textResult);


        loadData();
        Log.d("ELEMENT_CAR", "##########  " + elementCarId + " , " + elementCarDomain + "  ##########");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.254.101:8092/acs/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);






        ImageView bottomUpdate = (ImageView)findViewById(R.id.bottomUpdateDetails);

        if (bottomUpdate == null)
            Log.d("ERROR NULL", "bottomUpdate is null");

        bottomUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                UpdateDetailsFragment fragment = new UpdateDetailsFragment();

                fm.beginTransaction().add(R.id.container1, fragment).commit();
            }
        });



        //        mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.mapAPI);
//
//        mapFragment.getMapAsync(this);


    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(CreateUserActivity.SHARED_PREFS, MODE_PRIVATE);
        this.elementCarDomain = sharedPreferences.getString(CreateUserActivity.CAR_DOMAIN, "-1");
        this.elementCarId = sharedPreferences.getString(CreateUserActivity.CAR_ID, "-1");

    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude()
                            + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();


                    lat = currentLocation.getLatitude();
                    lng = currentLocation.getLongitude();
                    Log.d("TEST_LOCATION", lat + ", "+ lng);



                    //calls to Actions
                    ImageView bottomPark = (ImageView)findViewById(R.id.bottomParkAction);
                    bottomPark.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("CURRENT_LOCATION_PARK", "**********lat: " + currentLocation.getLatitude() + " lng: " + currentLocation.getLongitude() + "**********");

                            InvokeAction("park" , lat, lng);

                            //LatLng park = new LatLng(lat, lng);
                            //mapAPI.addMarker(new MarkerOptions().position(park).title("parking Caught ").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            //mapAPI.moveCamera(CameraUpdateFactory.newLatLng(park));
                        }
                    });

                    ImageView bottomDepart = (ImageView)findViewById(R.id.bottomDepartAction);
                    bottomDepart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("CURRENT_LOCATION_DEPART", "**********lat: " + currentLocation.getLatitude() + " lng: " + currentLocation.getLongitude() + "**********");
                            InvokeAction("depart" , lat , lng);
                        }
                    });

                    ImageView bottomSearch = (ImageView)findViewById(R.id.bottomSearchAction);
                    bottomSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("CURRENT_LOCATION_SEARCH", "**********lat: " + currentLocation.getLatitude() + " lng: " + currentLocation.getLongitude() + "**********");

                            InvokeAction("search", lat , lng);
                        }
                    });


                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.mapAPI);
                    supportMapFragment.getMapAsync(MapActivity.this);
                }
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI = googleMap;

        LatLng prak2 = new LatLng(32.114892, 34.818029);
        mapAPI.addMarker(new MarkerOptions().position(prak2).title("parking Caught").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(prak2));

        LatLng park3 = new LatLng(32.116430, 34.818353);
        mapAPI.addMarker(new MarkerOptions().position(park3).title("parking Caught").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(park3));

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("I'm here!");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        googleMap.addMarker(markerOptions);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                }
                break;
        }
    }




    private void InvokeAction(final String getType, double lat, double lng) {
        Log.d("ACTION BOUNDARY", "********** Start **********");
        String type = getType;

        UserIdBoundary userId = new UserIdBoundary(domain, email);
        InvokingUser invokingUser = new InvokingUser(userId);

        ElementIdBoundary elementIdCar = new ElementIdBoundary(elementCarDomain, elementCarId);
        ElementOfAction eoa = new ElementOfAction(elementIdCar);

        Map<String, Object> actionAttributes = new HashMap<>();
        actionAttributes.put("location" , new com.example.apparkinglot.logic.Boundaries.Element.Location(lat, lng));

        ActionBoundary actionBoundary = new ActionBoundary(new ActionIdBoundary(), type, eoa,
                null, invokingUser, actionAttributes);


        Call<Object> call = jsonPlaceHolderApi.invokeAction(actionBoundary);

        Log.d("CALL", "********** after call **********");

        call.enqueue(new Callback<Object>() {


            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {


                if (!response.isSuccessful()) {
                    //result.setText("code: " + response.code());
                    Log.d("CODE", response.code()+""+response.errorBody());
                    return;
                }


                Object actionBoundaryResponse = response.body();

                Log.d("OBJECT", actionBoundaryResponse.toString());

                //TODO Park
                if(getType.equals("park")){
                    Log.d("PARK","*********press park*********");
                   // if(actionBoundaryResponse.equals(Boolean.TRUE))
                        Toast.makeText(getApplicationContext(),getType + " successful",Toast.LENGTH_SHORT).show();
                   // else
                   //     Toast.makeText(getApplicationContext(),getType + " not succeed",Toast.LENGTH_SHORT).show();
                }

                //TODO Depart
                if(getType.equals("depart")){
                    Log.d("PARK","*********press depart*********");
                    //if(actionBoundaryResponse.equals(true))
                        Toast.makeText(getApplicationContext(),getType + " successful",Toast.LENGTH_SHORT).show();
                   // else
                   //     Toast.makeText(getApplicationContext(),getType + " not succeed",Toast.LENGTH_SHORT).show();
                }

                //Search
                if(getType.equals("search")) {

                    Gson gson = new Gson();
                    gson.toJson(actionBoundaryResponse);

                    ElementBoundary elements [] = null;
                    elements = gson.fromJson(gson.toJson(actionBoundaryResponse),ElementBoundary[].class);
                  //  Log.d("ELEMENTS", elements[0].getName());
                  //  Log.d("ELEMENTS", elements[1].getName());


                    for (ElementBoundary eb : elements) {
                        Log.d("LOCATION", eb.getLocation().getLat().toString() + " , " + eb.getLocation().getLng().toString());

                        if(eb.getType().equals("parking_lot")) {
                            LatLng park = new LatLng(eb.getLocation().getLat(), eb.getLocation().getLng());
                            mapAPI.addMarker(new MarkerOptions().position(park).title("parking").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            mapAPI.moveCamera(CameraUpdateFactory.newLatLng(park));
                        }
                        else { //type is parking
                            LatLng park = new LatLng(eb.getLocation().getLat(), eb.getLocation().getLng());
                            mapAPI.addMarker(new MarkerOptions().position(park).title("parking").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            mapAPI.moveCamera(CameraUpdateFactory.newLatLng(park));
                        }

                    }

                }


                Log.d("ACTION BOUNDARY", "********** DONE **********");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                //result.setText(t.getMessage());
                Log.d("ON FAILURE", t.getMessage());
            }
        });
    }




}
