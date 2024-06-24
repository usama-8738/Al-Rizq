package com.example.alrizq.ui.yourdonation;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.alrizq.databinding.ActivityYourDonationBinding;
import com.example.alrizq.donationdetail.DonationDetailActivity;
import com.example.alrizq.utils.Constant;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class YourDonationActivity extends AppCompatActivity implements YourDonationAdap.CardClick {

    ActivityYourDonationBinding binding;
    List<String> yourDonation;
    YourDonationAdap yourDonationAdap;
    Query query;
    int activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityYourDonationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        yourDonation = new ArrayList<>();

        Intent intent = getIntent();
        activity = intent.getIntExtra("activity", 0);

        binding.back.setOnClickListener(view -> {
            finish();
        });

        YourDonationFunc();
    }

    private void YourDonationFunc() {
        LinearLayoutManager
                lm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false); // last argument (true) is flag for reverse layout
        binding.yourDonationRecycle.setLayoutManager(lm);

        query = FirebaseDatabase.getInstance().getReference("donation").orderByChild("uId").equalTo(Constant.userId);

        FirebaseRecyclerOptions<yourDonationPojo> Options =
                new FirebaseRecyclerOptions.Builder<yourDonationPojo>()
                        .setQuery(query, yourDonationPojo.class)
                        .build();
        yourDonationAdap = new YourDonationAdap(Options, this, activity, this);
        binding.yourDonationRecycle.setAdapter(yourDonationAdap);

    }

    @Override
    protected void onStart() {
        super.onStart();
        yourDonationAdap.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        yourDonationAdap.stopListening();
    }


    @Override
    public void clickListener(yourDonationPojo model) {
        Intent intent = new Intent(YourDonationActivity.this, DonationDetailActivity.class);
        intent.putExtra("donation", model);
        intent.putExtra("activity", 2);
        startActivity(intent);
    }
}

