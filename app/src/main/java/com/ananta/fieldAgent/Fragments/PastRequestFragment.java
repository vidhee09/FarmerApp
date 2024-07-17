package com.ananta.fieldAgent.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ananta.fieldAgent.Adapters.PastReqAdapter;
import com.ananta.fieldAgent.Models.PastReqModel;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.databinding.FragmentPastRequestBinding;

import java.util.ArrayList;

public class PastRequestFragment extends Fragment {

    FragmentPastRequestBinding binding;
    PastReqAdapter adapter;
    ArrayList<PastReqModel> pastReqModelArrayList = new ArrayList<>();

    ApiInterface apiInterface;

    public static Fragment newInstance() {
        return new PastRequestFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPastRequestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        bindRcv();
        return view;
    }

    public void bindRcv(){

        pastReqModelArrayList.add(new PastReqModel("Jay Morjariya"));
        pastReqModelArrayList.add(new PastReqModel("Jay Morjariya"));
        pastReqModelArrayList.add(new PastReqModel("Jay Morjariya"));

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        binding.rcvPastReqView.setLayoutManager(manager);

        adapter = new PastReqAdapter(getActivity(),pastReqModelArrayList);
        binding.rcvPastReqView.setAdapter(adapter);

    }

//    public void get

}