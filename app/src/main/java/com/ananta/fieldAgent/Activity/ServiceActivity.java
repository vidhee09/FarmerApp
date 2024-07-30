package com.ananta.fieldAgent.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.ananta.fieldAgent.Activity.fieldAgent.AddRequestActivity;
import com.ananta.fieldAgent.Adapters.FarmerAdapter;
import com.ananta.fieldAgent.Adapters.ServiceAdapter;
import com.ananta.fieldAgent.Adapters.TabFragmentAdapter;
import com.ananta.fieldAgent.Fragments.CurrentRequestFragment;
import com.ananta.fieldAgent.Fragments.PastRequestFragment;
import com.ananta.fieldAgent.Models.CurrentReqModel;
import com.ananta.fieldAgent.Models.ServiceModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.Parser.Utils;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityServiceBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceActivity extends AppCompatActivity {

    ActivityServiceBinding binding;
    TabFragmentAdapter adapter;
    ApiInterface apiInterface;
    Preference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        preference = Preference.getInstance(ServiceActivity.this);

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

        binding.ivAddReqImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, AddRequestActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.ivBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        binding.swipeServiceMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCurrentRequestData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentRequestData();
    }

    public void setAllClicksDisable(boolean b){
        binding.ivBackPress.setClickable(b);
        binding.ivAddReqImage.setClickable(b);
    }

    public void getCurrentRequestData() {
        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        Log.d("Service ===>" ,"===>" + preference.getAgentId());
        hashMap.put("id", preference.getAgentId());

        Call<CurrentReqModel> call = apiInterface.getCurrentRequest(hashMap ,"Bearer "+preference.getToken());

        call.enqueue(new Callback<CurrentReqModel>() {
            @Override
            public void onResponse(@NonNull Call<CurrentReqModel> call, @NonNull Response<CurrentReqModel> response) {

                if (response.body() != null){
                    if (response.body().getSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        adapter = new TabFragmentAdapter(getSupportFragmentManager());
                        adapter.addFragment(new CurrentRequestFragment(response.body().getCurrent_service_data()), "Current Request");
                        adapter.addFragment(new PastRequestFragment(response.body().getPastServiceData()), "Past Request");
                        binding.vpViewPager.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        binding.tbTabLayout.setupWithViewPager(binding.vpViewPager);
                        binding.swipeServiceMain.setRefreshing(false);

                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(ServiceActivity.this, "Request not available", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Toast.makeText(ServiceActivity.this, "Request not available", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CurrentReqModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(ServiceActivity.this, " "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}