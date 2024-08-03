package com.ananta.fieldAgent.Activity.fieldAgent;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Models.CheckStatusModel;
import com.ananta.fieldAgent.Models.GetJointData;
import com.ananta.fieldAgent.Models.GetPumpData;
import com.ananta.fieldAgent.Models.JointSurveyorModel;
import com.ananta.fieldAgent.Models.PumpInstallModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivitySingleCurrentServiceDetailsBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleCurrentServiceDetailsActivity extends AppCompatActivity {

    ActivitySingleCurrentServiceDetailsBinding binding;
    String image, farmer_name, request_name, farmer_address, ComplaintId, ID, company_name, reason, description, farmer_ID, pump_report, joint_report;
    ApiInterface apiInterface;
    Preference preference;
    EditText etImeiNo, etMotorSerialNumber, etMultiplePanelIds;
    Button btnUpdate;
    Response<GetJointData> jointDataResponse;
    ImageView ivBackPress, ivAddMoreId;
    Response<GetPumpData> pumpDataResponse;
    Dialog dialog;
    CheckBox cbPumpHead1, cbPumpHead2, cbPumpHead3, cbPumpHead4;
    ArrayList<String> checkPumpHeadSurveyorArrayList = new ArrayList<>();
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

        checkReportStatus();

        binding.btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pumpDataResponse.body().getPumpInstallation() == null || jointDataResponse.body().getJointServey() == null
                        || pumpDataResponse.body().getPumpInstallation().isEmpty() || jointDataResponse.body().getJointServey().isEmpty()) {
                    Toast.makeText(SingleCurrentServiceDetailsActivity.this, "Please first filled Pump Installation report", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    dialog = new Dialog(SingleCurrentServiceDetailsActivity.this);
                    dialog.setContentView(R.layout.detail_form_dialog);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//                Log.d("Single ==>", "===>" + pumpDataResponse.body().getPumpInstallation().get(0).getImeiNo());
                    etImeiNo = dialog.findViewById(R.id.etImeiNo);
                    etMotorSerialNumber = dialog.findViewById(R.id.etMotorSerialNumber);
                    etMultiplePanelIds = dialog.findViewById(R.id.etMultiplePanelIds);
                    btnUpdate = dialog.findViewById(R.id.btnUpdate);

                    ivBackPress = dialog.findViewById(R.id.ivBackPress);
                    cbPumpHead1 = dialog.findViewById(R.id.cbPumpHead1);
                    cbPumpHead2 = dialog.findViewById(R.id.cbPumpHead2);
                    cbPumpHead3 = dialog.findViewById(R.id.cbPumpHead3);
                    cbPumpHead4 = dialog.findViewById(R.id.cbPumpHead4);
                    chipGroup = dialog.findViewById(R.id.chipGroup);
                    ivAddMoreId = dialog.findViewById(R.id.ivAddMoreId);

                    etImeiNo.setText(pumpDataResponse.body().getPumpInstallation().get(0).getImeiNo());
                    etMotorSerialNumber.setText(String.valueOf(pumpDataResponse.body().getPumpInstallation().get(0).getPumpId()));

                    String pumpSurveyor = jointDataResponse.body().getJointServey().get(0).getPump_recom_survey();

                    Log.d("Joint Get", "====>" + pumpSurveyor + " " + pumpSurveyor);
                    if (pumpSurveyor.contains("30")) {
                        cbPumpHead1.setChecked(true);
                    }
                    if (pumpSurveyor.contains("50")) {
                        cbPumpHead2.setChecked(true);
                    }
                    if (pumpSurveyor.contains("70")) {
                        cbPumpHead3.setChecked(true);
                    }
                    if (pumpSurveyor.contains("100")) {
                        cbPumpHead4.setChecked(true);
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

                            binding.pbProgressBar.setVisibility(View.VISIBLE);
                            if (cbPumpHead1.isChecked()) {
                                checkPumpHeadSurveyorArrayList.add(cbPumpHead1.getText().toString());
                            }
                            if (cbPumpHead2.isChecked()) {
                                checkPumpHeadSurveyorArrayList.add(cbPumpHead2.getText().toString());
                            }
                            if (cbPumpHead3.isChecked()) {
                                checkPumpHeadSurveyorArrayList.add(cbPumpHead3.getText().toString());
                            }
                            if (cbPumpHead4.isChecked()) {
                                checkPumpHeadSurveyorArrayList.add(cbPumpHead4.getText().toString());
                            }

                            if (countChipsInChipGroup(chipGroup) >= 9) {
                                updatePumpReport();
                                updateJointReport();
                            } else {
                                Toast.makeText(SingleCurrentServiceDetailsActivity.this, "Please enter minimum 9 panel ids", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    /*======= Get All Chips ===========*/

                    panelIdList.clear();
//                        String panel = response.body().getPumpInstallation().get(0).getPanelId();
                    String panel = pumpDataResponse.body().getPumpInstallation().get(0).getPanelId();

                    Log.d("panelList===", "get=" + new Gson().toJson(panel));

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
                                //panelIdList.indexOf(chip.getText().toString().trim());
                                Log.d("panelList===", "remove=" + panelIdList.indexOf(chip.getText().toString().trim()));
                                panelIdList.remove(chip.getText().toString().trim());
                            }
                        });

                        chipGroup.addView(chip);

                    }

                    dialog.show();

                }
            }


        });

    }


    public void checkReportStatus() {

        binding.pbProgressBar.setVisibility(View.VISIBLE);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("agent_id", preference.getAgentId());
        hashMap.put("farmer_id", farmer_ID);

        Call<CheckStatusModel> call = apiInterface.checkReportStatus(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<CheckStatusModel>() {
            @Override
            public void onResponse(Call<CheckStatusModel> call, Response<CheckStatusModel> response) {

                if (response.body() != null) {
                    binding.pbProgressBar.setVisibility(View.GONE);

                    joint_report = String.valueOf(response.body().getReports().getJointReport());
                    pump_report = String.valueOf(response.body().getReports().getPumpReport());

                    if (pump_report.equals("1") && joint_report.equals("1")){
                        getPumpReport();
                        getJointReport();
                    }else {
                        setAllClicksDisable(false);
                        Toast.makeText(SingleCurrentServiceDetailsActivity.this, "Please first filled reports", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("status====id=", "=joint=" + response.body().getReports().getJointReport() + "=pump=" + response.body().getReports().getPumpReport());

                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(false);
                    Toast.makeText(SingleCurrentServiceDetailsActivity.this, "Status not match", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CheckStatusModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                Toast.makeText(SingleCurrentServiceDetailsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updatePumpReport() {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", String.valueOf(pumpDataResponse.body().getPumpInstallation().get(0).getId()));
        hashMap.put("agent_id", preference.getAgentId());
        hashMap.put("farmer_id", farmer_ID);
        hashMap.put("pump_id", etMotorSerialNumber.getText().toString());
        hashMap.put("panel_id", panelIdList.toString());
        hashMap.put("controller_id", pumpDataResponse.body().getPumpInstallation().get(0).getControllerId());
        hashMap.put("imei_no", etImeiNo.getText().toString());
        hashMap.put("structure_id", pumpDataResponse.body().getPumpInstallation().get(0).getStructureId());
        hashMap.put("policy_no", pumpDataResponse.body().getPumpInstallation().get(0).getPolicyNo());
        hashMap.put("install_image", pumpDataResponse.body().getPumpInstallation().get(0).getInstallImage());
        hashMap.put("pump_benifi_image", pumpDataResponse.body().getPumpInstallation().get(0).getPumpBenifiImage());
        hashMap.put("pump_work_image", pumpDataResponse.body().getPumpInstallation().get(0).getPumpWorkImage());
        hashMap.put("sign", pumpDataResponse.body().getPumpInstallation().get(0).getSign());
        hashMap.put("date", formattedDate);

        Call<PumpInstallModel> call = apiInterface.updatePumpInstallReport(hashMap, "Bearer " + preference.getToken());

        call.enqueue(new Callback<PumpInstallModel>() {
            @Override
            public void onResponse(Call<PumpInstallModel> call, Response<PumpInstallModel> response) {

                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        dialog.dismiss();
                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(false);
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(false);
                }
            }

            @Override
            public void onFailure(Call<PumpInstallModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(SingleCurrentServiceDetailsActivity.this, "no open service requests", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateJointReport() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", String.valueOf(jointDataResponse.body().getJointServey().get(0).getId()));
        hashMap.put("agent_id", preference.getAgentId());
        hashMap.put("farmer_id", farmer_ID);
        hashMap.put("alternet_mo", jointDataResponse.body().getJointServey().get(0).getAlternet_mo());
        hashMap.put("imei_no", jointDataResponse.body().getJointServey().get(0).getImei_no());
        hashMap.put("latitude", jointDataResponse.body().getJointServey().get(0).getLatitude());
        hashMap.put("longitude", jointDataResponse.body().getJointServey().get(0).getLongitude());
        hashMap.put("is_water_source_available", jointDataResponse.body().getJointServey().get(0).getIs_water_source_available());
        hashMap.put("type_of_water_source", jointDataResponse.body().getJointServey().get(0).getType_of_water_source());
        hashMap.put("water_depth", jointDataResponse.body().getJointServey().get(0).getWater_depth());
        hashMap.put("constant_water", jointDataResponse.body().getJointServey().get(0).getConstant_water());
        hashMap.put("water_delivery_point", jointDataResponse.body().getJointServey().get(0).getWater_delivery_point());
        hashMap.put("pump_type", jointDataResponse.body().getJointServey().get(0).getPump_type());
        if (checkPumpHeadSurveyorArrayList.isEmpty()) {
            hashMap.put("pump_recom_survey", jointDataResponse.body().getJointServey().get(0).getPump_recom_survey());
        } else {
            hashMap.put("pump_recom_survey", checkPumpHeadSurveyorArrayList.toString());
        }
        hashMap.put("pump_recom_benefits", jointDataResponse.body().getJointServey().get(0).getPump_recom_benefits());
        hashMap.put("is_pump_electricity", jointDataResponse.body().getJointServey().get(0).getIs_pump_electricity());
        hashMap.put("is_solar_pump", jointDataResponse.body().getJointServey().get(0).getIs_solar_pump());
        hashMap.put("is_shadow_area", jointDataResponse.body().getJointServey().get(0).getIs_shadow_area());
        hashMap.put("is_mobile_network", jointDataResponse.body().getJointServey().get(0).getIs_mobile_network());
        hashMap.put("survey_person", jointDataResponse.body().getJointServey().get(0).getSurvey_person());
        hashMap.put("remark", jointDataResponse.body().getJointServey().get(0).getRemark());
        hashMap.put("water_res_image", jointDataResponse.body().getJointServey().get(0).getWater_res_image());
        hashMap.put("landmark_image", jointDataResponse.body().getJointServey().get(0).getLandmark_image());
        hashMap.put("beneficiary_image", jointDataResponse.body().getJointServey().get(0).getBeneficiary_image());
        hashMap.put("beneficiary_sign", jointDataResponse.body().getJointServey().get(0).getBeneficiary_sign());
        hashMap.put("survey_sign", jointDataResponse.body().getJointServey().get(0).getSurvey_sign());

        Call<JointSurveyorModel> call = apiInterface.updateJointReport(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<JointSurveyorModel>() {
            @Override
            public void onResponse(Call<JointSurveyorModel> call, Response<JointSurveyorModel> response) {

                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        dialog.dismiss();
                        Toast.makeText(SingleCurrentServiceDetailsActivity.this, "Data update successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(false);
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(false);
                }
            }

            @Override
            public void onFailure(Call<JointSurveyorModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(false);
                Toast.makeText(SingleCurrentServiceDetailsActivity.this, "no open service requests" + t, Toast.LENGTH_SHORT).show();
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

    public void getJointReport() {
        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("farmer_id", farmer_ID);

        Call<GetJointData> call = apiInterface.getJointReport(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<GetJointData>() {
            @Override
            public void onResponse(Call<GetJointData> call, Response<GetJointData> response) {

                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        jointDataResponse = response;

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
            public void onFailure(Call<GetJointData> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(false);
                Toast.makeText(SingleCurrentServiceDetailsActivity.this, " " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAllClicksDisable(boolean b) {
        binding.ivBackPress.setClickable(b);
        binding.btnComplete.setClickable(b);
    }

    public void getPumpReport() {
        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("farmer_id", farmer_ID);

        Call<GetPumpData> call = apiInterface.getPumpReport(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<GetPumpData>() {
            @Override
            public void onResponse(Call<GetPumpData> call, Response<GetPumpData> response) {

                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        pumpDataResponse = response;
                        binding.btnComplete.setClickable(true);
                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(false);
                        Toast.makeText(SingleCurrentServiceDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(false);
//                    Toast.makeText(PumpInstallationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetPumpData> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(false);
                Toast.makeText(SingleCurrentServiceDetailsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}