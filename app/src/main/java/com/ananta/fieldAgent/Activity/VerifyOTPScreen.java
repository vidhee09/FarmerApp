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
import com.ananta.fieldAgent.Parser.Utils;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityVerifyOtpscreenBinding;
import com.chaos.view.PinView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOTPScreen extends AppCompatActivity {

    ActivityVerifyOtpscreenBinding binding;
    String OTP = "", Number = "";
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpscreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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

        Utils.showCustomProgressDialog(VerifyOTPScreen.this,true);
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
                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        Utils.hideProgressDialog(VerifyOTPScreen.this);
                        pinView.getText().clear();
                        if (response.body().getType().equals("agent")) {
                            //old Activity when we want see old view
//                            Intent intent = new Intent(VerifyOTPScreen.this, MainActivity.class);
                            Intent intent = new Intent(VerifyOTPScreen.this, DashboardActivity.class);
                            Const.AGENT_ID = response.body().getUser_id();
                            Const.AGENT_NAME = response.body().getUser_name();
                            editor.putString("agentLogin", Const.AGENT_ID);
                            editor.putString("agentName", Const.AGENT_NAME);
                            editor.commit();
                            Const.COMPANY_NAME = response.body().getUser_companyname();
                            Const.MOBILE_NUMBER = response.body().getMobile_number();
                            startActivity(intent);
                            finish();

                        } else if (response.body().getType().equals("farmer")) {
                            Intent intent = new Intent(VerifyOTPScreen.this, FarmerDashboardActivity.class);
                            Const.FARMER_NAME = response.body().getUser_name();
                            Const.FARMER_LOGIN_ID = response.body().getUser_id();
                            Const.FARMER_NUM = response.body().getMobile_number();
                            startActivity(intent);
                            editor.putString("farmerLogin", Const.FARMER_LOGIN_ID);
                            editor.putString("farmerName", Const.FARMER_NAME);
                            editor.putString("farmerNum", Const.FARMER_NUM);
                            editor.commit();
                            finish();
                        }
                    }

                } else {
                    Utils.showCustomProgressDialog(VerifyOTPScreen.this, true);
                    Toast.makeText(VerifyOTPScreen.this, "Server Under Maintenance", Toast.LENGTH_SHORT).show();
                    pinView.getText().clear();
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