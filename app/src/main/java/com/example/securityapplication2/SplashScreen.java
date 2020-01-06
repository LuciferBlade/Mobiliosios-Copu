package com.example.securityapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        int uni = 0x1F6E1;

        String emoji = getEmoji(uni);
        final TextView temp = findViewById(R.id.textView);
        temp.setText(emoji + " Apsaugos sistema " + emoji);

        int delaySeconds = 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }, delaySeconds * 1000);
    }

    public String getEmoji(int uni){
        return new String(Character.toChars(uni));
    }
}
