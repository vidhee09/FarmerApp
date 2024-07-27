package com.ananta.fieldAgent.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ananta.fieldAgent.Activity.fieldAgent.ShowComplaintActivity;
import com.ananta.fieldAgent.Models.PastReqModel;
import com.ananta.fieldAgent.Models.PastServiceDatum;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.R;
import com.bumptech.glide.Glide;

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
        holder.tvNameFarmer.setText(model.getFarmer_id());
        holder.tvAddressPastReq.setText(model.getService_request());
        holder.tvPastReqName.setText(model.getService_request());

        Glide.with(context).load(Const.IMAGE_URL+model.getImage_name()).error(R.drawable.placeholder).into(holder.ivPastReqImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowComplaintActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("FName",model.getService_request());
                intent.putExtra("ComplaintID",model.getComplaint_id());
                intent.putExtra("ComplaintName",model.getService_request());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pastReqModelsList.size();
    }

    public void filterList(ArrayList<PastServiceDatum> filteredlist) {
        pastReqModelsList = filteredlist;
        notifyDataSetChanged();
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
