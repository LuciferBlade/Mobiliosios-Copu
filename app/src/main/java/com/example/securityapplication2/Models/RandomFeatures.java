package com.example.securityapplication2.Models;

import com.example.securityapplication2.R;

import java.util.Random;

public class RandomFeatures {

    public int randomColorNumber(){
        return new Random().nextInt(256);
    }
    public int[] getCats() {
        return new int[]{
                R.drawable.cat1,
                R.drawable.cat2,
                R.drawable.cat3,
                R.drawable.cat4,
                R.drawable.cat5,
                R.drawable.cat6,
                R.drawable.cat7,
                R.drawable.cat8,
        };
    }

    public int getSound() {
        return R.raw.tomcat;
    }
}
