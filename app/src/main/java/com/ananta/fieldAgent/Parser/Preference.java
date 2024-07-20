package com.ananta.fieldAgent.Parser;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {


    private static final String PREF_NAME = "PREF";
    private static final Preference preference = new Preference();
    private static SharedPreferences app_preferences;
    private static String MOBILE_NUMBER = "Mobile_number";
    private static String NAME = "name";
    private final String IS_WELCOME = "is_welcome";
    public  String FARMER_LOGIN_ID = "farmer_login_id";
    public  String FARMER_NAME = "farmer_name";
    public  String FARMER_NUM = "farmer_num";





    public static Preference getInstance(Context context) {
        app_preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preference;
    }

    public static void putMobileNUmber(String number) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(MOBILE_NUMBER, number);
        edit.apply();
    }

    public void putFarmerLoginId(String farmerLoginId) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(FARMER_LOGIN_ID, farmerLoginId);
        edit.apply();
    }

    public String getFarmerLoginId(){
        return app_preferences.getString(FARMER_LOGIN_ID, "");
    }

    public void putFarmerName(String farmerName) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(FARMER_NAME, farmerName);
        edit.apply();
    }

    public String getFarmerName(){
        return app_preferences.getString(FARMER_NAME, "");
    }

    public void putFarmerNum(String farmerName) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(FARMER_NUM, farmerName);
        edit.apply();
    }

    public String getFarmerNum(){
        return app_preferences.getString(FARMER_NUM, "");
    }

    public void putIsHideWelcomeScreen(boolean isHide) {
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putBoolean(IS_WELCOME, isHide);
        editor.apply();
    }

    public boolean getIsHideWelcomeScreen() {
        return app_preferences.getBoolean(IS_WELCOME, false);
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
