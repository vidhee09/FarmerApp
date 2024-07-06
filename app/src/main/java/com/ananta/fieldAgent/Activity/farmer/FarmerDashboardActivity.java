package com.ananta.fieldAgent.Activity.farmer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ananta.fieldAgent.Activity.fieldAgent.AddRequestActivity;
import com.ananta.fieldAgent.Adapters.TabFragmentAdapter;
import com.ananta.fieldAgent.Fragments.FramerFragmentFarm;
import com.ananta.fieldAgent.Fragments.ServiceFragmentFarm;
import com.ananta.fieldAgent.databinding.ActivityFarmerDashboardBinding;

public class FarmerDashboardActivity extends AppCompatActivity {

    ActivityFarmerDashboardBinding binding;

    TabFragmentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFarmerDashboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        adapter = new TabFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(FramerFragmentFarm.newInstance(), "Farmer");
        adapter.addFragment(ServiceFragmentFarm.newInstance(), "Service");
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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


        binding.ivAddReqImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FarmerDashboardActivity.this, AddRequestFarmerActivity.class);
                startActivity(intent);

            }
        });


    }
}