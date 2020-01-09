package com.example.securityapplication2.Views.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import com.example.securityapplication2.R;
import com.example.securityapplication2.ViewModels.Activity.MainViewModel;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    MainViewModel mvm;

    Toolbar toolbar;
    DrawerLayout dLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navView;
    int temp = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mvm = new MainViewModel(this);

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

    public void logout() {
        mvm.logoutAction();
    }

    /***/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //Toast.makeText(this, String.valueOf(temp), Toast.LENGTH_LONG).show();
        temp = mvm.menuSwitching(menuItem.getItemId(), temp, MainActivity.this,
                Navigation.findNavController(findViewById(R.id.fragment)));
        dLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        temp = mvm.backButtonAction(temp, Navigation.findNavController(findViewById(R.id.fragment)),
                this);
    }

    /***/
    public void notifyOut() {
        mvm.startNewActivity(this, new LoginActivity());
    }

}
