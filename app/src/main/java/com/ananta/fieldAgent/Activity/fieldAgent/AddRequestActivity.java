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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Models.AddServiceModel;
import com.ananta.fieldAgent.Models.AllFarmerModel;
import com.ananta.fieldAgent.Models.ImageModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.FileSelectionUtils;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.Parser.Utils;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityAddRequestBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
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

public class AddRequestActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int GALLERY = 101;
    private static final int CAMERA = 102;
    ActivityAddRequestBinding binding;
    String path = "", claim = "", reason = "", farmer_name="", farmer_id="" ;
    ApiInterface apiInterface;
    int photos;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ArrayList<AllFarmerModel> allFarmersList = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    private Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityAddRequestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preference = Preference.getInstance(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (preference.getFarmerName() != null){
            binding.rlFarmerName.setVisibility(View.GONE);
            binding.tvFarmerName.setVisibility(View.VISIBLE);
            binding.tvFarmerName.setText(preference.getFarmerName());
        }else {
            binding.rlFarmerName.setVisibility(View.VISIBLE);
            binding.tvFarmerName.setVisibility(View.GONE);
//            binding.tvFarmerName.setText(preference.getFarmerName());
            getAllFarmerData();
        }

        Const.AGENT_NAME = preference.getAgentName();

        loadData();

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Select type");
        categories.add("Service Request");
        categories.add("Insurance Claim");

        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spSpinner.setAdapter(dataAdapter);

        binding.spSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    binding.llInsauranceClaimLayout.setVisibility(View.GONE);

                } else if (position == 1) {
                    binding.tvErrorText.setVisibility(View.GONE);
                    binding.llServiceRequestLayout.setVisibility(View.VISIBLE);
                    binding.tvAddReqText.setText("Add Request");
                    binding.llInsauranceClaimLayout.setVisibility(View.GONE);

                } else if (position == 2) {
                    binding.tvErrorText.setVisibility(View.GONE);
                    binding.llInsauranceClaimLayout.setVisibility(View.VISIBLE);
                    binding.tvAddReqText.setText("Insaurance claim");
                    binding.llServiceRequestLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.ivBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void loadData() {

        clickListener();
        datePick();
        getInsuranceReasonData();
        getInsuranceClaim();
        getServiceRequest();

    }

    public void getInsuranceReasonData() {

        ArrayList<String> insuranceReason = new ArrayList<>();
        insuranceReason.add("Select one");
        insuranceReason.add("Strom");
        insuranceReason.add("Heavy rain");
        insuranceReason.add("Other");

        ArrayAdapter reasonAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, insuranceReason);
        reasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spSpinnerIcReason.setAdapter(reasonAdapter);

        binding.spSpinnerIcReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                reason = binding.spSpinnerIcReason.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getInsuranceClaim() {

        ArrayList<String> insuranceClaim = new ArrayList<>();
        insuranceClaim.add("Select one");
        insuranceClaim.add("Panel Broken");
        insuranceClaim.add("Motor brunt");

        ArrayAdapter claimAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, insuranceClaim);
        claimAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spSpinnerInsuranceClaim.setAdapter(claimAdapter);

        binding.spSpinnerInsuranceClaim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                claim = binding.spSpinnerInsuranceClaim.getSelectedItem().toString();

//                Toast.makeText(AddRequestActivity.this, claim, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void getServiceRequest() {

        ArrayList<String> request = new ArrayList<>();
        request.add("Select request");
        request.add("Pump not working");
        request.add("Water flow id low");
        request.add("Other");

        ArrayAdapter reqAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, request);
        reqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spSpinnerRequest.setAdapter(reqAdapter);
    }

    private void datePick() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        binding.tvRequestDate.setText(formattedDate);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void clickListener() {

        binding.ivReqCamera.setOnClickListener(this);
        binding.llAddReqBtn.setOnClickListener(this);
        binding.ivInsuranceCamera.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.llAddReqBtn) {
            if (binding.spSpinner.getSelectedItem().toString().equals("Insurance Claim")){
                if (validationIns()){
                    getAddRequestData();
                }else {
                    Toast.makeText(this, "Please filled all field and try again+ inssssssss", Toast.LENGTH_SHORT).show();
                }
            }else {
                if (validation()){
                    getAddRequestData();
                }else {
                    Toast.makeText(this, "Please filled all field and try again", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (id == R.id.ivReqCamera) {
            showPictureDialog(1);
        } else if (id == R.id.ivInsuranceCamera) {
            showPictureDialog(2);
        }
    }

    public boolean validation() {
        boolean isvalid = true;
        if (binding.spSpinner.getSelectedItem().equals("Select type")) {
            isvalid = false;
            binding.tvErrorText.setVisibility(View.VISIBLE);
        } else if (binding.spSpinnerRequest.getSelectedItem().equals("Select request")) {
            isvalid = false;
            binding.tvReqErrorText.setVisibility(View.VISIBLE);
        }else if (binding.edReqDescription.getText().toString().isEmpty()) {
            isvalid = false;
            Toast.makeText(this, "Please enter description", Toast.LENGTH_SHORT).show();
        }
        return isvalid;
    }


    public boolean validationIns() {
        boolean isvalid = true;
        if (binding.spSpinner.getSelectedItem().equals("Select type")) {
            isvalid = false;
            binding.tvErrorText.setVisibility(View.VISIBLE);
        } else if (binding.spSpinnerInsuranceClaim.getSelectedItem().equals("Select one")) {
            isvalid = false;
            Toast.makeText(this, "Please Select Insaurance Claim", Toast.LENGTH_SHORT).show();
        }else if (binding.spSpinnerIcReason.getSelectedItem().equals("Select one")) {
            isvalid = false;
            Toast.makeText(this, "Please select reason", Toast.LENGTH_SHORT).show();
        }else if (binding.edInsuranceDescription.getText().toString().isEmpty()) {
            isvalid = false;
            Toast.makeText(this, "Please enter description", Toast.LENGTH_SHORT).show();
        }
        return isvalid;
    }

    public void getAllFarmerData() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        binding.pbProgressBar.setVisibility(View.VISIBLE);

        HashMap<String, String> hashMap = new HashMap<>();

        Call<AllFarmerModel> call = apiInterface.getAllFarmerData(hashMap,"Bearer "+preference.getToken());
        call.enqueue(new Callback<AllFarmerModel>() {
            @Override
            public void onResponse(Call<AllFarmerModel> call, Response<AllFarmerModel> response) {

                if (response.isSuccessful()) {

                    binding.pbProgressBar.setVisibility(View.GONE);
                    allFarmersList.addAll(response.body().getFarmer());

                    for (int i = 0; i < allFarmersList.size(); i++) {
                        list.add(allFarmersList.get(i).getName());
                        ids.add(allFarmersList.get(i).getId());

                    }
                    setFarmerList();

                } else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(AddRequestActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AllFarmerModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(AddRequestActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                Log.d("Addrequest", "=" + t.getMessage());
            }
        });
    }

    public void setFarmerList() {

        ArrayAdapter farmSAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        farmSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spFarmerName.setAdapter(farmSAdapter);

        binding.spFarmerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                farmer_name = list.get(position);
                farmer_id = ids.get(position);

                Log.d("farmer id===>", "=list=" + farmer_name);

                Log.d("farmer id===>", "=ids=" + farmer_id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void getAddRequestData() {
       binding.pbProgressBar.setVisibility(View.VISIBLE);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("agent_id", Const.AGENT_ID);
        if (preference.getFarmerLoginId() != null){
            hashMap.put("farmer_id", preference.getFarmerLoginId());
        }else {
            hashMap.put("farmer_id", farmer_id);  // give id as per select farmer
        }
        hashMap.put("request_type", binding.spSpinner.getSelectedItem().toString());
        hashMap.put("service_request", binding.spSpinnerRequest.getSelectedItem().toString());
        if (binding.spSpinner.getSelectedItem().toString().equals("Insurance Claim")){
            hashMap.put("description", binding.edInsuranceDescription.getText().toString());
        }else {
            hashMap.put("description", binding.edReqDescription.getText().toString());
        }
        hashMap.put("image_name", path);
        hashMap.put("reason", reason);
        hashMap.put("incident_date", binding.tvRequestDate.getText().toString());
        hashMap.put("insaurance_claim", binding.spSpinnerInsuranceClaim.getSelectedItem().toString());

        Call<AddServiceModel> call = apiInterface.getAddServiceRequest(hashMap,"Bearer "+preference.getToken());
        call.enqueue(new Callback<AddServiceModel>() {
            @Override
            public void onResponse(Call<AddServiceModel> call, Response<AddServiceModel> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        binding.pbProgressBar.setVisibility(View.GONE);
                        finish();
                    } else {
                        binding.pbProgressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(AddRequestActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(AddRequestActivity.this, "Data not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddServiceModel> call, Throwable t) {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(AddRequestActivity.this, "Data not Found" + t, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showPictureDialog(int photoImage) {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery"/*"Choose photo from camera"*/};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary(photoImage);
                                break;
                            case 1:
                                takePhotoFromCamera();
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

    private void takePhotoFromCamera() {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
//                path = String.valueOf(contentURI);

                try {
                    if (photos == 1) {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        uploadImage(contentURI, 1);
                        path = String.valueOf(contentURI);
                        binding.ivRequestPhoto.setImageBitmap(bitmap);
                    } else {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        uploadImage(contentURI, 2);
                        path = String.valueOf(contentURI);
                        binding.ivInsurancePhoto.setImageBitmap(bitmap);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            binding.ivRequestPhoto.setImageBitmap(thumbnail);

            Uri tempUri = getImageUri(getApplicationContext(), thumbnail);

            path = String.valueOf(tempUri);

            saveImage(thumbnail);
            Log.d("path===>", "=2=" + path);
            Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
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


    public void uploadImage(Uri contentURI, int fromWhere) {
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

        Call<ImageModel> call = apiInterface.uploadImage(multipartBody, "profile_picture");

        final String[] imageName = {""};
        call.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                ImageModel imageModel = response.body();

                if (response.isSuccessful()) {
                    imageName[0] = imageModel.getFileUploadData().getImage_name();
                    Log.w("ImageName", imageName[0]);
                    if (fromWhere == 1) {
                        path = imageModel.getFileUploadData().getImage_name();
                    } else {
                        path = imageModel.getFileUploadData().getImage_name();
                    }
                } else {
                    Toast.makeText(AddRequestActivity.this, "image not uploaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                Toast.makeText(AddRequestActivity.this, "image not uploaded" + t, Toast.LENGTH_SHORT).show();

            }
        });
    }


}