package com.ananta.fieldAgent.Activity.fieldAgent;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

import com.ananta.fieldAgent.databinding.ActivityAddRequestBinding;

import java.util.ArrayList;

public class AddRequestActivity extends AppCompatActivity {

    ActivityAddRequestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRequestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Surface Solar Pump");
        categories.add("Solar Pump");
        categories.add("Surface Solar Pump");
        categories.add("Solar Pump");
        categories.add("Surface Solar");
        categories.add("Solar Pump");

        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spSpinner.setAdapter(dataAdapter);

        binding.ivBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}