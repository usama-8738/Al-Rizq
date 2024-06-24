package com.example.alrizq.ui.ngo.requesthistory;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.alrizq.databinding.ActivityYorRequestBinding;
import com.example.alrizq.donationdetail.DonationDetailActivity;
import com.example.alrizq.ui.admin.UsersList;
import com.example.alrizq.utils.Constant;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class YourRequestActivity extends AppCompatActivity implements YourRequestAdap.HireClick, YourRequestAdap.DeliveredClick, YourRequestAdap.CardClick {
    List<String> yourRequest;
    ActivityYorRequestBinding binding;
    YourRequestAdap yourRequestAdap;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityYorRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        yourRequest = new ArrayList<>();

        YourRequestFunc();
        binding.back.setOnClickListener(view -> {
            finish();
        });

    }

    private void YourRequestFunc() {
        LinearLayoutManager
                lm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false); // last argument (true) is flag for reverse layout
        binding.yourRequestRecycle.setLayoutManager(lm);

        query = FirebaseDatabase.getInstance().getReference("request").orderByChild("uId").equalTo(Constant.userId);

        FirebaseRecyclerOptions<YourRequestModel> Options =
                new FirebaseRecyclerOptions.Builder<YourRequestModel>()
                        .setQuery(query, YourRequestModel.class)
                        .build();
        yourRequestAdap = new YourRequestAdap(Options, this, this, this);
        binding.yourRequestRecycle.setAdapter(yourRequestAdap);

    }

    @Override
    protected void onStart() {
        super.onStart();
        yourRequestAdap.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        yourRequestAdap.stopListening();
    }


    @Override
    public void hireListener(YourRequestModel model) {

        Constant.selectedRole = "ridr";
        Constant.activity = 1;
        Constant.donationID = model.getId();
        Intent intent = new Intent(YourRequestActivity.this, UsersList.class);
        intent.putExtra("donation", model);
        startActivity(intent);

    }

    @Override
    public void deliveredListener(YourRequestModel model) {

    }

    @Override
    public void cardListener(YourRequestModel model) {
        Intent intent = new Intent(YourRequestActivity.this, DonationDetailActivity.class);
        intent.putExtra("donation", model);
        intent.putExtra("activity", 3);
        startActivity(intent);
    }
}