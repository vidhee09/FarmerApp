package com.ananta.fieldAgent.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ananta.fieldAgent.Models.CurrentRequestFarmerModel;
import com.ananta.fieldAgent.Models.PastFarmerRequestModel;
import com.ananta.fieldAgent.R;

import java.util.ArrayList;
import java.util.List;

public class FarmServiceAdapter extends RecyclerView.Adapter<FarmServiceAdapter.ViewHolder> {

    Context context;
    List<PastFarmerRequestModel> serviceList;

    public FarmServiceAdapter(Context context, List<PastFarmerRequestModel> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    public void filterList(ArrayList<PastFarmerRequestModel> filterlist) {
        serviceList = filterlist;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FarmServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_service_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmServiceAdapter.ViewHolder holder, int position) {
        PastFarmerRequestModel model = serviceList.get(position);
        holder.tvFarmerName.setText(model.getRequestType());
        holder.tvAddressName.setText(model.getDescription());
        holder.tvPumpName.setText(model.getServiceRequest());
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
