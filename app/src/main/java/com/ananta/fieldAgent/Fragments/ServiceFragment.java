package com.ananta.fieldAgent.Fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ananta.fieldAgent.Activity.LoginScreen;
import com.ananta.fieldAgent.Adapters.ServiceAdapter;
import com.ananta.fieldAgent.Adapters.TabFragmentAdapter;
import com.ananta.fieldAgent.Models.ServiceModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Utils;
import com.ananta.fieldAgent.databinding.FragmentServiceBinding;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceFragment extends Fragment {

    FragmentServiceBinding binding;
    ServiceAdapter serviceAdapter;
    ArrayList<ServiceModel> serviceArrayList = new ArrayList<>();
    ApiInterface apiInterface;
    TabFragmentAdapter adapter;

    public static Fragment newInstance() {
        return new ServiceFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentServiceBinding.inflate(inflater);
        View view = binding.getRoot();
//        getServiceData(Const.AGENT_ID);
        Log.d("id===","="+ Const.AGENT_ID);

        adapter = new TabFragmentAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(CurrentRequestFragment.newInstance(), "Current Request");
        adapter.addFragment(PastRequestFragment.newInstance(), "Past Request");
        binding.vpViewPager.setAdapter(adapter);
        binding.tbTabLayout.setupWithViewPager(binding.vpViewPager);

        binding.vpViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float positionOffset, int positionOffsetPx) {
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        return view;
    }


}