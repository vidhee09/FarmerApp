package com.ananta.fieldAgent.Activity.fieldAgent;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Models.CheckStatusModel;
import com.ananta.fieldAgent.Models.DetailModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityFarmerDetailBinding;
import com.permissionx.guolindev.PermissionX;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FarmerDetailActivity extends AppCompatActivity {

    ActivityFarmerDetailBinding binding;
    ApiInterface apiInterface;
    String FarmerPosition="",CompanyName="",FarmerName ="";
    public static final int PERMISSION_FOR_LOCATION = 2;

    String site_report,delivery_report,joint_report,pump_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityFarmerDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FarmerPosition = getIntent().getStringExtra("farmer_position");
        FarmerName = getIntent().getStringExtra("FarmerName");
        CompanyName = getIntent().getStringExtra("CompanyName");

        binding.tvAgentName.setText(FarmerName);
        binding.tvCompanyName.setText(CompanyName);

        binding.rlSiteReport.setOnClickListener(v -> PermissionX.init(FarmerDetailActivity.this).permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                 .explainReasonBeforeRequest()
                .onExplainRequestReason((scope, deniedList, beforeRequest) -> {
//                    CustomDialog customDialog = new CustomDialog(MainJavaActivity.this, "PermissionX needs following permissions to continue", deniedList);
//                    scope.showRequestReasonDialog(customDialog);
                    scope.showRequestReasonDialog(deniedList, "PermissionX needs following permissions to continue", "Allow");
                })
                .onForwardToSettings((scope, deniedList) -> {
                    scope.showForwardToSettingsDialog(deniedList, "Please allow following permissions in settings", "Allow");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        Intent intent = new Intent(FarmerDetailActivity.this,SitInspectionReportActivity.class);
                        intent.putExtra("FarmerPosition",FarmerPosition);
                        intent.putExtra("FarmerName",FarmerName);
                        startActivity(intent);
//                            Toast.makeText(FarmerDetailActivity.this, "All permissions are granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FarmerDetailActivity.this, "The following permissions are denied：" + deniedList, Toast.LENGTH_SHORT).show();
                    }
                }));
        checkReportStatus();

        binding.rlSiteReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerDetailActivity.this, SitInspectionReportActivity.class);
                intent.putExtra("site_report", site_report);
                Log.d("report===","="+ site_report);
                startActivity(intent);
            }
        });

        binding.rlDeliveryReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerDetailActivity.this, DeliveryReportActivity.class);
                intent.putExtra("delivery_report", delivery_report);
                startActivity(intent);
            }
        });
        binding.rlDeliveryReport.setOnClickListener(v ->PermissionX.init(FarmerDetailActivity.this).permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .explainReasonBeforeRequest()
                .onExplainRequestReason((scope, deniedList, beforeRequest) -> {
//                    CustomDialog customDialog = new CustomDialog(MainJavaActivity.this, "PermissionX needs following permissions to continue", deniedList);
//                    scope.showRequestReasonDialog(customDialog);
                    scope.showRequestReasonDialog(deniedList, "PermissionX needs following permissions to continue", "Allow");
                })
                .onForwardToSettings((scope, deniedList) -> {
                    scope.showForwardToSettingsDialog(deniedList, "Please allow following permissions in settings", "Allow");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        Intent intent = new Intent(FarmerDetailActivity.this,DeliveryReportActivity.class);
                        intent.putExtra("FarmerPosition",FarmerPosition);
                        intent.putExtra("FarmerName",FarmerName);
                        startActivity(intent);
//                            Toast.makeText(FarmerDetailActivity.this, "All permissions are granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FarmerDetailActivity.this, "The following permissions are denied：" + deniedList, Toast.LENGTH_SHORT).show();
                    }
                }));

        binding.rlPumpInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerDetailActivity.this, PumpInstallationActivity.class);
                intent.putExtra("pump_report",pump_report);
                startActivity(intent);
            }
        });

        binding.rlPumpInstall.setOnClickListener(v -> PermissionX.init(FarmerDetailActivity.this).permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .explainReasonBeforeRequest()
                .onExplainRequestReason((scope, deniedList, beforeRequest) -> {
//                    CustomDialog customDialog = new CustomDialog(MainJavaActivity.this, "PermissionX needs following permissions to continue", deniedList);
//                    scope.showRequestReasonDialog(customDialog);
                    scope.showRequestReasonDialog(deniedList, "PermissionX needs following permissions to continue", "Allow");
                })
                .onForwardToSettings((scope, deniedList) -> {
                    scope.showForwardToSettingsDialog(deniedList, "Please allow following permissions in settings", "Allow");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        Intent intent = new Intent(FarmerDetailActivity.this,PumpInstallationActivity.class);
                        intent.putExtra("FarmerPosition",FarmerPosition);
                        intent.putExtra("FarmerName",FarmerName);
                        startActivity(intent);
//                            Toast.makeText(FarmerDetailActivity.this, "All permissions are granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FarmerDetailActivity.this, "The following permissions are denied：" + deniedList, Toast.LENGTH_SHORT).show();
                    }
                }));


        binding.rlJointReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerDetailActivity.this, JointReportActivity.class);
                intent.putExtra("joint_report",joint_report);
                startActivity(intent);
            }
        });

        binding.rlJointReport.setOnClickListener(v -> PermissionX.init(FarmerDetailActivity.this).permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .explainReasonBeforeRequest()
                .onExplainRequestReason((scope, deniedList, beforeRequest) -> {
//                    CustomDialog customDialog = new CustomDialog(MainJavaActivity.this, "PermissionX needs following permissions to continue", deniedList);
//                    scope.showRequestReasonDialog(customDialog);
                    scope.showRequestReasonDialog(deniedList, "PermissionX needs following permissions to continue", "Allow");
                })
                .onForwardToSettings((scope, deniedList) -> {
                    scope.showForwardToSettingsDialog(deniedList, "Please allow following permissions in settings", "Allow");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        Intent intent = new Intent(FarmerDetailActivity.this,JointReportActivity.class);
                        intent.putExtra("FarmerName",FarmerName);
                        startActivity(intent);
//                            Toast.makeText(FarmerDetailActivity.this, "All permissions are granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FarmerDetailActivity.this, "The following permissions are denied：" + deniedList, Toast.LENGTH_SHORT).show();
                    }
                }));


        binding.ivBackPress.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkReportStatus();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void checkReportStatus() {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("agent_id", Const.AGENT_ID);
        hashMap.put("farmer_id", Const.FARMER_ID);

        Log.d("FARMER_ID==", "=" + Const.FARMER_ID + "aid===" + Const.AGENT_ID);

        Call<CheckStatusModel> call = apiInterface.checkReportStatus(hashMap);
        call.enqueue(new Callback<CheckStatusModel>() {
            @Override
            public void onResponse(Call<CheckStatusModel> call, Response<CheckStatusModel> response) {

                binding.pbProgressBar.setVisibility(View.GONE);

                site_report = response.body().getSite_report();
                delivery_report = response.body().getSite_report();
                joint_report= response.body().getJoint_report();
                pump_report = response.body().getPump_report();

                if (site_report.equals("1")){
                    binding.ivSiteRightArrow.setImageResource(R.drawable.right);
                }
                if (delivery_report.equals("1")){
                    binding.ivDeliveryRightArrow.setImageResource(R.drawable.right);
                }
                if (pump_report.equals("1")){
                    binding.ivPumpRightArrow.setImageResource(R.drawable.right);
                }
                if (joint_report.equals("1")){
                    binding.ivJointRightArrow.setImageResource(R.drawable.right);
                }

                Log.d("response====", "=" + site_report + delivery_report + joint_report + pump_report);
            }

            @Override
            public void onFailure(Call<CheckStatusModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(FarmerDetailActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });

    }

}