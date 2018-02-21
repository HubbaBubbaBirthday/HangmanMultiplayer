package com.blitz.hangmanmultiplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GuessMovie extends AppCompatActivity {

    private ConstraintLayout constraintLayout;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_movie);

        Intent movie = getIntent();
        String movieName = movie.getStringExtra("Movie_Name");
        System.out.println(movieName);

        TextView mov = findViewById(R.id.movieToGuess);

        mov.setText(movieName);

        constraintLayout = new ConstraintLayout(this);

        for(int j=0;j<=movieName.length()- 1;j++) {

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams( 120, 120);
            Button movieChar = new Button(this);

            String movieCharVal = String.valueOf(movieName.charAt(j));
            movieChar.setLayoutParams(params);
            movieChar.setText(movieCharVal);

            //movieChar.setBackgroundColor(Color.GRAY);
            if(Character.isLetter(movieName.charAt(j)))
                movieChar.setTextColor(Color.RED);

            if( movieCharVal.equals(" ")) {
                movieChar.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            }
            movieChar.setTextSize(15);
            movieChar.setId((j+1)*100);
            movieChar.setPadding(0, 0 ,0 ,0);
            constraintLayout.addView(movieChar);
        }

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        int marginCounter  = 0;
        int splitCount = 9;
        for(int j=0;j<=movieName.length()- 1;j++) {
            View clChild = constraintLayout.getChildAt(j);
            marginCounter = j/splitCount;
            int setLeftMargin = 120 * (j - marginCounter * splitCount ) + 5;
            int setTopMargin = 50 + 110 * marginCounter;
            constraintSet.connect(clChild.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, setLeftMargin );
            constraintSet.connect(clChild.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP,  setTopMargin);
        }
        constraintSet.applyTo(constraintLayout);
        setContentView(constraintLayout);

    }
}
