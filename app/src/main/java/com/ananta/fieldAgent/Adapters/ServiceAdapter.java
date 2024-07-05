package com.ananta.fieldAgent.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ananta.fieldAgent.Models.ServiceModel;
import com.ananta.fieldAgent.*;

import java.util.ArrayList;

public class ServiceAdapter  extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    Context context;
    ArrayList<ServiceModel> serviceList;

    public ServiceAdapter(Context context, ArrayList<ServiceModel> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_service_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder holder, int position) {

        ServiceModel model = serviceList.get(position);
        holder.tvFarmerName.setText(model.getName());
//        Glide.with(context).load(model.getImage()).into(holder.ivServiceImage);

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {

        TextView tvFarmerName,tvPumpName,tvAddressName;
        ImageView ivServiceImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFarmerName = itemView.findViewById(R.id.tvFarmerName);
            tvPumpName = itemView.findViewById(R.id.tvPumpName);
            tvAddressName = itemView.findViewById(R.id.tvAddressName);
            ivServiceImage = itemView.findViewById(R.id.ivServiceImage);

        }
    }
}
