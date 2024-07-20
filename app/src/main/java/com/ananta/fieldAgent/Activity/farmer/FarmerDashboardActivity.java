package com.ananta.fieldAgent.Activity.farmer;

import android.content.Intent;
import android.os.Bundle;
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
        View view = binding.getRoot();
        setContentView(view);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFarmerData(preference.getFarmerLoginId());
    }

    public void getFarmerData(String id){

        Utils.showCustomProgressDialog(this,true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id",id);

        Call<FarmerServiceResponseModel> call = apiInterface.getCurrentAndPastData(hashMap);

        call.enqueue(new Callback<FarmerServiceResponseModel>() {
            @Override
            public void onResponse(Call<FarmerServiceResponseModel> call, @NonNull Response<FarmerServiceResponseModel> response) {

                if (response.body() != null){
                    if (response.isSuccessful()){
                        Utils.hideProgressDialog(FarmerDashboardActivity.this);
                        adapter = new TabFragmentAdapter(getSupportFragmentManager());
                        adapter.addFragment(new CurrenRequestFarmerFragment(response.body().getCurrentServiceData()), "Current Request");
                        adapter.addFragment(new PastRequestFarmerFragment(response.body().getPastServiceData()), "Past Request");
                        binding.viewPager.setAdapter(adapter);
                        binding.tabLayout.setupWithViewPager(binding.viewPager);

                    }else {
                        Toast.makeText(FarmerDashboardActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Utils.showCustomProgressDialog(FarmerDashboardActivity.this,true);
                    Toast.makeText(FarmerDashboardActivity.this, "Server not responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FarmerServiceResponseModel> call, Throwable t) {
                Toast.makeText(FarmerDashboardActivity.this, "Server not responding", Toast.LENGTH_SHORT).show();
            }
        });
    }
}