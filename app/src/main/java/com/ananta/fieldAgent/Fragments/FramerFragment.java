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
import com.ananta.fieldAgent.Models.FarmerDatum;
import com.ananta.fieldAgent.Models.FarmerModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Preference;
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
    ArrayList<FarmerDatum> farmerModelArrayList = new ArrayList<>();
    ApiInterface apiInterface;
    Preference preference;
    String FarmerFarmID;

    public static FramerFragment newInstance() {
        return new FramerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFramerBinding.inflate(inflater);
        View view = binding.getRoot();

        preference = Preference.getInstance(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFarmerData(preference.getAgentId());
        Log.d("AgentId==", "=" + preference.getAgentId());

    }

    public void bindList() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rcvFarmerView.setLayoutManager(manager);

        farmerAdapter = new FarmerAdapter(getActivity(), farmerModelArrayList);
        binding.rcvFarmerView.setAdapter(farmerAdapter);

    }

    public void setClickDisable(boolean b){
        binding.rcvFarmerView.setClickable(b);
    }

    public void getFarmerData(String id) {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);

        Call<FarmerModel> call = apiInterface.getDashboardData(hashMap,"Bearer "+preference.getToken());

        call.enqueue(new Callback<FarmerModel>() {
            @Override
            public void onResponse(Call<FarmerModel> call, @NonNull Response<FarmerModel> response) {

                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        farmerModelArrayList.addAll(response.body().getFarmerData());
                        FarmerFarmID  = String.valueOf(response.body().getFarmerData().get(0).getId());
                        bindList();
                    } else {
                        binding.pbProgressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Farmer not available", Toast.LENGTH_SHORT).show();
                        binding.pbProgressBar.setVisibility(View.GONE);
                    }

                } else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    binding.pbProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<FarmerModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.pbProgressBar.setVisibility(View.GONE);
            }
        });
    }

}