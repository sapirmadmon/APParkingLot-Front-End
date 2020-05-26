package com.example.apparkinglot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateDetailsFragment extends Fragment {

    private EditText updateUserName;
    private EditText updateRole;
    private EditText updateAvatar;


    public UpdateDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_details, container, false);

        //String email = ((LoginActivity) this.getActivity()).email.getText().toString();
        //String domain = ((LoginActivity) this.getActivity()).domain.getText().toString();
        //Log.d("TEST", "#####" + email + " " + domain + "#######");


        //click on "update details" button
        Button bUpdate = (Button) view.findViewById(R.id.buttonUpdateDetails);
        if (bUpdate == null)
            Log.d("ERROR NULL", "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "update details BUTTON PRESSED");

                //TODO url update
                //TODO Pull email and domain from login window
                updateDetails("2020b.tamir.reznik", "sapir@gmail.com");


                getFragmentManager().beginTransaction().remove(UpdateDetailsFragment.this).commit(); //out from the fragment
            }
        });


        //click on "exit" (x) button
        Button bExit = (Button) view.findViewById(R.id.buttonExit);
        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "exit BUTTON PRESSED");
                getFragmentManager().beginTransaction().remove(UpdateDetailsFragment.this).commit(); ////out from the fragment
            }
        });


        return view;
    }

    private void updateDetails(String toString, String toString1) {

        updateUserName = getView().findViewById(R.id.editTextUpdateUserName);
        updateRole = getView().findViewById(R.id.editTextUpdateRole);
        updateAvatar = getView().findViewById(R.id.editTextUpdateAvatar);


    }
}
