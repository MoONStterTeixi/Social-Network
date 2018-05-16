package com.moonstterinc.epidemicgames.epidemicgames;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment3 extends Fragment {

    private EditText etnick, etgenre;

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        Toast.makeText(getContext(), "Frag : 2", Toast.LENGTH_LONG).show();

        EditText et_username = (EditText) view.findViewById(R.id.fetusername);



        String value = getArguments().getString(DataClass.nickFrag);
        et_username.setText(value);

    }

}
