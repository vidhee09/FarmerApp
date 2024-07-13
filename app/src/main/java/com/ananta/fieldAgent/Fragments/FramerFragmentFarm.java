package com.ananta.fieldAgent.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ananta.fieldAgent.Adapters.FarmAdapter;
import com.ananta.fieldAgent.Models.FarmModel;
import com.ananta.fieldAgent.databinding.FragmentFramerFarmBinding;

import java.util.ArrayList;

public class FramerFragmentFarm extends Fragment {

    FragmentFramerFarmBinding binding;

    FarmAdapter farmAdapter;
    ArrayList<FarmModel> farmerModelArrayList = new ArrayList<>();

    public static Fragment newInstance() {
        return new FramerFragmentFarm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFramerFarmBinding.inflate(inflater);
        View view = binding.getRoot();

        bindList();
        return view;
    }

    public void bindList() {

        farmerModelArrayList.add(new FarmModel("Jay Morjariya"));
        farmerModelArrayList.add(new FarmModel("Jay Morjariya"));
        farmerModelArrayList.add(new FarmModel("Jay Morjariya"));
        farmerModelArrayList.add(new FarmModel("Jay Morjariya"));
        farmerModelArrayList.add(new FarmModel("Jay Morjariya"));

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rcvFarmView.setLayoutManager(manager);

        farmAdapter = new FarmAdapter(getActivity(), farmerModelArrayList);
        binding.rcvFarmView.setAdapter(farmAdapter);

    }
}