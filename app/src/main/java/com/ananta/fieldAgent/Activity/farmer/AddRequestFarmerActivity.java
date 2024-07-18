package com.ananta.fieldAgent.Activity.farmer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.ananta.fieldAgent.Activity.DashboardActivity;
import com.ananta.fieldAgent.databinding.ActivityAddRequestFarmerBinding;

public class AddRequestFarmerActivity extends AppCompatActivity {

    private ActivityAddRequestFarmerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRequestFarmerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.llAddRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(AddRequestFarmerActivity.this, FarmerDashboardActivity.class);
                Intent intent = new Intent(AddRequestFarmerActivity.this, DashboardActivity.class);
                startActivity(intent);

            }
        });

    }

}