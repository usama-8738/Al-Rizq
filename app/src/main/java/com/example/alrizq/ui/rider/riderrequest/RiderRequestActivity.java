package com.example.alrizq.ui.rider.riderrequest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.alrizq.databinding.ActivityRiderRequestBinding;
import com.example.alrizq.donationdetail.DonationDetailActivity;
import com.example.alrizq.fcm.Fcm;
import com.example.alrizq.login.User;
import com.example.alrizq.ui.ngo.request.RequestModel;
import com.example.alrizq.utils.Constant;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RiderRequestActivity extends AppCompatActivity implements RiderRequestAdapter.AcceptClick, RiderRequestAdapter.RefuseClick, RiderRequestAdapter.DeleteClick, RiderRequestAdapter.CardClick {
    ActivityRiderRequestBinding binding;
    RiderRequestAdapter adapter;
    DatabaseReference reference;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRiderRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.deliveryRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();
        int activity = intent.getIntExtra("activity", 0);

        reference = FirebaseDatabase.getInstance().getReference().child("request");
        Query query = reference.orderByChild("requestUId").equalTo(Constant.userId);
        binding.deliveryRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        FirebaseRecyclerOptions<RequestModel> options
                = new FirebaseRecyclerOptions.Builder<RequestModel>()
                .setQuery(query, RequestModel.class)
                .build();

        adapter = new RiderRequestAdapter(options, activity, getApplicationContext(), this, this, this, this);
        binding.deliveryRecycle.setAdapter(adapter);



        binding.back.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    protected void onPause() {
        super.onPause();
        adapter.stopListening();
    }

    @Override
    public void acceptListener(RequestModel model) {
        binding.progress.getRoot().setVisibility(View.VISIBLE);

        reference.child(model.getId()).child("status").setValue("Request Accepted");

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("user");

        Query query = reference1.orderByChild("id").equalTo(Constant.userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        user = snapshot.getValue(User.class);
                        sendNotification("accepted");
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void refuseListener(RequestModel model) {
        binding.progress.getRoot().setVisibility(View.VISIBLE);

        reference.child(model.getId()).child("status").setValue("Request Refused");
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("user");

        Query query = reference1.orderByChild("id").equalTo(Constant.userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        user = dataSnapshot.getValue(User.class);
                        sendNotification("refused");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RiderRequestActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendNotification(String s) {
        try {
            JSONArray tokens = new JSONArray();

            tokens.put(user.getFcmToken());

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put("message", Constant.userName + s + "you request for Delivery");
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

    @Override
    public void deleteListener(RequestModel model) {
        binding.progress.getRoot().setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("request");
        databaseReference.child(model.getId()).child("requestUId").setValue("");
        databaseReference.child(model.getId()).child("status").setValue("Request Refused");

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("user");

        Query query = reference1.orderByChild("id").equalTo(Constant.userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        user = dataSnapshot.getValue(User.class);
                        sendNotification("refused");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RiderRequestActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        sendNotification("refused");
    }

    @Override
    public void clickListener(RequestModel model) {
        Intent intent = new Intent(RiderRequestActivity.this, DonationDetailActivity.class);
        intent.putExtra("activity", 4);
        intent.putExtra("donation", model);
        startActivity(intent);
    }
}