package com.ananta.fieldAgent.Activity.farmer;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityFarmerCurrentServiceSinglePageBinding;
import com.bumptech.glide.Glide;

public class FarmerCurrentServiceSinglePageActivity extends AppCompatActivity {

    ActivityFarmerCurrentServiceSinglePageBinding binding;

    String  name,request_name, farmer_address,image_name,ComplaintNumber,id,Comapany_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFarmerCurrentServiceSinglePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.ivBackPress.setOnClickListener(v -> {
            onBackPressed();
        });

        name = getIntent().getStringExtra("farmer_name");
        request_name = getIntent().getStringExtra("request_name");
        farmer_address = getIntent().getStringExtra("farmer_address");
        image_name = getIntent().getStringExtra("image_name");
        ComplaintNumber = getIntent().getStringExtra("ComplaintId");
        id = getIntent().getStringExtra("ID");
        Comapany_name = getIntent().getStringExtra("company_name");

        binding.tvFarmerName.setText(name);
        binding.tvFarmerComplaintName.setText(request_name);
        binding.tvFarmerID.setText(id);
        binding.tvFarmerAddress.setText(farmer_address);
        binding.tvFarmerApplicationNo.setText(ComplaintNumber);

        if (Comapany_name.isEmpty()){
            binding.llCompanyName.setVisibility(View.GONE);
        }else {
            binding.tvFarmerCompanyName.setText(Comapany_name);
        }

        if (image_name.isEmpty()){
            Glide.with(getApplicationContext()).load(R.drawable.ic_farmer).into(binding.farmerImage);
        }else {
            Glide.with(getApplicationContext()).load(Const.IMAGE_URL+ image_name).into(binding.farmerImage);
        }

    }
}