package com.ananta.fieldAgent.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.ananta.fieldAgent.Adapters.FarmAdapter;
import com.ananta.fieldAgent.Models.CurrentRequestFarmerModel;
import com.ananta.fieldAgent.Models.CurrentServiceDatum;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.databinding.FragmentFramerFarmBinding;

import java.util.ArrayList;
import java.util.List;

public class CurrenRequestFarmerFragment extends Fragment {

    FragmentFramerFarmBinding binding;
    List<CurrentServiceDatum> FarmerServiceresponseModelArrayList = new ArrayList<>();
    FarmAdapter farmAdapter;

    public CurrenRequestFarmerFragment(List<CurrentServiceDatum> farmerServiceresponseModelArrayList) {
        this.FarmerServiceresponseModelArrayList = farmerServiceresponseModelArrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFramerFarmBinding.inflate(inflater);
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

    @Override
    public void onResume() {
        super.onResume();
        bindList();
    }

    public void bindList() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rcvFarmView.setLayoutManager(manager);

        farmAdapter = new FarmAdapter(getActivity(), FarmerServiceresponseModelArrayList);
        binding.rcvFarmView.setAdapter(farmAdapter);

    }
    private void filter(String text) {
        ArrayList<CurrentServiceDatum> filteredlist = new ArrayList<>();

        for (CurrentServiceDatum item : FarmerServiceresponseModelArrayList) {
            if (item.getRequest_type().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }

        if (filteredlist.isEmpty()) {
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            farmAdapter.filterList(filteredlist);
        }
    }

}