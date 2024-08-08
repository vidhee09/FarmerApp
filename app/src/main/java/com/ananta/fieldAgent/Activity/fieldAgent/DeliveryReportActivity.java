package com.ananta.fieldAgent.Activity.fieldAgent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Models.DeliveryDataModel;
import com.ananta.fieldAgent.Models.GetDeliveryData;
import com.ananta.fieldAgent.Models.ImageModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.FileSelectionUtils;
import com.ananta.fieldAgent.Parser.GpsTracker;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityDeliveryReportBinding;
import com.bumptech.glide.Glide;
import com.github.gcacace.signaturepad.views.SignaturePad;

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


public class DeliveryReportActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityDeliveryReportBinding binding;
    ApiInterface apiInterface;
    String signImage, path;
    private static final int GALLERY = 100;
    private static final int CAMERA = 101;
    private static final int PERMISSION_CODE = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private String signatureName, delivery_report, reportId, Imagepath;
    double latitude, longitude;
    Preference preference;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preference = Preference.getInstance(DeliveryReportActivity.this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadData();
        fetchData();
        initView();
        setClickListener();
        clickListener();

    }

    public void loadData() {
        Const.AGENT_NAME = preference.getAgentName();
        delivery_report = getIntent().getStringExtra("delivery_report");
        binding.edSurveyorNameDelivery.setText(Const.AGENT_NAME);
        getLocation();
        datePick();
    }

    public void fetchData() {
        if (delivery_report.equals("0")) {
            binding.llDeliverySubmit.setText("Add Report");
        } else {
            binding.llDeliverySubmit.setText("Update Report");
            getData();
        }
    }


    public void setAllClicksDisable(boolean b){
        binding.ivBackPress.setClickable(b);
        binding.edPresentPersonNameDelivery.setClickable(b);
        binding.ivMaterialCamera.setClickable(b);
        binding.ivMaterialPhoto.setClickable(b);
        binding.btnClear.setClickable(b);
        binding.btnCompleted.setClickable(b);
        binding.llDeliverySubmit.setClickable(b);
    }

    public void getData() {

        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("farmer_id", preference.getAgentFarmerId());

        Call<GetDeliveryData> call = apiInterface.getDeliveryReport(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<GetDeliveryData>() {
            @Override
            public void onResponse(Call<GetDeliveryData> call, Response<GetDeliveryData> response) {

                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        binding.edPresentPersonNameDelivery.setText(response.body().getDelivery().get(0).getPresentPersonName());
                        reportId = String.valueOf(response.body().getDelivery().get(0).getId());
                        Glide.with(DeliveryReportActivity.this).load(Const.IMAGE_URL + response.body().getDelivery().get(0).getImage()).into(binding.ivMaterialPhoto);

                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(DeliveryReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Toast.makeText(DeliveryReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetDeliveryData> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(DeliveryReportActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void datePick() {
        final Calendar c = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        binding.tvDeliveryDate.setText(formattedDate);
    }

    public boolean validation() {
        boolean isValid = true;
        if (binding.edSurveyorNameDelivery.getText().toString().isEmpty()) {
            isValid = false;
            binding.tvErrorSurveyorName.setVisibility(View.VISIBLE);
        } else if (binding.edPresentPersonNameDelivery.getText().toString().isEmpty()) {
            isValid = false;
            binding.tvErrorPresentPersonName.setVisibility(View.VISIBLE);
        } else if (binding.tvAddressDelivery.getText().toString().isEmpty()) {
            isValid = false;
        } else if (signatureName == null || signatureName.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please add signature", Toast.LENGTH_SHORT).show();
        } else if (Imagepath == null || Imagepath.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please Select Image", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    /*  -- update report validation --     */
    public boolean updateValidation() {
        boolean isValid = true;
        if (binding.edSurveyorNameDelivery.getText().toString().isEmpty()) {
            isValid = false;
            binding.tvErrorSurveyorName.setVisibility(View.VISIBLE);
        } else if (binding.edPresentPersonNameDelivery.getText().toString().isEmpty()) {
            isValid = false;
            binding.tvErrorPresentPersonName.setVisibility(View.VISIBLE);
        } else if (binding.tvAddressDelivery.getText().toString().isEmpty()) {
            isValid = false;
        } else if (signatureName == null || signatureName.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "please add signature", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    /*  delivery update  */
    public void updateSiteReport(String reportId) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", reportId);
        hashMap.put("surveyor_name", Const.AGENT_NAME);
        hashMap.put("farmer_id", preference.getAgentFarmerId());
        hashMap.put("agent_id", preference.getAgentId());
        hashMap.put("present_person_name", binding.edPresentPersonNameDelivery.getText().toString());
        if (Imagepath == null || !Imagepath.toString().isEmpty()) {
            hashMap.put("image", Imagepath);
        }
        hashMap.put("date", binding.tvDeliveryDate.getText().toString());
        hashMap.put("sign", signatureName);
        hashMap.put("latitude", String.valueOf(latitude));
        hashMap.put("longitude", String.valueOf(longitude));

        Call<DeliveryDataModel> call = apiInterface.updateDeliveryReport(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<DeliveryDataModel>() {
            @Override
            public void onResponse(Call<DeliveryDataModel> call, Response<DeliveryDataModel> response) {

                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);

                        Toast.makeText(DeliveryReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(DeliveryReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Toast.makeText(DeliveryReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DeliveryDataModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(DeliveryReportActivity.this,  "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*   add report */
    public void addDeliveryReportData() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        binding.pbProgressBar.setVisibility(View.VISIBLE);
        setAllClicksDisable(false);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("surveyor_name", Const.AGENT_NAME);
        hashMap.put("farmer_id", preference.getAgentFarmerId());
        hashMap.put("agent_id", preference.getAgentId());
        hashMap.put("present_person_name", binding.edPresentPersonNameDelivery.getText().toString());
        hashMap.put("image", Imagepath);
        hashMap.put("date", binding.tvDeliveryDate.getText().toString());
        hashMap.put("sign", signatureName);
        hashMap.put("latitude", String.valueOf(latitude));
        hashMap.put("longitude", String.valueOf(longitude));

        Call<DeliveryDataModel> call = apiInterface.addDeliveryReport(hashMap, "Bearer " + preference.getToken());
        call.enqueue(new Callback<DeliveryDataModel>() {
            @Override
            public void onResponse(Call<DeliveryDataModel> call, Response<DeliveryDataModel> response) {

                Log.d("response===", "delivery=" + response.code());

                if (response.body() != null) {
                    if (response.body().isSuccess()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(DeliveryReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        Toast.makeText(DeliveryReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Toast.makeText(DeliveryReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DeliveryDataModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(DeliveryReportActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
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

        File fileName = new File(folder, "signatureDelivery.jpg");
        Log.d("Site===>", "==d=>" + fileName.toString());
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

    private void initView() {
        binding.signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
//                Toast.makeText(DeliveryReportActivity.this, "onStartSigning() triggered!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
//                Toast.makeText(DeliveryReportActivity.this, "onStartSigning() triggered!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
//                Toast.makeText(DeliveryReportActivity.this, "onStartSigning() triggered!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setClickListener() {
        binding.btnCompleted.setOnClickListener(v -> {
            signImage = binding.signaturePad.getSignatureSvg();
            signImage = BitMapToString(binding.signaturePad.getSignatureBitmap());
            saveBitmapIntoCacheDir(binding.signaturePad.getSignatureBitmap());
            Log.d("DeliveryReport===>", "===>" + signImage);
            binding.ivSignDeliveryImage.setImageBitmap(binding.signaturePad.getSignatureBitmap());
        });
        binding.btnClear.setOnClickListener(v -> {
            binding.signaturePad.clear();
        });
    }

    public void clickListener() {
        binding.ivMaterialCamera.setOnClickListener(this);
        binding.ivMaterialPhoto.setOnClickListener(this);
        binding.llDeliverySubmit.setOnClickListener(this);
        binding.tvAddressDelivery.setOnClickListener(this);
        binding.ivBackPress.setOnClickListener(this);
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", /*"Choose photo from Camera"*/};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, CAMERA);

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
                    Bitmap  bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    /*bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(String.valueOf(contentURI),options);*/
                    uploadImage(contentURI, 1);
                    binding.ivMaterialPhoto.setImageBitmap(bitmap);
                    Log.d("contentURI===>", "=bitmap=" + contentURI);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            binding.ivMaterialPhoto.setImageBitmap(thumbnail);

            Uri tempUri = getImageUri(getApplicationContext(), thumbnail);

            path = String.valueOf(tempUri);

            saveImage(thumbnail);
            Log.d("path===>", "=2=" + path);
//            Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
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
        if (id == R.id.ivMaterialCamera || id == R.id.ivMaterialPhoto) {
            showPictureDialog();
        } else if (id == R.id.llDeliverySubmit) {
            if (delivery_report.equals("0")) {
                if (validation()) {
                    addDeliveryReportData();
                } else {
                    Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (updateValidation()) {
                    updateSiteReport(reportId);
                } else {
                    Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (id == R.id.ivBackPress) {
            onBackPressed();
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
                Log.e("TAG", "Permissions result length is zero");
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

    public void getLocation() {

        GpsTracker gpsTracker = new GpsTracker(DeliveryReportActivity.this);
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            binding.tvAddressDelivery.setText(latitude + " , " + longitude);

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
                            Imagepath = imageModel.getUploadimage().getImage_name();
                        }
                    } else {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        setAllClicksDisable(true);
                        binding.ivMaterialPhoto.setImageResource(R.drawable.ic_farmer);
                        Toast.makeText(DeliveryReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    binding.ivMaterialPhoto.setImageResource(R.drawable.ic_farmer);
                    Toast.makeText(DeliveryReportActivity.this, "The image must not be greater than 2048 kilobytes, please upload again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(DeliveryReportActivity.this, "Image uploaded failed", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(DeliveryReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.GONE);
                    setAllClicksDisable(true);
                    Toast.makeText(DeliveryReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.GONE);
                setAllClicksDisable(true);
                Toast.makeText(DeliveryReportActivity.this, "Image upload failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}