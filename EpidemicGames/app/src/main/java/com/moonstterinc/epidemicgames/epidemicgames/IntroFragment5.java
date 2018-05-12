package com.moonstterinc.epidemicgames.epidemicgames;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment5 extends Fragment {


    public IntroFragment5() {
        // Required empty public constructor
    }

    public static android.support.v4.app.Fragment newInstance() {
        IntroFragment5 fragment = new IntroFragment5();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro5, container, false);
    }
}
