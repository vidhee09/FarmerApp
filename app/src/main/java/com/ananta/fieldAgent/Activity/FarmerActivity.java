package com.ananta.fieldAgent.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import com.ananta.fieldAgent.Activity.fieldAgent.AddRequestActivity;
import com.ananta.fieldAgent.Adapters.FarmerAdapter;
import com.ananta.fieldAgent.Models.FarmerModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Utils;
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
    ArrayList<FarmerModel> farmerModelArrayList = new ArrayList<>();
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityFarmerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addListener();
    }


    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(FarmerActivity.this,LinearLayoutManager.VERTICAL,false);
        binding.rcvFarmer.setLayoutManager(manager);

        farmerAdapter = new FarmerAdapter(FarmerActivity.this,farmerModelArrayList);
        binding.rcvFarmer.setAdapter(farmerAdapter);
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
        getFarmerData(Const.AGENT_ID);
    }

    private void getFarmerData(String agentId) {
        binding.pbProgressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id",agentId);

        Call<FarmerModel> call = apiInterface.getDashboardData(hashMap);

        call.enqueue(new Callback<FarmerModel>() {
            @Override
            public void onResponse(Call<FarmerModel> call, @NonNull Response<FarmerModel> response) {
                if (response.body() != null){
                    if (response.isSuccessful()){
                        binding.pbProgressBar.setVisibility(View.GONE);
                        farmerModelArrayList.clear();
                        farmerModelArrayList.addAll(response.body().getFarmer_data());
                        initView();
                    }else {
                        binding.pbProgressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(FarmerActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(FarmerActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FarmerModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(FarmerActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

