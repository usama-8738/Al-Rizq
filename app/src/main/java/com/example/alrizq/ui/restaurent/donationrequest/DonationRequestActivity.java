package com.example.alrizq.ui.restaurent.donationrequest;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.alrizq.databinding.ActivityDonationRequestBinding;
import com.example.alrizq.donationdetail.DonationDetailActivity;
import com.example.alrizq.ui.ngo.request.RequestModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DonationRequestActivity extends AppCompatActivity implements RequestAdapter.CardClick {

    ActivityDonationRequestBinding binding;
    RequestAdapter adapter; // Create Object of the Adapter class
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonationRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> {
            finish();
        });

        reference = FirebaseDatabase.getInstance().getReference().child("request");
        Query query = reference.orderByChild("status").equalTo("Pending");
        binding.requestRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        FirebaseRecyclerOptions<RequestModel> options
                = new FirebaseRecyclerOptions.Builder<RequestModel>()
                .setQuery(query, RequestModel.class)
                .build();

        adapter = new RequestAdapter(options, getApplicationContext(), this);
        binding.requestRecycle.setAdapter(adapter);
        binding.back.setOnClickListener(view -> {
            finish();
        });

    }

    @Override
    public void clickListener(RequestModel model) {
        Intent intent = new Intent(this, DonationDetailActivity.class);
        intent.putExtra("donation", model);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}