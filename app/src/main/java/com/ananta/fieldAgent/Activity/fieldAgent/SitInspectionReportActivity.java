package com.ananta.fieldAgent.Activity.fieldAgent;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ananta.fieldAgent.Activity.LoginScreen;
import com.ananta.fieldAgent.Models.SiteReportModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.GpsTracker;
import com.ananta.fieldAgent.Parser.Utils;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivitySitInspectionReportBinding;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SitInspectionReportActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PERMISSION_CODE = 1;
    ActivitySitInspectionReportBinding binding;
    ApiInterface apiInterface;
    String Farmer_ID = "", Imagepath = "", baneficiarypath = "", signImage = "";
    private static final int GALLERY = 100;
    private static final int CAMERA = 101;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String TAG = "PermissionRequest";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;

    String FarmerPosition,FarmerName,CompanyName;
    int photos;
    double latitude,longitude;
    private File FilepathName;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySitInspectionReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        clickLister();
        initView();
        setClickListener();
        loadData();

    }

    public void loadData() {
        FarmerPosition = getIntent().getStringExtra("farmer_position");
        FarmerName = getIntent().getStringExtra("FarmerName");
        CompanyName = getIntent().getStringExtra("CompanyName");
        binding.tvSurveyorName.setText(Const.AGENT_NAME);
        Log.d("id===", "=" + Farmer_ID);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
        datePick();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ivCameraPump) {
            checkAndRequestPermissions();
            showPictureDialog(1);
        } else if (id == R.id.ivBenificiaryCameraSite) {
            checkAndRequestPermissions();
            showPictureDialog(2);
        } else if (id == R.id.llSiteSubmit) {
            if (validation()) {
                if (isInternetAvailable()) {
                    getSiteReportData();
                }
            } else {
                Toast.makeText(this, "Please fill all field", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.ivBackPress) {
            onBackPressed();
        }
    }


    private void datePick() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        binding.tvDateSiteReport.setText(formattedDate);

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
        }

        return isValid;
    }

    private void initView() {
        binding.signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
//                Toast.makeText(SitInspectionReportActivity.this, "onStartSigning() triggered!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
//                Toast.makeText(SitInspectionReportActivity.this, "onStartSigning() triggered!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
//                Toast.makeText(SitInspectionReportActivity.this, "onStartSigning() triggered!", Toast.LENGTH_SHORT).show();
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
        FilepathName = fileName;
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
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public void getSiteReportData() {

        Utils.showCustomProgressDialog(SitInspectionReportActivity.this,true);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("farmer_id", Const.FARMER_ID);
        hashMap.put("agent_id", Const.AGENT_ID);
        hashMap.put("pump_image", Imagepath);
        hashMap.put("pump_benificiaryimage", baneficiarypath);
        hashMap.put("date", binding.tvDateSiteReport.getText().toString());
        hashMap.put("inspection_sign", FilepathName.toString());
        hashMap.put("inspection_officer_name", binding.edInspectionOfficerName.getText().toString());
        hashMap.put("present_person_name", binding.edPresentPersonName.getText().toString());
        hashMap.put("latitude", String.valueOf(latitude));
        hashMap.put("longitude", String.valueOf(longitude));

        Call<SiteReportModel> call = apiInterface.getSiteReport(hashMap);
        call.enqueue(new Callback<SiteReportModel>() {
            @Override
            public void onResponse(Call<SiteReportModel> call, Response<SiteReportModel> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("true")) {
                        Utils.hideProgressDialog(SitInspectionReportActivity.this);
                        Toast.makeText(SitInspectionReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SitInspectionReportActivity.this, FarmerDetailActivity.class);
                        intent.putExtra("FarmerName",FarmerName);
                        intent.putExtra("CompanyName",CompanyName);
                        startActivity(intent);

                    } else {
                        Utils.showCustomProgressDialog(SitInspectionReportActivity.this,true);
                        Toast.makeText(SitInspectionReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Utils.showCustomProgressDialog(SitInspectionReportActivity.this,true);
                    Toast.makeText(SitInspectionReportActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SiteReportModel> call, Throwable t) {
                Utils.showCustomProgressDialog(SitInspectionReportActivity.this,true);
                Toast.makeText(SitInspectionReportActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showPictureDialog(int photoImage) {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera"};
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
        startActivityForResult(camera_intent, CAMERA);
        photos = photo;
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
                Imagepath = String.valueOf(contentURI);

                try {
                    if (photos == 1) {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        Imagepath = saveImage(bitmap);
//                        Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                        binding.ivPumpPhoto.setImageBitmap(bitmap);
                    } else {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        baneficiarypath = String.valueOf(contentURI);
//                            baneficiarypath = saveImage(bitmap);
//                        Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                        binding.ivBenificiaryPhoto.setImageBitmap(bitmap);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            if (photos == 1) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
                Imagepath = String.valueOf(tempUri);
                binding.ivPumpPhoto.setImageBitmap(thumbnail);
//                Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                saveImage(thumbnail);
            } else {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
                baneficiarypath = String.valueOf(tempUri);
                binding.ivBenificiaryPhoto.setImageBitmap(thumbnail);
//                Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                saveImage(thumbnail);
            }

        }

    }

    public String saveImage(Bitmap myBitmap) {
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

    public void clickLister() {
        binding.ivCameraPump.setOnClickListener(this);
        binding.llSiteSubmit.setOnClickListener(this);
        binding.ivBenificiaryCameraSite.setOnClickListener(this);
        binding.tvDateSiteReport.setOnClickListener(this);
        binding.tvAddressSite.setOnClickListener(this);
        binding.ivBackPress.setOnClickListener(this);
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }


    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE);
        } else {
            // Permission has already been granted
//            Toast.makeText(this, "Permissions already granted", Toast.LENGTH_SHORT).show();
        }

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
//                    getCurrentLocation();
                } else {
//                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public void getLocation(){
        GpsTracker gpsTracker = new GpsTracker(SitInspectionReportActivity.this);
        if(gpsTracker.canGetLocation()){
             latitude = gpsTracker.getLatitude();
             longitude = gpsTracker.getLongitude();
            binding.tvAddressSite.setText(latitude+" , "+longitude);
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

}