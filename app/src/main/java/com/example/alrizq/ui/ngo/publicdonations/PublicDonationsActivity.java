package com.example.alrizq.ui.ngo.publicdonations;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.alrizq.databinding.ActivityPublicDonationsBinding;
import com.example.alrizq.donationdetail.DonationDetailActivity;
import com.example.alrizq.ui.yourdonation.yourDonationPojo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PublicDonationsActivity extends AppCompatActivity implements PublicDonationsAdapter.DonationClick {

    ActivityPublicDonationsBinding binding;
    PublicDonationsAdapter donationAdapter;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPublicDonationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LinearLayoutManager
                lm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false); // last argument (true) is flag for reverse layout
        binding.donationRecycle.setLayoutManager(lm);

        query = FirebaseDatabase.getInstance().getReference("donation").orderByChild("status").equalTo("Pending");

        FirebaseRecyclerOptions<yourDonationPojo> Options =
                new FirebaseRecyclerOptions.Builder<yourDonationPojo>()
                        .setQuery(query, yourDonationPojo.class)
                        .build();
        donationAdapter = new PublicDonationsAdapter(Options, getApplicationContext(), this);
        binding.donationRecycle.setAdapter(donationAdapter);

        binding.back.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        donationAdapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        donationAdapter.stopListening();
    }

    @Override
    public void clickListener(yourDonationPojo model) {
        Intent intent = new Intent(this, DonationDetailActivity.class);
        intent.putExtra("donation", model);
        intent.putExtra("activity", 1);
        startActivity(intent);
    }
}

