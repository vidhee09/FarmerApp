package com.ananta.fieldAgent.Activity.farmer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.ananta.fieldAgent.Activity.fieldAgent.AddRequestActivity;
import com.ananta.fieldAgent.Adapters.TabFragmentAdapter;
import com.ananta.fieldAgent.Fragments.FramerFragmentFarm;
import com.ananta.fieldAgent.Fragments.ServiceFragmentFarm;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityFarmerDashboardBinding;

public class FarmerDashboardActivity extends AppCompatActivity {

    ActivityFarmerDashboardBinding binding;

    TabFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityFarmerDashboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.tvFarmerName.setText(Const.FARMER_NAME);
        binding.tvFarmerNo.setText(Const.FARMER_NUM);
        adapter = new TabFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(FramerFragmentFarm.newInstance(), "Current Request");
        adapter.addFragment(ServiceFragmentFarm.newInstance(), "Past Request");
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


        binding.ivAddReqImage.setOnClickListener(v -> {

            Intent intent = new Intent(FarmerDashboardActivity.this, AddRequestActivity.class);

            startActivity(intent);

        });


    }
}