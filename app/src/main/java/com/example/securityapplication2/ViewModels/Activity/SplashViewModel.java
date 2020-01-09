package com.example.securityapplication2.ViewModels.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import androidx.lifecycle.ViewModel;

import com.example.securityapplication2.Models.EmojiText;

public class SplashViewModel extends ViewModel {

    EmojiText etm;

    public SplashViewModel(Context context) {
        etm = new EmojiText(context);
    }

    public String getText() {
        return etm.getText();
    }

    public void setText(String text) {
        etm.setText(text);
    }

    public void startDelayedActivity(int delay, final Activity currentActivity, final Activity nextActivity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentActivity.startActivity(new Intent(currentActivity.getApplicationContext(), nextActivity.getClass()));
                currentActivity.finish();
            }
        }, delay * 1000);
    }
}
