package com.ananta.fieldAgent.Activity.fieldAgent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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
            joint_report, reportId;
    int imagePhoto;
    double latitude, longitude;
    private FusedLocationProviderClient fusedLocationClient;
    File SignPath, beneficiarySign;
    ArrayList<String> checkbox_typeWaterSource = new ArrayList<>();
    ArrayList<String> checkbox_pump_surveyor = new ArrayList<>();
    ArrayList<String> checkbox_pump_beneficiary = new ArrayList<>();
    ArrayList<String> checkbox_available_person = new ArrayList<>();
    Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityJointReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preference = Preference.getInstance(JointReportActivity.this);

        loadData();
        View view = binding.getRoot();
        setContentView(view);
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

    public void fetchData() {
        if (joint_report.equals("0")) {
            Log.d("get==", "=" + joint_report);
            binding.tvSubmit.setText("Add Report");
        } else {
            Log.d("get==", "=" + joint_report);
            binding.tvSubmit.setText("Update Report");
            getData();
        }
    }

    public void getData() {
        binding.pbProgressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("farmer_id", Const.FARMER_ID);

        Call<GetJointData> call = apiInterface.getJointReport(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<GetJointData>() {
            @Override
            public void onResponse(Call<GetJointData> call, Response<GetJointData> response) {

                if (response.isSuccessful()) {
                    binding.pbProgressBar.setVisibility(View.GONE);

                    assert response.body() != null;
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

                    String seperate = response.body().getJointServey().get(0).getType_of_water_source();
                    Log.d("check====>", "=" + response.body().getJointServey().get(0).getType_of_water_source());
                    String[] items = seperate.split(",");
                    for (String item : items) {
                        Log.d("check====>", "w=" + item);
                        if (item.equals("Borewell")) {
                            binding.checkboxBoreWell.setChecked(true);
                        }
                        if (item.equals("River")) {
                            binding.checkboxRiver.setChecked(true);
                        }
                        if (item.equals("Lake")) {
                            binding.checkboxLake.setChecked(true);
                        }
                    }

                    String pumpSurveyor = response.body().getJointServey().get(0).getPump_recom_survey();
                    String[] pumpHead = pumpSurveyor.split(",");
                    for (String pump : pumpHead) {
                        Log.d("check====>", "s=" + pump);
                        if (pump.equals("30")) {
                            binding.cbPumpHead1.setChecked(true);
                        }
                        if (pump.equals("50")) {
                            binding.cbPumpHead2.setChecked(true);
                        }
                        if (pump.equals("70")) {
                            binding.cbPumpHead3.setChecked(true);
                        }
                        if (pump.equals("100")) {
                            binding.cbPumpHead4.setChecked(true);
                        }
                    }

                    String pumpBeneficiary = response.body().getJointServey().get(0).getPump_recom_benefits();
                    String[] Beneficiary = pumpBeneficiary.split(",");
                    for (String benefit : Beneficiary) {
                        Log.d("check====>", "b=" + Beneficiary);
                        if (benefit.equals("30")) {
                            binding.cbPumpHeadBeneficiary1.setChecked(true);
                        }
                        if (benefit.equals("50")) {
                            binding.cbPumpHeadBeneficiary2.setChecked(true);
                        }
                        if (benefit.equals("70")) {
                            binding.cbPumpHeadBeneficiary3.setChecked(true);
                        }
                        if (benefit.equals("100")) {
                            binding.cbPumpHeadBeneficiary4.setChecked(true);
                        }
                    }

                    String persons = response.body().getJointServey().get(0).getSurvey_person();
                    String[] person = persons.split(",");
                    for (String per : person) {
                        Log.d("check====>", "f=" + person);
                        if (per.equals("Field Engineer")) {
                            binding.cbFieldEng.setChecked(true);
                        } else if (per.equals("Farmer")) {
                            binding.cbFarmer.setChecked(true);
                        } else if (per.equals("Govt. Farmer")) {
                            binding.cbGovtFarmer.setChecked(true);
                        }
                    }

                    Glide.with(JointReportActivity.this).load(Const.IMAGE_URL + response.body().getJointServey().get(0).getWater_res_image()).into(binding.ivWaterPhoto);
                    Glide.with(JointReportActivity.this).load(Const.IMAGE_URL + response.body().getJointServey().get(0).getLandmark_image()).into(binding.ivBeneficiaryPhotoJoint);
                    Glide.with(JointReportActivity.this).load(Const.IMAGE_URL + response.body().getJointServey().get(0).getBeneficiary_image()).into(binding.ivLandmarkPhoto);

                } else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetJointData> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(JointReportActivity.this, "jointtttttt" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*  update joint report  */
    public void updateReport(String reportId) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        binding.pbProgressBar.setVisibility(View.VISIBLE);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", reportId);
        hashMap.put("agent_id", Const.AGENT_ID);
        hashMap.put("farmer_id", Const.FARMER_ID);
        hashMap.put("alternet_mo", binding.edSurveyorAlternativeNumber.getText().toString());
        hashMap.put("imei_no", binding.edSurveyorIMEIId.getText().toString());
        hashMap.put("latitude", String.valueOf(latitude));
        hashMap.put("longitude", String.valueOf(longitude));
        hashMap.put("is_water_source_available", selectedWater);
        hashMap.put("type_of_water_source", checkbox_typeWaterSource.toString());
        hashMap.put("water_depth", binding.edDepthWaterSource.getText().toString());
        hashMap.put("constant_water", binding.edConstantWaterLevel.getText().toString());
        hashMap.put("water_delivery_point", binding.edWaterDeliveryPoint.getText().toString());
        hashMap.put("pump_type", selectedPumpType);
        hashMap.put("pump_recom_survey", checkbox_pump_surveyor.toString());
        hashMap.put("pump_recom_benefits", checkbox_pump_beneficiary.toString());
        hashMap.put("is_pump_electricity", selectAgPump);
        hashMap.put("is_solar_pump", selectGovt);
        hashMap.put("is_shadow_area", selectShaow);
        hashMap.put("is_mobile_network", networkSelect);
        hashMap.put("survey_person", checkbox_available_person.toString());
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
                    if (response.isSuccessful()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        binding.pbProgressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JointSurveyorModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(JointReportActivity.this, "Data not found" + t, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*  add joint report  */

    public void addJointReportData() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        binding.pbProgressBar.setVisibility(View.VISIBLE);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("agent_id", Const.AGENT_ID);
        hashMap.put("farmer_id", Const.FARMER_ID);
        hashMap.put("alternet_mo", binding.edSurveyorAlternativeNumber.getText().toString());
        hashMap.put("imei_no", binding.edSurveyorIMEIId.getText().toString());
        hashMap.put("latitude", String.valueOf(latitude));
        hashMap.put("longitude", String.valueOf(longitude));
        hashMap.put("is_water_source_available", selectedWater);
        hashMap.put("type_of_water_source", checkbox_typeWaterSource.toString());
        hashMap.put("water_depth", binding.edDepthWaterSource.getText().toString());
        hashMap.put("constant_water", binding.edConstantWaterLevel.getText().toString());
        hashMap.put("water_delivery_point", binding.edWaterDeliveryPoint.getText().toString());
        hashMap.put("pump_type", selectedPumpType);
        hashMap.put("pump_recom_survey", checkbox_pump_surveyor.toString());
        hashMap.put("pump_recom_benefits", checkbox_pump_beneficiary.toString());
        hashMap.put("is_pump_electricity", selectAgPump);
        hashMap.put("is_solar_pump", selectGovt);
        hashMap.put("is_shadow_area", selectShaow);
        hashMap.put("is_mobile_network", networkSelect);
        hashMap.put("survey_person", checkbox_available_person.toString());
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
                        Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JointSurveyorModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(JointReportActivity.this, "Data not found" + t, Toast.LENGTH_SHORT).show();
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

    }

    public void getCheckboxData() {

        if (binding.checkboxRiver.isChecked()) {
            checkbox_typeWaterSource.add(binding.checkboxRiver.getText().toString());
        }
        if (binding.checkboxLake.isChecked()) {
            checkbox_typeWaterSource.add(binding.checkboxLake.getText().toString());
        }
        if (binding.checkboxBoreWell.isChecked()) {
            checkbox_typeWaterSource.add(binding.checkboxBoreWell.getText().toString());
        }

        if (binding.cbPumpHead1.isChecked()) {
            checkbox_pump_surveyor.add(binding.cbPumpHead1.getText().toString());
        }
        if (binding.cbPumpHead2.isChecked()) {
            checkbox_pump_surveyor.add(binding.cbPumpHead2.getText().toString());
        }
        if (binding.cbPumpHead3.isChecked()) {
            checkbox_pump_surveyor.add(binding.cbPumpHead3.getText().toString());
        }

        if (binding.cbPumpHeadBeneficiary1.isChecked()) {
            checkbox_pump_beneficiary.add(binding.cbPumpHeadBeneficiary1.getText().toString());
        }
        if (binding.cbPumpHeadBeneficiary2.isChecked()) {
            checkbox_pump_beneficiary.add(binding.cbPumpHeadBeneficiary2.getText().toString());
        }
        if (binding.cbPumpHeadBeneficiary3.isChecked()) {
            checkbox_pump_beneficiary.add(binding.cbPumpHeadBeneficiary3.getText().toString());
        }

        if (binding.cbFieldEng.isChecked()) {
            checkbox_available_person.add(binding.cbFieldEng.getText().toString());
        }
        if (binding.cbFarmer.isChecked()) {
            checkbox_available_person.add(binding.cbFarmer.getText().toString());
        }
        if (binding.cbGovtFarmer.isChecked()) {
            checkbox_available_person.add(binding.cbPumpHeadBeneficiary3.getText().toString());
        }
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
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

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
        uploadFileImage(fileName, pos);
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
        uploadFileImage(fileName, pos);
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
                        uploadImage(contentURI, 1);
                        binding.ivWaterPhoto.setImageBitmap(bitmap);
                    } else if (imagePhoto == 2) {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        landmarkPath = String.valueOf(contentURI);
                        uploadImage(contentURI, 2);
                        binding.ivLandmarkPhoto.setImageBitmap(bitmap);
                    } else {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        baneficiarypath = String.valueOf(contentURI);
                        uploadImage(contentURI, 3);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ivWaterSourceCamera) {
            showPictureDialog(1);

        } else if (id == R.id.ivLandmarkCamera) {
            showPictureDialog(2);

        } else if (id == R.id.ivBeneficiaryCameraJoint) {
            showPictureDialog(3);

        } else if (id == R.id.llJointSubmit) {
            if (validation()) {
                getCheckboxData();
                getSelectedRadioButton();
                if (joint_report.equals("0")) {
                    addJointReportData();
                } else {
                    updateReport(reportId);
                }
            } else {
                Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
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

                Log.d("response===","==j=code=="+response.code());

                if (response.body().isSuccess()) {
                    binding.pbProgressBar.setVisibility(View.GONE);
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
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(JointReportActivity.this, "Image not uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadFileImage(File file, int fromWhere) {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
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

                if (response.isSuccessful()) {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    imageName[0] = imageModel.getUploadimage().getImage_name();
                    if (fromWhere == 1) {
                        signatureSurveyor = imageModel.getUploadimage().getImage_name();
                    } else {
                        signatureBeneficiary = imageModel.getUploadimage().getImage_name();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(JointReportActivity.this, "-" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

}