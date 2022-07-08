package com.example.supermorpion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectionTaille extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_taille);

        Button choix3x3 = findViewById(R.id.choix3x3);
        choix3x3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(SelectionTaille.this, MainActivity.class);
                mainActivity.putExtra("taille", 3);
                startActivity(mainActivity);
            }
        });
        Button choix4x4 = findViewById(R.id.choix4x4);
        choix4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(SelectionTaille.this, MainActivity.class);
                mainActivity.putExtra("taille", 4);
                startActivity(mainActivity);
            }
        });
    }
}