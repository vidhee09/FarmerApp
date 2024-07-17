package com.ananta.fieldAgent.Fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ananta.fieldAgent.Activity.LoginScreen;
import com.ananta.fieldAgent.Adapters.ServiceAdapter;
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

    public static Fragment newInstance() {
        return new ServiceFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentServiceBinding.inflate(inflater);
        View view = binding.getRoot();

        getServiceData(Const.AGENT_ID);
        Log.d("id===","="+ Const.AGENT_ID);

        return view;
    }

    public void bindRcv(){

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        binding.rcvServiceView.setLayoutManager(manager);

        serviceAdapter = new ServiceAdapter(getActivity(),serviceArrayList);
        binding.rcvServiceView.setAdapter(serviceAdapter);

    }

    public void getServiceData(String id){

        Utils.showCustomProgressDialog(getActivity(),true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id",id);

        Call<ServiceModel> call = apiInterface.getDashboardService(hashMap);
        call.enqueue(new Callback<ServiceModel>() {
            @Override
            public void onResponse(Call<ServiceModel> call, Response<ServiceModel> response) {

                if (response.body() != null){
                    if (response.body().getSuccess().equals("true")){
                        Utils.hideProgressDialog(getActivity());
                        serviceArrayList.addAll(response.body().getService_data());
                        bindRcv();
                    }else {
                        Utils.showCustomProgressDialog(getActivity(),true);
                        Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Utils.showCustomProgressDialog(getActivity(),true);
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServiceModel> call, Throwable t) {
                Utils.showCustomProgressDialog(getActivity(),true);
                Toast.makeText(getActivity(), "Data not found-"+t, Toast.LENGTH_SHORT).show();
            }
        });

    }

}