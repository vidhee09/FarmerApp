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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Models.GetPumpData;
import com.ananta.fieldAgent.Models.GetSiteData;
import com.ananta.fieldAgent.Models.ImageModel;
import com.ananta.fieldAgent.Models.PumpInstallModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.FileSelectionUtils;
import com.ananta.fieldAgent.Parser.GpsTracker;
import com.ananta.fieldAgent.Parser.Utils;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityPumpInstallationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
            Log.d("get==", "=" + pump_report);
            binding.tvSubmit.setText("Add Report");
        } else {
            Log.d("get==", "=" + pump_report);
            binding.tvSubmit.setText("Update Report");
            getData();
        }
    }

    public void getData() {
        binding.pbProgressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("farmer_id", Const.FARMER_ID);

        Call<GetPumpData> call = apiInterface.getPumpReport(hashMap);
        call.enqueue(new Callback<GetPumpData>() {
            @Override
            public void onResponse(Call<GetPumpData> call, Response<GetPumpData> response) {
                if (response.isSuccessful()) {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    binding.edPumpId.setText(response.body().getPumpInstallation().get(0).getPumpId());
                    binding.edIMEIId.setText(response.body().getPumpInstallation().get(0).getImeiNo());
                    binding.edStructureId.setText(response.body().getPumpInstallation().get(0).getStructureId());
                    binding.edControllerId.setText(response.body().getPumpInstallation().get(0).getControllerId());
                    chip.setText(response.body().getPumpInstallation().get(0).getPanelId());
                    binding.chipGroup.addView(chip);
                    Log.d("chipp-======","="+ chip);

                    reportId = String.valueOf(response.body().getPumpInstallation().get(0).getId());
                    Glide.with(PumpInstallationActivity.this).load(Const.IMAGE_URL + response.body().getPumpInstallation().get(0).getInstallImage()).into(binding.ivPhotoInstallPump);
                    Glide.with(PumpInstallationActivity.this).load(Const.IMAGE_URL + response.body().getPumpInstallation().get(0).getPumpBenifiImage()).into(binding.ivBeneficiaryInstallPump);
                    Glide.with(PumpInstallationActivity.this).load(Const.IMAGE_URL + response.body().getPumpInstallation().get(0).getPumpWorkImage()).into(binding.ivPumpWorking);

                } else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(PumpInstallationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetPumpData> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(PumpInstallationActivity.this, "" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedData", MODE_PRIVATE);
        Const.AGENT_NAME = sharedPreferences.getString("agentName", "");
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
            binding.pbProgressBar.setVisibility(View.VISIBLE);
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
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        binding.tvDatePumpInstall.setText(formattedDate);
    }

    public void clickListener() {
        binding.ivCameraInstallPump.setOnClickListener(this);
        binding.ivBaneficiaryCameraPump.setOnClickListener(this);
        binding.ivWorkingPumpCamera.setOnClickListener(this);
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

        uploadFileImage(fileName);
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
//                        pumpPath = String.valueOf(contentURI);
                        uploadImage(contentURI, 1);
                        binding.ivPhotoInstallPump.setImageBitmap(bitmap);
                    } else if (imagePhoto == 2) {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                        baneficiarypath = String.valueOf(contentURI);
                        uploadImage(contentURI, 2);
                        binding.ivBeneficiaryInstallPump.setImageBitmap(bitmap);
                    } else {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                        workingPumpPath = String.valueOf(contentURI);
                        uploadImage(contentURI, 3);
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

    /*  update pump report   */
    public void updatePumpInstallReport(String reportId) {

        binding.pbProgressBar.setVisibility(View.VISIBLE);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", reportId);
        hashMap.put("agent_id", Const.AGENT_ID);
        hashMap.put("farmer_id", Const.FARMER_ID);
        hashMap.put("pump_id", binding.edPumpId.getText().toString());
        hashMap.put("panel_id", panelIdList.toString());
        hashMap.put("controller_id", binding.edControllerId.getText().toString());
        hashMap.put("imei_no", binding.edIMEIId.getText().toString());
        hashMap.put("structure_id", binding.edStructureId.getText().toString());
        hashMap.put("policy_no", binding.edPolicyNumberPumpInstall.getText().toString());
        Log.d("imagee===","="+pumpPath+"=="+baneficiarypath+"="+workingPumpPath);
        if ( pumpPath == null || !pumpPath.isEmpty()) {
            hashMap.put("install_image", pumpPath);
        }
        if ( baneficiarypath == null || !baneficiarypath.isEmpty()) {
            hashMap.put("pump_benifi_image", baneficiarypath);
        }
        if ( workingPumpPath == null || !workingPumpPath.isEmpty()) {
            hashMap.put("pump_work_image", workingPumpPath);
        }
        hashMap.put("sign", signatureName);
        hashMap.put("date", binding.tvDatePumpInstall.getText().toString());

        Call<PumpInstallModel> call = apiInterface.updatePumpInstallReport(hashMap);

        call.enqueue(new Callback<PumpInstallModel>() {
            @Override
            public void onResponse(Call<PumpInstallModel> call, Response<PumpInstallModel> response) {

                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        Toast.makeText(PumpInstallationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        binding.pbProgressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(PumpInstallationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(PumpInstallationActivity.this, "Data not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PumpInstallModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(PumpInstallationActivity.this, "Data not Found", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*  add report  */
    public void addPumpInstallationData() {

        binding.pbProgressBar.setVisibility(View.VISIBLE);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("agent_id", Const.AGENT_ID);
        hashMap.put("farmer_id", Const.FARMER_ID);
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

        Call<PumpInstallModel> call = apiInterface.getPumpInstallData(hashMap);

        call.enqueue(new Callback<PumpInstallModel>() {
            @Override
            public void onResponse(Call<PumpInstallModel> call, Response<PumpInstallModel> response) {

                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        Toast.makeText(PumpInstallationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        binding.pbProgressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(PumpInstallationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(PumpInstallationActivity.this, "Data not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PumpInstallModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(PumpInstallationActivity.this, "Data not Found", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ivCameraInstallPump) {
            showPictureDialog(1);
        } else if (id == R.id.ivBaneficiaryCameraPump) {
            showPictureDialog(2);
        } else if (id == R.id.ivWorkingPumpCamera) {
            showPictureDialog(3);
        } else if (id == R.id.llSubmitPumpInstall) {
            if (validation()) {
                if (pump_report.equals("0")) {
                    if (countChipsInChipGroup(binding.chipGroup) >= 9) {
                        addPumpInstallationData();
                    } else {
                        Toast.makeText(this, "Panel id minimum 9 required", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    updatePumpInstallReport(reportId);
                }

            } else {
                Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.ivAddMoreId) {
            if (!binding.edPanelId.getText().toString().isEmpty()) {
                addMorePanelId(binding.edPanelId.getText().toString());
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

         chip = new Chip(PumpInstallationActivity.this);
        chip.setText(txet);
        binding.chipGroup.addView(chip);

        panelIdList.add(txet);
        Log.d("chip====", "=" + new Gson().toJson(panelIdList));

        countChipsInChipGroup(binding.chipGroup);
        Log.d("chipgroup====", "=" + countChipsInChipGroup(binding.chipGroup));

        /*  if remove chip   */
       /* chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.chipGroup.removeView(chip);
            }
        });*/

    }

    private int countChipsInChipGroup(ChipGroup chipGroup) {
        return binding.chipGroup.getChildCount();
    }

    public void uploadImage(Uri contentURI, int fromWhere) {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        Uri uri = null;
        String fName = "";
        try {
            uri = FileSelectionUtils.getFilePathFromUri(getApplicationContext(), contentURI);
            Log.w("FilePathURL", "" + contentURI + " " + uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File file = new File(uri.getPath());
        Log.w("FilePath", file.getPath());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<ImageModel> call = apiInterface.uploadImage(multipartBody, "profile_picture");

        final String[] imageName = {""};
        call.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                ImageModel imageModel = response.body();

                if (response.isSuccessful()) {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    imageName[0] = imageModel.getFileUploadData().getImage_name();
                    Log.w("ImageName", imageName[0]);
                    if (fromWhere == 1) {
                        pumpPath = imageModel.getFileUploadData().getImage_name();
                    }else  if (fromWhere == 2) {
                        baneficiarypath = imageModel.getFileUploadData().getImage_name();
                    } else {
                        workingPumpPath = imageModel.getFileUploadData().getImage_name();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(PumpInstallationActivity.this, "Data not found" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadFileImage(File file) {
        binding.pbProgressBar.setVisibility(View.VISIBLE);
        Uri uri = null;
        String fName = "";
        Log.w("FilePath", file.getPath());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<ImageModel> call = apiInterface.uploadImage(multipartBody, "profile_picture");

        final String[] imageName = {""};
        call.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                ImageModel imageModel = response.body();

                if (response.isSuccessful()) {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    imageName[0] = imageModel.getFileUploadData().getImage_name();
                    signatureName = imageModel.getFileUploadData().getImage_name();
                } else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(PumpInstallationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(PumpInstallationActivity.this, "-" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }


}

