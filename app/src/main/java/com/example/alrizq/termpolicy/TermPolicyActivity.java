package com.example.alrizq.termpolicy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.alrizq.databinding.ActivityAboutBinding;
import com.example.alrizq.databinding.ActivityTermPolicyBinding;

public class TermPolicyActivity extends AppCompatActivity {

    ActivityTermPolicyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTermPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> {
            finish();
        });
    }
}