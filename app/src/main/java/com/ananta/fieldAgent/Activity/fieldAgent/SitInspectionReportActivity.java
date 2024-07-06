package com.ananta.fieldAgent.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.databinding.ActivitySitInspectionReportBinding;

public class SitInspectionReportActivity extends AppCompatActivity {

    ActivitySitInspectionReportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySitInspectionReportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



    }
}