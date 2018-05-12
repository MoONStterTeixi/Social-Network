package com.moonstterinc.epidemicgames.epidemicgames;


import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class Registerv2Activity extends AppCompatActivity {

    public static Vibrator vb1;
    public Button nextScreen;

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
                    return IntroFragment1.newInstance();

                case 1:
                    return IntroFragment2.newInstance();

                case 2:
                    return IntroFragment3.newInstance();

                case 3:
                    return IntroFragment4.newInstance();

                case 4:
                    return IntroFragment5.newInstance();

                default:
                    return IntroFragment5.newInstance();
            }
        }


        @Override
        public int getCount() {
            return 5;
        }

    }
}
