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
import com.ananta.fieldAgent.R;
import java.util.ArrayList;

public class PastReqAdapter extends RecyclerView.Adapter<PastReqAdapter.ViewHolder> {

    Context context;
    ArrayList<PastReqModel> pastReqModelsList;

    public PastReqAdapter(Context context, ArrayList<PastReqModel> pastReqModelsList) {
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

        PastReqModel model = pastReqModelsList.get(position);
        holder.tvNamePastReq.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return pastReqModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamePastReq,tvPumpNamePastReq,tvAddressPastReq;
        ImageView ivPastReqImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNamePastReq = itemView.findViewById(R.id.tvNamePastReq);
            tvPumpNamePastReq = itemView.findViewById(R.id.tvPumpNamePastReq);
            tvAddressPastReq = itemView.findViewById(R.id.tvAddressPastReq);
            ivPastReqImage = itemView.findViewById(R.id.ivPastReqImage);


        }
    }
}
