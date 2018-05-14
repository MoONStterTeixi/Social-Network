package com.moonstterinc.epidemicgames.epidemicgames;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment2 extends Fragment {

    private EditText etpwd, etrepwd;
    private Button bt_eviar;

    public IntroFragment2() {
        // Required empty public constructor

    }

    public static android.support.v4.app.Fragment newInstance() {
        IntroFragment2 fragment = new IntroFragment2();


        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro2, container, false);


    }

}
