package com.ananta.fieldAgent.Activity.fieldAgent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.databinding.ActivityFarmerDetailBinding;


public class FarmerDetailActivity extends AppCompatActivity {

    ActivityFarmerDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFarmerDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.rlSiteReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerDetailActivity.this,SitInspectionReportActivity.class);
                startActivity(intent);
            }
        });

        binding.rlDeliveryReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerDetailActivity.this,DeliveryReportActivity.class);
                startActivity(intent);
            }
        });

        binding.rlServiceReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FarmerDetailActivity.this,ServiceReportActivity.class);
                startActivity(intent);
            }
        });


        binding.rlJointReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerDetailActivity.this,JointReportActivity.class);
                startActivity(intent);
            }
        });

    }

}