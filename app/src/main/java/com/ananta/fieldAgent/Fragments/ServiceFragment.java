package com.ananta.fieldAgent.Fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ananta.fieldAgent.Adapters.ServiceAdapter;
import com.ananta.fieldAgent.Models.ServiceModel;
import com.ananta.fieldAgent.databinding.FragmentServiceBinding;
import java.util.ArrayList;

public class ServiceFragment extends Fragment {

    FragmentServiceBinding binding;
    ServiceAdapter serviceAdapter;
    ArrayList<ServiceModel> serviceArrayList = new ArrayList<>();

    public static Fragment newInstance() {
        return new ServiceFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentServiceBinding.inflate(inflater);
        View view = binding.getRoot();

        bindRcv();

        return view;
    }

    public void bindRcv(){

        serviceArrayList.add(new ServiceModel("Freya Tana"));
        serviceArrayList.add(new ServiceModel("Freya Tana"));
        serviceArrayList.add(new ServiceModel("Freya Tana"));
        serviceArrayList.add(new ServiceModel("Freya Tana"));
        serviceArrayList.add(new ServiceModel("Freya Tana"));

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        binding.rcvServiceView.setLayoutManager(manager);

        serviceAdapter = new ServiceAdapter(getActivity(),serviceArrayList);
        binding.rcvServiceView.setAdapter(serviceAdapter);

    }
}