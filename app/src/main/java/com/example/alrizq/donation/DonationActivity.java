package com.example.alrizq.donation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.alrizq.databinding.ActivityDonationBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class DonationActivity extends AppCompatActivity {

    ActivityDonationBinding binding;
    List<String> donation;
    DonationAdapter donationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        donation = new ArrayList<>();

        DonationFunc();
    }

    private void DonationFunc() {
        LinearLayoutManager
                lm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false); // last argument (true) is flag for reverse layout
        binding.donationRecycle.setLayoutManager(lm);


        Query query = FirebaseDatabase.getInstance().getReference("donation");
        FirebaseRecyclerOptions<donationPojo> Options =
                new FirebaseRecyclerOptions.Builder<donationPojo>()
                        .setQuery(query, donationPojo.class)
                        .build();
        donationAdapter = new DonationAdapter(Options);
        binding.donationRecycle.setAdapter(donationAdapter);
        donationAdapter.startListening();
    }
}