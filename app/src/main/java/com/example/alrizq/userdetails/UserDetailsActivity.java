package com.example.alrizq.userdetails;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alrizq.databinding.ActivityUsedDetailsBinding;
import com.example.alrizq.fcm.Fcm;
import com.example.alrizq.login.User;
import com.example.alrizq.utils.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class UserDetailsActivity extends AppCompatActivity {

    ActivityUsedDetailsBinding binding;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsedDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("request");

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        binding.userName.setText(user.getName());
        binding.email.setText(String.format("Email: %s", user.getEmail()));
        binding.phone.setText(String.format("Phone: %s", user.getPhone()));
        binding.adress.setText(String.format("Address: %s", user.getAddress()));
        binding.aboutData.setText(user.getAbout());


        binding.hire.setOnClickListener(view -> {
            reference.child(Constant.donationID).child("status").setValue("Request Sent");
            reference.child(Constant.donationID).child("requestUId").setValue(user.getId());
            sendNotification();
        });

        binding.cancel.setOnClickListener(view -> {
            finish();
        });


    }

    private void sendNotification() {
        try {
            JSONArray tokens = new JSONArray();

            tokens.put(user.getFcmToken());

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put("message", Constant.userName + " sent you request for Delivery");
            data.put("title", "Delivery Request!");
            data.put(Constant.REMOTE_MSG_CONTENT_TYPE, "request");
            body.put(Constant.REMOTE_MSG_DATA, data);
            body.put(Constant.REMOTE_MSG_REGISTRATION_IDS, tokens);
            Log.d("fcmData", body.toString());
            Fcm.sendMessage(body.toString());

            binding.progress.getRoot().setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}