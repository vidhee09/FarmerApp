package com.ananta.fieldAgent.Activity.farmer;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;

import com.ananta.fieldAgent.Activity.DashboardActivity;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityAddRequestFarmerBinding;

public class AddRequestFarmerActivity extends AppCompatActivity {

    private ActivityAddRequestFarmerBinding binding;
    private Preference preference;
    private  String complaintName, complaintNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityAddRequestFarmerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preference = Preference.getInstance(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        complaintName = getIntent().getStringExtra("complaint_name");
        complaintNumber = getIntent().getStringExtra("complaint_number");

        binding.tvFarmerName.setText(preference.getFarmerName());
        binding.tvFarmerNumber.setText(preference.getFarmerNum());
        binding.tvFarmerComplaintName.setText(complaintName);
        binding.tvFarmerComplaintNumber.setText(complaintNumber);

        binding.ivBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });
    }

}