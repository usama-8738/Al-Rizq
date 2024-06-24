package com.example.alrizq.contactus;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alrizq.databinding.ActivityContactBinding;

public class ContactActivity extends AppCompatActivity {

    ActivityContactBinding binding;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.facebook.setOnClickListener(view -> {
            startActivity(getOpenFacebookIntent());
        });


        binding.whatsapp.setOnClickListener(view -> {
            String mobileNumber = "Enter Your WhatsApp Number";
            String message = "Your message here";

            boolean installed = appInstalledOrNot();
            if (installed) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + "+92" + mobileNumber + "&text=" + message));
                startActivity(intent);
            } else {
                Toast.makeText(ContactActivity.this, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
            }
        });


        binding.instagram.setOnClickListener(view -> {

            String name = "Enter Your Instagram Name";
            Uri uri = Uri.parse("http://instagram.com/" + name);
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/" + name)));
            }
        });

        binding.phone.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:03xxxxxxxx"));
            startActivity(intent);
        });
    }

    private Intent getOpenFacebookIntent() {

        String name = "Enter Your Facebook Name";
        try {
            getPackageManager().getPackageInfo("com.facebook", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + name));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + name));
        }
    }

    //Create method appInstalledOrNot
    private boolean appInstalledOrNot() {
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

}