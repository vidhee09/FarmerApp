package com.ananta.fieldAgent.Activity;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.databinding.ActivityDeliveryReportBinding;


public class DeliveryReportActivity extends AppCompatActivity {

    ActivityDeliveryReportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryReportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


    }
}