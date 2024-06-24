package com.example.alrizq.ui.donor.aspay;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alrizq.databinding.ActivityDonateAsPayBinding;
import com.example.alrizq.fcm.Fcm;
import com.example.alrizq.login.User;
import com.example.alrizq.utils.Constant;
import com.example.alrizq.utils.Validation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class DonateAsPayActivity extends AppCompatActivity {
    Query query;
    ActivityDonateAsPayBinding binding;
    DatabaseReference databaseReference;
    ArrayList<User> arrayList = new ArrayList<>();
    // String ngoName, payment;
    String ngoId, ngoFcmtoken, payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonateAsPayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GetNgoFunc();

        binding.back.setOnClickListener(view -> {
            finish();
        });

        binding.submit.setOnClickListener(view -> {
            payment = binding.payment.getEditText().getText().toString();
            String ngoName = binding.ngoDropDown.getEditText().getText().toString();
            String ngoPhone = binding.phone.getEditText().getText().toString();
            String ngoAddress = binding.address.getEditText().getText().toString();
            if (!Validation.itemName(payment, binding.payment) | !Validation.itemName(ngoName, binding.ngoDropDown)) {
                return;
            } else {
                binding.progress.getRoot().setVisibility(View.VISIBLE);
                this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

                databaseReference = FirebaseDatabase.getInstance().getReference("donationAsPay");
                String id = databaseReference.push().getKey();

                DonateAsPayModel donateAsPayModel = new DonateAsPayModel(id, Constant.userId, ngoId, payment, ngoPhone, ngoAddress, Constant.userName);
                databaseReference.child(id).setValue(donateAsPayModel).addOnSuccessListener(unused -> {


                    Toast.makeText(this, "Data saved Successfully", Toast.LENGTH_SHORT).show();

                    sendNotification();
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                });

            }
        });

    }

    private void sendNotification() {
        try {
            JSONArray tokens = new JSONArray();

            tokens.put(ngoFcmtoken);

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put("message", Constant.userName + "has send you Rs." + payment);
            data.put("title", "Delivery Request!");
            data.put(Constant.REMOTE_MSG_CONTENT_TYPE, "request");
            body.put(Constant.REMOTE_MSG_DATA, data);
            body.put(Constant.REMOTE_MSG_REGISTRATION_IDS, tokens);
            Log.d("fcmData", body.toString());
            Fcm.sendMessage(body.toString());

            binding.progress.getRoot().setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void GetNgoFunc() {

        query = FirebaseDatabase.getInstance().getReference("user").orderByChild("role").equalTo("ngo");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    User ngo = data.getValue(User.class);
                    arrayList.add(ngo);

                    NgoListAdapter ngoListAdapter = new NgoListAdapter(getApplicationContext(), arrayList);
                    binding.dropDown.setAdapter(ngoListAdapter);
                    ngoListAdapter.notifyDataSetChanged();
                    ((AutoCompleteTextView) Objects.requireNonNull(binding.ngoDropDown.getEditText())).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            String ngoName = ngoListAdapter.getItem(position).getName();
                            Toast.makeText(getApplicationContext(), "" + ngoName, Toast.LENGTH_SHORT).show();
                            binding.ngoDropDown.getEditText().setText(ngoName);
                            binding.phone.getEditText().setText(ngoListAdapter.getItem(position).getPhone());
                            binding.address.getEditText().setText(ngoListAdapter.getItem(position).getAddress());
                            ngoListAdapter.getFilter().filter(null);
                            ngoId = ngoListAdapter.getItem(position).getId();
                            ngoFcmtoken = ngoListAdapter.getItem(position).getFcmToken();

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}