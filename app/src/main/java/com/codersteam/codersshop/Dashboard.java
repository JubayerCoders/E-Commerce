package com.codersteam.codersshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        INTERNET_CHECK();

        //============Bottom Navigation Page Switch==========//
        navigationView = findViewById(R.id.navigationView);

        replaceFragment(new Home_Fragment());


        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId() == R.id.home) {
                    replaceFragment(new Home_Fragment());

                }
                else if (item.getItemId() == R.id.cart){
                    replaceFragment(new Cart_Fragment());
                }




                return true;
            }
        });



        //============Bottom Navigation Page Switch==========//


    }

    private void jubayer (){
        Toast.makeText(Dashboard.this, "Hey this is test", Toast.LENGTH_SHORT).show();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.linearlayout, fragment);
        fragmentTransaction.commit();

    }

    public void INTERNET_CHECK(){

        //==============Net Check===============//
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();

        if (isConnected) {

            replaceFragment(new Home_Fragment());



        } else {

            // No network connection, show a message to the user
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Internet");
            builder.setMessage("Please check your internet connection and try again! ");

            // Add a button to dismiss the dialog
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (isConnected) {
                        dialog.dismiss();
                    } else INTERNET_CHECK();
                    // Dismiss the dialog when OK is clicked


                }
            });

            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();

                }
            });

            builder.setCancelable(false);
            builder.setIcon(R.drawable.img);

            // Create and show the dialog
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        //==============Net Check===============//



    }

}