package com.ananta.fieldAgent.Parser;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.R;

public class Utils {
    private static Dialog dialog;
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
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
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

       
}
