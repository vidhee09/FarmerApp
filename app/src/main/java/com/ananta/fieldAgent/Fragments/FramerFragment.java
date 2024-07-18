package com.ananta.fieldAgent.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ananta.fieldAgent.Activity.LoginScreen;
import com.ananta.fieldAgent.Adapters.FarmerAdapter;
import com.ananta.fieldAgent.Models.FarmerModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Utils;
import com.ananta.fieldAgent.databinding.FragmentFramerBinding;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FramerFragment extends Fragment {

    FragmentFramerBinding binding;
    FarmerAdapter farmerAdapter;
    ArrayList<FarmerModel> farmerModelArrayList = new ArrayList<>();
    ApiInterface apiInterface;

    public static FramerFragment newInstance() {
        return new FramerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFramerBinding.inflate(inflater);
        View view =  binding.getRoot();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFarmerData(Const.AGENT_ID);
        Log.d("AgentId==","="+Const.AGENT_ID);

    }

    public void  bindList(){

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        binding.rcvFarmerView.setLayoutManager(manager);

        farmerAdapter = new FarmerAdapter(getActivity(),farmerModelArrayList);
        binding.rcvFarmerView.setAdapter(farmerAdapter);

    }

    public void getFarmerData(String id){

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id",id);
        Log.d("id===>","="+id);

        Call<FarmerModel> call = apiInterface.getDashboardData(hashMap);

        call.enqueue(new Callback<FarmerModel>() {
            @Override
            public void onResponse(Call<FarmerModel> call, @NonNull Response<FarmerModel> response) {

                if (response.body() != null){
                    if (response.isSuccessful()){
                        binding.pbProgressBar.setVisibility(View.GONE);
                        farmerModelArrayList.addAll(response.body().getFarmer_data());
                        Const.FARMER_ID = response.body().getFarmer_data().get(0).getId();
                        bindList();
                    }else {
                        binding.pbProgressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Server not responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FarmerModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Server not responding", Toast.LENGTH_SHORT).show();
            }
        });
    }

}