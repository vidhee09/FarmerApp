package com.ananta.fieldAgent.Activity.farmer;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;

import com.ananta.fieldAgent.Activity.DashboardActivity;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityAddRequestFarmerBinding;

public class AddRequestFarmerActivity extends AppCompatActivity {

    private ActivityAddRequestFarmerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityAddRequestFarmerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.edtFarmerName.setText(Const.FARMER_NAME);
        binding.llAddRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(AddRequestFarmerActivity.this, FarmerDashboardActivity.class);
                Intent intent = new Intent(AddRequestFarmerActivity.this, DashboardActivity.class);
                startActivity(intent);

            }
        });

    }

}