package com.example.securityapplication2.Views.Fragments;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.securityapplication2.R;
import com.example.securityapplication2.ViewModels.Fragment.RandamoniumViewModel;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Randamonium extends Fragment {

    private RandamoniumViewModel rvm;

    private View view2;
    private ImageView imageView;
    private MediaPlayer mp;

    public Randamonium() {
        rvm = new RandamoniumViewModel();
    }

    // Toast.makeText(view.getContext(), String.valueOf(imageView.getSolidColor()), Toast.LENGTH_LONG).show();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view2 = inflater.inflate(R.layout.fragment_randamonium, container, false);

        Button button = view2.findViewById(R.id.colorButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvm.setBackgroundColor(view2.getRootView());
                if (!mp.isPlaying()) {
                    mp.start();
                }
            }
        });

        button = view2.findViewById(R.id.soundButton);
        mp = MediaPlayer.create(Objects.requireNonNull(getActivity()).getApplicationContext(), rvm.getSound());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mp.isPlaying()) {
                    mp.start();
                }
            }
        });

        button = view2.findViewById(R.id.imgButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView = Objects.requireNonNull(getView()).findViewById(R.id.picView);
                rvm.setImage(imageView);
                if (!mp.isPlaying()) {
                    mp.start();
                }
            }
        });

        return view2;
    }

    public void setToast(String message) {
        Toast.makeText(view2.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
