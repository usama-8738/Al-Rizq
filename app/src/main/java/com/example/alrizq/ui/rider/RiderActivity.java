package com.example.alrizq.ui.rider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alrizq.databinding.ActivityRiderBinding;
import com.example.alrizq.ui.rider.riderrequest.RiderRequestActivity;

public class RiderActivity extends Fragment {
    ActivityRiderBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = ActivityRiderBinding.inflate(inflater, container, false);

        binding.request.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), RiderRequestActivity.class);
            startActivity(intent);
        });

        binding.cancelRequest.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), RiderRequestActivity.class);
            intent.putExtra("activity", 1);
            startActivity(intent);
        });

        return binding.getRoot();

    }
}