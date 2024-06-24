package com.example.alrizq.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Validation {

    public static boolean phone(String phone, TextInputLayout Phone) {
        if (TextUtils.isEmpty(phone)) {
            Phone.setError("Please enter your phone number");
            return false;
        } else if (phone.length() < 6) {
            Phone.setError("Please enter a valid Phone Number");
            return false;
        } else {
            Phone.setError(null);
            Phone.setErrorEnabled(false);
            return true;
        }
    }

    public static boolean name(String username, TextInputLayout Name) {

        if (TextUtils.isEmpty(username)) {
            Name.setError("Please enter your user name");
            return false;
        } else if (username.length() < 5) {
            Name.setError("Please enter a valid user name");
            return false;
        } else {
            Name.setError(null);
            Name.setErrorEnabled(false);
            return true;
        }
    }

    public static boolean email(String email, TextInputLayout Email) {

        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if (TextUtils.isEmpty(email)) {
            Email.setError("Please enter your email");
            return false;
        } else if (!pattern.matcher(email).matches()) {
            Email.setError("Please enter a valid email");
            return false;
        } else {
            Email.setError(null);
            Email.setErrorEnabled(false);
            return true;
        }
    }

    public static boolean password(String password, TextInputLayout Pass) {
        if (TextUtils.isEmpty(password)) {
            Pass.setError("Please enter your password");
            Pass.setErrorIconDrawable(null);
            return false;
        } else if (password.length() < 8) {
            Pass.setError("Please enter a valid password");
            Pass.setErrorIconDrawable(null);
            return false;
        } else {
            Pass.setError(null);
            Pass.setErrorEnabled(false);
            return true;
        }
    }

    public static boolean role(String selectedRole, Context context) {
        if (TextUtils.isEmpty(selectedRole)) {
            Toast.makeText(context, "Please select a user role", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public static boolean itemName(String itemName, TextInputLayout name) {
        if (itemName.isEmpty()) {
            name.setError("Please enter " + name.getEditText().getHint());
            return false;
        } else {
            name.setError(null);
            // binding.itemName.setErrorEnabled(false);
            return true;
        }
    }
}
