package com.moonstterinc.epidemicgames.epidemicgames;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment3 extends Fragment {


    public IntroFragment3() {
        // Required empty public constructor
    }

    public static android.support.v4.app.Fragment newInstance() {
        IntroFragment3 fragment = new IntroFragment3();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro3, container, false);
    }

}
