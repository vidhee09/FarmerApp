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
    TabFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServiceReportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        adapter = new TabFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(CurrentRequestFragment.newInstance(), "Current Request");
        adapter.addFragment(PastRequestFragment.newInstance(), "Past Request");
        binding.vpViewPager.setAdapter(adapter);
        binding.tbTabLayout.setupWithViewPager(binding.vpViewPager);

        binding.vpViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float positionOffset, int positionOffsetPx) {
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
}