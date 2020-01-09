package com.example.securityapplication2.ViewModels.Fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;

import com.example.securityapplication2.Models.RandomFeatures;

import java.util.Random;

public class RandamoniumViewModel extends ViewModel {
    RandomFeatures randomFeatures;

    public RandamoniumViewModel(){
        randomFeatures = new RandomFeatures();
    }

    public void setBackgroundColor(View view){
        int temp = Color.rgb(randomFeatures.randomColorNumber(),
                randomFeatures.randomColorNumber(),
                randomFeatures.randomColorNumber());
        view.setBackgroundColor(temp);
        Log.d("TagColor", String.valueOf(temp));
    }

    public int getSound(){
        return randomFeatures.getSound();
    }

    public void setImage(ImageView imageView) {
        int[] cats = randomFeatures.getCats();
        imageView.setImageResource(cats[new Random().nextInt(8)]);
    }
}
