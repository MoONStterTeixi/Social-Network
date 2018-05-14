package com.moonstterinc.epidemicgames.epidemicgames;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment1 extends Fragment {

    private EditText etnick, etgenre;


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

        View view = inflater.inflate(R.layout.fragment_intro1, container, false);

        //boton = (Button)view.findViewById(R.id.main_button);
        etnick = view.findViewById(R.id.nick);
        etgenre = view.findViewById(R.id.nick);

        DataClass.user = etnick.toString();

        return inflater.inflate(R.layout.fragment_intro1, container, false);
    }

}
