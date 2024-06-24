package com.example.alrizq.forgetpassword;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alrizq.R;
import com.example.alrizq.databinding.ActivityDonationDetailBinding;
import com.example.alrizq.databinding.ActivityForgetPasswordBinding;

public class ForgetPasswordActivity extends AppCompatActivity {

    ActivityForgetPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.back.setOnClickListener(view -> {
            finish();
        });
    }
}