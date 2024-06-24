package com.example.alrizq.ui.restaurent.donation;

import android.Manifest;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.alrizq.R;
import com.example.alrizq.databinding.ActivityFoodDetailBinding;
import com.example.alrizq.utils.Constant;
import com.example.alrizq.utils.Validation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DonationActivity extends AppCompatActivity {

    ActivityFoodDetailBinding binding;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String utensil = null;
    Uri imageUri;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> {
            finish();
        });
        binding.yes.setOnClickListener(view -> {
            utensil = "yes";
        });
        binding.no.setOnClickListener(view -> {
            utensil = "no";
        });

        binding.back.setOnClickListener(view -> {
            finish();
        });


        binding.timeOPrepration.getEditText().setOnClickListener(view -> {
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(DonationActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                int mHour = selectedHour;
                int mMin = selectedMinute;
                String AM_PM;
                if (selectedHour < 12) {
                    AM_PM = "am";

                } else {
                    AM_PM = "pm";
                    mHour = mHour - 12;
                }

                binding.timeOPrepration.getEditText().setText(mHour + ":" + mMin + " " + AM_PM);


            }, 12, 0, false);
            mTimePicker.show();
        });

        binding.itemImage.setOnClickListener(view -> {
//            imagePickDialog();
            if (!storagePermission()) {
                requestStoragePermission();
            } else {
                fromStorage();
            }
        });
        binding.submit.setOnClickListener(view -> {
            String itemName = binding.itemName.getEditText().getText().toString();
            String timeOPrepration = binding.timeOPrepration.getEditText().getText().toString();
            String quantity = binding.quantity.getEditText().getText().toString();
            String address = binding.address.getEditText().getText().toString();


            if (!Validation.itemName(itemName, binding.itemName) | !Validation.itemName(timeOPrepration, binding.timeOPrepration) | !Validation.itemName(quantity, binding.quantity) | !Validation.itemName(address, binding.address)) {
                return;
            } else if (utensil.isEmpty()) {
                Toast.makeText(getApplicationContext(), "select yes or no for utensils", Toast.LENGTH_SHORT).show();
            } else if (imageUri == null) {

                Toast.makeText(getApplicationContext(), "Please select an image", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(getApplicationContext(), "yes", Toast.LENGTH_SHORT).show();
                binding.progress.getRoot().setVisibility(View.VISIBLE);
                this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                databaseReference = FirebaseDatabase.getInstance().getReference("donation");
                DonationModel data = new DonationModel();
                String id = databaseReference.push().getKey();
                storageReference = FirebaseStorage.getInstance().getReference().child("donation" + "/" + id);
                storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri uri) {

                                final String downloadUrl = uri.toString();
                                data.setImage(downloadUrl);
                                data.setItemName(itemName);
                                data.setTimeOfPreparation(timeOPrepration);
                                data.setQuantity(quantity);
                                data.setAddress(address);
                                data.setUtensil(utensil);
                                data.setStatus("Pending");
                                data.setuId(Constant.userId);
                                data.setId(id);
                                data.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
                                databaseReference.child(id).setValue(data).addOnSuccessListener(unused -> {
                                    Toast.makeText(DonationActivity.this, "Donation added successful", Toast.LENGTH_SHORT).show();
                                }).addOnFailureListener(e -> {
                                    Toast.makeText(DonationActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                                binding.progress.getRoot().setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                finish();
                            }
                        });
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                });

            }

        });


    }

    private void imagePickDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select image from");
        builder.setItems(options, (dialogInterface, i) -> {

            if (i == 0) {
                if (!cameraPermission()) {
                    requestCameraPermission();
                } else {
                    fromCamera();
                }

            } else if (i == 1) {
                if (!storagePermission()) {
                    requestStoragePermission();
                } else {
                    fromStorage();
                }
            }
        });
        builder.create().show();
    }

    private boolean cameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
    }

    private void fromCamera() {
        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE, "Image title");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "Image description");

        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    //
    private boolean storagePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);


    }

    private void fromStorage() {
        Intent gallaryIntent = new Intent(Intent.ACTION_PICK);
        gallaryIntent.setType("image/*");
        startActivityForResult(gallaryIntent, IMAGE_PICK_GALLERY_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        fromStorage();
                    } else {
                        Toast.makeText(this, "Storage permission required", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            break;

            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (accepted) {
                        fromCamera();
                    } else {
                        Toast.makeText(this, "Storage permission required", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            break;


        }


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == IMAGE_PICK_GALLERY_CODE) {

                imageUri = data.getData();

                Glide.with(DonationActivity.this)
                        .load(imageUri)
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(binding.itemImage);
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                imageUri = data.getData();
                Glide.with(DonationActivity.this)
                        .load(imageUri)
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(binding.itemImage);
//                File file = new File(FileUtil.getRealPathFromURI(getApplicationContext(), imageUriOne));
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}