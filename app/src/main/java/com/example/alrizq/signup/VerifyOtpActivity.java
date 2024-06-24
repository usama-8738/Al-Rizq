package com.example.alrizq.signup;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alrizq.databinding.ActivityVerifyOtpBinding;
import com.example.alrizq.utils.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VerifyOtpActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 59000;
    String verificationCodeBysystem;
    ActivityVerifyOtpBinding binding;
    String phoneNo;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBysystem = s;
            binding.verify.setEnabled(true);
            Toast.makeText(VerifyOtpActivity.this, "Code sent", Toast.LENGTH_SHORT).show();
            PhoneAuthProvider.ForceResendingToken token = forceResendingToken;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            Log.d("TAG", "onVerificationFailed" + code);
            Log.d("TAG", "onVerificationCompleted:" + phoneAuthCredential);
            binding.pinView.setText(code);
            if (code != null) {
                binding.pbar.setVisibility(View.VISIBLE);
                binding.verify.setEnabled(true);
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyOtpActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        phoneNo = Constant.phoneNumber;
        binding.numberget.setText(phoneNo);

        binding.verify.setEnabled(false);

        binding.verify.setOnClickListener(view -> {
            String code = binding.pinView.getText().toString();
            if (code.isEmpty() || code.length() < 6) {
                Toast.makeText(VerifyOtpActivity.this, "OTP Invalid", Toast.LENGTH_LONG).show();
                binding.pbar.setVisibility(View.GONE);
                return;
            }
            binding.pbar.setVisibility(View.VISIBLE);
            verifycode(code);
        });

        Starttimer();
        sendVerificationcode(phoneNo);


    }

    private void Starttimer() {
        binding.resendotp.setClickable(false);
        binding.resendotp.setFocusable(false);
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                mTimerRunning = false;

                binding.resendotp.setText("Resend OTP");
                binding.resendotp.setClickable(true);
                binding.resendotp.setFocusable(true);
                binding.resendotp.setOnClickListener(v -> {
                    final Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), "Verification code sent", Snackbar.LENGTH_LONG);
                    snackBar.setAction("Close", v1 -> {
                        snackBar.dismiss();
                    });
                    snackBar.show();
                    sendVerificationcode(phoneNo);
                    mTimeLeftInMillis = 59000;
                    Starttimer();
                });

            }
        }.start();
        mTimerRunning = true;

    }

    private void updateCountDownText() {
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", 0, seconds);
        binding.resendotp.setText(timeLeftFormatted);

    }

    private void sendVerificationcode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(VerifyOtpActivity.this)               // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifycode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBysystem, code);
        signInTheuserbycredentials(credential);
    }

    private void signInTheuserbycredentials(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyOtpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(VerifyOtpActivity.this, RegisterActivity.class));
                            finish();
                            binding.pbar.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        } else {
                            Toast.makeText(VerifyOtpActivity.this, "OTP Invalid", Toast.LENGTH_SHORT).show();
                            binding.pbar.setVisibility(View.GONE);
                            return;
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(VerifyOtpActivity.this, OtpActivity.class));
    }


}