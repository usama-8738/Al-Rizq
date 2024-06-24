package com.example.alrizq.ui.aboutusshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.alrizq.databinding.ActivityAboutBinding;
import com.example.alrizq.utils.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NotificationsFragment extends Fragment {

    ActivityAboutBinding binding;
    DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = ActivityAboutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // AboutFunc();
        binding.about.setText(Constant.userAbout);
        return root;
    }

    private void AboutFunc() {

        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(Constant.userId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                binding.about.setText(snapshot.child("about").getValue(String.class));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}