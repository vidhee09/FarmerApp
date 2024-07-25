package com.ananta.fieldAgent.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ananta.fieldAgent.Activity.farmer.PastServiceDetailActivity;
import com.ananta.fieldAgent.Models.PastFarmerRequestModel;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.R;
import com.bumptech.glide.Glide;

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
        if (!model.getImageName().isEmpty()){
            Glide.with(context).load(Const.IMAGE_URL+model.getImageName()).into(holder.ivServiceImage);
        }else {
            Glide.with(context).load(R.drawable.placeholder).into(holder.ivServiceImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).startActivity(new Intent(context, PastServiceDetailActivity.class).putExtra("complaint_name", model.getServiceRequest()).putExtra("complaint_number", model.getComplaint_id()));
            }
        });
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
