package com.ananta.fieldAgent.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Activity.farmer.FarmerDashboardActivity;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivitySplashBinding;


public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.main.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("sharedData", MODE_PRIVATE);
                if (!sharedPreferences.getString("agentLogin", "").equals("")) {
                    Const.AGENT_ID = sharedPreferences.getString("agentLogin", "");
                    // old screen
//                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else if (!sharedPreferences.getString("farmerLogin", "").equals("")) {
                    Const.FARMER_LOGIN_ID = sharedPreferences.getString("farmerLogin", "");
                    Const.FARMER_NAME = sharedPreferences.getString("farmerName", "");
                    Const.FARMER_NUM = sharedPreferences.getString("farmerNum", "");
                    Intent intent = new Intent(SplashActivity.this, FarmerDashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 5000);
    }
}