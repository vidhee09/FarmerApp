package com.ananta.fieldAgent.Activity;

import static com.ananta.fieldAgent.Parser.ErrorLogStatement.LOGIN_FAIL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.Models.LoginModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
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
//                    Intent intent = new Intent(LoginScreen.this, verifyOTPScreen.class);
//                    startActivity(intent);
                    login();

                }
            }
        });

    }

    public boolean checkValidation() {
        boolean isValid = true;
        if (binding.edMobileNo.getText().toString().isEmpty()) {
            isValid = false;
            binding.edMobileNo.setError("Please Enter Mobile Number");
        }
        return isValid;

    }

    public void login() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("mobile_number", binding.edMobileNo.getText().toString());

        Call<LoginModel> call = apiInterface.getLoginData(hashmap);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                assert response.body() != null;
                if (response.body().getSuccess().equals("true")){
                    Intent intent = new Intent(LoginScreen.this, verifyOTPScreen.class);
                    intent.putExtra("OTP",response.body().getOtp());
                    intent.putExtra("NUMBER",binding.edMobileNo.getText().toString());
                    startActivity(intent);

                }else {
                    Toast.makeText(LoginScreen.this, LOGIN_FAIL, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(LoginScreen.this, LOGIN_FAIL, Toast.LENGTH_SHORT).show();

            }
        });

    }

}