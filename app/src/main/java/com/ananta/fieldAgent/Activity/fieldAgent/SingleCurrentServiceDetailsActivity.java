package com.ananta.fieldAgent.Activity.fieldAgent;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Models.CheckStatusModel;
import com.ananta.fieldAgent.Models.ServiceRequestGetDataResponseModel;
import com.ananta.fieldAgent.Models.ServiceRequestUpdateResponseModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivitySingleCurrentServiceDetailsBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleCurrentServiceDetailsActivity extends AppCompatActivity {

    ActivitySingleCurrentServiceDetailsBinding binding;
    String image, farmer_name, request_name, farmer_address, ComplaintId, ID, company_name, reason, description, farmer_ID, pump_report, joint_report,pumpSurveyor="";
    ApiInterface apiInterface;
    Preference preference;
    EditText etImeiNo, etMotorSerialNumber, etMultiplePanelIds;
    Button btnUpdate;
    Response<ServiceRequestGetDataResponseModel> serviceRequestGetDataResponseModelResponse;
    ImageView ivBackPress, ivAddMoreId;
    Dialog dialog;
    ProgressBar pbDialog;
    RadioGroup radioGroupHeadSurveyor;
    RadioButton rbPumpHeadSurveyor30,rbPumpHeadSurveyor50,rbPumpHeadSurveyor70,rbPumpHeadSurveyor100;
    Chip chip;
    ChipGroup chipGroup;
    ArrayList<String> panelIdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingleCurrentServiceDetailsBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        preference = Preference.getInstance(SingleCurrentServiceDetailsActivity.this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.ivBackPress.setOnClickListener(v -> {
            onBackPressed();
        });

        farmer_name = getIntent().getStringExtra("farmer_name");
        farmer_ID = getIntent().getStringExtra("farmer_ID");
        request_name = getIntent().getStringExtra("request_name");
        farmer_address = getIntent().getStringExtra("farmer_address");
        image = getIntent().getStringExtra("image_name");
        ComplaintId = getIntent().getStringExtra("ComplaintId");
        ID = getIntent().getStringExtra("ID");
        Log.d("Single ==>", "===>" + ID);

        reason = getIntent().getStringExtra("reason");
        description = getIntent().getStringExtra("description");

        binding.tvFarmerNameCurrentService.setText(farmer_name);
        binding.tvAddressCurrentService.setText(farmer_address);
        binding.tvApplicationNoCurrentService.setText(ComplaintId);
        binding.tvFarmerIDCurrentService.setText(ID);
        binding.tvFarmerCompanyName.setText(company_name);
        binding.tvComplaintNameCurrentService.setText(request_name);
        binding.tvServiceDescription.setText(description);

        if (reason == null || reason.isEmpty() || company_name == null || company_name.isEmpty()) {
            binding.llReason.setVisibility(View.GONE);
            binding.llCompanyName.setVisibility(View.GONE);
        } else {
            binding.tvReason.setText(reason);
            company_name = getIntent().getStringExtra("company_name");
        }

        Glide.with(getApplicationContext()).load(Const.IMAGE_URL + image).into(binding.ivFarmerImageCurrentService);

        binding.btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(SingleCurrentServiceDetailsActivity.this);
                dialog.setContentView(R.layout.detail_form_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//                Log.d("Single ==>", "===>" + pumpDataResponse.body().getPumpInstallation().get(0).getImeiNo());
                etImeiNo = dialog.findViewById(R.id.etImeiNo);
                etMotorSerialNumber = dialog.findViewById(R.id.etMotorSerialNumber);
                etMultiplePanelIds = dialog.findViewById(R.id.etMultiplePanelIds);
                btnUpdate = dialog.findViewById(R.id.btnUpdate);
                pbDialog = dialog.findViewById(R.id.pbDialog);
                radioGroupHeadSurveyor = dialog.findViewById(R.id.radioGroupHeadSurveyor);
                ivBackPress = dialog.findViewById(R.id.ivBackPress);
                rbPumpHeadSurveyor30 = dialog.findViewById(R.id.rbPumpHeadSurveyor30);
                rbPumpHeadSurveyor50 = dialog.findViewById(R.id.rbPumpHeadSurveyor50);
                rbPumpHeadSurveyor70 = dialog.findViewById(R.id.rbPumpHeadSurveyor70);
                rbPumpHeadSurveyor100 = dialog.findViewById(R.id.rbPumpHeadSurveyor100);
                chipGroup = dialog.findViewById(R.id.chipGroup);
                ivAddMoreId = dialog.findViewById(R.id.ivAddMoreId);

                etImeiNo.setText(serviceRequestGetDataResponseModelResponse.body().getReports().get(0).getImeiNo());
                etMotorSerialNumber.setText(String.valueOf(serviceRequestGetDataResponseModelResponse.body().getReports().get(0).getPumpId()));

                pumpSurveyor = serviceRequestGetDataResponseModelResponse.body().getReports().get(0).getPumpRecomSurvey();

                Log.d("Joint Get", "====>" + pumpSurveyor + " " + pumpSurveyor);
                if (pumpSurveyor.contains("30")) {
                    rbPumpHeadSurveyor30.setChecked(true);
                }
                if (pumpSurveyor.contains("50")) {
                    rbPumpHeadSurveyor50.setChecked(true);
                }
                if (pumpSurveyor.contains("70")) {
                    rbPumpHeadSurveyor70.setChecked(true);
                }
                if (pumpSurveyor.contains("100")) {
                    rbPumpHeadSurveyor100.setChecked(true);
                }

                ivBackPress.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });

                ivAddMoreId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!etMultiplePanelIds.getText().toString().isEmpty()) {
                            addMorePanelId(etMultiplePanelIds.getText().toString().trim());
                            etMultiplePanelIds.setText("");
                        }
                    }
                });

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*---- Motor head ---*/
                        int selectedId = radioGroupHeadSurveyor.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton) dialog.findViewById(selectedId);
                        if (radioButton != null || selectedId != -1){
                            pumpSurveyor = radioButton.getText().toString();
                        }else {
                            Log.d("Joint Get", "===selectedId=else=>" + pumpSurveyor);
                        }

                        if (countChipsInChipGroup(chipGroup) >= 9) {
                            updateRequest();
                        } else {
                            Toast.makeText(SingleCurrentServiceDetailsActivity.this, "Please enter minimum 9 panel ids", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                /*======= Get All Chips ===========*/

                panelIdList.clear();
                String panel = serviceRequestGetDataResponseModelResponse.body().getReports().get(0).getPanelId();
                String[] panels = panel.split(",");

                String[] trimmedArray = new String[panels.length];
                for (int i = 0; i < panels.length; i++)
                    trimmedArray[i] = panels[i].trim();

                panelIdList.addAll(Arrays.asList(trimmedArray));

                for (int i = 0; i < panels.length; i++) {

                    Chip chip = new Chip(SingleCurrentServiceDetailsActivity.this);
                    LinearLayout.LayoutParams layoutParams = new
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(5, 1, 5, 1);

                    chip.setLayoutParams(layoutParams);
                    chip.setText(panels[i]);

                    chip.setCloseIconVisible(true);
                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Chip chip = (Chip) v;
                            chipGroup.removeView(chip);
                            panelIdList.remove(chip.getText().toString().trim());
                        }
                    });

                    chipGroup.addView(chip);
                }
                dialog.show();
            }

        });

        checkReportStatus();
    }

    private void updateRequest() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        pbDialog.setVisibility(View.VISIBLE);
        btnUpdate.setClickable(false);
        setAllClicksDisable(false);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("farmer_id", farmer_ID);
        if (etMotorSerialNumber.getText().toString().isEmpty()) {
            Toast.makeText(SingleCurrentServiceDetailsActivity.this, "Please enter motor serial number", Toast.LENGTH_SHORT).show();
            return;
        } else {
            hashMap.put("pump_id", etMotorSerialNumber.getText().toString());
        }
        if (etImeiNo.getText().toString().isEmpty()) {
            Toast.makeText(SingleCurrentServiceDetailsActivity.this, "Please enter imei number", Toast.LENGTH_SHORT).show();
            return;
        } else {
            hashMap.put("imei_no", etImeiNo.getText().toString());
        }
        if (panelIdList.isEmpty()){
            Toast.makeText(SingleCurrentServiceDetailsActivity.this, "Please enter panel id", Toast.LENGTH_SHORT).show();
            return;
        }else {
            hashMap.put("panel_id", panelIdList.toString());
        }
        if (pumpSurveyor.isEmpty()) {
            Toast.makeText(SingleCurrentServiceDetailsActivity.this, "Please enter pump surveyor", Toast.LENGTH_SHORT).show();
            return;
        } else {
            hashMap.put("pump_recom_survey", pumpSurveyor);
        }


        Call<ServiceRequestUpdateResponseModel> call = apiInterface.updateRequest(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<ServiceRequestUpdateResponseModel>() {
            @Override
            public void onResponse(Call<ServiceRequestUpdateResponseModel> call, Response<ServiceRequestUpdateResponseModel> response) {

                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        pbDialog.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        btnUpdate.setClickable(true);
                        dialog.dismiss();
                        Toast.makeText(SingleCurrentServiceDetailsActivity.this, "Request update successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        pbDialog.setVisibility(View.GONE);
                        setAllClicksDisable(false);
                        btnUpdate.setClickable(false);
                    }
                } else {
                    pbDialog.setVisibility(View.GONE);
//                    Toast.makeText(SingleCurrentServiceDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    setAllClicksDisable(false);
                    btnUpdate.setClickable(false);
                }
            }

            @Override
            public void onFailure(Call<ServiceRequestUpdateResponseModel> call, Throwable t) {
                pbDialog.setVisibility(View.GONE);
                setAllClicksDisable(false);
                btnUpdate.setClickable(false);
                Toast.makeText(SingleCurrentServiceDetailsActivity.this, "no open service requests" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkReportStatus() {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("agent_id", preference.getAgentId());
        hashMap.put("farmer_id", farmer_ID);

        Call<CheckStatusModel> call = apiInterface.checkReportStatus(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<CheckStatusModel>() {
            @Override
            public void onResponse(Call<CheckStatusModel> call, Response<CheckStatusModel> response) {

                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        joint_report = String.valueOf(response.body().getReports().getJointReport());
                        pump_report = String.valueOf(response.body().getReports().getPumpReport());

                        if (pump_report.equals("1") && joint_report.equals("1")){
                            getServiceReportData();
                        }else {
                            binding.ivBackPress.setClickable(true);
                            binding.btnComplete.setClickable(false);
                            Toast.makeText(SingleCurrentServiceDetailsActivity.this, "Please first filled reports", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        binding.ivBackPress.setClickable(true);
                        binding.btnComplete.setClickable(false);
                        Toast.makeText(SingleCurrentServiceDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    binding.ivBackPress.setClickable(true);
                    binding.btnComplete.setClickable(false);
                    Toast.makeText(SingleCurrentServiceDetailsActivity.this, "Please first filled reports", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CheckStatusModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                Toast.makeText(SingleCurrentServiceDetailsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getServiceReportData() {
        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("farmer_id", farmer_ID);

        Call<ServiceRequestGetDataResponseModel> call = apiInterface.getServiceRequestData(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<ServiceRequestGetDataResponseModel>() {
            @Override
            public void onResponse(Call<ServiceRequestGetDataResponseModel> call, Response<ServiceRequestGetDataResponseModel> response) {

                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        serviceRequestGetDataResponseModelResponse = response;

                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(false);
                        Toast.makeText(SingleCurrentServiceDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(false);
                    Toast.makeText(SingleCurrentServiceDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServiceRequestGetDataResponseModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(false);
                Toast.makeText(SingleCurrentServiceDetailsActivity.this, " " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMorePanelId(String txet) {
        if (panelIdList.contains(txet)) {
            Toast.makeText(SingleCurrentServiceDetailsActivity.this, "Panel Id already added", Toast.LENGTH_SHORT).show();
            etMultiplePanelIds.setText("");
        } else{
            chip = new Chip(SingleCurrentServiceDetailsActivity.this);
            chip.setText(txet);
            chipGroup.addView(chip);
            panelIdList.add(txet);
            countChipsInChipGroup(chipGroup);
            /*  if remove chip   */
            chip.setCloseIconVisible(true);
            chip.setOnCloseIconClickListener(v -> chipGroup.removeView(chip));
        }
    }

    private int countChipsInChipGroup(ChipGroup chipGroup) {
        return chipGroup.getChildCount();
    }

    private void setAllClicksDisable(boolean b) {
        binding.ivBackPress.setClickable(b);
        binding.btnComplete.setClickable(b);
    }
}