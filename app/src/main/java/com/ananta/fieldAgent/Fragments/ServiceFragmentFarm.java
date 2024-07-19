package com.ananta.fieldAgent.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ananta.fieldAgent.Adapters.FarmServiceAdapter;
import com.ananta.fieldAgent.Models.FarmerServiceResponseModel;
import com.ananta.fieldAgent.Models.PastFarmerRequestModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Utils;
import com.ananta.fieldAgent.databinding.FragmentServiceFarmBinding;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceFragmentFarm extends Fragment {

    FragmentServiceFarmBinding binding;
    FarmServiceAdapter serviceAdapter;
    ArrayList<PastFarmerRequestModel> serviceArrayList = new ArrayList<>();
    ApiInterface apiInterface;


    public static Fragment newInstance() {
        return new ServiceFragmentFarm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentServiceFarmBinding.inflate(inflater);
        View view = binding.getRoot();

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
                        serviceArrayList.addAll(response.body().getPastServiceData());
                        bindRcv();
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


    public void bindRcv(){
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        binding.rcvFarmServiceView.setLayoutManager(manager);

        serviceAdapter = new FarmServiceAdapter(getActivity(),serviceArrayList);
        binding.rcvFarmServiceView.setAdapter(serviceAdapter);

    }


}