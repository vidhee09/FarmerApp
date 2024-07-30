package com.ananta.fieldAgent.Activity.fieldAgent;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivitySingleCurrentServiceDetailsBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SingleCurrentServiceDetailsActivity extends AppCompatActivity {

    ActivitySingleCurrentServiceDetailsBinding binding;
    String image, farmer_name, request_name, farmer_address, ComplaintId, ID, company_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingleCurrentServiceDetailsBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.ivBackPress.setOnClickListener(v -> {
            onBackPressed();
        });

        farmer_name = getIntent().getStringExtra("farmer_name");
        request_name = getIntent().getStringExtra("request_name");
        farmer_address = getIntent().getStringExtra("farmer_address");
        image = getIntent().getStringExtra("image_name");
        ComplaintId = getIntent().getStringExtra("ComplaintId");
        ID = getIntent().getStringExtra("ID");
        company_name = getIntent().getStringExtra("company_name");

        binding.tvFarmerNameCurrentService.setText(farmer_name);
        binding.tvAddressCurrentService.setText(farmer_address);
        binding.tvApplicationNoCurrentService.setText(ComplaintId);
        binding.tvFarmerIDCurrentService.setText(ID);
        binding.tvComplaintNameCurrentService.setText(request_name);

        Glide.with(getApplicationContext()).load(Const.IMAGE_URL + image).into(binding.ivFarmerImageCurrentService);


        binding.btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(SingleCurrentServiceDetailsActivity.this);
                builder.setView(R.layout.detail_form_dialog).create().show();
                Toast.makeText(SingleCurrentServiceDetailsActivity.this, "complete", Toast.LENGTH_SHORT).show();

            }
        });

    }
}