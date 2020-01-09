package com.example.securityapplication2.Views.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.securityapplication2.R;
import com.example.securityapplication2.ViewModels.Activity.SplashViewModel;

public class SplashScreen extends AppCompatActivity {

    SplashViewModel svm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        svm = new SplashViewModel(this);

        TextView temp = findViewById(R.id.textView);
        temp.setText(svm.getText());

        svm.startDelayedActivity(1,this, new LoginActivity());
    }
}
