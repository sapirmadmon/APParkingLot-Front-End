package com.example.apparkinglot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mapAPI;
    SupportMapFragment mapFragment;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private  static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



 //        mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.mapAPI);
//
//        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }

    private void fetchLastLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude()
                    +""+currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

                    findViewById(R.id.bottomAction1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("CURRENT_LOCATION","**********lat: " + currentLocation.getLatitude() + " lng: " + currentLocation.getLongitude() + "**********");
                        }
                    });

                    SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager()
                            .findFragmentById(R.id.mapAPI);
                    supportMapFragment.getMapAsync(MapActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI = googleMap;

        LatLng afekaCollege = new LatLng(32.114892, 34.818029);
        mapAPI.addMarker(new MarkerOptions().position(afekaCollege).title("afeka").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(afekaCollege));

        LatLng school = new LatLng(32.116430, 34.818353);
        mapAPI.addMarker(new MarkerOptions().position(school).title("school"));
        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(school));

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("I'm here!");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        googleMap.addMarker(markerOptions);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                  fetchLastLocation();
                }
                break;
    }
}
}
