package com.ananta.fieldAgent.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Activity.farmer.FarmerDashboardActivity;
import com.ananta.fieldAgent.Activity.fieldAgent.MainActivity;
import com.ananta.fieldAgent.Models.LoginModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityVerifyOtpscreen2Binding;
import com.chaos.view.PinView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOTPScreen extends AppCompatActivity {

    ActivityVerifyOtpscreen2Binding binding;
    String OTP = "", Number = "";
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otpscreen2);

        OTP = getIntent().getStringExtra("OTP");
        Number = getIntent().getStringExtra("NUMBER");

        binding.pinView.setFocusable(true);
        binding.pinView.setText(OTP);

        binding.tvOtpMobileNumber.setText("+91" + Number);

        binding.btnOTPVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginWithOtp(OTP, binding.pinView);

            }
        });

        binding.tvResend.setOnClickListener(view1 -> {
            binding.tvSetTime.setVisibility(View.VISIBLE);
            binding.tvResend.setVisibility(View.GONE);
            binding.pinView.getText().clear();

//            callResendApi();
        });

        countDownTimerFun();

    }

    private void loginWithOtp(String otp, PinView pinView) {

//        Utils.showCustomProgressDialog(VerifyOTPScreen.this,true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobile_number", Number);
        hashMap.put("otp", otp);

        Call<LoginModel> call = apiInterface.getOtpVerify(hashMap);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                SharedPreferences sharedPreferences = getSharedPreferences("sharedData",MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                if (response.isSuccessful()) {
//                    Utils.hideProgressDialog(VerifyOTPScreen.this);
                    pinView.getText().clear();

                    if (response.body().getType().equals("agent")){

                        Intent intent = new Intent(VerifyOTPScreen.this, MainActivity.class);
                        Const.AGENT_ID = response.body().getUser_id();
                        Const.AGENT_NAME = response.body().getUser_name();
                        editor.putString("agentLogin", Const.AGENT_ID);
                        editor.putString("agentName",Const.AGENT_NAME);
                        Log.d("Name===","=aa=="+Const.AGENT_NAME);
                        editor.commit();
                        Const.COMPANY_NAME = response.body().getUser_companyname();
                        Const.MOBILE_NUMBER = response.body().getMobile_number();
                        startActivity(intent);
                        finish();

                    }else if (response.body().getType().equals("farmer")){

                        Intent intent = new Intent(VerifyOTPScreen.this, FarmerDashboardActivity.class);
                        startActivity(intent);
                    }


                } else {
//                    Utils.showCustomProgressDialog(verifyOTPScreen.this,true);
                    Toast.makeText(VerifyOTPScreen.this, "Invalid Otp, please enter valid otp", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

                Toast.makeText(VerifyOTPScreen.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /*------------  CountDownTimer -----------*/

    public void countDownTimerFun() {
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                binding.tvSetTime.setText(f.format(sec) + "s");
            }

            public void onFinish() {
                binding.tvSetTime.setVisibility(View.GONE);
                binding.tvResend.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}