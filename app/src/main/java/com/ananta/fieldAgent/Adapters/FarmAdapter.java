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

import com.ananta.fieldAgent.Activity.farmer.FarmerProfileActivity;
import com.ananta.fieldAgent.Activity.fieldAgent.FarmerDetailActivity;
import com.ananta.fieldAgent.Models.FarmModel;
import com.ananta.fieldAgent.R;

import java.util.ArrayList;

public class FarmAdapter extends RecyclerView.Adapter<FarmAdapter.ViewHolder>{

    Context context;
    ArrayList<FarmModel> farmerList;

    public FarmAdapter(Context context, ArrayList<FarmModel> farmerList) {
        this.context = context;
        this.farmerList = farmerList;
    }

    @NonNull
    @Override
    public FarmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_farmer_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmAdapter.ViewHolder holder, int position) {
        FarmModel model = farmerList.get(position);
        holder.tvFarmerName.setText(model.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FarmerProfileActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return farmerList.size();
    }


    public class  ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFarmerName,tvPumpName,tvAddressName;
        ImageView ivFarmerImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFarmerName = itemView.findViewById(R.id.tvFarmerName);
            tvPumpName = itemView.findViewById(R.id.tvPumpName);
            tvAddressName = itemView.findViewById(R.id.tvAddressName);
            ivFarmerImage = itemView.findViewById(R.id.ivFarmerImage);
        }
    }
}
