package com.ananta.fieldAgent.Activity.fieldAgent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.Models.JointSurveyorModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.GpsTracker;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityJointReportBinding;
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
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JointReportActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityJointReportBinding binding;
    private static final int GALLERY = 100;
    private static final int CAMERA = 101;
    ApiInterface apiInterface;
    String signImage, path, beneficiarySignImage,Farmer_ID,pumpPath,landmarkPath,baneficiarypath,rbwaterSource,rbpumpType,rbagPump,rbshadowRadio,rbnetwork,rbGovtSolarPump,
            river,borewell, lake;
    int imagePhoto;
    double latitude, longitude;
    private FusedLocationProviderClient fusedLocationClient;
    File SignPath,beneficiarySign;
    private ArrayList<CheckBox> arrayCheckBoxWaterSource = new ArrayList<>();

    private int mYear, mMonth, mDay, mHour, mMinute;
    StringBuilder result = new StringBuilder(" ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJointReportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initView();
        setClickListener();
        clickListener();

        loadData();

        binding.rgRadioWaterSource.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb= (RadioButton) findViewById(checkedId);
                        rbwaterSource = rb.getText().toString();
                    }
                });


        binding.rgPumpType.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        rbpumpType = radioButton.getText().toString();
                    }
                });

        binding.rgAGPumpConnection.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        rbagPump = radioButton.getText().toString();
                    }
                });


        binding.rgShadowArea.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        rbshadowRadio = radioButton.getText().toString();
                    }
                });


        binding.rgNetworkAvailable.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        rbnetwork = radioButton.getText().toString();
                    }
                });

        binding.rgSolarGovtPump.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb= (RadioButton) findViewById(checkedId);
                        rbGovtSolarPump = rb.getText().toString();
                    }
                });


    }

    public void loadData() {
        Farmer_ID = getIntent().getStringExtra("FarmerPosition");
        binding.tvSurveyorNameJoint.setText(Const.AGENT_NAME);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
        datePick();
        arrayCheckBoxWaterSource.add(binding.checkboxRiver);
        arrayCheckBoxWaterSource.add(binding.checkboxBoreWell);
        arrayCheckBoxWaterSource.add(binding.checkboxLake);

        if (binding.checkboxRiver.isChecked()) {
            result.append("River");
        }
        if (binding.checkboxBoreWell.isChecked()) {
            result.append(",borewell");
        }
        if (binding.checkboxLake.isChecked()) {
            result.append(",Lake");
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
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        binding.tvSurveyorDateTime.setText(formattedDate);

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
                //Event triggered when the pad is touched
//                Toast.makeText(JointReportActivity.this, "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
//                Toast.makeText(JointReportActivity.this, "signed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
//                Toast.makeText(JointReportActivity.this, "clear", Toast.LENGTH_SHORT).show();
            }
        });

        binding.signaturePadBeneficiary.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
//                Toast.makeText(JointReportActivity.this, "onStartSigning() triggered!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
//                Toast.makeText(JointReportActivity.this, "onStartSigning() triggered!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
                Toast.makeText(JointReportActivity.this, "clear", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setClickListener() {

        binding.btnCompletedSign.setOnClickListener(v -> {
            signImage = binding.signaturePadSign.getSignatureSvg();
            signImage = BitMapToString(binding.signaturePadSign.getSignatureBitmap());
            saveBitmapIntoCacheDir(binding.signaturePadSign.getSignatureBitmap());
            Log.d("DeliveryReport===>", "===>" + signImage);
            binding.ivSignImage.setImageBitmap(binding.signaturePadSign.getSignatureBitmap());
        });

        binding.btnClearSign.setOnClickListener(v -> {
            binding.signaturePadSign.clear();
        });

        binding.btnCompletedBeneficiary.setOnClickListener(v -> {
            beneficiarySignImage = binding.signaturePadBeneficiary.getSignatureSvg();
            beneficiarySignImage = BitMapToString(binding.signaturePadBeneficiary.getSignatureBitmap());
            BitmapIntoCacheDir(binding.signaturePadBeneficiary.getSignatureBitmap());
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
    }

    private void BitmapIntoCacheDir(Bitmap signatureBitmap) {
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
    }

    private void showPictureDialog(Integer photo) {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera"};
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
                        binding.ivWaterPhoto.setImageBitmap(bitmap);
                    } else if (imagePhoto == 2) {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        landmarkPath = String.valueOf(contentURI);
                        binding.ivLandmarkPhoto.setImageBitmap(bitmap);
                    } else {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        baneficiarypath = String.valueOf(contentURI);
                        binding.ivBeneficiaryPhotoJoint.setImageBitmap(bitmap);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {

            if (imagePhoto == 1) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
                pumpPath = saveImage(thumbnail);
                binding.ivWaterPhoto.setImageBitmap(thumbnail);
//                Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                saveImage(thumbnail);
            } else if (imagePhoto == 2) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
                landmarkPath = saveImage(thumbnail);
                binding.ivLandmarkPhoto.setImageBitmap(thumbnail);
//                Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                saveImage(thumbnail);
            } else {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
                baneficiarypath = saveImage(thumbnail);
                binding.ivBeneficiaryPhotoJoint.setImageBitmap(thumbnail);
//                Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
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
                getJointReportData();
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "please fill all filled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getJointReportData() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("agent_id", Const.AGENT_ID);
        hashMap.put("farmer_id", Const.FARMER_ID);
        hashMap.put("alternet_mo", binding.edSurveyorAlternativeNumber.getText().toString());
        hashMap.put("imei_no", binding.edSurveyorIMEIId.getText().toString());
        hashMap.put("latitude", String.valueOf(latitude));
        hashMap.put("longitude", String.valueOf(longitude));
        hashMap.put("is_water_source_available", rbwaterSource);
        hashMap.put("type_of_water_source", result.toString());
        hashMap.put("water_depth", binding.edDepthWaterSource.getText().toString());
        hashMap.put("constant_water", binding.edConstantWaterLevel.getText().toString());
        hashMap.put("water_delivery_point", binding.edWaterDeliveryPoint.getText().toString());
        hashMap.put("pump_type", rbpumpType);
        hashMap.put("pump_recom_survey", "checkbox");
        hashMap.put("pump_recom_benefits", "checkbox");
        hashMap.put("is_pump_electricity", rbagPump);
        hashMap.put("is_solar_pump", rbGovtSolarPump);
        hashMap.put("is_shadow_area", rbshadowRadio);
        hashMap.put("is_mobile_network", rbnetwork);
        hashMap.put("survey_person", binding.tvSurveyorNameJoint.getText().toString());
        hashMap.put("remark", binding.edRemark.getText().toString());
        hashMap.put("water_res_image", pumpPath);
        hashMap.put("landmark_image", landmarkPath);
        hashMap.put("beneficiary_image", baneficiarypath);
        hashMap.put("beneficiary_sign", beneficiarySign.toString());
        hashMap.put("survey_sign", SignPath.toString());

        Call<JointSurveyorModel> call = apiInterface.getJointSurveyorData(hashMap);
        call.enqueue(new Callback<JointSurveyorModel>() {
            @Override
            public void onResponse(Call<JointSurveyorModel> call, Response<JointSurveyorModel> response) {

                if (response.body() != null) {
                    if (response.body().getSuccess().equals("true")) {
                        Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(JointReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JointReportActivity.this, "", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<JointSurveyorModel> call, Throwable t) {
                Toast.makeText(JointReportActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
            }
        });

    }

}