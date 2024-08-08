package com.ananta.fieldAgent.Activity.farmer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.ananta.fieldAgent.Activity.DashboardActivity;
import com.ananta.fieldAgent.Activity.LoginScreen;
import com.ananta.fieldAgent.Activity.fieldAgent.SitInspectionReportActivity;
import com.ananta.fieldAgent.Adapters.TabFragmentAdapter;
import com.ananta.fieldAgent.Fragments.CurrenRequestFarmerFragment;
import com.ananta.fieldAgent.Fragments.PastRequestFarmerFragment;
import com.ananta.fieldAgent.Models.FarmerServiceResponseModel;
import com.ananta.fieldAgent.Models.PastServiceDatumFarmer;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityFarmerDashboardBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmerDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityFarmerDashboardBinding binding;
    TabFragmentAdapter adapter;
    private Preference preference;
    ApiInterface apiInterface;
    TextView headerName, headerMobileNumber;
    ImageView headerProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityFarmerDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preference = Preference.getInstance(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View view = LayoutInflater.from(FarmerDashboardActivity.this).inflate(R.layout.sidebar_drawer, null);
        headerProfileImage = view.findViewById(R.id.nav_headerImage);
        headerName = view.findViewById(R.id.nav_header_agentName);
        headerMobileNumber = view.findViewById(R.id.nav_mobileNumber);
        binding.navSideBar.addHeaderView(view);

        headerName.setText(preference.getFarmerName());
        headerMobileNumber.setText(preference.getFarmerNum());

        binding.navSideBar.setNavigationItemSelectedListener(this);
        binding.tvFarmerName.setText(preference.getFarmerName());
        binding.tvFarmerNo.setText(preference.getFarmerNum());
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float positionOffset, int positionOffsetPx) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        binding.btnCreateRequest.setOnClickListener(v -> {
            Intent intent = new Intent(FarmerDashboardActivity.this, AddNewRequestFarmer.class);
            startActivity(intent);
            finish();
        });

        binding.ivSignOut.setOnClickListener(v -> {
            preference.putIsHideWelcomeScreen(false);
            preference.putFarmerName(null);
            preference.putFarmerNum(null);
            preference.putFarmerLoginId(null);
            preference.putFarmerName(null);
            preference.putProfileImage(null);
            Intent intent = new Intent(FarmerDashboardActivity.this, LoginScreen.class);
            startActivity(intent);
            finishAffinity();
        });

        binding.ivOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.myDrawerLayout.openDrawer(binding.navSideBar);
            }
        });


    }

    @Override
    protected void onResume() {
        if (Const.isInternetConnected(FarmerDashboardActivity.this)){
            getFarmerData(preference.getFarmerLoginId());
        }else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }

    public void setClickDisable(boolean b) {

        binding.ivSignOut.setClickable(b);
        binding.ivAddReqImage.setEnabled(b);
        binding.ivOpenDrawer.setEnabled(b);
    }

    public void getFarmerData(String id) {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setClickDisable(false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);

        Call<FarmerServiceResponseModel> call = apiInterface.getCurrentAndPastData(hashMap, "Bearer " + preference.getToken());
        Log.d("farmerData===TOKEN", "" + preference.getToken());

        call.enqueue(new Callback<FarmerServiceResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<FarmerServiceResponseModel> call, @NonNull Response<FarmerServiceResponseModel> response) {

                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setClickDisable(true);
                        binding.btnCreateRequest.setVisibility(View.VISIBLE);
                        binding.tvTitle.setVisibility(View.VISIBLE);
                        adapter = new TabFragmentAdapter(getSupportFragmentManager());
                        adapter.addFragment(new CurrenRequestFarmerFragment(response.body().getCurrent_service_data()), "Current Request");
                        binding.viewPager.setAdapter(adapter);
                        binding.tabLayout.setupWithViewPager(binding.viewPager);

                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setClickDisable(true);
                        Toast.makeText(FarmerDashboardActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setClickDisable(true);
                    Toast.makeText(FarmerDashboardActivity.this, "Data not available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FarmerServiceResponseModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setClickDisable(true);
                Toast.makeText(FarmerDashboardActivity.this, " " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*CustomDialogAlert customDialogAlert = new CustomDialogAlert(this, this.getResources().getString(R.string.close_application), this.getResources().getString(R.string.close_text), this.getResources().getString(R.string.yes)) {
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

        customDialogAlert.show();*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        int id = item.getItemId();
        if (id == R.id.profile) {
            intent = new Intent(FarmerDashboardActivity.this, FarmerProfileActivity.class);
            startActivity(intent);
        }

        if (id == R.id.logout) {
            preference.putIsHideWelcomeScreen(false);
            preference.putFarmerName(null);
            preference.putFarmerNum(null);
            preference.putFarmerLoginId(null);
            preference.putFarmerName(null);
            intent = new Intent(FarmerDashboardActivity.this, LoginScreen.class);
            startActivity(intent);
            finishAffinity();
        }

        if (id == R.id.pastReq) {
            intent = new Intent(FarmerDashboardActivity.this, PastRequestActivity.class);
            startActivity(intent);
        }

        binding.myDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}