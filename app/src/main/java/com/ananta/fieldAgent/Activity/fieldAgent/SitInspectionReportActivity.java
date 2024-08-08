package com.ananta.fieldAgent.Activity.fieldAgent;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Activity.LoginScreen;
import com.ananta.fieldAgent.Models.GetSiteData;
import com.ananta.fieldAgent.Models.ImageModel;
import com.ananta.fieldAgent.Models.JointSurveyorModel;
import com.ananta.fieldAgent.Models.SiteReportModel;
import com.ananta.fieldAgent.Models.Siteinspectionn;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.CompressImage;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.FileSelectionUtils;
import com.ananta.fieldAgent.Parser.GpsTracker;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.Parser.Utils;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivitySitInspectionReportBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SitInspectionReportActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSION_CODE = 1;
    private static final int CAMERA = 100;
    ActivitySitInspectionReportBinding binding;
    ApiInterface apiInterface;
    String Imagepath = "", baneficiarypath = "", signImage = "";
    private static final int GALLERY = 101;
    Preference preference;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String TAG = "PermissionRequest";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;

    String reportId;
    int photos;
    double latitude, longitude;
    private String FilepathName, site_report;
    Siteinspectionn siteinspectionnModel;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivitySitInspectionReportBinding.inflate(getLayoutInflater());
        preference = Preference.getInstance(SitInspectionReportActivity.this);
        setContentView(binding.getRoot());

        loadData();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        clickLister();
        initView();
        setClickListener();

    }

    public void loadData() {
        Const.AGENT_NAME = preference.getAgentName();
        Log.d("Name===", "=site==" + Const.AGENT_NAME);
        site_report = getIntent().getStringExtra("site_report");
        binding.tvSurveyorName.setText(Const.AGENT_NAME);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
        datePick();
        fetchData();
    }

    public void fetchData() {
        if (site_report.equals("0")) {
            binding.llSiteSubmit.setText("Add Report");
        } else {
            binding.llSiteSubmit.setText("Update Report");
            if (Const.isInternetConnected(SitInspectionReportActivity.this)){
                getData();
            }else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setAllClicksDisable(boolean b){
        binding.edPresentPersonName.setClickable(b);
        binding.edInspectionOfficerName.setClickable(b);
        binding.ivCameraPump.setClickable(b);
        binding.ivBenificiaryCameraSite.setClickable(b);
        binding.btnCompleted.setClickable(b);
        binding.btnClear.setClickable(b);
        binding.ivBackPress.setClickable(b);
        binding.llSiteSubmit.setClickable(b);
        binding.ivBenificiaryPhoto.setClickable(b);
        binding.ivPumpPhoto.setClickable(b);
        binding.llSiteSubmit.setClickable(b);

    }
    /*  site report update  */

    public void updateSiteReport(String reportId) {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", reportId);
        hashMap.put("farmer_id", preference.getAgentFarmerId());
        Log.d("idddd=====","=farmerid==siteAdd=="+preference.getAgentFarmerId());

        hashMap.put("agent_id", preference.getAgentId());
        hashMap.put("date", binding.tvDateSiteReport.getText().toString());
        if (Imagepath == null || !Imagepath.isEmpty()) {
            hashMap.put("pump_image", Imagepath);
        }
        if (baneficiarypath == null || !baneficiarypath.isEmpty()) {
            hashMap.put("pump_benificiaryimage", baneficiarypath);
        }
        hashMap.put("inspection_sign", FilepathName);
        hashMap.put("inspection_officer_name", binding.edInspectionOfficerName.getText().toString());
        hashMap.put("present_person_name", binding.edPresentPersonName.getText().toString());
        hashMap.put("latitude", String.valueOf(latitude));
        hashMap.put("longitude", String.valueOf(longitude));

        Call<SiteReportModel> call = apiInterface.updateSiteReport(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<SiteReportModel>() {
            @Override
            public void onResponse(Call<SiteReportModel> call, Response<SiteReportModel> response) {

                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Siteinspectionn siteinspectionn = response.body().getSiteinspection();
                        Toast.makeText(SitInspectionReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(SitInspectionReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Toast.makeText(SitInspectionReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SiteReportModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(SitInspectionReportActivity.this, " " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*   add report */

    public void addSiteReportData() {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("farmer_id", preference.getAgentFarmerId());
        hashMap.put("agent_id", preference.getAgentId());
        hashMap.put("pump_image", Imagepath);
        hashMap.put("pump_benificiaryimage", baneficiarypath);
        hashMap.put("date", binding.tvDateSiteReport.getText().toString());
        hashMap.put("inspection_sign", FilepathName);
        hashMap.put("inspection_officer_name", binding.edInspectionOfficerName.getText().toString());
        hashMap.put("present_person_name", binding.edPresentPersonName.getText().toString());
        hashMap.put("latitude", String.valueOf(latitude));
        hashMap.put("longitude", String.valueOf(longitude));

        Call<SiteReportModel> call = apiInterface.addSiteInspection(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<SiteReportModel>() {
            @Override
            public void onResponse(Call<SiteReportModel> call, @NonNull Response<SiteReportModel> response) {

                Log.d("response==", "=" + response.code());

                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(SitInspectionReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Const.SiteReport = response.body().isSuccess();
                        siteinspectionnModel = response.body().getSiteinspection();
                        Const.ID = response.body().getSiteinspection().getId();
                        Log.d("SiteInspection ==>", "===>" + Const.SiteReport);
                        finish();

                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(SitInspectionReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Log.d("response==", "dfgfdf=" + response.body().getMessage());
                    Toast.makeText(SitInspectionReportActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SiteReportModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(SitInspectionReportActivity.this, " " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData() {
        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("farmer_id", preference.getAgentFarmerId());

        Call<GetSiteData> call = apiInterface.getSiteReport(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<GetSiteData>() {
            @Override
            public void onResponse(Call<GetSiteData> call, Response<GetSiteData> response) {

                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Log.d("sitemodel===>", "==success=>" + response.body().getMessage());
                        binding.edInspectionOfficerName.setText(response.body().getSiteInpections().get(0).getInspectionOfficerName());
                        binding.edPresentPersonName.setText(response.body().getSiteInpections().get(0).getPresentPersonName());
                        reportId = String.valueOf(response.body().getSiteInpections().get(0).getId());

                        Glide.with(SitInspectionReportActivity.this).load(Const.IMAGE_URL + response.body().getSiteInpections().get(0).getPumpImage()).into(binding.ivPumpPhoto);
                        Glide.with(SitInspectionReportActivity.this).load(Const.IMAGE_URL + response.body().getSiteInpections().get(0).getPumpBenificiaryimage()).into(binding.ivBenificiaryPhoto);

                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(SitInspectionReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Toast.makeText(SitInspectionReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetSiteData> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(SitInspectionReportActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ivCameraPump || id == R.id.ivPumpPhoto) {
            showPictureDialog(1);
        } else if (id == R.id.ivBenificiaryCameraSite || id == R.id.ivBenificiaryPhoto) {
            showPictureDialog(2);
        } else if (id == R.id.llSiteSubmit) {
                if (site_report.equals("0")) {
                    if (validation()) {
                        if (Const.isInternetConnected(SitInspectionReportActivity.this)){
                            addSiteReportData();
                        }else {
                            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (updateValidation()) {
                        if (Const.isInternetConnected(SitInspectionReportActivity.this)){
                            updateSiteReport(reportId);
                        }else {
                            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
                    }
                }

        } else if (id == R.id.ivBackPress) {
            onBackPressed();
        }
    }

    private void datePick() {

        final Calendar c = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        binding.tvDateSiteReport.setText(formattedDate );

    }

    public boolean validation() {
        boolean isValid = true;
        if (binding.edInspectionOfficerName.getText().toString().isEmpty()) {
            isValid = false;
            binding.edInspectionOfficerName.setError("please fill name here");
        } else if (binding.edPresentPersonName.getText().toString().isEmpty()) {
            isValid = false;
            binding.edPresentPersonName.setError("please fill name here");
        } else if (binding.tvDateSiteReport.getText().toString().isEmpty()) {
            isValid = false;
            binding.tvAddressSite.setError("please enter date");
        } else if (signImage == null || signImage.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please add signature", Toast.LENGTH_SHORT).show();
        } else if (Imagepath == null || Imagepath.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select Image", Toast.LENGTH_SHORT).show();
        }else if (baneficiarypath == null || baneficiarypath.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please select Image", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    public boolean updateValidation() {
        boolean isValid = true;
        if (binding.edInspectionOfficerName.getText().toString().isEmpty()) {
            isValid = false;
            binding.edInspectionOfficerName.setError("please fill name here");
        } else if (binding.edPresentPersonName.getText().toString().isEmpty()) {
            isValid = false;
            binding.edPresentPersonName.setError("please fill name here");
        } else if (binding.tvDateSiteReport.getText().toString().isEmpty()) {
            isValid = false;
            binding.tvAddressSite.setError("please enter date");
        } else if (signImage == null ||signImage.isEmpty()) {
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
            signImage = binding.signaturePad.getSignatureSvg();
            saveBitmapIntoCacheDir(binding.signaturePad.getSignatureBitmap());
            binding.ivSignImage.setImageBitmap(binding.signaturePad.getSignatureBitmap());
        });
        binding.btnClear.setOnClickListener(v -> {
            binding.signaturePad.clear();
        });
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

        File fileName = new File(folder, "signature.jpg");

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
        if (Const.isInternetConnected(SitInspectionReportActivity.this)){
            uploadFileImage(fileName);
        }else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


private void showPictureDialog(int photoImage) {
    AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
    pictureDialog.setTitle("Select Action");
    String[] pictureDialogItems = {"Select photo from gallery"/*,"Choose photo from camera"*/};
    pictureDialog.setItems(pictureDialogItems,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            choosePhotoFromGallary(photoImage);
                            break;
                        case 1:
                            takePhotoFromCamera(photoImage);
                            break;
                    }
                }
            });
    pictureDialog.show();
}

    public void choosePhotoFromGallary(int photo) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY);
        photos = photo;
    }

    private void takePhotoFromCamera(int photo) {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera_intent.setType("image/*");
        startActivityForResult(camera_intent, CAMERA);
        photos = photo;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == GALLERY) {
                if (data != null) {
                    Uri contentURI = data.getData();
//                path = String.valueOf(contentURI);
                    try {
                        if (photos == 1) {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                            if (Const.isInternetConnected(SitInspectionReportActivity.this)){
                                uploadImage(contentURI, 1);
                            }else {
                                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                            binding.ivPumpPhoto.setImageBitmap(bitmap);
                        } else {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                            if (Const.isInternetConnected(SitInspectionReportActivity.this)){
                                uploadImage(contentURI, 2);
                            }else {
                                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                            binding.ivBenificiaryPhoto.setImageBitmap(bitmap);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }

            } else if (requestCode == CAMERA) {
                Bitmap myBmp = (Bitmap) data.getExtras().get("data");
                Uri uri =getImageUri(SitInspectionReportActivity.this, myBmp);
                if (photos == 1){
//                    uploadImage(uri,1);
                    binding.ivPumpPhoto.setImageBitmap(myBmp);
                }else {
//                    uploadImage(uri,2);
                    binding.ivBenificiaryPhoto.setImageBitmap(myBmp);
                }
                saveImage(myBmp);
            }
        }

            /*if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
            if (photos == 1){
                uploadImage(tempUri, 1);
                binding.ivRequestPhoto.setImageBitmap(thumbnail);
            }else {
                uploadImage(tempUri, 2);
                binding.ivRequestPhoto.setImageBitmap(thumbnail);

            }
            saveImage(thumbnail);
            Log.d("path===>", "=2=" + tempUri);
            Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }*/
    }


    public String saveImage(Bitmap myBitmap) {
        String imageFile = "";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "12345");
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
            imageFile = f.getAbsolutePath();
//            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return imageFile;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void clickLister() {
        binding.ivCameraPump.setOnClickListener(this);
        binding.llSiteSubmit.setOnClickListener(this);
        binding.ivPumpPhoto.setOnClickListener(this);
        binding.ivBenificiaryPhoto.setOnClickListener(this);
        binding.ivBenificiaryCameraSite.setOnClickListener(this);
        binding.tvDateSiteReport.setOnClickListener(this);
        binding.tvAddressSite.setOnClickListener(this);
        binding.ivBackPress.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE || requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0) {
                boolean readExternalStorageGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean cameraGranted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (readExternalStorageGranted && cameraGranted) {
//                    Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e(TAG, "Permissions result length is zero");
            }

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
//                Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
            }

            if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
//                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public void getLocation() {
        GpsTracker gpsTracker = new GpsTracker(SitInspectionReportActivity.this);
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            binding.tvAddressSite.setText(latitude + " , " + longitude);

        } else {
            gpsTracker.showSettingsAlert();
        }
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
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("image", file.getPath(), requestFile);
        Log.w("Imagepath==sdgvsdg==", "img==" + file.getName());

        Call<ImageModel> call = apiInterface.uploadImage(multipartBody, "profile_picture", "Bearer " + preference.getToken());

        final String[] imageName = {""};
        call.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                ImageModel imageModel = response.body();

                Log.d("ImageName-Code=", "="+response.code());
                if (response.body() != null){
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        imageName[0] = imageModel.getUploadimage().getImage_name();
                        Log.d("ImageName", imageName[0]);
                        if (fromWhere == 1) {
                            Log.d("ImageName==", Imagepath);
                            Imagepath = imageModel.getUploadimage().getImage_name();
                        } else {
                            Log.d("ImageName==", baneficiarypath);
                            baneficiarypath = imageModel.getUploadimage().getImage_name();
                        }
                    } else {
                        Log.d("ImageName==", "else" + Imagepath);
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        if (fromWhere == 1) {
                            binding.ivPumpPhoto.setImageResource(R.drawable.ic_farmer);
                        }else {
                            binding.ivBenificiaryPhoto.setImageResource(R.drawable.ic_farmer);
                        }
                        Toast.makeText(SitInspectionReportActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Log.d("ImageName==", "else" + Imagepath);
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    if (fromWhere == 1) {
                        binding.ivPumpPhoto.setImageResource(R.drawable.ic_farmer);
                    }else {
                        binding.ivBenificiaryPhoto.setImageResource(R.drawable.ic_farmer);
                    }
                    Toast.makeText(SitInspectionReportActivity.this, "The image must not be greater than 2048 kilobytes, please upload again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(SitInspectionReportActivity.this, "Image uploaded failed", Toast.LENGTH_SHORT).show();
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
        Log.w("FilePath==", file.getPath());
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
                        FilepathName = imageModel.getUploadimage().getImage_name();
                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(SitInspectionReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Toast.makeText(SitInspectionReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(SitInspectionReportActivity.this, "Signature image uploaded failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
