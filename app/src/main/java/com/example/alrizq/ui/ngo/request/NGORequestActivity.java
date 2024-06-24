package com.example.alrizq.ui.ngo.request;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alrizq.databinding.ActivityNgoRequestBinding;
import com.example.alrizq.utils.Constant;
import com.example.alrizq.utils.Validation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NGORequestActivity extends AppCompatActivity {

    ActivityNgoRequestBinding binding;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNgoRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> {
            finish();
        });
        binding.needBefore.getEditText().setOnClickListener(view -> {
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(NGORequestActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                int mHour = selectedHour;
                int mMin = selectedMinute;
                String AM_PM;
                if (selectedHour < 12) {
                    AM_PM = "am";

                } else {
                    AM_PM = "pm";
                    mHour = mHour - 12;
                }

                binding.needBefore.getEditText().setText(mHour + ":" + mMin + " " + AM_PM);


            }, 12, 0, false);
            mTimePicker.show();
        });

        binding.submit.setOnClickListener(view -> {

            String needBefore = binding.needBefore.getEditText().getText().toString();
            String quantity = binding.quantity.getEditText().getText().toString();
            String address = binding.address.getEditText().getText().toString();
            String description = binding.description.getEditText().getText().toString();

            if (!Validation.itemName(needBefore, binding.needBefore) | !Validation.itemName(quantity, binding.quantity) | !Validation.itemName(address, binding.address) | !Validation.itemName(description, binding.description)) {
                return;
            } else {
                binding.progress.getRoot().setVisibility(View.VISIBLE);
                this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

                databaseReference = FirebaseDatabase.getInstance().getReference("request");
                RequestModel data = new RequestModel();
                String key = databaseReference.push().getKey();
                data.setId(key);
                data.setNgoName(Constant.userName);
                data.setNeedBefore(needBefore);
                data.setQuantity(quantity);
                data.setAddress(address);
                data.setStatus("Pending");
                data.setuId(Constant.userId);
                data.setDescription(description);

                data.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

                databaseReference.child(key).setValue(data).addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Request posted successfully", Toast.LENGTH_SHORT).show();
                    binding.progress.getRoot().setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    binding.progress.getRoot().setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                });

            }
        });
    }
}