package com.ananta.fieldAgent.Parser;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.ananta.fieldAgent.Models.Siteinspectionn;

public class Preference {

    private static final String PREF_NAME = "PREF";
    private static final Preference preference = new Preference();
    private static SharedPreferences app_preferences ;
    private static String MOBILE_NUMBER = "Mobile_number";
    private static String NAME = "name";
    public static Siteinspectionn siteinspectionn;
//    SharedPreferences sharedPreferences = getSharedPreferences("sharedData", MODE_PRIVATE);

    public Preference() {}

    public  Siteinspectionn getSiteinspectionn() {
        return siteinspectionn;
    }

    public static Preference getInstance(Context context) {
        app_preferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return preference;
    }

    public static void putMobileNUmber(String number) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(MOBILE_NUMBER, number);
        edit.apply();
    }


    public static String getMobileNumber(){
        return app_preferences.getString(MOBILE_NUMBER, "");
    }

    public static void putName(String name) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(NAME, name);
        edit.apply();
    }

    public static String getString(String name){
        return app_preferences.getString(NAME, "");
    }

}
