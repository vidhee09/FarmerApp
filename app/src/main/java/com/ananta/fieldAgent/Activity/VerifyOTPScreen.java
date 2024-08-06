package com.ananta.fieldAgent.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Activity.farmer.FarmerDashboardActivity;
import com.ananta.fieldAgent.Models.OtpResponseModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Preference;
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
    private Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpscreen2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preference = Preference.getInstance(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        OTP = getIntent().getStringExtra("OTP");
        Log.d("Otp===", "ott=" + OTP);
        Number = getIntent().getStringExtra("NUMBER");

        binding.pinView.setFocusable(true);
        binding.pinView.setText(OTP);

        String maskNumber = maskMobileNumber(Number);
        binding.tvOtpMobileNumber.setText("+91" + maskNumber);

        binding.btnOTPVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithOtp(OTP, binding.pinView);
                Log.d("Otp===", "=otp==" + OTP);
            }
        });

        binding.tvResend.setOnClickListener(view1 -> {
            binding.tvSetTime.setVisibility(View.VISIBLE);
            binding.tvResend.setVisibility(View.GONE);
            binding.pinView.getText().clear();

        });

        countDownTimerFun();

        binding.ivBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private String maskMobileNumber(String mobileNumber) {
        // Check if the mobile number is valid
        if (mobileNumber != null && mobileNumber.length() >= 10) {
            // Replace the first 6 digits with '*'
            return mobileNumber.replaceAll("\\d(?=\\d{4})", "*");
        } else {
            // Return the original number if it's not valid
            return mobileNumber;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void loginWithOtp(String otp, PinView pinView) {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobile_number", Number);
        hashMap.put("otp", otp);
        Log.d("opt====", "=otp==" + otp);

        Call<OtpResponseModel> call = apiInterface.getOtpVerify(hashMap);
        call.enqueue(new Callback<OtpResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<OtpResponseModel> call, @NonNull Response<OtpResponseModel> response) {

                if (response.body() != null) {
                    if (response.body().getType().equals("agent")) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        pinView.getText().clear();
                        Intent intent1 = new Intent(VerifyOTPScreen.this, DashboardActivity.class);
//                        preference.putAgentID("agent_id",String.valueOf(response.body().getUser_id()));
//                        = String.valueOf(response.body().getUser_id());
                        Const.AGENT_NAME = response.body().getUser_name();
                        Const.COMPANY_NAME = response.body().getUser_companyname();
                        Const.MOBILE_NUMBER = response.body().getMobile_number();
                        preference.putAgentName(response.body().getUser_name());
                        preference.putAgentID(String.valueOf(response.body().getUser_id()));
                        preference.putAgentNumber(response.body().getMobile_number());
                        preference.putToken(response.body().getToken());
                        preference.putCompanyName(response.body().getUser_companyname());
                        preference.putProfileImage(response.body().getImage());
//                        preference.putAgentFarmerId(response.body().get);
                        Const.SERVER_TOKEN = response.body().getToken();
                        String token = preference.putToken(response.body().getToken());
//                        ApiClient.setLoginDetail(response.body().getToken());
                        startActivity(intent1);
                        finishAffinity();

                    } else if (response.body().getType().equals("farmer")) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        pinView.getText().clear();
                        preference.putToken("");
                        Intent intent3 = new Intent(VerifyOTPScreen.this, FarmerDashboardActivity.class);
                        Const.LOGIN_FARMER_ID = String.valueOf(response.body().getUser_id());
                        preference.putIsHideWelcomeScreen(true);
                        preference.putFarmerLoginId(String.valueOf(response.body().getUser_id()));
                        preference.putFarmerName(response.body().getUser_name());
                        preference.putFarmerNum(response.body().getMobile_number());
                        Const.SERVER_TOKEN = response.body().getToken();
                        preference.putCompanyName(response.body().getUser_companyname());
//                        ApiClient.setLoginDetail(response.body().getToken());
                        String token = preference.putToken(response.body().getToken());
                        startActivity(intent3);
                        finishAffinity();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(VerifyOTPScreen.this, "server not responding right now", Toast.LENGTH_SHORT).show();
                    binding.pbProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<OtpResponseModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(VerifyOTPScreen.this, "server not responding", Toast.LENGTH_SHORT).show();
                binding.pbProgressBar.setVisibility(View.GONE);
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