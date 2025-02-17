package com.ananta.fieldAgent.Activity.fieldAgent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Models.GetPumpData;
import com.ananta.fieldAgent.Models.ImageModel;
import com.ananta.fieldAgent.Models.PumpInstallModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.FileSelectionUtils;
import com.ananta.fieldAgent.Parser.GpsTracker;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityPumpInstallationBinding;
import com.bumptech.glide.Glide;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PumpInstallationActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityPumpInstallationBinding binding;

    private static final int GALLERY = 100;
    private static final int CAMERA = 101;
    ApiInterface apiInterface;
    String pump_report, signImage, path;
    String reportId, pumpPath, baneficiarypath, workingPumpPath, signatureName;
    File SignPath;
    int imagePhoto;
    private FusedLocationProviderClient fusedLocationClient;

    double latitude, longitude;
    ArrayList<String> panelIdList = new ArrayList<>();
    private int SpannedLength = 0, chipLength = 4;
    Chip chip;
    Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityPumpInstallationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preference = Preference.getInstance(PumpInstallationActivity.this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadData();
        initView();
        setClickListener();
        clickListener();
    }

    public void fetchData() {
        if (pump_report.equals("0")) {
            binding.llSubmitPumpInstall.setText("Add Report");
        } else {
            binding.llSubmitPumpInstall.setText("Update Report");
            if (Const.isInternetConnected(PumpInstallationActivity.this)){
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

        Call<GetPumpData> call = apiInterface.getPumpReport(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<GetPumpData>() {
            @Override
            public void onResponse(Call<GetPumpData> call, Response<GetPumpData> response) {
                if (response.body().getSuccess()) {
                if (response.body() != null) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        binding.edPumpId.setText(response.body().getPumpInstallation().get(0).getPumpId());
                        binding.edIMEIId.setText(response.body().getPumpInstallation().get(0).getImeiNo());
                        binding.edStructureId.setText(response.body().getPumpInstallation().get(0).getStructureId());
                        binding.edControllerId.setText(response.body().getPumpInstallation().get(0).getControllerId());
                        String policyNumber = response.body().getPumpInstallation().get(0).getPolicyNo();

                        if (policyNumber == null || policyNumber.isEmpty()){
                            binding.edPolicyNumberPumpInstall.setText("");
                        }else {
                            binding.edPolicyNumberPumpInstall.setText(response.body().getPumpInstallation().get(0).getPolicyNo());
                        }

                        panelIdList.clear();
                        String panel = response.body().getPumpInstallation().get(0).getPanelId();
                        String[] panels = panel.split(",");

                        String[] trimmedArray = new String[panels.length];
                        for (int i = 0; i < panels.length; i++)
                            trimmedArray[i] = panels[i].trim();

                        panelIdList.addAll(Arrays.asList(trimmedArray));

                        for (int i = 0; i < panels.length; i++) {

                            Chip chip = new Chip(binding.chipGroup.getContext());
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(5, 1, 5, 1);

                            chip.setLayoutParams(layoutParams);
                            chip.setText(panels[i]);

                            chip.setCloseIconVisible(true);
                            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Chip chip = (Chip) v;
                                    binding.chipGroup.removeView(chip);
                                    panelIdList.remove(chip.getText().toString().trim());

                                }
                            });

                            binding.chipGroup.addView(chip);
                        }

                        reportId = String.valueOf(response.body().getPumpInstallation().get(0).getId());
                        Glide.with(PumpInstallationActivity.this).load(Const.IMAGE_URL + response.body().getPumpInstallation().get(0).getInstallImage()).into(binding.ivPhotoInstallPump);
                        Glide.with(PumpInstallationActivity.this).load(Const.IMAGE_URL + response.body().getPumpInstallation().get(0).getPumpBenifiImage()).into(binding.ivBeneficiaryInstallPump);
                        Glide.with(PumpInstallationActivity.this).load(Const.IMAGE_URL + response.body().getPumpInstallation().get(0).getPumpWorkImage()).into(binding.ivPumpWorking);

                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(PumpInstallationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                  Toast.makeText(PumpInstallationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetPumpData> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(PumpInstallationActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadData() {
        Const.AGENT_NAME = preference.getAgentName();
        pump_report = getIntent().getStringExtra("pump_report");
        binding.tvSurveyorNamePump.setText(Const.AGENT_NAME);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
        datePick();
        chip = new Chip(PumpInstallationActivity.this);
        fetchData();
    }

    public boolean validation() {
        boolean isValid = true;
        if (binding.edPumpId.getText().toString().isEmpty()) {
            isValid = false;
            binding.edPumpId.setError("please enter pump id");

        } else if (binding.edIMEIId.getText().toString().isEmpty()) {
            isValid = false;
            binding.edIMEIId.setError("please enter IMEI Id");

        } else if (binding.edStructureId.getText().toString().isEmpty()) {
            isValid = false;
            binding.edStructureId.setError("please enter structure id ");

        } else if (binding.edControllerId.getText().toString().isEmpty()) {
            isValid = false;
            binding.edControllerId.setError("please enter controller id ");
        } else if (pumpPath == null || pumpPath.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select Image", Toast.LENGTH_SHORT).show();
        } else if (baneficiarypath == null || baneficiarypath.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select Image", Toast.LENGTH_SHORT).show();
        } else if (workingPumpPath == null || workingPumpPath.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select Image", Toast.LENGTH_SHORT).show();
        } else if (signatureName == null || signatureName.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please add signature", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    public boolean updateValidation() {
        boolean isValid = true;
        if (binding.edPumpId.getText().toString().isEmpty()) {
            isValid = false;
            binding.edPumpId.setError("please enter pump id");

        } else if (binding.edIMEIId.getText().toString().isEmpty()) {
            isValid = false;
            binding.edIMEIId.setError("please enter IMEI Id");

        } else if (binding.edStructureId.getText().toString().isEmpty()) {
            isValid = false;
            binding.edStructureId.setError("please enter structure id ");

        } else if (binding.edControllerId.getText().toString().isEmpty()) {
            isValid = false;
            binding.edControllerId.setError("please enter controller id ");
        } else if (signatureName == null || signatureName.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please add signature", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    private void initView() {
        binding.signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
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
        binding.btnCompleted.setOnClickListener(v -> {
//            binding.pbProgressBar.setVisibility(View.VISIBLE);
            signImage = binding.signaturePad.getSignatureSvg();
            signImage = BitMapToString(binding.signaturePad.getSignatureBitmap());
            saveBitmapIntoCacheDir(binding.signaturePad.getSignatureBitmap());
            Log.d("DeliveryReport===>", "===>" + signImage);
            binding.ivSignImagePump.setImageBitmap(binding.signaturePad.getSignatureBitmap());
        });
        binding.btnClear.setOnClickListener(v -> {
            binding.signaturePad.clear();
        });
    }

    private void datePick() {
        final Calendar c = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        binding.tvDatePumpInstall.setText(formattedDate);
    }

    public void clickListener() {
        binding.ivCameraInstallPump.setOnClickListener(this);
        binding.ivBaneficiaryCameraPump.setOnClickListener(this);
        binding.ivWorkingPumpCamera.setOnClickListener(this);
        binding.ivPhotoInstallPump.setOnClickListener(this);
        binding.ivBeneficiaryInstallPump.setOnClickListener(this);
        binding.ivPumpWorking.setOnClickListener(this);
        binding.llSubmitPumpInstall.setOnClickListener(this);
        binding.ivAddMoreId.setOnClickListener(this);
        binding.ivBackPress.setOnClickListener(this);
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private void saveBitmapIntoCacheDir(Bitmap signatureBitmap) {
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

        if (Const.isInternetConnected(PumpInstallationActivity.this)){
            uploadFileImage(fileName);
        }else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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
//                path = String.valueOf(contentURI);

                try {
                    if (imagePhoto == 1) {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        if (Const.isInternetConnected(PumpInstallationActivity.this)){
                            uploadImage(contentURI, 1);
                        }else {
                            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                        binding.ivPhotoInstallPump.setImageBitmap(bitmap);
                    } else if (imagePhoto == 2) {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        if (Const.isInternetConnected(PumpInstallationActivity.this)){
                            uploadImage(contentURI, 2);
                        }else {
                            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                        binding.ivBeneficiaryInstallPump.setImageBitmap(bitmap);
                    } else {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        if (Const.isInternetConnected(PumpInstallationActivity.this)){
                            uploadImage(contentURI, 3);
                        }else {
                            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                        binding.ivPumpWorking.setImageBitmap(bitmap);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void getLocation() {
        GpsTracker gpsTracker = new GpsTracker(PumpInstallationActivity.this);
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            binding.tvAddressPump.setText(latitude + " , " + longitude);
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    public void setAllClicksDisable(boolean b) {
        binding.ivBackPress.setClickable(b);
        binding.edPumpId.setClickable(b);
        binding.ivAddMoreId.setClickable(b);
        binding.edPanelId.setClickable(b);
        binding.edControllerId.setClickable(b);
        binding.edIMEIId.setClickable(b);
        binding.edStructureId.setClickable(b);
        binding.edPolicyNumberPumpInstall.setClickable(b);
        binding.ivCameraInstallPump.setClickable(b);
        binding.ivBaneficiaryCameraPump.setClickable(b);
        binding.ivWorkingPumpCamera.setClickable(b);
        binding.ivPhotoInstallPump.setClickable(b);
        binding.ivBeneficiaryInstallPump.setClickable(b);
        binding.ivPumpWorking.setClickable(b);
        binding.btnCompleted.setClickable(b);
        binding.btnClear.setClickable(b);
    }

    /*  update pump report   */
    public void updatePumpInstallReport(String reportId) {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", reportId);
        hashMap.put("agent_id", preference.getAgentId());
        hashMap.put("farmer_id", preference.getAgentFarmerId());
        hashMap.put("pump_id", binding.edPumpId.getText().toString());
        hashMap.put("panel_id", panelIdList.toString());
        hashMap.put("controller_id", binding.edControllerId.getText().toString());
        hashMap.put("imei_no", binding.edIMEIId.getText().toString());
        hashMap.put("structure_id", binding.edStructureId.getText().toString());
        hashMap.put("policy_no", binding.edPolicyNumberPumpInstall.getText().toString());
        Log.d("imagee===", "=" + pumpPath + "==" + baneficiarypath + "=" + workingPumpPath);
        if (pumpPath == null || !pumpPath.isEmpty()) {
            hashMap.put("install_image", pumpPath);
        }
        if (baneficiarypath == null || !baneficiarypath.isEmpty()) {
            hashMap.put("pump_benifi_image", baneficiarypath);
        }
        if (workingPumpPath == null || !workingPumpPath.isEmpty()) {
            hashMap.put("pump_work_image", workingPumpPath);
        }
        hashMap.put("sign", signatureName);
        hashMap.put("date", binding.tvDatePumpInstall.getText().toString());


        Call<PumpInstallModel> call = apiInterface.updatePumpInstallReport(hashMap, "Bearer " + preference.getToken());

        call.enqueue(new Callback<PumpInstallModel>() {
            @Override
            public void onResponse(Call<PumpInstallModel> call, Response<PumpInstallModel> response) {

                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(PumpInstallationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(PumpInstallationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Toast.makeText(PumpInstallationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PumpInstallModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(PumpInstallationActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*  add report  */
    public void addPumpInstallationData() {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("agent_id", preference.getAgentId());
        hashMap.put("farmer_id", preference.getAgentFarmerId());
        hashMap.put("pump_id", binding.edPumpId.getText().toString());
        hashMap.put("panel_id", panelIdList.toString());
        hashMap.put("controller_id", binding.edControllerId.getText().toString());
        hashMap.put("imei_no", binding.edIMEIId.getText().toString());
        hashMap.put("structure_id", binding.edStructureId.getText().toString());
        hashMap.put("policy_no", binding.edPolicyNumberPumpInstall.getText().toString());
        hashMap.put("install_image", pumpPath);
        hashMap.put("pump_benifi_image", baneficiarypath);
        hashMap.put("pump_work_image", workingPumpPath);
        hashMap.put("sign", signatureName);
        hashMap.put("date", binding.tvDatePumpInstall.getText().toString());

        Call<PumpInstallModel> call = apiInterface.getPumpInstallData(hashMap, "Bearer " + preference.getToken());

        call.enqueue(new Callback<PumpInstallModel>() {
            @Override
            public void onResponse(Call<PumpInstallModel> call, Response<PumpInstallModel> response) {

                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(PumpInstallationActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(PumpInstallationActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Toast.makeText(PumpInstallationActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PumpInstallModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(PumpInstallationActivity.this, "Data not Found", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ivCameraInstallPump || id == R.id.ivPhotoInstallPump) {
            showPictureDialog(1);
        } else if (id == R.id.ivBaneficiaryCameraPump || id == R.id.ivBeneficiaryInstallPump) {
            showPictureDialog(2);
        } else if (id == R.id.ivWorkingPumpCamera || id == R.id.ivPumpWorking) {
            showPictureDialog(3);
        } else if (id == R.id.llSubmitPumpInstall) {
            if (pump_report.equals("0")) {
                if (countChipsInChipGroup(binding.chipGroup) >= 9) {
                    if (validation()) {
                        if (Const.isInternetConnected(PumpInstallationActivity.this)){
                            addPumpInstallationData();
                        }else {
                            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "please filled all details", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Panel id minimum 9 required", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (countChipsInChipGroup(binding.chipGroup) >= 9) {
                    if (updateValidation()) {
                        if (Const.isInternetConnected(PumpInstallationActivity.this)){
                            updatePumpInstallReport(reportId);
                        }else {
                            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Panel id minimum 9 required", Toast.LENGTH_SHORT).show();
                }

            }
        } else if (id == R.id.ivAddMoreId) {
            if (!binding.edPanelId.getText().toString().isEmpty()) {
                addMorePanelId(binding.edPanelId.getText().toString().trim());
                binding.edPanelId.setText("");
            }
        } else if (id == R.id.ivBackPress) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void addMorePanelId(String txet) {

        if (panelIdList.contains(txet)) {
            Toast.makeText(this, "Panel Id already added", Toast.LENGTH_SHORT).show();
            binding.edPanelId.setText("");
        } else {
            chip = new Chip(PumpInstallationActivity.this);
            chip.setText(txet);
            binding.chipGroup.addView(chip);
            panelIdList.add(txet);
            countChipsInChipGroup(binding.chipGroup);
            chip.setCloseIconVisible(true);
            chip.setOnCloseIconClickListener(v -> binding.chipGroup.removeView(chip));
        }
    }

    private int countChipsInChipGroup(ChipGroup chipGroup) {
        return binding.chipGroup.getChildCount();
    }

    public void uploadImage(Uri contentURI, int fromWhere) {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);
        Uri uri = null;
        String fName = "";
        try {
            uri = FileSelectionUtils.getFilePathFromUri(getApplicationContext(), contentURI);
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

                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        imageName[0] = imageModel.getUploadimage().getImage_name();
                        Log.w("ImageName", imageName[0]);
                        if (fromWhere == 1) {
                            pumpPath = imageModel.getUploadimage().getImage_name();
                        } else if (fromWhere == 2) {
                            baneficiarypath = imageModel.getUploadimage().getImage_name();
                        } else {
                            workingPumpPath = imageModel.getUploadimage().getImage_name();
                        }
                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        if (fromWhere==1){
                            binding.ivPhotoInstallPump.setImageResource(R.drawable.ic_farmer);
                        } else if (fromWhere == 2) {
                            binding.ivBeneficiaryInstallPump.setImageResource(R.drawable.ic_farmer);
                        }else {
                            binding.ivPumpWorking.setImageResource(R.drawable.ic_farmer);
                        }
                        Toast.makeText(PumpInstallationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    if (fromWhere==1){
                        binding.ivPhotoInstallPump.setImageResource(R.drawable.ic_farmer);
                    } else if (fromWhere == 2) {
                        binding.ivBeneficiaryInstallPump.setImageResource(R.drawable.ic_farmer);
                    }else {
                        binding.ivPumpWorking.setImageResource(R.drawable.ic_farmer);
                    }
                    Toast.makeText(PumpInstallationActivity.this, "The image must not be greater than 2048 kilobytes, please upload again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(PumpInstallationActivity.this, " " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadFileImage(File file) {
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
                        signatureName = imageModel.getUploadimage().getImage_name();
                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(PumpInstallationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Toast.makeText(PumpInstallationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(PumpInstallationActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

