package com.example.alrizq;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.alrizq.databinding.ActivityBottomNavigationBinding;
import com.example.alrizq.utils.Constant;


public class BottomNavigationActivity extends AppCompatActivity {

    String role;
    ActivityBottomNavigationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBottomNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        role = Constant.userRole;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home,
//                R.id.navigation_profile,
//                R.id.navigation_notifications)
//                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity);
        NavInflater inflater = navController.getNavInflater();
        NavGraph graph;

        switch (role) {
            case "admn": {
                graph = inflater.inflate(R.navigation.admin_nav);
                navController.setGraph(graph);
                break;
            }
            case "donr": {
                graph = inflater.inflate(R.navigation.donor_navigation);
                navController.setGraph(graph);
                break;
            }
            case "res": {
                graph = inflater.inflate(R.navigation.restaurant_navigation);
                navController.setGraph(graph);
                break;
            }
            case "ngo":
                graph = inflater.inflate(R.navigation.mobile_navigation3);
                navController.setGraph(graph);
                break;

            case "ridr":
                graph = inflater.inflate(R.navigation.rider_navigation);
                navController.setGraph(graph);
                break;

        }

        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    public void finishHome() {
        finishAffinity();
        System.gc();
        binding = null;
    }


}