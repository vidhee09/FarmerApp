package com.ananta.fieldAgent.Activity;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ananta.fieldAgent.Activity.fieldAgent.AddRequestActivity;
import com.ananta.fieldAgent.Adapters.FarmerAdapter;
import com.ananta.fieldAgent.Models.FarmerDatum;
import com.ananta.fieldAgent.Models.FarmerModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityFarmerBinding;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmerActivity extends AppCompatActivity {

    ActivityFarmerBinding binding;
    FarmerAdapter farmerAdapter;
    ArrayList<FarmerDatum> farmerModelArrayList = new ArrayList<>();
    ApiInterface apiInterface;
    Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityFarmerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preference = Preference.getInstance(getApplicationContext());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addListener();

        binding.svSearchView.clearFocus();
        binding.svSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                filter(newText);
                return false;
            }
        });

        binding.swipeRefreshFarmerDetail.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFarmerData(preference.getAgentId());
                binding.swipeRefreshFarmerDetail.setRefreshing(false);

            }
        });
    }

    private void filter(String text) {
        ArrayList<FarmerDatum> filteredlist = new ArrayList<>();

        for (FarmerDatum item : farmerModelArrayList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }

        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Search Result..", Toast.LENGTH_SHORT).show();
        } else {
            farmerAdapter.filterList(filteredlist);
        }
    }

    private void initView() {

        if (farmerModelArrayList.isEmpty() || farmerModelArrayList.contains(null)){
            binding.rlNoData.setVisibility(View.VISIBLE);
            binding.rlData.setVisibility(View.GONE);
        }else{
            binding.rlNoData.setVisibility(View.GONE);
            binding.rlData.setVisibility(View.VISIBLE);
            LinearLayoutManager manager = new LinearLayoutManager(FarmerActivity.this,LinearLayoutManager.VERTICAL,false);
            binding.rcvFarmer.setLayoutManager(manager);
            farmerAdapter = new FarmerAdapter(FarmerActivity.this,farmerModelArrayList);
            binding.rcvFarmer.setAdapter(farmerAdapter);
        }

    }

    private void addListener() {
        binding.ivBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.ivAddReqImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerActivity.this, AddRequestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Farmer=====","==>"+ preference.getAgentId());
        getFarmerData(preference.getAgentId());
    }

    private void getFarmerData(String agentId) {
        binding.pbProgressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id",agentId);

        Call<FarmerModel> call = apiInterface.getDashboardData(hashMap,"Bearer "+preference.getToken());

        call.enqueue(new Callback<FarmerModel>() {
            @Override
            public void onResponse(@NonNull Call<FarmerModel> call, @NonNull Response<FarmerModel> response) {

                if (response.body() != null){
                    if (response.body().getSuccess()){
                        binding.pbProgressBar.setVisibility(View.GONE);
                        farmerModelArrayList.clear();
                        farmerModelArrayList.addAll(response.body().getFarmerData());

                       /* for (int i = 0; i< response.body().getFarmerData().size(); i++){
                            if (!farmerModelArrayList.isEmpty()){
                                preference.putAgentFarmerId(String.valueOf(response.body().getFarmerData().get(i).getId()));
                                Log.d("hello===","farmerID=="+response.body().getFarmerData().get(i).getId());
                            }else {
                                Log.d("hello===","farmerID=else="+response.body().getFarmerData().get(i));
                                Toast.makeText(FarmerActivity.this, "farmerID==="+response.body().getFarmerData().size(), Toast.LENGTH_SHORT).show();
                            }
                        }*/


                        initView();
                    }else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        Toast.makeText(FarmerActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    Toast.makeText(FarmerActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FarmerModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                Log.d("response===","=fail="+t.getMessage());
            }
        });
    }
}

