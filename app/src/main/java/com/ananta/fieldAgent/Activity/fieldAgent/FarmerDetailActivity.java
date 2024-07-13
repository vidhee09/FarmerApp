package com.ananta.fieldAgent.Activity.fieldAgent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.Models.DetailModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.databinding.ActivityFarmerDetailBinding;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FarmerDetailActivity extends AppCompatActivity {

    ActivityFarmerDetailBinding binding;
    ApiInterface apiInterface;
    String FarmerPosition="",CompanyName="",FarmerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFarmerDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        FarmerPosition = getIntent().getStringExtra("farmer_position");
        FarmerName = getIntent().getStringExtra("FarmerName");
        CompanyName = getIntent().getStringExtra("CompanyName");
        Log.d("FarmerPosition====","=="+FarmerPosition);
        Log.d("FarmerPosition====","=="+FarmerName);
        Log.d("FarmerPosition====","=="+CompanyName);

        binding.tvAgentName.setText(FarmerName);
        binding.tvCompanyName.setText(CompanyName);

        binding.rlSiteReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerDetailActivity.this,SitInspectionReportActivity.class);
                intent.putExtra("FarmerPosition",FarmerPosition);
                intent.putExtra("FarmerName",FarmerName);
                startActivity(intent);
            }
        });

        binding.rlDeliveryReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerDetailActivity.this,DeliveryReportActivity.class);
                intent.putExtra("FarmerPosition",FarmerPosition);
                intent.putExtra("FarmerName",FarmerName);
                startActivity(intent);
            }
        });

        binding.rlPumpInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FarmerDetailActivity.this,PumpInstallationActivity.class);
                intent.putExtra("FarmerPosition",FarmerPosition);
                intent.putExtra("FarmerName",FarmerName);
                startActivity(intent);
            }
        });


        binding.rlJointReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerDetailActivity.this,JointReportActivity.class);
                intent.putExtra("FarmerName",FarmerName);
                startActivity(intent);
            }
        });

        binding.ivBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}