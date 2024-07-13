package com.ananta.fieldAgent.Activity.fieldAgent;

import static android.provider.MediaStore.Images.Media.getBitmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.ananta.fieldAgent.Models.AddServiceModel;
import com.ananta.fieldAgent.Parser.ApiClient;
import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityAddRequestBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRequestActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int GALLERY = 101;
    private static final int CAMERA = 102;
    ActivityAddRequestBinding binding;
    String path = "";
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRequestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        clickListener();
        binding.tvFarmerName.setText(Const.AGENT_NAME);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Select type");
        categories.add("Service Request");
        categories.add("Insurance Claim");

        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spSpinner.setAdapter(dataAdapter);

        binding.ivBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ArrayList<String> request = new ArrayList<>();
        request.add("Select request");
        request.add("Pump not working");
        request.add("Water flow id low");
        request.add("Other");

        ArrayAdapter reqAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, request);
        reqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spSpinnerRequest.setAdapter(reqAdapter);

        binding.spSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    binding.llInsauranceClaimLayout.setVisibility(View.GONE);

                } else if (position == 1) {
                    binding.tvErrorText.setVisibility(View.GONE);
                    binding.llServiceRequestLayout.setVisibility(View.VISIBLE);
                    binding.llInsauranceClaimLayout.setVisibility(View.GONE);

                } else if (position == 2) {
                    binding.tvErrorText.setVisibility(View.GONE);
                    binding.llInsauranceClaimLayout.setVisibility(View.VISIBLE);
                    binding.llServiceRequestLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void clickListener() {

        binding.ivReqCamera.setOnClickListener(this::onClick);
        binding.llAddReqBtn.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.llAddReqBtn) {
            if (validation()){
                getAddRequestData();
            }else {
                Toast.makeText(this, "Please filled all field and try again", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.ivReqCamera) {
            showPictureDialog();
        }

    }

    public boolean validation(){
        boolean isvalid = true;
        if (binding.edReqDescription.getText().toString().isEmpty()){
            isvalid = false;
            binding.edReqDescription.setError("Please Enter Description");
        }else if (binding.spSpinner.getSelectedItem().equals("Select type")){
            isvalid = false;
            binding.tvErrorText.setVisibility(View.VISIBLE);
        }else if (binding.spSpinnerRequest.getSelectedItem().equals("Select request")){
            isvalid = false;
            binding.tvReqErrorText.setVisibility(View.VISIBLE);
        }else if (binding.edRequestDate.getText().toString().isEmpty()){
            isvalid = false;
            binding.tvDateErrorText.setVisibility(View.VISIBLE);
        }
        return isvalid;
    }


    public void getAddRequestData() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("request_type", binding.spSpinner.getSelectedItem().toString());
        hashMap.put("service_request", binding.spSpinnerRequest.getSelectedItem().toString());
        hashMap.put("description", binding.edReqDescription.getText().toString());
        hashMap.put("image_name", path);
        hashMap.put("agent_id",Const.AGENT_ID);
        hashMap.put("incident_date", binding.edRequestDate.getText().toString());

        Call<AddServiceModel> call = apiInterface.getAddServiceRequest(hashMap);
        call.enqueue(new Callback<AddServiceModel>() {
            @Override
            public void onResponse(Call<AddServiceModel> call, Response<AddServiceModel> response) {

                if (response.body() != null) {
                    if (response.body().getSuccess().equals("true")) {
                        Toast.makeText(AddRequestActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddRequestActivity.this, MainActivity.class);
                        startActivity(intent);

                    }else {
                        Toast.makeText(AddRequestActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AddRequestActivity.this, "Data not Found...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddServiceModel> call, Throwable t) {
                Toast.makeText(AddRequestActivity.this, "Data not Found...", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera"};
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
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(galleryIntent, GALLERY);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY);
    }

    private void takePhotoFromCamera() {
//        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, CAMERA);
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
                path = String.valueOf(contentURI);
                Log.d("path===>", "=1=" + path);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                    path = saveImage(bitmap);
                    Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    binding.ivRequestPhoto.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if (requestCode == CAMERA) {
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

}