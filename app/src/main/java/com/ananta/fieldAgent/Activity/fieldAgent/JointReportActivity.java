package com.ananta.fieldAgent.Activity.fieldAgent;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.databinding.ActivityJointReportBinding;


public class JointReportActivity extends AppCompatActivity {

     ActivityJointReportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJointReportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


    }
}