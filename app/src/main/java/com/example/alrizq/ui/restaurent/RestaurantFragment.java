package com.example.alrizq.ui.restaurent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.alrizq.databinding.FragmentRestaurantBinding;
import com.example.alrizq.ui.restaurent.donation.DonationActivity;
import com.example.alrizq.ui.restaurent.donationrequest.DonationRequestActivity;
import com.example.alrizq.ui.yourdonation.YourDonationActivity;


public class RestaurantFragment extends Fragment {

    FragmentRestaurantBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRestaurantBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.makeDonation.setOnClickListener(view -> {

            startActivity(new Intent(getActivity(), DonationActivity.class));
        });

        binding.donateRequest.setOnClickListener(view -> {

            startActivity(new Intent(getActivity(), DonationRequestActivity.class));
        });

        binding.yourDonations.setOnClickListener(view -> {

            startActivity(new Intent(getActivity(), YourDonationActivity.class));

        });




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}