package com.ananta.fieldAgent.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
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
import com.ananta.fieldAgent.Parser.Preference;
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


    public PastRequestFragment(ArrayList<PastServiceDatum> pastReqModelArrayList) {
        this.pastReqModelArrayList = pastReqModelArrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPastRequestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.svSearchViewPast.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                filter(newText);
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
        if (pastReqModelArrayList == null || pastReqModelArrayList.isEmpty() ){
            binding.llData.setVisibility(View.GONE);
            binding.rlNoData.setVisibility(View.VISIBLE);
        }else{
            binding.llData.setVisibility(View.VISIBLE);
            binding.rlNoData.setVisibility(View.GONE);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            binding.rcvPastReqView.setLayoutManager(manager);
            adapter = new PastReqAdapter(getActivity(), pastReqModelArrayList);
            binding.rcvPastReqView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void filter(String text) {
        ArrayList<PastServiceDatum> filteredlist = new ArrayList<>();

        for (PastServiceDatum item : pastReqModelArrayList) {
            if (item.getService_request().toLowerCase().contains(text.toLowerCase())) {
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