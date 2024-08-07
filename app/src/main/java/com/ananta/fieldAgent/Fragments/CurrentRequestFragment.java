package com.ananta.fieldAgent.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class CurrentRequestFragment extends Fragment {

    FragmentCurrentRequestBinding binding;
    CurrentRequestAdapter adapter;
    ArrayList<CurrentServiceDatum> currentReqList = new ArrayList<>();

    public CurrentRequestFragment(ArrayList<CurrentServiceDatum> currentReqList) {
        this.currentReqList = currentReqList;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCurrentRequestBinding.inflate(inflater);
        View view = binding.getRoot();

        binding.svSearchViewCurrent.clearFocus();
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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        bindRcv();
    }

    public void bindRcv() {

        if (currentReqList.isEmpty()){
            binding.rlData.setVisibility(View.GONE);
            binding.rlNoData.setVisibility(View.VISIBLE);
        }else{
            binding.rlData.setVisibility(View.VISIBLE);
            binding.rlNoData.setVisibility(View.GONE);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            binding.rcvCurrentReqView.setLayoutManager(manager);
            adapter = new CurrentRequestAdapter(getActivity(), currentReqList);
            binding.rcvCurrentReqView.setAdapter(adapter);
        }

    }

    private void filter(String text) {
        ArrayList<CurrentServiceDatum> filteredlist = new ArrayList<>();

        for (CurrentServiceDatum item : currentReqList) {
            if (item.getRequest_type().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(getContext(), "No Search Result found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
    }
}