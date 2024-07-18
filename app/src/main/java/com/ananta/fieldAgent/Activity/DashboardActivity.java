package com.ananta.fieldAgent.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.Activity.fieldAgent.AddRequestActivity;
import com.ananta.fieldAgent.R;

public class DashboardActivity extends AppCompatActivity {

    private LinearLayout llFarmer, llService;
    private ImageView ivAddReqImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bindView();
        addListener();
    }

    private void bindView() {
        llFarmer = findViewById(R.id.llFarmer);
        llService = findViewById(R.id.llService);
        ivAddReqImage = findViewById(R.id.ivAddReqImage);
    }


    private void addListener() {
        llFarmer.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, FarmerActivity.class);
            startActivity(intent);
        });

        llService.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ServiceActivity.class);
            startActivity(intent);
        });

        ivAddReqImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AddRequestActivity.class);
                startActivity(intent);
            }
        });
    }
}