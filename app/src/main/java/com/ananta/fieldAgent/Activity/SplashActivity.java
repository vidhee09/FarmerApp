package com.ananta.fieldAgent.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.Activity.fieldAgent.AddRequestActivity;
import com.ananta.fieldAgent.Activity.fieldAgent.JointReportActivity;
import com.ananta.fieldAgent.Activity.fieldAgent.MainActivity;
import com.ananta.fieldAgent.Activity.fieldAgent.PumpInstallationActivity;
import com.ananta.fieldAgent.Activity.fieldAgent.SitInspectionReportActivity;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.databinding.ActivitySplashBinding;

import java.util.HashMap;


public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.main.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("sharedData", MODE_PRIVATE);
                if (!sharedPreferences.getString("agentLogin", "").equals("")) {
                    Const.AGENT_ID = sharedPreferences.getString("agentLogin", "");
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
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