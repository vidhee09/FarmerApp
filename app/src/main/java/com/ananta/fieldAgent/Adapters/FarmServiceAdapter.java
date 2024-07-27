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
import com.ananta.fieldAgent.Models.PastServiceDatum;
import com.ananta.fieldAgent.Models.PastServiceDatumFarmer;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FarmServiceAdapter extends RecyclerView.Adapter<FarmServiceAdapter.ViewHolder> {

    Context context;
    List<PastServiceDatumFarmer> serviceList;

    public FarmServiceAdapter(Context context, List<PastServiceDatumFarmer> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    public void filterList(ArrayList<PastServiceDatumFarmer> filterlist) {
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
        PastServiceDatumFarmer model = serviceList.get(position);
        holder.tvServiceName.setText(model.getRequest_type());
        holder.tvDescription.setText(model.getDescription());
        holder.tvCcomplaintId.setText(model.getComplaint_id());
        if (model.getImage_name() == null || !model.getImage_name().toString().isEmpty()){
            Glide.with(context).load(Const.IMAGE_URL+model.getImage_name()).into(holder.ivServiceImage);
        }else {
            Glide.with(context).load(R.drawable.placeholder).into(holder.ivServiceImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).startActivity(new Intent(context, PastServiceDetailActivity.class).putExtra("complaint_name", model.getService_request()).putExtra("complaint_number", model.getComplaint_id()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {

        TextView tvServiceName,tvCcomplaintId,tvDescription;
        ImageView ivServiceImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvServiceName = itemView.findViewById(R.id.tvFarmerName);
            tvCcomplaintId = itemView.findViewById(R.id.tvPumpName);
            tvDescription = itemView.findViewById(R.id.tvAddressName);
            ivServiceImage = itemView.findViewById(R.id.ivServiceImage);

        }
    }
}
