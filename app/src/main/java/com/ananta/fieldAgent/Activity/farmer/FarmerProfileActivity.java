package com.ananta.fieldAgent.Activity.farmer;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityFarmerProfileBinding;

public class FarmerProfileActivity extends AppCompatActivity {

    ActivityFarmerProfileBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFarmerProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


    }
}