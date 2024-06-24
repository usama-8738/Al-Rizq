package com.example.alrizq.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alrizq.BottomNavigationActivity;
import com.example.alrizq.databinding.ActivityLoginBinding;
import com.example.alrizq.forgetpassword.ForgetPasswordActivity;
import com.example.alrizq.signup.OtpActivity;
import com.example.alrizq.utils.Constant;
import com.example.alrizq.utils.Validation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {


    ActivityLoginBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user");

//        reference.push().setValue(new User("Admin", "admin@gmail.com", "123456789", "1"));
        binding.progress.getRoot().setVisibility(View.VISIBLE);
        getFcm();
        binding.register.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, OtpActivity.class));
        });

        binding.forgetpassword.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
        });

        binding.login.setOnClickListener(view -> {

            String email = binding.email.getEditText().getText().toString().trim();
            if (Validation.email(email, binding.email)) {
                binding.progress.getRoot().setVisibility(View.VISIBLE);
                Query query = reference.orderByChild("email").equalTo(email);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                User user = snapshot.getValue(User.class);
                                if (user.password.equals(binding.password.getEditText().getText().toString().trim())) {

                                    Constant.userRole = user.role;
                                    Constant.userId = user.id;
                                    Constant.userName = user.name;
                                    Constant.userEmail = user.email;

                                    reference.child(user.id).child("fcmToken").setValue(Constant.FcmToken);

                                    SharedPreferences preferences = getSharedPreferences("rizq", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("uId", user.id);
                                    editor.putString("role", user.role);
                                    editor.putString("name", user.name);
                                    editor.putString("fcm", Constant.FcmToken);
                                    editor.putString("email", user.email);
                                    editor.putString("about", user.about);
                                    editor.apply();

                                    switch (user.role) {
                                        case "admn":
                                        case "res":
                                        case "ngo":
                                        case "ridr":
                                        case "donr":
                                            Intent intent = new Intent(LoginActivity.this, BottomNavigationActivity.class);
                                            startActivity(intent);
                                            break;
                                        default:
                                            Toast.makeText(LoginActivity.this, "Invalid user", Toast.LENGTH_SHORT).show();
                                            break;

                                    }
                                    binding.progress.getRoot().setVisibility(View.GONE);
                                } else {

                                    binding.progress.getRoot().setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this, "Password is wrong", Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {

                            binding.progress.getRoot().setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        binding.progress.getRoot().setVisibility(View.GONE);
                    }
                });

            }
        });
    }

    private void getFcm() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Error: " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Get new FCM registration token
                    Constant.FcmToken = task.getResult();
                    binding.progress.getRoot().setVisibility(View.GONE);
                });
    }
}