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

import com.ananta.fieldAgent.Activity.farmer.FarmerCurrentServiceSinglePageActivity;
import com.ananta.fieldAgent.Models.CurrentServiceDatumFarmer;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FarmAdapter extends RecyclerView.Adapter<FarmAdapter.ViewHolder> {

    Context context;
    List<CurrentServiceDatumFarmer> farmerList;

    public FarmAdapter(Context context, List<CurrentServiceDatumFarmer> farmerList) {
        this.context = context;
        this.farmerList = farmerList;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<CurrentServiceDatumFarmer> filterlist) {
        farmerList = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FarmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_farmer_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmAdapter.ViewHolder holder, int position) {
        CurrentServiceDatumFarmer model = farmerList.get(position);
        holder.tvFarmerName.setText(model.getRequest_type());
        holder.tvAddressName.setText(model.getDescription());
        holder.tvPumpName.setText(model.getService_request());

        if (!model.getImage_name().isEmpty()) {
            Glide.with(context).load(Const.IMAGE_URL + model.getImage_name()).into(holder.ivFarmerImage);
        } else {
            Glide.with(context).load(R.drawable.ic_farmer).into(holder.ivFarmerImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FarmerCurrentServiceSinglePageActivity.class);
                intent.putExtra("farmer_name", model.getFarmer_name());
                intent.putExtra("request_name", model.getRequest_type());
                intent.putExtra("farmer_address", model.getFarmer_address());
                intent.putExtra("image_name", model.getImage_name());
                intent.putExtra("ComplaintId", model.getComplaint_id());
                intent.putExtra("ID", String.valueOf(model.getId()));
                intent.putExtra("company_name", model.getCompany_name());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return farmerList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFarmerName, tvPumpName, tvAddressName;
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
