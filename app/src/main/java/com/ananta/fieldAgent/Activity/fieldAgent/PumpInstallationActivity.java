package com.ananta.fieldAgent.Activity.fieldAgent;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.databinding.ActivityPumpInstallationBinding;


public class PumpInstallationActivity extends AppCompatActivity {

    ActivityPumpInstallationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPumpInstallationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}