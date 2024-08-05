package com.ananta.fieldAgent.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ananta.fieldAgent.Activity.farmer.FarmerCurrentServiceSinglePageActivity;
import com.ananta.fieldAgent.Activity.fieldAgent.SingleCurrentServiceDetailsActivity;
import com.ananta.fieldAgent.Fragments.CurrentRequestFragment;
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
        holder.tvRequestName.setText(model.getRequest_type());
        holder.tvAddressCurrentReq.setText(model.getFarmer_address());
        Glide.with(context).load(Const.IMAGE_URL+model.getImage_name()).error(R.drawable.ic_farmer).into(holder.ivCurrentReqImage);

        Log.d("Current ==>" , "=reason===>" + model.getReason());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleCurrentServiceDetailsActivity.class);
                intent.putExtra("farmer_name",model.getFarmer_name());
                intent.putExtra("farmer_ID",""+model.getFarmer_id());
                Log.d("Current ==>" , "=farmerId===>" +model.getFarmer_id());
                intent.putExtra("request_name",model.getRequest_type());
                intent.putExtra("farmer_address",model.getFarmer_address());
                intent.putExtra("image_name",model.getImage_name());
                intent.putExtra("ComplaintId",model.getComplaint_id());
                intent.putExtra("ID",String.valueOf(model.getId()));

                Log.d("Current ==>" , "=id===>" + model.getId());

                intent.putExtra("description", model.getDescription());
                intent.putExtra("reason", model.getReason());
                intent.putExtra("company_name",model.getCompany_name());
                context.startActivity(intent);

            }
        });

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
