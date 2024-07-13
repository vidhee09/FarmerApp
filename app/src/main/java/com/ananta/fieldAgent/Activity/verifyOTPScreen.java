package com.ananta.fieldAgent.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.Activity.farmer.FarmerDashboardActivity;
import com.ananta.fieldAgent.Activity.fieldAgent.MainActivity;
import com.ananta.fieldAgent.Models.LoginModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Utils;
import com.ananta.fieldAgent.databinding.ActivityVerifyOtpscreenBinding;
import com.chaos.view.PinView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class verifyOTPScreen extends AppCompatActivity {

    ActivityVerifyOtpscreenBinding binding;
    String OTP = "", Number = "";
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpscreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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

        Utils.showCustomProgressDialog(verifyOTPScreen.this,true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobile_number", Number);
        hashMap.put("otp", otp);

        Call<LoginModel> call = apiInterface.getOtpVerify(hashMap);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                if (response.body().getSuccess().equals("true")) {
                    Utils.hideProgressDialog(verifyOTPScreen.this);
                    pinView.getText().clear();

                    if (response.body().getType().equals("agent")){

                        Intent intent = new Intent(verifyOTPScreen.this, MainActivity.class);
                        Const.AGENT_ID = response.body().getUser_id();
                        Log.d("id===","="+Const.AGENT_ID);
                        Const.AGENT_NAME = response.body().getUser_name();
                        Const.COMPANY_NAME = response.body().getUser_companyname();
                        Const.MOBILE_NUMBER = response.body().getMobile_number();
                        startActivity(intent);
                        finish();

                    }else if (response.body().getType().equals("farmer")){

                        Intent intent = new Intent(verifyOTPScreen.this, FarmerDashboardActivity.class);
                        startActivity(intent);
                    }


                } else {
                    Utils.showCustomProgressDialog(verifyOTPScreen.this,true);
                    Toast.makeText(verifyOTPScreen.this, "Invalid Otp, please enter valid otp", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

                Toast.makeText(verifyOTPScreen.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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