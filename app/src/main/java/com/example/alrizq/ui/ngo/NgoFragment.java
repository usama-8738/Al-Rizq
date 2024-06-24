package com.example.alrizq.ui.ngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.alrizq.databinding.FragmentNgoBinding;
import com.example.alrizq.ui.ngo.publicdonations.PublicDonationsActivity;
import com.example.alrizq.ui.ngo.request.NGORequestActivity;
import com.example.alrizq.ui.ngo.requesthistory.YourRequestActivity;
import com.example.alrizq.utils.Constant;

public class NgoFragment extends Fragment {

    FragmentNgoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNgoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.ngoName.append(Constant.userName);

        binding.donationRequest.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), NGORequestActivity.class));
        });

        binding.donations.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), PublicDonationsActivity.class));
        });

        binding.requestHistory.setOnClickListener(view -> {

            startActivity(new Intent(getActivity(), YourRequestActivity.class));

        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}