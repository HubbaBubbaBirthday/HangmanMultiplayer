package com.blitz.hangmanmultiplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GuessMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_movie);

        Intent movie = getIntent();
        String movieName = movie.getStringExtra("Movie_Name");
        System.out.println(movieName);

        TextView mov = findViewById(R.id.textView);

        mov.setText(movieName);
    }
}
