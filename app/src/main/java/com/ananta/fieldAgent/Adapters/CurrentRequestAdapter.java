package com.ananta.fieldAgent.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ananta.fieldAgent.Fragments.CurrentRequestFragment;
import com.ananta.fieldAgent.Models.CurrentReqModel;
import com.ananta.fieldAgent.Models.CurrentServiceDatum;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CurrentRequestAdapter extends RecyclerView.Adapter<CurrentRequestAdapter.ViewHolder> {

    Context context;
    ArrayList<CurrentServiceDatum>currentReqList;

    public CurrentRequestAdapter(Context context, ArrayList<CurrentServiceDatum> currentReqList) {
        this.context = context;
        this.currentReqList = currentReqList;
    }

    @NonNull
    @Override
    public CurrentRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_current_request_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentRequestAdapter.ViewHolder holder, int position) {

        CurrentServiceDatum model = currentReqList.get(position);
        holder.tvFarmerName.setText(model.getFarmer_name());
        holder.tvRequestName.setText(model.getServiceRequest());
        holder.tvAddressCurrentReq.setText(model.getFarmer_address());
        Glide.with(context).load(Const.IMAGE_URL+model.getImageName()).error(R.drawable.placeholder).into(holder.ivCurrentReqImage);

    }

    @Override
    public int getItemCount() {
        return currentReqList.size();
    }

    public void filterList(ArrayList<CurrentServiceDatum> filteredlist) {
        currentReqList = filteredlist;
        notifyDataSetChanged();
    }

    public CurrentRequestFragment getFilter() {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFarmerName,tvRequestName,tvAddressCurrentReq;
        ImageView ivCurrentReqImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFarmerName= itemView.findViewById(R.id.tvFarmerName);
            tvRequestName= itemView.findViewById(R.id.tvRequestName);
            tvAddressCurrentReq= itemView.findViewById(R.id.tvAddressCurrentReq);
            ivCurrentReqImage= itemView.findViewById(R.id.ivCurrentReqImage);

        }
    }



}
