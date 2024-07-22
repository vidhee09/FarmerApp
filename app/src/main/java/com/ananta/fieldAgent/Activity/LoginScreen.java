package com.ananta.fieldAgent.Activity;

import static com.ananta.fieldAgent.Parser.ErrorLogStatement.LOGIN_FAIL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.Models.LoginModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Utils;
import com.ananta.fieldAgent.databinding.ActivityLoginScreenBinding;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {

    ActivityLoginScreenBinding binding;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.tvSendOtpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    if (Utils.isInternetAvailable(LoginScreen.this)) {
                        login();
                    } else {
                       binding.pbProgressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginScreen.this, "No Internet", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public boolean checkValidation() {
        boolean isValid = true;
        if (binding.edMobileNo.getText().toString().isEmpty()) {
            isValid = false;
            binding.edMobileNo.setError("Please Enter Mobile Number");
        } else if (binding.edMobileNo.getText().toString().length() != 10) {
            isValid = false;
            binding.edMobileNo.setError("Please Enter valid Mobile Number");
        }
        return isValid;
    }

    public void login() {

        binding.pbProgressBar.setVisibility(View.VISIBLE);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("mobile_number", binding.edMobileNo.getText().toString());

        Call<LoginModel> call = apiInterface.getLoginData(hashmap);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {


                if (response.isSuccessful()) {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    binding.tvErrorMobileNumber.setVisibility(View.GONE);
                    Intent intent = new Intent(LoginScreen.this, VerifyOTPScreen.class);
                    intent.putExtra("OTP", response.body().getOtp());
                    intent.putExtra("NUMBER", binding.edMobileNo.getText().toString());
                    startActivity(intent);
                    finish();

                } else {
                    binding.tvErrorMobileNumber.setVisibility(View.VISIBLE);
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginScreen.this, LOGIN_FAIL, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(LoginScreen.this, LOGIN_FAIL, Toast.LENGTH_SHORT).show();

            }
        });

    }

}