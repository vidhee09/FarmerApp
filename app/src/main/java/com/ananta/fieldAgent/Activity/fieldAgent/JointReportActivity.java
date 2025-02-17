package com.ananta.fieldAgent.Activity.fieldAgent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Models.GetJointData;
import com.ananta.fieldAgent.Models.GetPumpData;
import com.ananta.fieldAgent.Models.ImageModel;
import com.ananta.fieldAgent.Models.JointSurveyorModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.FileSelectionUtils;
import com.ananta.fieldAgent.Parser.GpsTracker;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.Parser.Utils;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityJointReportBinding;
import com.bumptech.glide.Glide;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JointReportActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityJointReportBinding binding;
    private static final int GALLERY = 100;
    private static final int CAMERA = 101;
    ApiInterface apiInterface;
    String signImage, path, beneficiarySignImage, pumpPath, landmarkPath, baneficiarypath,
            signatureSurveyor, signatureBeneficiary, selectedWater, selectedPumpType, selectAgPump, selectGovt, selectShaow, networkSelect,
            joint_report, reportId,TypeOfWaterSource,pumpHeadSurveyor,pumpHeadRecombaneficiary,availablePerson;
    int imagePhoto;
    double latitude, longitude;
    private FusedLocationProviderClient fusedLocationClient;
    File SignPath, beneficiarySign;
    Preference preference;
    ArrayAdapter waterSourceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityJointReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preference = Preference.getInstance(JointReportActivity.this);

        loadData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        setClickListener();
        clickListener();

    }

    public void loadData() {
        Const.AGENT_NAME = preference.getAgentName();
        joint_report = getIntent().getStringExtra("joint_report");
        binding.tvSurveyorNameJoint.setText(Const.AGENT_NAME);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
        datePick();
        fetchData();

    }

    public void setAllClicksDisable(boolean b) {
        binding.rbPumpHeadSurveyor30.setClickable(b);
        binding.rbPumpHeadSurveyor50.setClickable(b);
        binding.rbPumpHeadSurveyor70.setClickable(b);
        binding.rbPumpHeadSurveyor100.setClickable(b);

        binding.rbPumpHeadbaneficiary30.setClickable(b);
        binding.rbPumpHeadbaneficiary50.setClickable(b);
        binding.rbPumpHeadbaneficiary70.setClickable(b);
        binding.rbPumpHeadbaneficiary100.setClickable(b);

        binding.rbFieldEngineer.setClickable(b);
        binding.rbFarmer.setClickable(b);
        binding.rbGovtFarmer.setClickable(b);

        binding.edRemark.setClickable(b);
        binding.edSurveyorAlternativeNumber.setClickable(b);
        binding.edSurveyorIMEIId.setClickable(b);
        binding.ivBackPress.setClickable(b);
        binding.rbAgYes.setClickable(b);
        binding.rbAgNo.setClickable(b);
        binding.rbSolarYes.setClickable(b);
        binding.rbSolarNo.setClickable(b);
        binding.rbShadowYes.setClickable(b);
        binding.rbShadowNo.setClickable(b);
        binding.rbNetworkYes.setClickable(b);
        binding.rbNetworkNo.setClickable(b);
        binding.rbYesWaterBtn.setClickable(b);
        binding.rbNoWaterBtn.setClickable(b);
        binding.rbSubmarsible.setClickable(b);
        binding.rbSurface.setClickable(b);

        binding.rblake.setClickable(b);
        binding.rbRiver.setClickable(b);
        binding.rbBorewell.setClickable(b);

        binding.edDepthWaterSource.setClickable(b);
        binding.edConstantWaterLevel.setClickable(b);
        binding.edWaterDeliveryPoint.setClickable(b);
        binding.ivWaterSourceCamera.setClickable(b);
        binding.ivLandmarkCamera.setClickable(b);
        binding.ivBeneficiaryCameraJoint.setClickable(b);
        binding.btnCompletedBeneficiary.setClickable(b);
        binding.btnClearBeneficiary.setClickable(b);
        binding.btnClearSign.setClickable(b);
        binding.btnCompletedSign.setClickable(b);
        binding.llJointSubmit.setClickable(b);
    }

    public void fetchData() {
        if (joint_report.equals("0")) {
            Log.d("get==", "=" + joint_report);
            binding.llJointSubmit.setText("Add Report");
        } else {
            Log.d("get==", "=" + joint_report);
            binding.llJointSubmit.setText("Update Report");
            if (Const.isInternetConnected(JointReportActivity.this)){
                getData();
            }else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getData() {
        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("farmer_id", preference.getAgentFarmerId());

        Call<GetJointData> call = apiInterface.getJointReport(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<GetJointData>() {
            @Override
            public void onResponse(Call<GetJointData> call, Response<GetJointData> response) {

                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        reportId = String.valueOf(response.body().getJointServey().get(0).getId());
                        binding.edSurveyorIMEIId.setText(response.body().getJointServey().get(0).getImei_no());
                        binding.edConstantWaterLevel.setText(response.body().getJointServey().get(0).getConstant_water());
                        binding.edSurveyorAlternativeNumber.setText(response.body().getJointServey().get(0).getAlternet_mo());
                        binding.edDepthWaterSource.setText(response.body().getJointServey().get(0).getWater_depth());
                        binding.edWaterDeliveryPoint.setText(response.body().getJointServey().get(0).getWater_delivery_point());
                        binding.edRemark.setText(response.body().getJointServey().get(0).getRemark());

                        selectedWater = response.body().getJointServey().get(0).getIs_water_source_available();
                        if (selectedWater.equals("Yes")) {
                            binding.rbYesWaterBtn.setChecked(true);
                        } else {
                            binding.rbNoWaterBtn.setChecked(true);
                        }

                        selectedPumpType = response.body().getJointServey().get(0).getPump_type();
                        if (selectedPumpType.equals("Submarsible")) {
                            binding.rbSubmarsible.setChecked(true);
                        } else {
                            binding.rbSurface.setChecked(true);
                        }

                        selectAgPump = response.body().getJointServey().get(0).getIs_pump_electricity();
                        if (selectAgPump.equals("Yes")) {
                            binding.rbAgYes.setChecked(true);
                        } else {
                            binding.rbAgNo.setChecked(true);
                        }

                        selectGovt = response.body().getJointServey().get(0).getIs_pump_electricity();
                        if (selectGovt.equals("Yes")) {
                            binding.rbSolarYes.setChecked(true);
                        } else {
                            binding.rbSolarNo.setChecked(true);
                        }

                        selectShaow = response.body().getJointServey().get(0).getIs_shadow_area();
                        if (selectShaow.equals("Yes")) {
                            binding.rbShadowYes.setChecked(true);
                        } else {
                            binding.rbShadowNo.setChecked(true);
                        }

                        networkSelect = response.body().getJointServey().get(0).getIs_mobile_network();
                        if (networkSelect.equals("Yes")) {
                            binding.rbNetworkYes.setChecked(true);
                        } else {
                            binding.rbNetworkNo.setChecked(true);
                        }


                        TypeOfWaterSource = response.body().getJointServey().get(0).getType_of_water_source();

                        Log.d("TypeOfWaterSource=", "====>" + TypeOfWaterSource);
                        if (TypeOfWaterSource.contains("Borewell")) {
                            binding.rbBorewell.setChecked(true);
                        }

                        if (TypeOfWaterSource.contains("River")){
                            binding.rbRiver.setChecked(true);

                        }if (TypeOfWaterSource.contains("Lake")){
                            binding.rblake.setChecked(true);
                        }


                         pumpHeadSurveyor = response.body().getJointServey().get(0).getPump_recom_survey();
                        Log.d("Joint Get", "====>" +  pumpHeadSurveyor );

                        if (pumpHeadSurveyor.contains("30")) {
                            binding.rbPumpHeadSurveyor30.setChecked(true);
                        }
                        if (pumpHeadSurveyor.contains("50")) {
                            binding.rbPumpHeadSurveyor50.setChecked(true);
                        }
                        if (pumpHeadSurveyor.contains("70")) {
                            binding.rbPumpHeadSurveyor70.setChecked(true);
                        }
                        if (pumpHeadSurveyor.contains("100") ) {
                            binding.rbPumpHeadSurveyor100.setChecked(true);
                        }

                        pumpHeadRecombaneficiary = response.body().getJointServey().get(0).getPump_recom_benefits();
                        Log.d("Joint Get", "====>" + pumpHeadRecombaneficiary );

                        if (pumpHeadRecombaneficiary.contains("30") ) {
                            binding.rbPumpHeadbaneficiary30.setChecked(true);
                        }
                        if (pumpHeadRecombaneficiary.contains("50") ) {
                            binding.rbPumpHeadbaneficiary50.setChecked(true);
                        }
                        if (pumpHeadRecombaneficiary.contains("70") ) {
                            binding.rbPumpHeadbaneficiary70.setChecked(true);
                        }
                        if (pumpHeadRecombaneficiary.contains("100") ) {
                            binding.rbPumpHeadbaneficiary100.setChecked(true);
                        }

                        availablePerson = response.body().getJointServey().get(0).getSurvey_person();
                        Log.d("Joint Get", "====>" + availablePerson + " " + availablePerson);

                        if (availablePerson.contains("Field Engineer") ) {
                            binding.rbFieldEngineer.setChecked(true);
                        }
                        if (availablePerson.contains("Farmer")) {
                            binding.rbFarmer.setChecked(true);
                        }
                        if (availablePerson.contains("Govt. Farmer")) {
                            binding.rbGovtFarmer.setChecked(true);
                        }

                        Glide.with(JointReportActivity.this).load(Const.IMAGE_URL + response.body().getJointServey().get(0).getWater_res_image()).into(binding.ivWaterPhoto);
                        Glide.with(JointReportActivity.this).load(Const.IMAGE_URL + response.body().getJointServey().get(0).getLandmark_image()).into(binding.ivBeneficiaryPhotoJoint);
                        Glide.with(JointReportActivity.this).load(Const.IMAGE_URL + response.body().getJointServey().get(0).getBeneficiary_image()).into(binding.ivLandmarkPhoto);

                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetJointData> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(JointReportActivity.this, " " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*  update joint report  */
    public void updateReport(String reportId) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", reportId);
        hashMap.put("agent_id", preference.getAgentId());
        hashMap.put("farmer_id", preference.getAgentFarmerId() );
        hashMap.put("alternet_mo", binding.edSurveyorAlternativeNumber.getText().toString());
        hashMap.put("imei_no", binding.edSurveyorIMEIId.getText().toString());
        hashMap.put("latitude", String.valueOf(latitude));
        hashMap.put("longitude", String.valueOf(longitude));
        hashMap.put("is_water_source_available", selectedWater);
        hashMap.put("type_of_water_source", TypeOfWaterSource);
        hashMap.put("water_depth", binding.edDepthWaterSource.getText().toString());
        hashMap.put("constant_water", binding.edConstantWaterLevel.getText().toString());
        hashMap.put("water_delivery_point", binding.edWaterDeliveryPoint.getText().toString());
        hashMap.put("pump_type", selectedPumpType);
        hashMap.put("pump_recom_survey", pumpHeadSurveyor);
        hashMap.put("pump_recom_benefits", pumpHeadRecombaneficiary);
        hashMap.put("is_pump_electricity", selectAgPump);
        hashMap.put("is_solar_pump", selectGovt);
        hashMap.put("is_shadow_area", selectShaow);
        hashMap.put("is_mobile_network", networkSelect);
        hashMap.put("survey_person", availablePerson);
        hashMap.put("remark", binding.edRemark.getText().toString());
        if (pumpPath == null || !pumpPath.isEmpty()) {
            hashMap.put("water_res_image", pumpPath);
        }
        if (landmarkPath == null || !landmarkPath.isEmpty()) {
            hashMap.put("landmark_image", landmarkPath);
        }
        if (baneficiarypath == null || !baneficiarypath.isEmpty()) {
            hashMap.put("beneficiary_image", baneficiarypath);
        }
        hashMap.put("beneficiary_sign", signatureBeneficiary);
        hashMap.put("survey_sign", signatureSurveyor);

        Call<JointSurveyorModel> call = apiInterface.updateJointReport(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<JointSurveyorModel>() {
            @Override
            public void onResponse(Call<JointSurveyorModel> call, Response<JointSurveyorModel> response) {

                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JointSurveyorModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(JointReportActivity.this, "Data not found" + t, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*  add joint report  */

    public void addJointReportData() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("agent_id", preference.getAgentId());
        hashMap.put("farmer_id",preference.getAgentFarmerId());
        hashMap.put("alternet_mo", binding.edSurveyorAlternativeNumber.getText().toString());
        hashMap.put("imei_no", binding.edSurveyorIMEIId.getText().toString());
        hashMap.put("latitude", String.valueOf(latitude));
        hashMap.put("longitude", String.valueOf(longitude));
        hashMap.put("is_water_source_available", selectedWater);
        hashMap.put("type_of_water_source",TypeOfWaterSource);
        hashMap.put("water_depth", binding.edDepthWaterSource.getText().toString());
        hashMap.put("constant_water", binding.edConstantWaterLevel.getText().toString());
        hashMap.put("water_delivery_point", binding.edWaterDeliveryPoint.getText().toString());
        hashMap.put("pump_type", selectedPumpType);
        hashMap.put("pump_recom_survey", pumpHeadSurveyor);
        hashMap.put("pump_recom_benefits", pumpHeadRecombaneficiary);
        hashMap.put("is_pump_electricity", selectAgPump);
        hashMap.put("is_solar_pump", selectGovt);
        hashMap.put("is_shadow_area", selectShaow);
        hashMap.put("is_mobile_network", networkSelect);
        hashMap.put("survey_person", availablePerson);
        hashMap.put("remark", binding.edRemark.getText().toString());
        hashMap.put("water_res_image", pumpPath);
        hashMap.put("landmark_image", landmarkPath);
        hashMap.put("beneficiary_image", baneficiarypath);
        hashMap.put("beneficiary_sign", signatureBeneficiary);
        hashMap.put("survey_sign", signatureSurveyor);

        Call<JointSurveyorModel> call = apiInterface.addJointSurveyorData(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<JointSurveyorModel>() {
            @Override
            public void onResponse(@NonNull Call<JointSurveyorModel> call, @NonNull Response<JointSurveyorModel> response) {

                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JointSurveyorModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(JointReportActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getSelectedRadioButton() {

        int selectedId = binding.rgRadioWaterSource.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        selectedWater = selectedRadioButton.getText().toString();

        int selectedType = binding.rgPumpType.getCheckedRadioButtonId();
        RadioButton selectedRadioButtonType = findViewById(selectedType);
        selectedPumpType = selectedRadioButtonType.getText().toString();

        int selectedAgPump = binding.rgAGPumpConnection.getCheckedRadioButtonId();
        RadioButton selectedAgPumpButton = findViewById(selectedAgPump);
        selectAgPump = selectedAgPumpButton.getText().toString();

        int selectedGovtPump = binding.rgSolarGovtPump.getCheckedRadioButtonId();
        RadioButton govtPump = findViewById(selectedGovtPump);
        selectGovt = govtPump.getText().toString();

        int selectedShadow = binding.rgShadowArea.getCheckedRadioButtonId();
        RadioButton shadowFree = findViewById(selectedShadow);
        selectShaow = shadowFree.getText().toString();

        int selectNetwork = binding.rgNetworkAvailable.getCheckedRadioButtonId();
        RadioButton selectNet = findViewById(selectNetwork);
        networkSelect = selectNet.getText().toString();

        /*---- type of water source ---*/
        int WaterSourceType = binding.rgTypeOfWaterSource.getCheckedRadioButtonId();
        RadioButton selectedWaterType = findViewById(WaterSourceType);
        TypeOfWaterSource = selectedWaterType.getText().toString();

        /*---- pump head recommanded by surveyor ---*/

        int pumpHeadRecomSurveyor = binding.rgPumpHeadRecomSurveyor.getCheckedRadioButtonId();
        RadioButton selectedPumpHeadSurveyor = findViewById(pumpHeadRecomSurveyor);
        pumpHeadSurveyor = selectedPumpHeadSurveyor.getText().toString();

        /*---- pump head recommanded by baneficiary ---*/

        int pumpHeadRecommandedByBeneficiary = binding.rgPumpHeadRecomBaneficiary.getCheckedRadioButtonId();
        RadioButton selectedPumpHeadbaneficiary = findViewById(pumpHeadRecommandedByBeneficiary);
        pumpHeadRecombaneficiary = selectedPumpHeadbaneficiary.getText().toString();

        /*---- all available person ---*/

        int allAvailablePerson = binding.rgAvailablePerson.getCheckedRadioButtonId();
        RadioButton selectAvailablePerson = findViewById(allAvailablePerson);
        availablePerson = selectAvailablePerson.getText().toString();

    }

    public void getLocation() {
        GpsTracker gpsTracker = new GpsTracker(JointReportActivity.this);
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            binding.tvSurveyorAddress.setText(latitude + " , " + longitude);

        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    private void datePick() {
        final Calendar c = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        binding.tvSurveyorDateTime.setText(formattedDate + " , " + currentTime);

    }

    public boolean validation() {
        boolean isValid = true;
        if (binding.edDepthWaterSource.getText().toString().isEmpty()) {
            isValid = false;
            binding.edDepthWaterSource.setError("please enter depth water source level");
        } else if (binding.edConstantWaterLevel.getText().toString().isEmpty()) {
            isValid = false;
            binding.edConstantWaterLevel.setError("please enter water constant level");
        } else if (binding.edSurveyorIMEIId.getText().toString().isEmpty()) {
            isValid = false;
            binding.edSurveyorIMEIId.setError("please enter IMEI Id");
        } else if (binding.edSurveyorAlternativeNumber.getText().toString().isEmpty()) {
            isValid = false;
            binding.edSurveyorAlternativeNumber.setError("please enter alternate number");
        } else if (binding.edWaterDeliveryPoint.getText().toString().isEmpty()) {
            isValid = false;
            binding.edWaterDeliveryPoint.setError("please enter water delivery point ");
        } else if (binding.edRemark.getText().toString().isEmpty()) {
            isValid = false;
            binding.edRemark.setError("please enter remark");
        } else if ( TypeOfWaterSource.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select water source", Toast.LENGTH_SHORT).show();
        } else if (pumpHeadSurveyor.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select pump head by surveyor", Toast.LENGTH_SHORT).show();
        } else if (availablePerson.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select available person", Toast.LENGTH_SHORT).show();
        } else if (pumpHeadRecombaneficiary== null || pumpHeadRecombaneficiary.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select pump head by beneficiary", Toast.LENGTH_SHORT).show();
        } else if (pumpPath == null || pumpPath.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select Image", Toast.LENGTH_SHORT).show();
        } else if (baneficiarypath == null || baneficiarypath.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select Image", Toast.LENGTH_SHORT).show();
        } else if (landmarkPath == null || landmarkPath.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select Image", Toast.LENGTH_SHORT).show();
        } else if (selectedWater == null || selectedWater.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select water availability", Toast.LENGTH_SHORT).show();
        } else if ( selectedPumpType == null ||selectedPumpType.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select pump type", Toast.LENGTH_SHORT).show();
        } else if (selectAgPump == null || selectAgPump.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select Ag pump availability", Toast.LENGTH_SHORT).show();
        } else if ( selectGovt == null || selectGovt.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select Govt. scheme", Toast.LENGTH_SHORT).show();
        } else if (networkSelect== null || networkSelect.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select network availability ", Toast.LENGTH_SHORT).show();
        } else if ( selectShaow== null ||selectShaow.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select available area", Toast.LENGTH_SHORT).show();
        }else if (signatureSurveyor == null || signatureSurveyor.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please add signature", Toast.LENGTH_SHORT).show();
        }else if (signatureBeneficiary == null ||signatureBeneficiary.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please add signature", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    public boolean updateValidation() {
        boolean isValid = true;
        if (binding.edDepthWaterSource.getText().toString().isEmpty()) {
            isValid = false;
            binding.edDepthWaterSource.setError("please enter depth water source level");
        } else if (binding.edConstantWaterLevel.getText().toString().isEmpty()) {
            isValid = false;
            binding.edConstantWaterLevel.setError("please enter water constant level");
        } else if (binding.edSurveyorIMEIId.getText().toString().isEmpty()) {
            isValid = false;
            binding.edSurveyorIMEIId.setError("please enter IMEI Id");
        } else if (binding.edSurveyorAlternativeNumber.getText().toString().isEmpty()) {
            isValid = false;
            binding.edSurveyorAlternativeNumber.setError("please enter alternate number");
        } else if (binding.edWaterDeliveryPoint.getText().toString().isEmpty()) {
            isValid = false;
            binding.edWaterDeliveryPoint.setError("please enter water delivery point ");
        } else if (binding.edRemark.getText().toString().isEmpty()) {
            isValid = false;
            binding.edRemark.setError("please enter remark");
        } else if (TypeOfWaterSource.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select water source", Toast.LENGTH_SHORT).show();
        } else if (pumpHeadSurveyor.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select pump head by surveyor", Toast.LENGTH_SHORT).show();
        } else if (availablePerson.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select available person", Toast.LENGTH_SHORT).show();
        } else if (pumpHeadRecombaneficiary.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select pump head by beneficiary", Toast.LENGTH_SHORT).show();
        } else if (selectedWater.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select water availability", Toast.LENGTH_SHORT).show();
        } else if (selectedPumpType.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select pump type", Toast.LENGTH_SHORT).show();
        } else if (selectAgPump.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select Ag pump availability", Toast.LENGTH_SHORT).show();
        } else if (selectGovt.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select Govt. scheme", Toast.LENGTH_SHORT).show();
        } else if (networkSelect.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select network availability ", Toast.LENGTH_SHORT).show();
        } else if (selectShaow.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select available area", Toast.LENGTH_SHORT).show();
        }else if (signatureSurveyor == null || signatureSurveyor.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please add signature", Toast.LENGTH_SHORT).show();
        }else if (signatureBeneficiary == null ||signatureBeneficiary.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please add signature", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    private void initView() {
        binding.signaturePadSign.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
            }

            @Override
            public void onSigned() {
            }

            @Override
            public void onClear() {
            }
        });

        binding.signaturePadBeneficiary.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
            }

            @Override
            public void onSigned() {
            }

            @Override
            public void onClear() {
            }
        });
    }

    private void setClickListener() {

        binding.btnCompletedSign.setOnClickListener(v -> {
            signImage = binding.signaturePadSign.getSignatureSvg();
            signImage = BitMapToString(binding.signaturePadSign.getSignatureBitmap());
            saveBitmapIntoCacheDir(binding.signaturePadSign.getSignatureBitmap(), 1);
            Log.d("DeliveryReport===>", "===>" + signImage);
            binding.ivSignImage.setImageBitmap(binding.signaturePadSign.getSignatureBitmap());
        });

        binding.btnClearSign.setOnClickListener(v -> {
            binding.signaturePadSign.clear();
        });

        binding.btnCompletedBeneficiary.setOnClickListener(v -> {
            beneficiarySignImage = binding.signaturePadBeneficiary.getSignatureSvg();
            beneficiarySignImage = BitMapToString(binding.signaturePadBeneficiary.getSignatureBitmap());
            BitmapIntoCacheDir(binding.signaturePadBeneficiary.getSignatureBitmap(), 2);
            Log.d("DeliveryReport===>", "===>" + beneficiarySignImage);
            binding.ivBeneficiarySignImage.setImageBitmap(binding.signaturePadBeneficiary.getSignatureBitmap());
        });

        binding.btnClearBeneficiary.setOnClickListener(v -> {
            binding.signaturePadBeneficiary.clear();
        });
    }

    public void clickListener() {
        binding.ivBeneficiaryCameraJoint.setOnClickListener(this);
        binding.ivLandmarkCamera.setOnClickListener(this);
        binding.ivWaterSourceCamera.setOnClickListener(this);
        binding.ivBeneficiaryPhotoJoint.setOnClickListener(this);
        binding.ivLandmarkPhoto.setOnClickListener(this);
        binding.ivWaterPhoto.setOnClickListener(this);
        binding.llJointSubmit.setOnClickListener(this);
        binding.ivBackPress.setOnClickListener(this);
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private void saveBitmapIntoCacheDir(Bitmap signatureBitmap, int pos) {
        File sd = getCacheDir();
        File folder = new File(sd, "/myfolder/");
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Log.e("ERROR", "Cannot create a directory!");
            } else {
                folder.mkdirs();
            }
        }

        File fileName = new File(folder, "PumpSignature.jpg");
        SignPath = fileName;
        Log.d("Site===>", "===>" + fileName.toString());
        try {
            FileOutputStream outputStream = new FileOutputStream(String.valueOf(fileName));
            signatureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Const.isInternetConnected(JointReportActivity.this)){
            uploadFileImage(fileName, pos);
        }else {
            Toast.makeText(JointReportActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void BitmapIntoCacheDir(Bitmap signatureBitmap, int pos) {
        File sd = getCacheDir();
        File folder = new File(sd, "/myfolder/");
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Log.e("ERROR", "Cannot create a directory!");
            } else {
                folder.mkdirs();
            }
        }

        File fileName = new File(folder, "beneficiarySignature.jpg");
        beneficiarySign = fileName;
        Log.d("Site===>", "===>" + fileName.toString());
        try {
            FileOutputStream outputStream = new FileOutputStream(String.valueOf(fileName));
            signatureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Const.isInternetConnected(JointReportActivity.this)){
            uploadFileImage(fileName, pos);
        }else {
            Toast.makeText(JointReportActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPictureDialog(Integer photo) {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary(photo);
                                break;
                            case 1:
                                takePhotoFromCamera(photo);
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary(Integer photo) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY);
        imagePhoto = photo;
    }

    private void takePhotoFromCamera(Integer photo) {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, CAMERA);
        imagePhoto = photo;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                path = String.valueOf(contentURI);
                try {
                    if (imagePhoto == 1) {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        pumpPath = String.valueOf(contentURI);
                        if (Const.isInternetConnected(JointReportActivity.this)){
                            uploadImage(contentURI, 1);
                        }else {
                            Toast.makeText(JointReportActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                        }
                        binding.ivWaterPhoto.setImageBitmap(bitmap);
                    } else if (imagePhoto == 2) {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        landmarkPath = String.valueOf(contentURI);
                        if (Const.isInternetConnected(JointReportActivity.this)){
                            uploadImage(contentURI, 2);
                        }else {
                            Toast.makeText(JointReportActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                        }
                        binding.ivLandmarkPhoto.setImageBitmap(bitmap);
                    } else {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        baneficiarypath = String.valueOf(contentURI);
                        if (Const.isInternetConnected(JointReportActivity.this)){
                            uploadImage(contentURI, 3);
                        }else {
                            Toast.makeText(JointReportActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                        }
                        binding.ivBeneficiaryPhotoJoint.setImageBitmap(bitmap);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == CAMERA) {

            if (imagePhoto == 1) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
                pumpPath = saveImage(thumbnail);
                binding.ivWaterPhoto.setImageBitmap(thumbnail);
                saveImage(thumbnail);
            } else if (imagePhoto == 2) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
                landmarkPath = saveImage(thumbnail);
                binding.ivLandmarkPhoto.setImageBitmap(thumbnail);
                saveImage(thumbnail);
            } else {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
                baneficiarypath = saveImage(thumbnail);
                binding.ivBeneficiaryPhotoJoint.setImageBitmap(thumbnail);
                saveImage(thumbnail);
            }

        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "123");
        if (!wallpaperDirectory.exists()) {  // have the object build the directory structure, if needed.
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public Boolean checkRadioButtonValidation(){
        boolean valid = true;
        if (binding.rgRadioWaterSource.getCheckedRadioButtonId() == -1){
            valid = false;
            Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
        } if (binding.rgPumpHeadRecomSurveyor.getCheckedRadioButtonId() == -1){
            valid = false;
            Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
        } if (binding.rgPumpHeadRecomBaneficiary.getCheckedRadioButtonId() == -1){
            valid = false;
            Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
        } if (binding.rgPumpType.getCheckedRadioButtonId() == -1){
            valid = false;
            Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
        } if (binding.rgAGPumpConnection.getCheckedRadioButtonId() == -1){
            valid = false;
            Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
        } if (binding.rgSolarGovtPump.getCheckedRadioButtonId() == -1){
            valid = false;
            Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
        } if (binding.rgShadowArea.getCheckedRadioButtonId() == -1){
            valid = false;
            Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
        } if (binding.rgNetworkAvailable.getCheckedRadioButtonId() == -1){
            valid = false;
            Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
        } if (binding.rgTypeOfWaterSource.getCheckedRadioButtonId() == -1){
            valid = false;
            Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
        } if (binding.rgAvailablePerson.getCheckedRadioButtonId() == -1){
            valid = false;
            Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ivWaterSourceCamera || id == R.id.ivWaterPhoto) {
            showPictureDialog(1);

        } else if (id == R.id.ivLandmarkCamera || id == R.id.ivLandmarkPhoto) {
            showPictureDialog(2);

        } else if (id == R.id.ivBeneficiaryCameraJoint || id == R.id.ivBeneficiaryPhotoJoint) {
            showPictureDialog(3);

        } else if (id == R.id.llJointSubmit) {
            if (checkRadioButtonValidation()){
                getSelectedRadioButton();
                if (joint_report.equals("0")) {
                    if (validation()) {
                        if (Const.isInternetConnected(JointReportActivity.this)){
                            addJointReportData();
                        }else {
                            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (updateValidation()) {
                        if (Const.isInternetConnected(JointReportActivity.this)){
                            updateReport(reportId);
                        }else {
                            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
                    }
                }
            }else {
                Toast.makeText(this, "please check all buttons", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.ivBackPress) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void uploadImage(Uri contentURI, int fromWhere) {
        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);

        Uri uri = null;
        String fName = "";
        try {
            uri = FileSelectionUtils.getFilePathFromUri(getApplicationContext(), contentURI);
            Log.w("FilePathURL", "" + FileSelectionUtils.getFilePathFromUri(getApplicationContext(), contentURI));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File file = new File(uri.getPath());
        Log.w("FilePath", file.getPath());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<ImageModel> call = apiInterface.uploadImage(multipartBody, "profile_picture", "Bearer " + preference.getToken());

        final String[] imageName = {""};
        call.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                ImageModel imageModel = response.body();

                Log.d("response===", "==j=code==" + response.code());

                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        imageName[0] = imageModel.getUploadimage().getImage_name();
                        Log.w("ImageName", imageName[0]);
                        if (fromWhere == 1) {
                            pumpPath = imageModel.getUploadimage().getImage_name();
                        } else if (fromWhere == 2) {
                            landmarkPath = imageModel.getUploadimage().getImage_name();
                        } else {
                            baneficiarypath = imageModel.getUploadimage().getImage_name();
                        }
                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        if (fromWhere == 1){
                            binding.ivWaterPhoto.setImageResource(R.drawable.ic_farmer);
                        } else if (fromWhere == 2) {
                            binding.ivLandmarkPhoto.setImageResource(R.drawable.ic_farmer);
                        }else {
                            binding.ivBeneficiaryPhotoJoint.setImageResource(R.drawable.ic_farmer);
                        }
                        Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    if (fromWhere == 1){
                        binding.ivWaterPhoto.setImageResource(R.drawable.ic_farmer);
                    } else if (fromWhere == 2) {
                        binding.ivLandmarkPhoto.setImageResource(R.drawable.ic_farmer);
                    }else {
                        binding.ivBeneficiaryPhotoJoint.setImageResource(R.drawable.ic_farmer);
                    }
                    Toast.makeText(JointReportActivity.this,"The image must not be greater than 2048 kilobytes, please upload again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(JointReportActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadFileImage(File file, int fromWhere) {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);
        Uri uri = null;
        String fName = "";
        Log.w("FilePath", file.getPath());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<ImageModel> call = apiInterface.uploadImage(multipartBody, "profile_picture", "Bearer " + preference.getToken());

        final String[] imageName = {""};
        call.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                ImageModel imageModel = response.body();

                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        imageName[0] = imageModel.getUploadimage().getImage_name();
                        if (fromWhere == 1) {
                            signatureSurveyor = imageModel.getUploadimage().getImage_name();
                        } else {
                            signatureBeneficiary = imageModel.getUploadimage().getImage_name();
                        }
                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(JointReportActivity.this, " " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}