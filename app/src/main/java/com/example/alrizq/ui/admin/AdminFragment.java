package com.example.alrizq.ui.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alrizq.BottomNavigationActivity;
import com.example.alrizq.contactus.ContactActivity;
import com.example.alrizq.databinding.FragmentAdminBinding;
import com.example.alrizq.splash.SplashActivity;
import com.example.alrizq.utils.Constant;

public class AdminFragment extends Fragment {
    FragmentAdminBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminBinding.inflate(inflater, container, false);

        binding.doner.setOnClickListener(view -> {
            usersList("donr");
        });

        binding.ngo.setOnClickListener(view -> {
            usersList("ngo");
        });

        binding.restaurant.setOnClickListener(view -> {
            usersList("res");
        });

        binding.rider.setOnClickListener(view -> {
            usersList("ridr");
        });

        binding.contactUs.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), ContactActivity.class));
        });

        binding.logout.setOnClickListener(view -> {
            SharedPreferences preferences = getActivity().getSharedPreferences("rizq", Context.MODE_PRIVATE);
            preferences.edit().clear().apply();
            startActivity(new Intent(requireActivity(), SplashActivity.class));
            ((BottomNavigationActivity) requireActivity()).finishHome();
        });
        return binding.getRoot();
    }

    private void usersList(String role) {
        Constant.selectedRole = role;
        startActivity(new Intent(requireActivity(), UsersList.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}