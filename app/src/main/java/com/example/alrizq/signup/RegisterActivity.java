package com.example.alrizq.signup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alrizq.BottomNavigationActivity;
import com.example.alrizq.R;
import com.example.alrizq.databinding.ActivityRegisterBinding;
import com.example.alrizq.login.User;
import com.example.alrizq.utils.Constant;
import com.example.alrizq.utils.Validation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;


public class RegisterActivity extends AppCompatActivity {

    RadioButton radioButton;
    String value;
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user");

        binding.progress.getRoot().setVisibility(View.VISIBLE);
        getFcm();

        binding.radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.donor:
                    Constant.userRole = "donr";
                    break;
                case R.id.ngo:
                    Constant.userRole = "ngo";
                    break;
                case R.id.resturent:
                    Constant.userRole = "res";
                    break;
                case R.id.rider:
                    Constant.userRole = "ridr";
                    break;
                default:
                    Toast.makeText(this, "Invalid choice", Toast.LENGTH_SHORT).show();
                    break;
            }


        });

        binding.submit.setOnClickListener(view -> {
            String name = binding.name.getEditText().getText().toString().trim();
            String phone = binding.phone.getEditText().getText().toString().trim();
            String email = binding.email.getEditText().getText().toString().trim();
            String password = binding.password.getEditText().getText().toString().trim();
            String rePassword = binding.retypePassword.getEditText().getText().toString().trim();

            if (Validation.name(name, binding.name) & Validation.phone(phone, binding.phone)
                    & Validation.email(email, binding.email) & Validation.password(password, binding.password)
                    & Validation.role(Constant.userRole, getApplicationContext())) {
                if (!password.equals(rePassword)) {
                    binding.retypePassword.setError("Password mismatch!");
                    return;
                }

                String uId = reference.push().getKey();
                User user = new User(uId, name, "", phone, email, password, Constant.FcmToken, "", "", Constant.userRole,
                        "0", "");
                reference.child(uId).setValue(user).addOnSuccessListener(unused -> {

                    Constant.userId = uId;

                    SharedPreferences preferences = getSharedPreferences("rizq", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("uId", uId);
                    editor.putString("role", Constant.userRole);
                    editor.putString("name", name);
                    editor.putString("fcm", Constant.FcmToken);
                    editor.apply();

                    Toast.makeText(this, "Data saved Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, BottomNavigationActivity.class));
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

    }

    private void getFcm() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Error: " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Get new FCM registration token
                    Constant.FcmToken = task.getResult();
                    binding.progress.getRoot().setVisibility(View.GONE);
                });
    }
}
