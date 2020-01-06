package com.example.securityapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout dLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navView;
    Button button;
    int temp = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, dLayout, toolbar, R.string.open, R.string.close);
        dLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        navView = findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(this);

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //Toast.makeText(this, String.valueOf(temp), Toast.LENGTH_LONG).show();

        switch (menuItem.getItemId()) {
            case R.id.profile:
                temp++;
                Navigation.findNavController(findViewById(R.id.fragment)).navigate(R.id.to_profile);
                dLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.allLocations:
                temp++;
                Navigation.findNavController(findViewById(R.id.fragment)).navigate(R.id.to_allLocations);
                dLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.protectedLocations:
                temp++;
                Navigation.findNavController(findViewById(R.id.fragment)).navigate(R.id.to_protectedLocations);
                dLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.allGuards:
                temp++;
                Navigation.findNavController(findViewById(R.id.fragment)).navigate(R.id.to_allGuards);
                dLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.Logs:
                temp++;
                Navigation.findNavController(findViewById(R.id.fragment)).navigate(R.id.to_logs);
                dLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.logout:
                logout(findViewById(R.id.logout));

            case R.id.exit:
                finish();
                System.exit(0);

            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        NavController navCont = Navigation.findNavController(findViewById(R.id.fragment));
        if (temp > 0) {
            navCont.popBackStack();
            temp--;
        } else {
            finish();
            System.exit(0);
        }
    }

}
