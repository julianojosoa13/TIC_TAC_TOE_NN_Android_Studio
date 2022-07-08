package com.example.supermorpion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.supermorpion.view.GameView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int taille = getIntent().getExtras().getInt("taille");
        GameView gV = (GameView) findViewById(R.id.gameView);
        gV.setNbr_square(taille);
    }
}