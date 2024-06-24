package com.example.alrizq.ui.profile;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.alrizq.BottomNavigationActivity;
import com.example.alrizq.R;
import com.example.alrizq.about.AboutActivity;
import com.example.alrizq.databinding.FragmentProfileBinding;
import com.example.alrizq.editprofile.EditProfileActivity;
import com.example.alrizq.splash.SplashActivity;
import com.example.alrizq.termpolicy.TermPolicyActivity;
import com.example.alrizq.utils.Constant;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {
    DatabaseReference databaseReference;
    FragmentProfileBinding binding;
    TextInputLayout oldPassword, newPassword;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        binding.userName.setText(Constant.userName);

        binding.logout.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            // View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.exitalert, null);

            //    builder.setView(view);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want logout");
            final AlertDialog dialog = builder.create();
            //  dialog.setCanceledOnTouchOutside(false);

            dialog.setButton(Dialog.BUTTON_POSITIVE, getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    SharedPreferences preferences = getActivity().getSharedPreferences("rizq", Context.MODE_PRIVATE);
                    preferences.edit().clear().apply();
                    dialog.dismiss();
                    startActivity(new Intent(requireActivity(), SplashActivity.class));
                    ((BottomNavigationActivity) requireActivity()).finishHome();
                }
            });
            dialog.setButton(Dialog.BUTTON_NEGATIVE, getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);


        });

        binding.editProfile.setOnClickListener(view -> {

            startActivity(new Intent(requireActivity(), EditProfileActivity.class));
        });

        binding.about.setOnClickListener(view -> {


            Intent intent = new Intent(requireActivity(), AboutActivity.class);
            intent.putExtra("editext", "1");
            startActivity(intent);
        });

        binding.setting.setOnClickListener(view -> {


            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            view = getLayoutInflater().inflate(R.layout.updatepassword, null);


//            ImageView userbankicon = view.findViewById(R.id.myuserbankicon);
            oldPassword = view.findViewById(R.id.oldPassword);
            newPassword = view.findViewById(R.id.newPassword);
//            TextView mybankname = view.findViewById(R.id.mybankname);
            MaterialButton update = view.findViewById(R.id.update);
            builder.setView(view);
            final AlertDialog dialog = builder.create();


            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!validateoldpassword() | !validatenewpassword()) {
                        return;
                    } else {


                        oldPassword.setError(null);
                        newPassword.setError(null);

                        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(Constant.userId);
                        // databaseReference = FirebaseDatabase.getInstance().getReference("user").child("-N7gvzi4cGgQuYAw_IWJ");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String oldpss = oldPassword.getEditText().getText().toString().trim();
                                String serveroldpss = snapshot.child("password").getValue(String.class);

                                if (oldpss.equals(serveroldpss)) {
                                    databaseReference.child("password").setValue(newPassword.getEditText().getText().toString().trim());
                                    Toast.makeText(getContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getContext(), "Incorrect old password", Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }


                }


            });

            dialog.show();

        });

        binding.terms.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), TermPolicyActivity.class));
        });


        return binding.getRoot();
    }

    private boolean validatenewpassword() {
        String password = newPassword.getEditText().getText().toString().trim();

        if (password.isEmpty()) {
            newPassword.setError("Please enter your new password");
            return false;
        } else if (password.length() < 8) {
            newPassword.setError("Password should be of minimum 6 characters");
//            userPassword.getEditText().getText().clear();
            return false;
        } else {
            newPassword.setError(null);
            newPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateoldpassword() {
        String password = oldPassword.getEditText().getText().toString().trim();
        if (password.isEmpty()) {
            oldPassword.setError("Please enter your old password");
            return false;
        } else if (password.length() < 8) {
            oldPassword.setError("Password should be of minimum 6 characters");
//            userPassword.getEditText().getText().clear();
            return false;
        } else {
            oldPassword.setError(null);
            oldPassword.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}