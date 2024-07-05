package com.ananta.fieldAgent.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ananta.fieldAgent.Adapters.FarmerAdapter;
import com.ananta.fieldAgent.Models.FarmerModel;
import com.ananta.fieldAgent.databinding.FragmentFramerBinding;

import java.util.ArrayList;

public class FramerFragment extends Fragment {


    FragmentFramerBinding binding;
    FarmerAdapter farmerAdapter;
    ArrayList<FarmerModel> farmerModelArrayList = new ArrayList<>();

    public static FramerFragment newInstance() {
        return new FramerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFramerBinding.inflate(inflater);
        View view =  binding.getRoot();

        bindList();

        return view;
    }


    public void  bindList(){

        farmerModelArrayList.add(new FarmerModel("Jay Morjariya"));
        farmerModelArrayList.add(new FarmerModel("Jay Morjariya"));
        farmerModelArrayList.add(new FarmerModel("Jay Morjariya"));
        farmerModelArrayList.add(new FarmerModel("Jay Morjariya"));
        farmerModelArrayList.add(new FarmerModel("Jay Morjariya"));

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        binding.rcvFarmerView.setLayoutManager(manager);

        farmerAdapter = new FarmerAdapter(getActivity(),farmerModelArrayList);
        binding.rcvFarmerView.setAdapter(farmerAdapter);


    }


}