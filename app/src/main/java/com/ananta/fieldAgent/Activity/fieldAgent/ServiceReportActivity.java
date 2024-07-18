package com.ananta.fieldAgent.Activity.fieldAgent;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ananta.fieldAgent.Adapters.TabFragmentAdapter;
import com.ananta.fieldAgent.Fragments.CurrentRequestFragment;
import com.ananta.fieldAgent.Fragments.PastRequestFragment;
import com.ananta.fieldAgent.databinding.ActivityServiceReportBinding;

public class ServiceReportActivity extends AppCompatActivity {

    ActivityServiceReportBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServiceReportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


    }
}