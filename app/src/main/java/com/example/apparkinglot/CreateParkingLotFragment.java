package com.example.apparkinglot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apparkinglot.logic.Boundaries.Element.ElementBoundary;
import com.example.apparkinglot.logic.Boundaries.Element.ElementIdBoundary;
import com.example.apparkinglot.logic.Boundaries.Element.Location;
import com.example.apparkinglot.logic.Boundaries.User.UserIdBoundary;
import com.example.apparkinglot.logic.JsonPlaceHolderApi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateParkingLotFragment extends Fragment {

    MyURL myUrl = new MyURL();

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private EditText name;
    private EditText lng;
    private EditText lat;
    private EditText content;

    //  private TextView textViewResult;

    String email = LoginActivity.email.getText().toString();
    String domain = LoginActivity.domain.getText().toString();


    public CreateParkingLotFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_parking_lot, container, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myUrl.getBaseURL())
                //.baseUrl("http://172.16.254.101:8092/acs/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //click on "create parking lot" button
        Button bParkingLot = view.findViewById(R.id.bottomCreateParkingLotElement);
        bParkingLot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("BUTTON", "create parking lot BUTTON PRESSED");
                createParkingLotElement(domain, email);

                Toast.makeText(getActivity() , "your parking-lot saved", Toast.LENGTH_SHORT).show();

                getFragmentManager().beginTransaction().remove(CreateParkingLotFragment.this).commit(); //out from the fragment
            }
        });


        //click on "exit" (x) button
        Button bExit = view.findViewById(R.id.buttonExit);
        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "exit BUTTON PRESSED");
                getFragmentManager().beginTransaction().remove(CreateParkingLotFragment.this).commit(); ////out from the fragment
            }
        });

        return view;
    }



    private void createParkingLotElement(String domain, String email) {

        String type = "parking_lot";
        name = getView().findViewById(R.id.editTextNameParkingLot);
        lat = getView().findViewById(R.id.editTextLat);
        lng = getView().findViewById(R.id.editTextLng);
        content = getView().findViewById(R.id.editTextContents);

        final HashMap<String, UserIdBoundary> myMap= new HashMap<>();
        UserIdBoundary userId = new UserIdBoundary(domain, email);
        myMap.put("userId", userId);

        String[] arrCar = new String[0];
        final HashMap<String, Object> myMapElementsAttributes= new HashMap<>();
        myMapElementsAttributes.put("carCounter", 0);
        myMapElementsAttributes.put("carList", arrCar);
        myMapElementsAttributes.put("capacity", Integer.parseInt(content.getText().toString()));


        ElementBoundary elementBoundary = new ElementBoundary(new ElementIdBoundary(), type, name.getText().toString(), Boolean.TRUE, null,
                new Location(Double.parseDouble(lat.getText().toString()),
                        Double.parseDouble(lng.getText().toString())), myMapElementsAttributes, myMap);


        Call<ElementBoundary> call = jsonPlaceHolderApi.CreateNewElement(domain, email, elementBoundary);

        call.enqueue(new Callback<ElementBoundary>() {
            @Override
            public void onResponse(Call<ElementBoundary> call, Response<ElementBoundary> response) {

                if (!response.isSuccessful()) {
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



                Log.d("CREATE PARKING_LOT", content);

            }

            @Override
            public void onFailure(Call<ElementBoundary> call, Throwable t) {
                //result.setText(t.getMessage());
                Log.d("ON FAILURE", t.getMessage());
            }
        });
    }
}
