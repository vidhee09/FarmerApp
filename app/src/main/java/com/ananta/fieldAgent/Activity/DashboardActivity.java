package com.ananta.fieldAgent.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ananta.fieldAgent.Activity.fieldAgent.AddRequestActivity;
import com.ananta.fieldAgent.Activity.fieldAgent.AgentProfileActivity;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.Utils.CustomDialogAlert;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout llFarmer, llService;
    private ImageView ivAddReqImage, ivSignOut, ivPersonalDetail;
    DrawerLayout drawer_layout;
    Preference preference;
    NavigationView navSideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        preference = Preference.getInstance(DashboardActivity.this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindView();
        addListener();
        navSideBar.setNavigationItemSelectedListener(this);
    }

    private void bindView() {
        llFarmer = findViewById(R.id.llFarmer);
        llService = findViewById(R.id.llService);
        ivAddReqImage = findViewById(R.id.ivAddReqImage);
//        ivSignOut = findViewById(R.id.ivSignOut);
        ivPersonalDetail = findViewById(R.id.ivPersonalDetail);
        drawer_layout = findViewById(R.id.drawer_layout);
        navSideBar = findViewById(R.id.navSideBar);
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

   /*     ivSignOut.setOnClickListener(v -> {
            preference.putAgentID("");
            Intent intent = new Intent(DashboardActivity.this, LoginScreen.class);
            startActivity(intent);
            finishAffinity();
        });*/

        ivPersonalDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profileShow) {
            Intent intent = new Intent(DashboardActivity.this, AgentProfileActivity.class);
            startActivity(intent);
        }

        if (id == R.id.signOut) {
            preference.putAgentID("");
            Intent intent = new Intent(DashboardActivity.this, LoginScreen.class);
            startActivity(intent);
            finishAffinity();
        }
        return false;
    }
}