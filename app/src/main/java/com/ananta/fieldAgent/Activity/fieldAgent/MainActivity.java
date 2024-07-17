package com.ananta.fieldAgent.Activity.fieldAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ananta.fieldAgent.Activity.LoginScreen;
import com.ananta.fieldAgent.Adapters.TabFragmentAdapter;
import com.ananta.fieldAgent.Fragments.FramerFragment;
import com.ananta.fieldAgent.Fragments.ServiceFragment;
import com.ananta.fieldAgent.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    TabFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        adapter = new TabFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(FramerFragment.newInstance(), "Farmer");
        adapter.addFragment(ServiceFragment.newInstance(), "Service");
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

                Intent intent = new Intent(MainActivity.this, AddRequestActivity.class);
                startActivity(intent);

            }
        });


        binding.ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AgentProfileActivity.class);
                startActivity(intent);

            }
        });

        binding.ivSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("sharedData",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("agentLogin","");
                editor.commit();
                Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                startActivity(intent);
                finishAffinity();
            }
        });

    }

}