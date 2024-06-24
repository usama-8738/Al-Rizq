package com.example.alrizq.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alrizq.utils.Constant;
import com.example.alrizq.databinding.ActivityOtpBinding;

public class OtpActivity extends AppCompatActivity {

    ActivityOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.sentotp.setOnClickListener(view -> {

            String code = binding.codePicker.getSelectedCountryCodeWithPlus().toString();
            String number = binding.phonevalue.getText().toString();
            Constant.phoneNumber = code + "" + number;

            if (!TextUtils.isEmpty(number)) {
                startActivity(new Intent(OtpActivity.this, VerifyOtpActivity.class));
            } else {
                binding.phonevalue.setError("Phone number can't be empty");
            }


        });
    }
}