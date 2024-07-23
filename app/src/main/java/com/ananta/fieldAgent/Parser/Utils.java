package com.ananta.fieldAgent.Parser;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Utils {
    private static Dialog dialog;
    public static MediaType MEDIA_TYPE_IMAGE = MediaType.parse("placeholder/*");

  static ProgressBar ivProgressBar;

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static void showCustomProgressDialog(Context context, boolean isCancel) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        if (isInternetConnected(context) && !((AppCompatActivity) context).isFinishing()) {
            dialog = new Dialog(context, R.style.progressbar);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.progressbar_dialog);
            ivProgressBar = dialog.findViewById(R.id.pbProgressBar);
            dialog.setCancelable(isCancel);
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(params);
            dialog.getWindow().setDimAmount(0);
            dialog.show();
        }
    }

    public static void hideProgressDialog(Context context) {
        if (dialog != null){
            dialog.hide();
        }
    }

    public static MultipartBody.Part makeMultipartRequestBody(Context context, String photoPath, String partName) {
        try {
            File file = new File(photoPath);
            RequestBody requestFile = RequestBody.create(file, MEDIA_TYPE_IMAGE);
            return MultipartBody.Part.createFormData(partName, context.getResources().getString(R.string.app_name), requestFile);
        } catch (NullPointerException e) {
            Log.d("AddRequestActivity", "makeMultipartRequestBody" + e.getMessage());
            return null;
        }
    }


       
}
