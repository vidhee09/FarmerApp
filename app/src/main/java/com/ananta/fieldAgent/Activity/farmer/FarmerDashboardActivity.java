package com.ananta.fieldAgent.Activity.farmer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.ananta.fieldAgent.Activity.DashboardActivity;
import com.ananta.fieldAgent.Activity.LoginScreen;
import com.ananta.fieldAgent.Activity.fieldAgent.AddRequestActivity;
import com.ananta.fieldAgent.Adapters.TabFragmentAdapter;
import com.ananta.fieldAgent.Fragments.CurrenRequestFarmerFragment;
import com.ananta.fieldAgent.Fragments.PastRequestFarmerFragment;
import com.ananta.fieldAgent.Models.CurrentRequestFarmerModel;
import com.ananta.fieldAgent.Models.FarmerServiceResponseModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.Parser.Utils;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityFarmerDashboardBinding;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmerDashboardActivity extends AppCompatActivity {

    ActivityFarmerDashboardBinding binding;
    TabFragmentAdapter adapter;
    private Preference preference;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityFarmerDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preference = Preference.getInstance(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.tvFarmerName.setText(preference.getFarmerName());
        binding.tvFarmerNo.setText(preference.getFarmerNum());
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

        binding.ivSignOut.setOnClickListener(v -> {
            preference.putIsHideWelcomeScreen(false);
            preference.putFarmerName(null);
            preference.putFarmerNum(null);
            preference.putFarmerLoginId(null);
            preference.putFarmerName(null);
            Intent intent = new Intent(FarmerDashboardActivity.this, LoginScreen.class);
            startActivity(intent);
            finishAffinity();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFarmerData(preference.getFarmerLoginId());
    }

    public void getFarmerData(String id) {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);

        Call<FarmerServiceResponseModel> call = apiInterface.getCurrentAndPastData(hashMap ,"Bearer "+preference.getToken());
        Log.d("farmerData===TOKEN",""+preference.getToken());

        call.enqueue(new Callback<FarmerServiceResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<FarmerServiceResponseModel> call, @NonNull Response<FarmerServiceResponseModel> response) {

                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        adapter = new TabFragmentAdapter(getSupportFragmentManager());
                        adapter.addFragment(new CurrenRequestFarmerFragment(response.body().getCurrent_service_data()), "Current Request");
                        adapter.addFragment(new PastRequestFarmerFragment(response.body().getPast_service_data()), "Past Request");
                        binding.viewPager.setAdapter(adapter);
                        binding.tabLayout.setupWithViewPager(binding.viewPager);

                    } else {
                        binding.pbProgressBar.setVisibility(View.VISIBLE);
                        Log.d("farmerData===","=else="+response.body().isSuccess());
                        Toast.makeText(FarmerDashboardActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d("farmerData===","nll="+response.body().isSuccess());
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(FarmerDashboardActivity.this, "Server not responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FarmerServiceResponseModel> call, Throwable t) {
                Log.d("farmerData===","fail="+t.getMessage());
                Toast.makeText(FarmerDashboardActivity.this, "Server not responding", Toast.LENGTH_SHORT).show();
            }
        });
    }
}