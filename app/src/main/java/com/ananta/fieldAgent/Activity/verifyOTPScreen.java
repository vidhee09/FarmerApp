package com.ananta.fieldAgent.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.databinding.ActivityVerifyOtpscreenBinding;
import com.chaos.view.PinView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class verifyOTPScreen extends AppCompatActivity {

    ActivityVerifyOtpscreenBinding binding;
    String OTP = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpscreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        OTP = getIntent().getStringExtra("OTP");

        binding.pinView.setFocusable(true);

        binding.tvOtpMobileNumber.setText("+91" + "" + binding.tvOtpMobileNumber.getText());

        binding.btnOTPVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(verifyOTPScreen.this,MainActivity.class);
                startActivity(intent);
            }
        });

       loginWithOtp(binding.pinView.getText().toString().trim(), binding.pinView);

        binding.tvResend.setOnClickListener(view1 -> {
            binding.tvSetTime.setVisibility(View.VISIBLE);
            binding.tvResend.setVisibility(View.GONE);
            binding.pinView.getText().clear();

//            callResendApi();
        });

        countDownTimerFun();

    }

    private void loginWithOtp(String otp, PinView pinView) {


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

            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                binding.tvSetTime.setVisibility(View.GONE);
                binding.tvResend.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}