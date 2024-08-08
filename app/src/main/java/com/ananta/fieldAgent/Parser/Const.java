package com.ananta.fieldAgent.Parser;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Const {


    public static String AGENT_ID = "";
    public static String AGENT_NAME = "";
    public static String COMPANY_NAME = "";
    public static String MOBILE_NUMBER = "";
    public static String COMPLAINT_NUMBER = "";
    public static String COMPLAINT_NAME = "";
    public static boolean SiteReport = false;
    public static Integer ID;
    //    public static String FARMER_ID = "";
    public static String SERVER_TOKEN = "";
    public static String LOGIN_FARMER_ID = "";

    public static String IMAGE_URL = "https://farmer.idglock.com/images/uploads/";

//    public static String IMAGE_URL = "http://192.168.1.14:8000/images/uploads/";

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static void hideSoftKeyboard(AppCompatActivity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void openSoftKeyboard(AppCompatActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }

}
