package com.ananta.fieldAgent.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.ananta.fieldAgent.Models.CurrentRequestFarmerModel;
import com.ananta.fieldAgent.Models.CurrentServiceDatum;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Preference;
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
    Preference preference;

    public static Fragment newInstance() {
        return new CurrentRequestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCurrentRequestBinding.inflate(inflater);
        View view = binding.getRoot();
        preference = Preference.getInstance(getActivity());

        getCurrentRequestData();
        return view;
    }

    public void bindRcv() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rcvCurrentReqView.setLayoutManager(manager);

        adapter = new CurrentRequestAdapter(getActivity(), currentReqList);
        binding.rcvCurrentReqView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void getCurrentRequestData() {

        binding.pbProgressBar.setVisibility(View.VISIBLE);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", Const.AGENT_ID);

        Call<CurrentReqModel> call = apiInterface.getCurrentRequest(hashMap ,"Bearer "+preference.getToken());

        call.enqueue(new Callback<CurrentReqModel>() {
            @Override
            public void onResponse(@NonNull Call<CurrentReqModel> call, @NonNull Response<CurrentReqModel> response) {

                Log.d("response===","=current"+response.code());
                if (response.body() != null){
                    if (response.body().getSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        assert response.body() != null;
                        currentReqList.addAll(response.body().getCurrent_service_data());
                        bindRcv();
                    } else {
                        binding.pbProgressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Request not available", Toast.LENGTH_SHORT).show();
                        binding.pbProgressBar.setVisibility(View.GONE);
                    }
                }else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Request not available", Toast.LENGTH_SHORT).show();
                    binding.pbProgressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<CurrentReqModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), " "+t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.pbProgressBar.setVisibility(View.GONE);
            }
        });

        binding.svSearchViewCurrent.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    private void filter(String text) {
        ArrayList<CurrentServiceDatum> filteredlist = new ArrayList<>();

        for (CurrentServiceDatum item : currentReqList) {
            if (item.getRequest_type().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
    }
}