package com.ananta.fieldAgent.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ananta.fieldAgent.Adapters.FarmServiceAdapter;
import com.ananta.fieldAgent.Models.FarmServiceModel;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.FragmentFramerFarmBinding;
import com.ananta.fieldAgent.databinding.FragmentServiceFarmBinding;

import java.util.ArrayList;

public class ServiceFragmentFarm extends Fragment {

    FragmentServiceFarmBinding binding;
    FarmServiceAdapter serviceAdapter;
    ArrayList<FarmServiceModel> serviceArrayList = new ArrayList<>();

    public static Fragment newInstance() {
        return new ServiceFragmentFarm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentServiceFarmBinding.inflate(inflater);
        View view = binding.getRoot();

        bindRcv();
        return view;
    }

    public void bindRcv(){

        serviceArrayList.add(new FarmServiceModel("Freya Tana"));
        serviceArrayList.add(new FarmServiceModel("Freya Tana"));
        serviceArrayList.add(new FarmServiceModel("Freya Tana"));
        serviceArrayList.add(new FarmServiceModel("Freya Tana"));
        serviceArrayList.add(new FarmServiceModel("Freya Tana"));

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        binding.rcvFarmServiceView.setLayoutManager(manager);

        serviceAdapter = new FarmServiceAdapter(getActivity(),serviceArrayList);
        binding.rcvFarmServiceView.setAdapter(serviceAdapter);

    }


}