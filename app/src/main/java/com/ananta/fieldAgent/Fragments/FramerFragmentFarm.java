package com.ananta.fieldAgent.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ananta.fieldAgent.Adapters.FarmAdapter;
import com.ananta.fieldAgent.Models.CurrentFarmerRequestModel;
import com.ananta.fieldAgent.Models.FarmerServiceResponseModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Utils;
import com.ananta.fieldAgent.databinding.FragmentFramerFarmBinding;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FramerFragmentFarm extends Fragment {

    FragmentFramerFarmBinding binding;
    ApiInterface apiInterface;
    FarmAdapter farmAdapter;
    ArrayList<CurrentFarmerRequestModel> FarmerServiceresponseModelArrayList = new ArrayList<>();

    public static Fragment newInstance() {
        return new FramerFragmentFarm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFramerFarmBinding.inflate(inflater);
        View view = binding.getRoot();

//        bindList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFarmerData(Const.FARMER_LOGIN_ID);
    }
    public void getFarmerData(String id){

        Utils.showCustomProgressDialog(getActivity(),true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id",id);

        Call<FarmerServiceResponseModel> call = apiInterface.getCurrentAndPastData(hashMap);

        call.enqueue(new Callback<FarmerServiceResponseModel>() {
            @Override
            public void onResponse(Call<FarmerServiceResponseModel> call, @NonNull Response<FarmerServiceResponseModel> response) {

                if (response.body() != null){
                    if (response.isSuccessful()){
                        Utils.hideProgressDialog(getActivity());
                        FarmerServiceresponseModelArrayList.addAll(response.body().getCurrentServiceData());
                        bindList();
                    }else {
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Utils.showCustomProgressDialog(getActivity(),true);
                    Toast.makeText(getActivity(), "Server not responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FarmerServiceResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Server not responding", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bindList() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rcvFarmView.setLayoutManager(manager);

        farmAdapter = new FarmAdapter(getActivity(), FarmerServiceresponseModelArrayList);
        binding.rcvFarmView.setAdapter(farmAdapter);

    }
}