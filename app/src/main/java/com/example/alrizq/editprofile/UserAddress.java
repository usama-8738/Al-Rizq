package com.example.alrizq.editprofile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.alrizq.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.Locale;

public class UserAddress extends FragmentActivity implements OnMapReadyCallback {
    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;
    MaterialButton getlocation;
    private LocationCallback locationCallback;
    double lat, lng;
    SupportMapFragment mapFragment;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 1000;
    private long FASTEST_INTERVAL = 1000;
    String userlocation;
    double userLat, userlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        getlocation = findViewById(R.id.getlocation);

        userlocation = getIntent().getStringExtra("location");
        if (!TextUtils.isEmpty(userlocation)) {
            String strMain = userlocation;
            String[] arrSplit = strMain.split(",");
            userLat = Double.parseDouble(arrSplit[0]);
            userlng = Double.parseDouble(arrSplit[1]);
        }


        if (isLocationEnabled(UserAddress.this)) {
            setUpMap();
        } else {
            showLocationEnableDialog();
        }

        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                Geocoder geocoder = new Geocoder(UserAddress.this);
                try {
                    Address addresses = geocoder.getFromLocation(lat, lng, 1).get(0);
                    String values = addresses.getAddressLine(0); //0 to obtain first possible address
                    intent.putExtra("myadress", values);
                    intent.putExtra("latlng", lat + "," + lng);


                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        enableMyLocation();


        // Add a marker in Sydney and move the camera


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling.
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        if (!TextUtils.isEmpty(userlocation)) {
            mMap.setMyLocationEnabled(false);
            getlocation.setVisibility(View.GONE);
            mMap.addMarker(new MarkerOptions().position(new LatLng(userLat, userlng)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLat, userlng), 15), null);
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            // last location get  kr reha apni
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object


                        lat = (location.getLatitude());
                        lng = (location.getLongitude());

                        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15), null);

                        // marker pr click kr reha
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                Geocoder geocoder = new Geocoder(UserAddress.this, Locale.getDefault());
                                Address addresses = null; //1 num of possible location returned
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0);
                                    String address = addresses.getAddressLine(0); //0 to obtain first possible address

                                    //create your custom title
                                    Toast.makeText(UserAddress.this, "" + address, Toast.LENGTH_SHORT).show();
                                    //  String title = address + "-" + city + "-" + state;
                                    String title = addresses.getAddressLine(0);
                                    marker.setTitle(title);
                                    marker.showInfoWindow();
                                    return true;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    return false;
                                }
                            }
                        });


                    } else {
                        //  Toast.makeText(UserAddress.this, "hello"+location, Toast.LENGTH_SHORT).show();
                        setUpMap();
                    }
                }
            });

            // map pr click krny pr marker set ho ga
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    mMap.clear();

                    lat = (latLng.latitude);
                    lng = (latLng.longitude);
                    MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                    mMap.addMarker(markerOptions);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    // marker pr click kr reha
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            Geocoder geocoder = new Geocoder(UserAddress.this, Locale.getDefault());
                            Address addresses = null; //1 num of possible location returned
                            try {
                                addresses = geocoder.getFromLocation(lat, lng, 1).get(0);
                                String address = addresses.getAddressLine(0); //0 to obtain first possible address

                                //create your custom title
                                Toast.makeText(UserAddress.this, "" + address, Toast.LENGTH_SHORT).show();
                                //  String title = address + "-" + city + "-" + state;
                                String title = addresses.getAddressLine(0);
                                marker.setTitle(title);
                                marker.showInfoWindow();
                                return true;
                            } catch (IOException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                    });
                }
            });

        }
    }

    public void setUpMap() {

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    private boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public void showLocationEnableDialog() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10);
        mLocationRequest.setSmallestDisplacement(10);
        mLocationRequest.setFastestInterval(10);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);

        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

        task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.


                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        UserAddress.this,
                                        101);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 101:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
                        //Toast.makeText(UserCart.this, states.isLocationPresent() + "", Toast.LENGTH_SHORT).show();
                        setUpMap();

                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        //Toast.makeText(UserCart.this, "Canceled", Toast.LENGTH_SHORT).show();
                        finish();
                        Toast.makeText(this, "Please turn On GPS.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }


}