package com.example.alrizq.ui.ngo.hirerider;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alrizq.databinding.ActivityHireRiderBinding;

public class HireRiderActivity extends AppCompatActivity {

    ActivityHireRiderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHireRiderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}