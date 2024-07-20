package com.ananta.fieldAgent.Activity.fieldAgent;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityFarmerDetailBinding;
import com.permissionx.guolindev.PermissionX;



public class FarmerDetailActivity extends AppCompatActivity {

    ActivityFarmerDetailBinding binding;
    ApiInterface apiInterface;
    String FarmerPosition="",CompanyName="",FarmerName;
    public static final int PERMISSION_FOR_LOCATION = 2;

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
        Log.d("FarmerPosition====","=="+FarmerPosition);
        Log.d("FarmerPosition====","=="+FarmerName);
        Log.d("FarmerPosition====","=="+CompanyName);

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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}