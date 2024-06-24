package com.example.alrizq.ui.donor.donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.alrizq.databinding.FragmentDonorBinding;
import com.example.alrizq.ui.donor.aspay.DonateAsPayActivity;
import com.example.alrizq.ui.donor.donations.DonorDonationActivity;
import com.example.alrizq.ui.yourdonation.YourDonationActivity;

public class DonorFragment extends Fragment {

    FragmentDonorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDonorBinding.inflate(inflater, container, false);

        binding.makeDonation.setOnClickListener(v -> {
            startActivity(new Intent(requireActivity(), DonorDonationActivity.class));
        });

        binding.yourDonations.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), YourDonationActivity.class));
        });

        binding.donateAsPay.setOnClickListener(v -> {
            startActivity(new Intent(requireActivity(), DonateAsPayActivity.class));
        });

        binding.deleteDonation.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), YourDonationActivity.class);
            intent.putExtra("activity", 1);
            startActivity(intent);
        });


        binding.donateAsPay.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), DonateAsPayActivity.class));
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}