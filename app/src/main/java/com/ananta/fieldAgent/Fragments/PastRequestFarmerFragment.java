package com.ananta.fieldAgent.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.ananta.fieldAgent.Adapters.FarmServiceAdapter;

import com.ananta.fieldAgent.Models.PastFarmerRequestModel;

import com.ananta.fieldAgent.Models.PastServiceDatum;
import com.ananta.fieldAgent.Models.PastServiceDatumFarmer;
import com.ananta.fieldAgent.databinding.FragmentServiceFarmBinding;

import java.util.ArrayList;
import java.util.List;


public class PastRequestFarmerFragment extends Fragment {

    FragmentServiceFarmBinding binding;
    FarmServiceAdapter serviceAdapter;
    List<PastServiceDatumFarmer> serviceArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentServiceFarmBinding.inflate(inflater);
        View view = binding.getRoot();

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
        return view;
    }

    public PastRequestFarmerFragment(List<PastServiceDatumFarmer> serviceArrayList) {
        this.serviceArrayList = serviceArrayList;
    }

    @Override
    public void onResume() {
        super.onResume();
        bindRcv();
    }

    public void bindRcv() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rcvFarmServiceView.setLayoutManager(manager);

        serviceAdapter = new FarmServiceAdapter(getActivity(), serviceArrayList);
        binding.rcvFarmServiceView.setAdapter(serviceAdapter);

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
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // passing the filtered list to the adapter class
            serviceAdapter.filterList(filteredlist);
        }
    }

}