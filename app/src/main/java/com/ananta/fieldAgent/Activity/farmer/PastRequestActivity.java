package com.ananta.fieldAgent.Activity.farmer;

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

import com.ananta.fieldAgent.Adapters.FarmServiceAdapter;
import com.ananta.fieldAgent.Adapters.TabFragmentAdapter;
import com.ananta.fieldAgent.Fragments.CurrenRequestFarmerFragment;
import com.ananta.fieldAgent.Models.FarmerServiceResponseModel;
import com.ananta.fieldAgent.Models.PastServiceDatumFarmer;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.FragmentServiceFarmBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastRequestActivity extends AppCompatActivity {

    FragmentServiceFarmBinding binding;
    FarmServiceAdapter serviceAdapter;
    List<PastServiceDatumFarmer> serviceArrayList = new ArrayList<>();
    Preference preference;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = FragmentServiceFarmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        preference = Preference.getInstance(PastRequestActivity.this);
        binding.svSearchView.clearFocus();

        binding.svSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        getFarmerData(preference.getFarmerLoginId());

    }

    private void getFarmerData(String id) {
        binding.pbProgressBar.setVisibility(View.VISIBLE);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);

        Call<FarmerServiceResponseModel> call = apiInterface.getCurrentAndPastData(hashMap, "Bearer " + preference.getToken());

        call.enqueue(new Callback<FarmerServiceResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<FarmerServiceResponseModel> call, @NonNull Response<FarmerServiceResponseModel> response) {

                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        if (serviceArrayList == null || serviceArrayList.isEmpty()){
                            binding.rlNoData.setVisibility(View.VISIBLE);
                            binding.llData.setVisibility(View.GONE);

                        }else {
                            binding.rlNoData.setVisibility(View.GONE);
                            binding.llData.setVisibility(View.VISIBLE);
                            LinearLayoutManager manager = new LinearLayoutManager(PastRequestActivity.this, LinearLayoutManager.VERTICAL, false);
                            binding.rcvFarmServiceView.setLayoutManager(manager);
                            serviceAdapter = new FarmServiceAdapter(PastRequestActivity.this, serviceArrayList);
                            binding.rcvFarmServiceView.setAdapter(serviceAdapter);
                        }
                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        Toast.makeText(PastRequestActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    Toast.makeText(PastRequestActivity.this, "Data not available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FarmerServiceResponseModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                Toast.makeText(PastRequestActivity.this, " " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void filter(String text) {
        // creating a new array list to filter data
        ArrayList<PastServiceDatumFarmer> filteredlist = new ArrayList<>();

        // running a for loop to compare elements
        for (PastServiceDatumFarmer item : serviceArrayList) {
            // checking if the entered string matches any item of our recycler view
            if (item.getRequest_type().toLowerCase().contains(text.toLowerCase())) {
                // adding matched item to the filtered list
                filteredlist.add(item);
            }
        }

        if (filteredlist.isEmpty()) {
            // displaying a toast message if no data found
            Toast.makeText(PastRequestActivity.this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // passing the filtered list to the adapter class
            serviceAdapter.filterList(filteredlist);
        }
    }
}