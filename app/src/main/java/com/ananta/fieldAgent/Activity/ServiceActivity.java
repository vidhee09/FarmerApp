package com.ananta.fieldAgent.Activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.ananta.fieldAgent.Adapters.ServiceAdapter;
import com.ananta.fieldAgent.Models.ServiceModel;
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

public class ServiceActivity extends AppCompatActivity {

    private RecyclerView rcvService;
    ServiceAdapter serviceAdapter;
    ArrayList<ServiceModel> serviceArrayList = new ArrayList<>();
    ApiInterface apiInterface;
    ImageView ivBackPress, ivAddReqImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
//        getServiceData(Const.AGENT_ID);

        bindView();
        addListener();
    }

    private void bindView() {
        rcvService = findViewById(R.id.rcvService);
        ivBackPress = findViewById(R.id.ivBackPress);
        ivAddReqImage = findViewById(R.id.ivAddReqImage);
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
                Intent intent = new Intent(ServiceActivity.this, AddRequestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(ServiceActivity.this,LinearLayoutManager.VERTICAL,false);
        rcvService.setLayoutManager(manager);

        serviceAdapter = new ServiceAdapter(ServiceActivity.this,serviceArrayList);
        rcvService.setAdapter(serviceAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getServiceData(Const.AGENT_ID);

    }

    public void getServiceData(String id){

//        Utils.showCustomProgressDialog(ServiceActivity.this,true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id",id);

        Call<ServiceModel> call = apiInterface.getDashboardService(hashMap);
        call.enqueue(new Callback<ServiceModel>() {
            @Override
            public void onResponse(Call<ServiceModel> call, Response<ServiceModel> response) {

                if (response.body() != null){
                    if (response.body().getSuccess()){
                        serviceArrayList.clear();
//                        Utils.hideProgressDialog(ServiceActivity.this);
                        serviceArrayList.addAll(response.body().getService_data());
                        initView();
                    }else {
//                        Utils.showCustomProgressDialog(ServiceActivity.this,true);
                        Toast.makeText(ServiceActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                    }
                }else {
//                    Utils.showCustomProgressDialog(ServiceActivity.this,true);
                    Toast.makeText(ServiceActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServiceModel> call, Throwable t) {
//                Utils.showCustomProgressDialog(ServiceActivity.this,true);
                Toast.makeText(ServiceActivity.this, "Data not found-"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}