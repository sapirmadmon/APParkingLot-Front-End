package com.example.apparkinglot;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.apparkinglot.logic.Boundaries.Element.ElementBoundary;
import com.example.apparkinglot.logic.JsonPlaceHolderApi;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class GetElementsFragment extends Fragment {

    MyURL myUrl = new MyURL();

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private TextView result;

    String email = LoginActivity.email.getText().toString();
    String domain = LoginActivity.domain.getText().toString();

    private Spinner spinnerType;
    private String type;

    public GetElementsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_elements, container, false);

        spinnerType = (Spinner) view.findViewById(R.id.spinnerType);

        spinner();

        result = view.findViewById(R.id.textViewResultGetElemants);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myUrl.getBaseURL())
                //.baseUrl("http://172.16.254.101:8092/acs/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        GetAllElements();

        Button bExit = view.findViewById(R.id.buttonExit);
        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "exit BUTTON PRESSED");
                getFragmentManager().beginTransaction().remove(GetElementsFragment.this).commit(); ////out from the fragment
            }
        });

        return view;
    }


    private void spinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
                Log.d("TYPE SPINNER", type);
                if (type.equals("all types")) {
                    GetAllElements();
                } else {
                    GetByTypeElements();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        //spinnerType.setOnItemSelectedListener(this);
    }


    private void GetByTypeElements() {

        Call<ElementBoundary[]> call = jsonPlaceHolderApi.getElementsByType(domain, email, type);

        call.enqueue(new Callback<ElementBoundary[]>() {
            @Override
            public void onResponse(Call<ElementBoundary[]> call, Response<ElementBoundary[]> response) {

                if (!response.isSuccessful()) {
                    result.setText("code: " + response.code() + " " + response.message());
                    result.setTextColor(Color.RED);
                    return;
                }

                ElementBoundary[] ElementArrBoundaryResponse = response.body();

                Gson gson = new Gson();

                String content = "";
                content += "code: " + response.code() + "\n";

                for (ElementBoundary eb : ElementArrBoundaryResponse) {
                    content += gson.toJson(eb) + "\n";
                    content += "\n";

                }

                Log.d("CONTENT_BY_TYPE", content);
                result.setText(content);
            }

            @Override
            public void onFailure(Call<ElementBoundary[]> call, Throwable t) {
                result.setText(t.getMessage());
                Log.d("ON_FAILURE", t.getMessage());
            }
        });
    }

    private void GetAllElements() {

        Call<ElementBoundary[]> call = jsonPlaceHolderApi.RetreiveElementArr(domain, email);

        call.enqueue(new Callback<ElementBoundary[]>() {
            @Override
            public void onResponse(Call<ElementBoundary[]> call, Response<ElementBoundary[]> response) {

                if (!response.isSuccessful()) {
                    result.setText("code: " + response.code() + " " + response.message());
                    result.setTextColor(Color.RED);
                    return;
                }

                ElementBoundary[] ElementArrBoundaryResponse = response.body();

                Gson gson = new Gson();
//                gson.toJson(ElementArrBoundaryResponse);
//
//                ElementBoundary elements[] = null;
//                elements = gson.fromJson(gson.toJson(ElementArrBoundaryResponse), ElementBoundary[].class);
//                Log.d("ELEMENTS", elements[0].getName());

                String content = "";
                content += "code: " + response.code() + "\n";

                for (ElementBoundary eb : ElementArrBoundaryResponse) {
                    content += gson.toJson(eb) + "\n";
                    content += "\n";

                }


                Log.d("CONTENT_ALL", content);

                result.setText(content);

            }

            @Override
            public void onFailure(Call<ElementBoundary[]> call, Throwable t) {
                result.setText(t.getMessage());
                Log.d("RR", "********************************&&&#$");
            }
        });
    }
}
