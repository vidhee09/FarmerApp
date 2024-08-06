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
    private final String IS_WELCOME = "is_welcome";
    public  String FARMER_LOGIN_ID = "farmer_login_id";
    public  String FARMER_NAME = "farmer_name";
    public  String FARMER_NUM = "farmer_num";
    public  String TOKEN = "token";


    public Preference() {}

    public static Preference getInstance(Context context) {
        app_preferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
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

    public String getToken(){
        return app_preferences.getString(TOKEN, "");
    }

    public String putToken(String token) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(TOKEN, token);
        edit.apply();
        return token;
    }




    /* -------agent------ */

    /*    use for agent after login     */
    public static String AGENT_ID = "agent_id";
    public static String AGENT_NAME = "agent_name";
    public static String COMPANY_NAME = "company_name";
    public static String COMPLAINT_NUMBER = "complaint_number";
    public static String COMPLAINT_NAME = "complaint_name";
    public static String AGNET_MobileNo = "mobile_number";
    public static Integer  ID ;
    public static String FARMER_ID = "farmer_id";
    public static String PROFILE_IMAGE = "image";

    public static String AGENT_FARMER_ID = "farmer_id";


    public String getAgentName(){
        return app_preferences.getString(AGENT_NAME, "");
    }

    public void putAgentName(String agent_name) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(AGENT_NAME, agent_name);
        edit.apply();
    }

    public String getAgentId(){
        return app_preferences.getString(AGENT_ID, "");
    }

    public void putAgentID(String agent_id) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(AGENT_ID, agent_id);
        edit.apply();
    }

    public String getAgent(){
        return app_preferences.getString(FARMER_ID, "");
    }

    public void putAgentFarmerID(String far_id) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(FARMER_ID, far_id);
        edit.apply();
    }

    public String getAgentNumber(){
        return app_preferences.getString(AGNET_MobileNo, "");
    }

    public void putAgentNumber(String mobile) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(AGNET_MobileNo, mobile);
        edit.apply();
    }

    public String getAgentFarmerId(){
        return app_preferences.getString(AGENT_FARMER_ID, "");
    }

    public void putAgentFarmerId(String farmId) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(AGENT_FARMER_ID, farmId);
        edit.apply();
    }
 public String getCompanyName(){
        return app_preferences.getString(COMPANY_NAME, "");
    }

    public void putCompanyName(String companyName) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(COMPANY_NAME, companyName);
        edit.apply();
    }

    public String getProfileImage(){
        return app_preferences.getString(PROFILE_IMAGE, "");
    }

    public void putProfileImage(String image) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(PROFILE_IMAGE, image);
        edit.apply();
    }

}
