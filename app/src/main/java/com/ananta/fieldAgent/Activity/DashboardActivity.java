package com.ananta.fieldAgent.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Activity.fieldAgent.AddRequestActivity;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.Utils.CustomDialogAlert;

public class DashboardActivity extends AppCompatActivity {

    private LinearLayout llFarmer, llService;
    private ImageView ivAddReqImage, ivSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindView();
        initView();
        addListener();
    }

    private void bindView() {
        llFarmer = findViewById(R.id.llFarmer);
        llService = findViewById(R.id.llService);
        ivAddReqImage = findViewById(R.id.ivAddReqImage);
        ivSignOut = findViewById(R.id.ivSignOut);
    }

    private void initView() {
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

        ivAddReqImage.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AddRequestActivity.class);
            startActivity(intent);
        });


        ivSignOut.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("sharedData",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("agentLogin","");
            editor.commit();
            Intent intent = new Intent(DashboardActivity.this, LoginScreen.class);
            startActivity(intent);
            finishAffinity();
        });
    }

    @Override
    public void onBackPressed() {
        CustomDialogAlert customDialogAlert = new CustomDialogAlert(this, this.getResources().getString(R.string.close_application), this.getResources().getString(R.string.close_text), this.getResources().getString(R.string.yes)) {
            @Override
            public void onClickLeftButton() {
                dismiss();
            }

            @Override
            public void onClickRightButton() {
                dismiss();
                finish();
            }
        };
        customDialogAlert.show();
    }
}