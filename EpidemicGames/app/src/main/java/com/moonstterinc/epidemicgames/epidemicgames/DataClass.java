package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Context;
import android.widget.Toast;

public class DataClass {
    public static Context context;
    public static int contador = 0;
    public static String UserJson = "";
    public static User usr;
    public static String  nameUsername = "TestDummy";;
    public static boolean checkUJ = false;
    public static Context  cnt = null;
    public static void msToast(String s){
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }
}
