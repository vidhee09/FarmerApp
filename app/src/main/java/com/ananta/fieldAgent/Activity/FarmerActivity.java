package com.ananta.fieldAgent.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmerActivity extends AppCompatActivity {

    FarmerAdapter farmerAdapter;
    ArrayList<FarmerModel> farmerModelArrayList = new ArrayList<>();
    ApiInterface apiInterface;
    private RecyclerView rcvFarmer;
    private ImageView ivBackPress, ivAddReqImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);
//        getFarmerData(Const.AGENT_ID);

        bindView();
        addListener();
    }

    private void bindView() {
        rcvFarmer = findViewById(R.id.rcvFarmer);
        ivBackPress = findViewById(R.id.ivBackPress);
        ivAddReqImage = findViewById(R.id.ivAddReqImage);
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(FarmerActivity.this,LinearLayoutManager.VERTICAL,false);
        rcvFarmer.setLayoutManager(manager);

        farmerAdapter = new FarmerAdapter(FarmerActivity.this,farmerModelArrayList);
        rcvFarmer.setAdapter(farmerAdapter);
    }

    private void addListener() {
        ivBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivAddReqImage.setOnClickListener(new View.OnClickListener() {
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
//        Utils.showCustomProgressDialog(FarmerActivity.this,true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id",agentId);

        Call<FarmerModel> call = apiInterface.getDashboardData(hashMap);

        call.enqueue(new Callback<FarmerModel>() {
            @Override
            public void onResponse(Call<FarmerModel> call, @NonNull Response<FarmerModel> response) {

                if (response.body() != null){
                    if (response.isSuccessful()){
                        farmerModelArrayList.clear();
//                        Utils.hideProgressDialog(FarmerActivity.this);
                        farmerModelArrayList.addAll(response.body().getFarmer_data());
                        Const.FARMER_ID = response.body().getFarmer_data().get(0).getId();
                        initView();
                    }else {
                        Toast.makeText(FarmerActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                }else {
//                    Utils.showCustomProgressDialog(FarmerActivity.this,true);
                    Toast.makeText(FarmerActivity.this, "Server not responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FarmerModel> call, Throwable t) {
                Toast.makeText(FarmerActivity.this, "Server not responding", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

