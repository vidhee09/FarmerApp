package com.ananta.fieldAgent.Parser;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {


    private static final String PREF_NAME = "PREF";
    private static final Preference preference = new Preference();
    private static SharedPreferences app_preferences;
    private static String MOBILE_NUMBER = "Mobile_number";
    private static String NAME = "name";

    public static Preference getInstance(Context context) {
        app_preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
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
