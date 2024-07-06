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

import com.ananta.fieldAgent.Adapters.FarmerAdapter;
import com.ananta.fieldAgent.Models.FarmerModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
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
    String id ;

    public static FramerFragment newInstance() {
        return new FramerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFramerBinding.inflate(inflater);
        View view =  binding.getRoot();

        Toast.makeText(getActivity(), "id=="+Const.USER_ID, Toast.LENGTH_SHORT).show();
        getFarmerData(Const.USER_ID);

        return view;
    }


    public void  bindList(){


        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        binding.rcvFarmerView.setLayoutManager(manager);

        farmerAdapter = new FarmerAdapter(getActivity(),farmerModelArrayList);
        binding.rcvFarmerView.setAdapter(farmerAdapter);

    }


    public void getFarmerData(String id){

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id",id);
        Log.d("id===>","="+id);

        Call<FarmerModel> call = apiInterface.getDashboardData(hashMap);

        call.enqueue(new Callback<FarmerModel>() {
            @Override
            public void onResponse(Call<FarmerModel> call, @NonNull Response<FarmerModel> response) {

                if (response.body() != null){
                    Log.d("id===>","="+response.body());
                    Toast.makeText(getActivity(), "Data found true", Toast.LENGTH_SHORT).show();
//                    farmerModelArrayList.addAll(response.body().getFarmer_data());
                    farmerModelArrayList.add(new FarmerModel("nameeeee"));
                    bindList();

                }else {
                    Log.d("id===>","="+response.body());
                    Toast.makeText(getActivity(), "Data not found null", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<FarmerModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Data not found-"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

}