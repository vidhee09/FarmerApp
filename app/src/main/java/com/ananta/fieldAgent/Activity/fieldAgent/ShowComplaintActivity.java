package com.ananta.fieldAgent.Activity.fieldAgent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityShowComplaintAtivityBinding;
import com.bumptech.glide.Glide;

public class ShowComplaintActivity extends AppCompatActivity {

    ActivityShowComplaintAtivityBinding binding;
    String position,farmerName,number,comaplaintId,ComplaintName,PastImageName;
    Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityShowComplaintAtivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preference = Preference.getInstance(ShowComplaintActivity.this);

        position = getIntent().getStringExtra("position");
        number = preference.getAgentNumber();
        farmerName = getIntent().getStringExtra("FName");
        comaplaintId =getIntent().getStringExtra("ComplaintID");
        ComplaintName = getIntent().getStringExtra("ComplaintName");
        PastImageName = getIntent().getStringExtra("PastImageName");
        Log.d("image===","="+PastImageName);

        if (PastImageName == null || PastImageName.isEmpty()) {
            binding.ivPastReqImageShow.setImageResource(R.drawable.farmer_new_logo);
        }else {
            Glide.with(ShowComplaintActivity.this).load(Const.IMAGE_URL+PastImageName).into(binding.ivPastReqImageShow);

        }

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