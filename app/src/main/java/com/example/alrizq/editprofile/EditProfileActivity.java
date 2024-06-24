package com.example.alrizq.editprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alrizq.databinding.ActivityEditProfileBinding;
import com.example.alrizq.utils.Constant;
import com.example.alrizq.utils.Validation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getFunc();

        binding.update.setOnClickListener(view -> {

            UpdateFunc();
        });

        binding.back.setOnClickListener(view -> {
            finish();
        });


        binding.latLng.setOnClickListener(view -> {

            Intent in = new Intent(EditProfileActivity.this, UserAddress.class);
            startActivityForResult(in, 1);

        });
    }

    private void getFunc() {
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(Constant.userId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.name.getEditText().setText(snapshot.child("name").getValue(String.class));
                binding.address.getEditText().setText(snapshot.child("address").getValue(String.class));
                binding.email.getEditText().setText(snapshot.child("email").getValue(String.class));
                binding.location.getEditText().setText(snapshot.child("location").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void UpdateFunc() {
        String name = binding.name.getEditText().getText().toString().trim();
        String address = binding.address.getEditText().getText().toString().trim();
        String email = binding.email.getEditText().getText().toString().trim();
        String location = binding.location.getEditText().getText().toString().trim();

        if (Validation.name(name, binding.name) & Validation.itemName(address, binding.address)
                & Validation.email(email, binding.email) & Validation.itemName(location, binding.location)) {
            binding.progress.getRoot().setVisibility(View.VISIBLE);

            databaseReference = FirebaseDatabase.getInstance().getReference("user").child(Constant.userId);
            databaseReference.child("name").setValue(name).addOnSuccessListener(unused -> {

                databaseReference.child("address").setValue(address);
                databaseReference.child("email").setValue(email);
                databaseReference.child("location").setValue(location);
                binding.progress.getRoot().setVisibility(View.GONE);
                finish();

            }).addOnFailureListener(e -> {

                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            });


        }


    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                binding.location.getEditText().setText(data.getStringExtra("latlng"));
                String latlng = data.getStringExtra("myadress");
                Toast.makeText(getApplicationContext(), "" + latlng, Toast.LENGTH_SHORT).show();

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}