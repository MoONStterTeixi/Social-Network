package com.moonstterinc.epidemicgames.epidemicgames;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment1 extends Fragment {

    public IntroFragment1() {
        // Required empty public constructor
    }

    public static android.support.v4.app.Fragment newInstance() {
        IntroFragment1 fragment = new IntroFragment1();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro1, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here

        Toast.makeText(getContext(), "Frag : 1", Toast.LENGTH_LONG).show();

        EditText et_username = (EditText) view.findViewById(R.id.nickfrag);
        DataClass.nickFrag = et_username.getText().toString();


        //Put the value
        IntroFragment1 ldf = new IntroFragment1 ();
        Bundle args = new Bundle();
        args.putString("YourKey", "YourValue");
        ldf.setArguments(args);

//Inflate the fragment
        getFragmentManager().beginTransaction().add(R.id.container, ldf).commit();
    }

}
