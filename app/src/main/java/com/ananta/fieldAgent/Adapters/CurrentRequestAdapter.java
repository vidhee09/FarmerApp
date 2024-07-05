package com.ananta.fieldAgent.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ananta.fieldAgent.Models.CurrentReqModel;
import com.ananta.fieldAgent.R;

import java.util.ArrayList;

public class CurrentRequestAdapter extends RecyclerView.Adapter<CurrentRequestAdapter.ViewHolder> {

    Context context;
    ArrayList<CurrentReqModel>currentReqList;

    public CurrentRequestAdapter(Context context, ArrayList<CurrentReqModel> currentReqList) {
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

        CurrentReqModel model = currentReqList.get(position);

        holder.tvNameCurrentReq.setText(model.getName());

    }

    @Override
    public int getItemCount() {
        return currentReqList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNameCurrentReq,tvPumpNameCurrentReq,tvAddressCurrentReq;
        ImageView ivCurrentReqImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCurrentReq= itemView.findViewById(R.id.tvNameCurrentReq);
            tvPumpNameCurrentReq= itemView.findViewById(R.id.tvPumpNameCurrentReq);
            tvAddressCurrentReq= itemView.findViewById(R.id.tvAddressCurrentReq);
            ivCurrentReqImage= itemView.findViewById(R.id.ivCurrentReqImage);

        }
    }

}
