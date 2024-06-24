package com.example.alrizq.donationdetail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.alrizq.R;
import com.example.alrizq.databinding.ActivityDonationDetailBinding;
import com.example.alrizq.editprofile.UserAddress;
import com.example.alrizq.fcm.Fcm;
import com.example.alrizq.login.User;
import com.example.alrizq.ui.ngo.request.RequestModel;
import com.example.alrizq.ui.ngo.requesthistory.YourRequestModel;
import com.example.alrizq.ui.yourdonation.yourDonationPojo;
import com.example.alrizq.utils.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DonationDetailActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    ActivityDonationDetailBinding binding;
    RequestModel model;
    yourDonationPojo donation;
    User user;
    YourRequestModel requestModel;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonationDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Intent i = getIntent();

        int activity = i.getIntExtra("activity", 0);


        binding.back.setOnClickListener(view -> {
            finish();
        });


        if (activity == 1) {
            donation = (yourDonationPojo) i.getSerializableExtra("donation");
            binding.progress.getRoot().setVisibility(View.VISIBLE);
            Query query = databaseReference.child("user").orderByChild("id").equalTo(donation.getuId());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        user = snapshot.getValue(User.class);
                        location = user.getLocation();
                    }
                    binding.progress.getRoot().setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    binding.progress.getRoot().setVisibility(View.GONE);
                    Toast.makeText(DonationDetailActivity.this, R.string.bad_response, Toast.LENGTH_SHORT).show();
                }
            });

            binding.itemName.setText(donation.getItemName());
            binding.quantity.append(donation.getQuantity());
            binding.date.append(donation.getTimeOfPreparation());
            binding.location.append(donation.getAddress());
            binding.descriptionData.setVisibility(View.GONE);
            binding.description.setVisibility(View.GONE);

            Glide.with(this)
                    .load(donation.getImage())
                    .placeholder(R.drawable.placeholder)
                    .into(binding.foodImage);


            binding.latLng.setVisibility(View.VISIBLE);
            binding.latLng.setOnClickListener(view -> {
                if (TextUtils.isEmpty(location)) {
                    Toast.makeText(this, "No user location found", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, UserAddress.class);
                    intent.putExtra("location", location);
                    startActivity(intent);
                }
            });

            binding.cancel.setOnClickListener(view -> {
                finish();
            });

            binding.confirm.setOnClickListener(view -> {
                binding.progress.getRoot().setVisibility(View.VISIBLE);
                this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                databaseReference = FirebaseDatabase.getInstance().getReference().child("donation").child(donation.getId());

                databaseReference.child("status").setValue("Accepted").addOnSuccessListener(unused -> {
                    databaseReference.child("acceptor").setValue(Constant.userId).addOnSuccessListener(unused1 -> {
                        Toast.makeText(DonationDetailActivity.this, "Request confirmed", Toast.LENGTH_SHORT).show();
                        sendNotification();
                        binding.progress.getRoot().setVisibility(View.GONE);
                    }).addOnFailureListener(e -> {
                        Toast.makeText(DonationDetailActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        binding.progress.getRoot().setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    });
                });
            });

        } else if (activity == 2) {

            //Your donations res,donor

            donation = (yourDonationPojo) i.getSerializableExtra("donation");

            binding.itemName.setText(donation.getItemName());
            binding.quantity.append(donation.getQuantity());
            binding.date.append(donation.getTimeOfPreparation());
            binding.location.append(donation.getAddress());
            if (Constant.userRole.equals("res")) {
                binding.date.setText(String.format("Preparation Time: %s", donation.getTimeOfPreparation()));
            }

            binding.descriptionData.setVisibility(View.GONE);
            binding.description.setVisibility(View.GONE);
            binding.confirm.setVisibility(View.GONE);
            binding.cancel.setVisibility(View.GONE);

            Glide.with(this)
                    .load(donation.getImage())
                    .placeholder(R.drawable.placeholder)
                    .into(binding.foodImage);

        } else if (activity == 3) {

            requestModel = (YourRequestModel) i.getSerializableExtra("donation");

            binding.itemName.setVisibility(View.GONE);
            binding.quantity.append(requestModel.getQuantity());
            binding.date.setText("Need Before: " + requestModel.getNeedBefore());
            binding.location.append(requestModel.getAddress());
            binding.descriptionData.setText(requestModel.getDescription());

            binding.confirm.setVisibility(View.GONE);
            binding.cancel.setVisibility(View.GONE);

            Glide.with(this)
                    .load("")
                    .placeholder(R.drawable.rizq)
                    .into(binding.foodImage);

        } else if (activity == 4) {

            model = (RequestModel) i.getSerializableExtra("donation");

            binding.progress.getRoot().setVisibility(View.VISIBLE);

            Query query = databaseReference.child("user").orderByChild("id").equalTo(model.getuId());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        user = snapshot.getValue(User.class);
                        location = user.getLocation();
                        binding.phone.setText(String.format("Phone: %s", user.getPhone()));
                        binding.phone.setVisibility(View.VISIBLE);
                        binding.latLng.setVisibility(View.VISIBLE);
                    }
                    binding.progress.getRoot().setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(DonationDetailActivity.this, R.string.bad_response, Toast.LENGTH_SHORT).show();
                    binding.progress.getRoot().setVisibility(View.GONE);
                }
            });

            binding.latLng.setOnClickListener(view -> {
                if (TextUtils.isEmpty(location)) {
                    Toast.makeText(this, "No user location found", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, UserAddress.class);
                    intent.putExtra("location", location);
                    startActivity(intent);
                }
            });

            binding.itemName.setText(model.getNgoName());
            binding.quantity.append(model.getQuantity());
            binding.date.append(model.getNeedBefore());
            binding.location.append(model.getAddress());
            binding.descriptionData.setText(model.getDescription());

            binding.confirm.setVisibility(View.GONE);
            binding.cancel.setVisibility(View.GONE);

        } else {

            model = (RequestModel) i.getSerializableExtra("donation");

            binding.progress.getRoot().setVisibility(View.VISIBLE);

            Query query = databaseReference.child("user").orderByChild("id").equalTo(model.getuId());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        user = snapshot.getValue(User.class);
                    }
                    binding.progress.getRoot().setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(DonationDetailActivity.this, R.string.bad_response, Toast.LENGTH_SHORT).show();
                    binding.progress.getRoot().setVisibility(View.GONE);
                }
            });

            binding.itemName.setText(model.getNgoName());
            binding.quantity.append(model.getQuantity());
            binding.date.append(model.getNeedBefore());
            binding.location.append(model.getAddress());
            binding.descriptionData.setText(model.getDescription());

            binding.cancel.setOnClickListener(view -> {
                finish();
            });

            binding.confirm.setOnClickListener(view -> {

                confirmDonation();
            });
        }

    }

    private void confirmDonation() {
        binding.progress.getRoot().setVisibility(View.VISIBLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("request").child(model.getId());

        databaseReference.child("status").setValue("Accepted").addOnSuccessListener(unused -> {
            databaseReference.child("acceptor").setValue(Constant.userId).addOnSuccessListener(unused1 -> {
                Toast.makeText(DonationDetailActivity.this, "Request confirmed", Toast.LENGTH_SHORT).show();
                sendNotification();
            }).addOnFailureListener(e -> {
                Toast.makeText(DonationDetailActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                binding.progress.getRoot().setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            });
        });

    }

    private void sendNotification() {
        try {
            JSONArray tokens = new JSONArray();
            tokens.put(user.getFcmToken());

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put("message", "Your Request for donation has been accepted by " + Constant.userName);
            data.put("title", "Request Accepted!");
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