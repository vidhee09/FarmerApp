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

import com.ananta.fieldAgent.Activity.fieldAgent.FarmerDetailActivity;
import com.ananta.fieldAgent.Activity.fieldAgent.MainActivity;
import com.ananta.fieldAgent.Models.CurrentRequestFarmerModel;
import com.ananta.fieldAgent.Models.FarmerDatum;
import com.ananta.fieldAgent.Models.FarmerModel;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.R;

import java.util.ArrayList;

public class FarmerAdapter extends RecyclerView.Adapter<FarmerAdapter.ViewHolder> {

    Context context;
    ArrayList<FarmerDatum> farmerList;

    public FarmerAdapter(Context context, ArrayList<FarmerDatum> farmerList) {
        this.context = context;
        this.farmerList = farmerList;

    }

    @NonNull
    @Override
    public FarmerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_farmer_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerAdapter.ViewHolder holder, int position) {

        FarmerDatum model = farmerList.get(position);
        holder.tvFarmerName.setText(model.getName());
        holder.tvAddressName.setText(model.getAddress());

        Log.d("farmer address===>","="+model.getAddress());

//      holder.tvPumpName.setText(model.getName());
//      holder.tvAddressName.setText(model.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FarmerDetailActivity.class);
                intent.putExtra("Selected_farmer_id",""+model.getId());
                intent.putExtra("FarmerName",model.getName());
                intent.putExtra("CompanyName",model.getAddress());
                context.startActivity(intent);

//                Const.FARMER_ID = String.valueOf(model.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return farmerList.size();
    }

    public void filterList(ArrayList<FarmerDatum> filteredlist) {
        farmerList = filteredlist;
        notifyDataSetChanged();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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
