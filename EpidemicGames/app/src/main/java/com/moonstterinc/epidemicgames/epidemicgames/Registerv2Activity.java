package com.moonstterinc.epidemicgames.epidemicgames;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registerv2Activity extends AppCompatActivity {

    public static Vibrator vb1;
    public Button nextScreen;
    private EditText et_username, et_fetusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerv2);

        /***/
        vb1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        /**
         *  Create the adapter that will return a fragment
         *  for each of the N primary sections of the activity.
         *  */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        /** Set up the ViewPager with the sections adapter.*/
        ViewPager mViewPager = findViewById(R.id.containerIntroScreen);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        Reference();

       // etpwd.setText(DataClass.nameUsername);

    }

    public void Reference(){
        et_username = findViewById(R.id.nickfrag);
        et_fetusername  =findViewById(R.id.fetusername);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case 0:
                    DataClass.contadorFragments = 1;
                    return IntroFragment1.newInstance();


                case 1:
                    DataClass.contadorFragments = 2;
                    return IntroFragment2.newInstance();

                case 2:
                    DataClass.contadorFragments = 3;
                    return IntroFragment3.newInstance();

                default:
                    return IntroFragment3.newInstance();
            }
        }


        @Override
        public int getCount() {
            return 3;
        }

    }
}
