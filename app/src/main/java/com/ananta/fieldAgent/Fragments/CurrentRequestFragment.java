package com.ananta.fieldAgent.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ananta.fieldAgent.Models.CurrentServiceDatum;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.databinding.FragmentCurrentRequestBinding;
import com.ananta.fieldAgent.Adapters.CurrentRequestAdapter;
import com.ananta.fieldAgent.Models.CurrentReqModel;


import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentRequestFragment extends Fragment {

    FragmentCurrentRequestBinding binding;
    CurrentRequestAdapter adapter;
    ArrayList<CurrentServiceDatum> currentReqList = new ArrayList<>();
    ApiInterface apiInterface;

    public static Fragment newInstance() {
        return new CurrentRequestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCurrentRequestBinding.inflate(inflater);
        View view = binding.getRoot();

        getCurrentRequestData();
        return view;
    }

    public void bindRcv(){

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        binding.rcvCurrentReqView.setLayoutManager(manager);

        adapter = new CurrentRequestAdapter(getActivity(),currentReqList);
        binding.rcvCurrentReqView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    public void getCurrentRequestData(){

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id", Const.AGENT_ID);

        Call<CurrentReqModel> call = apiInterface.getCurrentRequest(hashMap);

        call.enqueue(new Callback<CurrentReqModel>() {
            @Override
            public void onResponse(Call<CurrentReqModel> call, Response<CurrentReqModel> response) {

                if (response.isSuccessful()){
                    currentReqList.addAll(response.body().getCurrentServiceData());
                    bindRcv();
                }else {
                    Toast.makeText(getActivity(), "not success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CurrentReqModel> call, Throwable t) {
                Toast.makeText(getActivity(), "fail ", Toast.LENGTH_SHORT).show();

            }
        });

    }
}