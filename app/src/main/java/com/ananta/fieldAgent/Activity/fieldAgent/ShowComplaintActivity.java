package com.ananta.fieldAgent.Activity.fieldAgent;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityShowComplaintAtivityBinding;

public class ShowComplaintActivity extends AppCompatActivity {

    ActivityShowComplaintAtivityBinding binding;
    String position,farmerName,number,comaplaintId,ComplaintName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityShowComplaintAtivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("sharedData", MODE_PRIVATE);

        position = getIntent().getStringExtra("position");
        number = sharedPreferences.getString("MOBILE_NUMBER","");
        farmerName = getIntent().getStringExtra("FName");
        comaplaintId =getIntent().getStringExtra("ComplaintID");
        ComplaintName = getIntent().getStringExtra("ComplaintName");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.tvFarmerName.setText(farmerName);
        binding.tvFarmerNumber.setText(number);
        binding.tvFarmerComplaintName.setText(ComplaintName);
        binding.tvFarmerComplaintNumber.setText(comaplaintId);

    }
}