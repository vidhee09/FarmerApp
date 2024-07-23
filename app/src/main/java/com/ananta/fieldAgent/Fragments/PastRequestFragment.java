package com.ananta.fieldAgent.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.ananta.fieldAgent.Adapters.PastReqAdapter;
import com.ananta.fieldAgent.Models.CurrentReqModel;
import com.ananta.fieldAgent.Models.PastServiceDatum;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.databinding.FragmentPastRequestBinding;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastRequestFragment extends Fragment {

    FragmentPastRequestBinding binding;
    PastReqAdapter adapter;
    ArrayList<PastServiceDatum> pastReqModelArrayList = new ArrayList<>();

    ApiInterface apiInterface;

    public static Fragment newInstance() {
        return new PastRequestFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPastRequestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        getPastRequestData();

        return view;
    }

    public void bindRcv() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rcvPastReqView.setLayoutManager(manager);

        adapter = new PastReqAdapter(getActivity(), pastReqModelArrayList);
        binding.rcvPastReqView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    public void getPastRequestData() {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", Const.AGENT_ID);

        Call<CurrentReqModel> call = apiInterface.getPastRequest(hashMap);

        call.enqueue(new Callback<CurrentReqModel>() {
            @Override
            public void onResponse(Call<CurrentReqModel> call, Response<CurrentReqModel> response) {

                if (response.isSuccessful()) {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    pastReqModelArrayList.addAll(response.body().getPastServiceData());
                    bindRcv();

                } else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CurrentReqModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
            }
        });

        binding.svSearchViewPast.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        ArrayList<PastServiceDatum> filteredlist = new ArrayList<>();

        for (PastServiceDatum item : pastReqModelArrayList) {
            if (item.getRequestType().toLowerCase().contains(text.toLowerCase())) {
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