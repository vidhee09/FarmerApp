package com.ananta.fieldAgent.Activity.fieldAgent;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ananta.fieldAgent.Parser.ApiInterface;
import com.ananta.fieldAgent.Parser.Const;
import com.ananta.fieldAgent.Parser.Preference;
import com.ananta.fieldAgent.R;
import com.ananta.fieldAgent.databinding.ActivityAgentProfileBinding;
import com.bumptech.glide.Glide;

public class AgentProfileActivity extends AppCompatActivity {

    ActivityAgentProfileBinding binding;
    Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityAgentProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preference = Preference.getInstance(AgentProfileActivity.this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.tvAgentName.setText(preference.getAgentName());
        binding.tvAgentCompanyName.setText(preference.getCompanyName());
        binding.tvAgentMobileNumber.setText(preference.getAgentNumber());

        if (preference.getProfileImage() == null || preference.getProfileImage().isEmpty()){
            binding.ivAgentProfilePhoto.setImageResource(R.drawable.ic_farmer);
        }else {
            Glide.with(AgentProfileActivity.this).load(Const.IMAGE_URL+preference.getProfileImage()).into(binding.ivAgentProfilePhoto);
        }

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