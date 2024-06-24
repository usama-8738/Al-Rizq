package com.example.alrizq.about;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alrizq.databinding.ActivityAboutBinding;
import com.example.alrizq.utils.Constant;
import com.example.alrizq.utils.Validation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AboutActivity extends AppCompatActivity {
    ActivityAboutBinding binding;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent i = getIntent();
        String value = i.getStringExtra("editext");

        binding.back.setOnClickListener(view -> {
            finish();
        });

        if (value.equals("1")) {
            binding.description.setVisibility(View.VISIBLE);
            binding.update.setVisibility(View.VISIBLE);
            binding.about.setVisibility(View.GONE);
        } else {
            binding.description.setVisibility(View.GONE);
            binding.update.setVisibility(View.GONE);
            binding.about.setVisibility(View.VISIBLE);
        }
        binding.description.getEditText().setText(Constant.userAbout);

        binding.update.setOnClickListener(view -> {

            String description = binding.description.getEditText().getText().toString().trim();
            if (Validation.itemName(description, binding.description)) {
                binding.progress.getRoot().setVisibility(View.VISIBLE);
                databaseReference = FirebaseDatabase.getInstance().getReference("user").child(Constant.userId);
                databaseReference.child("about").setValue(description).addOnSuccessListener(unused -> {

                    Constant.userAbout = description;
                    SharedPreferences preferences = getSharedPreferences("rizq", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("about", description);
                    editor.apply();

                    binding.progress.getRoot().setVisibility(View.GONE);
                    finish();

                }).addOnFailureListener(e -> {

                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                });


            }

        });

    }
}