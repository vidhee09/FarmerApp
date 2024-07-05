package com.ananta.fieldAgent.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ananta.fieldAgent.databinding.FragmentCurrentRequestBinding;
import com.ananta.fieldAgent.Adapters.CurrentRequestAdapter;
import com.ananta.fieldAgent.Models.CurrentReqModel;


import java.util.ArrayList;

public class CurrentRequestFragment extends Fragment {

    FragmentCurrentRequestBinding binding;
    CurrentRequestAdapter adapter;
    ArrayList<CurrentReqModel> currentReqList = new ArrayList<>();

    public static Fragment newInstance() {
        return new CurrentRequestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCurrentRequestBinding.inflate(inflater);
        View view = binding.getRoot();

        bindRcv();
        return view;

    }

    public void bindRcv(){

        currentReqList.add(new CurrentReqModel("Jay Morjariya"));
        currentReqList.add(new CurrentReqModel("Jay Morjariya"));
        currentReqList.add(new CurrentReqModel("Jay Morjariya"));
        currentReqList.add(new CurrentReqModel("Jay Morjariya"));

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        binding.rcvCurrentReqView.setLayoutManager(manager);

        adapter = new CurrentRequestAdapter(getActivity(),currentReqList);
        binding.rcvCurrentReqView.setAdapter(adapter);

    }
}