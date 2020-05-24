package com.example.apparkinglot;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateUserFragment extends Fragment {


//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;


    public CreateUserFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment CreateUserFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static CreateUserFragment newInstance(String param1, String param2) {
//        CreateUserFragment fragment = new CreateUserFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       // return inflater.inflate(R.layout.fragment_create_user, container, false);

         View view = inflater.inflate(R.layout.fragment_create_user, container, false);

    Button b = (Button) view.findViewById(R.id.bottomCreateUser);

        b.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //TODO postURL of create user

            getFragmentManager().beginTransaction().remove(CreateUserFragment.this).commit(); //exit from the fragment

            // if (CreateUserFragment.this.mListener != null) {
//                     CreateUserFragment.this.mListener.viewAnimationRequested();

//                }
//
        }

    });
        return view;
}

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        try {
//            CreateUserFragmentListener listener = (CreateUserFragmentListener) context;
//
//            if (listener != null)
//                this.registerListener(listener);
//        } catch (ClassCastException e) {
//
//            throw new ClassCastException(context.toString() + " must implement CreateUserFragmentListener");
//
//        }
//
//    }
//
//    public void registerListener(CreateUserFragmentListener listener) {
//        this.mListener = listener;
//    }


}
