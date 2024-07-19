package com.ananta.fieldAgent.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ananta.fieldAgent.Models.PastReqModel;
import com.ananta.fieldAgent.Models.PastServiceDatum;
import com.ananta.fieldAgent.R;
import java.util.ArrayList;

public class PastReqAdapter extends RecyclerView.Adapter<PastReqAdapter.ViewHolder> {

    Context context;
    ArrayList<PastServiceDatum> pastReqModelsList;

    public PastReqAdapter(Context context, ArrayList<PastServiceDatum> pastReqModelsList) {
        this.context = context;
        this.pastReqModelsList = pastReqModelsList;
    }

    @NonNull
    @Override
    public PastReqAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_past_req_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PastReqAdapter.ViewHolder holder, int position) {

        PastServiceDatum model = pastReqModelsList.get(position);
        holder.tvNameFarmer.setText("jay");
        holder.tvPastReqName.setText(model.getServiceRequest());
    }

    @Override
    public int getItemCount() {
        return pastReqModelsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNameFarmer,tvPastReqName,tvAddressPastReq;
        ImageView ivPastReqImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameFarmer = itemView.findViewById(R.id.tvNameFarmer);
            tvPastReqName = itemView.findViewById(R.id.tvPastReqName);
            tvAddressPastReq = itemView.findViewById(R.id.tvAddressPastReq);
            ivPastReqImage = itemView.findViewById(R.id.ivPastReqImage);

        }
    }
}
