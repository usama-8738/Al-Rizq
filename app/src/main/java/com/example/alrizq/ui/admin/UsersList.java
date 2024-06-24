package com.example.alrizq.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.alrizq.databinding.ActivityUsersListBinding;
import com.example.alrizq.login.User;
import com.example.alrizq.ui.ngo.requesthistory.YourRequestModel;
import com.example.alrizq.userdetails.UserDetailsActivity;
import com.example.alrizq.utils.Constant;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UsersList extends AppCompatActivity implements UserListAdapter.ButtonClick, UserListAdapter.CardClick {
    ActivityUsersListBinding binding;
    UserListAdapter adapter; // Create Object of the Adapter class
    DatabaseReference reference;
    YourRequestModel requestModel;

    // Create object of the
    // Firebase Realtime Database
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reference = FirebaseDatabase.getInstance().getReference().child("user");
        Query query = reference.orderByChild("role").equalTo(Constant.selectedRole);

        binding.userRecycle.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<User> options
                = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        adapter = new UserListAdapter(options, getApplicationContext(), this, this);
        binding.userRecycle.setAdapter(adapter);
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

    @Override
    public void clickListener(String id, String status) {
        reference.child(id).child("status").setValue(status).addOnSuccessListener(unused -> {
            Toast.makeText(this, "Status updated.", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void clickListener(User model) {

        Intent intent = new Intent(UsersList.this, UserDetailsActivity.class);
        intent.putExtra("user", model);
        startActivity(intent);

    }
}