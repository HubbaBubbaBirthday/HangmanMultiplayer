package com.blitz.hangmanmultiplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class GuessMovie extends AppCompatActivity {

    public ConstraintLayout constraintLayout;
    int numberOfGuesses = 0;
    int maxGuesses = 6;
    int correctGuesses = 0;
    int uniqueChar = 0;

    Intent resultPop;

    public static int uniqueCharacters(String movie) {
        String res = "";
        for (int i = 0; i < movie.length(); i++) {
            int count = 0;
            for (int j = 0; j < res.length(); j++) {
                if (movie.charAt(i) == res.charAt(j)) {
                    count++;
                }
            }
            if (count == 0 && Character.isLetter(movie.charAt(i))) {
                res = res + movie.charAt(i);
            }
        }
        return (res.length());
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_movie);

        resultPop = new Intent( this, ResultPopActivity.class );

        Intent movie = getIntent();
        String movieName = movie.getStringExtra("Movie_Name").toLowerCase();
        ConstraintSet constraintSet = new ConstraintSet();

        // Split the Movie Into Single Characters
        // and add in Constraint layout
        constraintLayout = new ConstraintLayout(this);

        for (int j = 0; j <= movieName.length() - 1; j++) {

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(120, 120);
            Button movieChar = new Button(this);

            String movieCharVal = String.valueOf(movieName.charAt(j));

            movieChar.setLayoutParams(params);
            movieChar.setText(movieCharVal);
            movieChar.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));

            if (Character.isLetter(movieName.charAt(j)))
                movieChar.setTextColor(Color.LTGRAY);

            if (movieCharVal.equals(" ")) {
                movieChar.setVisibility(View.INVISIBLE);
                //movieChar.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            }

            movieChar.setTextSize(15);
            movieChar.setId(j);
            movieChar.setPadding(0, 0, 0, 0);
            constraintLayout.addView(movieChar);
        }

        constraintSet.clone(constraintLayout);

        int marginCounter = 0;
        int splitCount = 9;
        for (int j = 0; j <= movieName.length() - 1; j++) {
            View clChild = constraintLayout.getChildAt(j);
            marginCounter = j / splitCount;
            int setLeftMargin = 120 * (j - marginCounter * splitCount) + 5;
            int setTopMargin = 150 + 110 * marginCounter;
            constraintSet.connect(clChild.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, setLeftMargin);
            constraintSet.connect(clChild.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, setTopMargin);
        }
        constraintSet.applyTo(constraintLayout);


        // KeyBoard Display
        // and add in Constraint layout

        int initialSizeCl = constraintLayout.getChildCount();
        int visibility = 0;
        String appendString = "1";
        String fileName;

        for (char imageCounter = 97; imageCounter <= 148; imageCounter++) {

            if (imageCounter > 122) {
                visibility = 4; // inviisible, for gone this should be 8
                appendString = "2";
                fileName = String.valueOf((char) (imageCounter - 26));
            } else {
                fileName = String.valueOf(imageCounter);
            }

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(120, 120);
            ImageButton image = new ImageButton(this);
            Drawable drawable = getResources().getDrawable(getResources()
                    .getIdentifier(fileName + appendString, "drawable", getPackageName()));

            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 48, 48, true));
            image.setImageDrawable(d);
            image.setId(imageCounter);
            image.setVisibility(visibility);
            //System.out.println("Image Id: " + imageCounter );
            constraintLayout.addView(image);
            image.setOnClickListener(handleOnClick(image));
        }

        constraintSet.clone(constraintLayout);
        int finalSizeCl = constraintLayout.getChildCount();

        for (int j = 0; j < 26; j++) {
            View clChild = constraintLayout.getChildAt(initialSizeCl + j);
            marginCounter = j / splitCount;
            int setLeftMargin = 120 * (j - marginCounter * splitCount) + 5;
            int setTopMargin = 400 - 110 * marginCounter;
            constraintSet.connect(clChild.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, setLeftMargin);
            constraintSet.connect(clChild.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, setTopMargin);
        }
        constraintSet.applyTo(constraintLayout);

        for (int j = 0; j < 26; j++) {
            View clChild = constraintLayout.getChildAt(initialSizeCl + j + 26);
            marginCounter = j / splitCount;
            int setLeftMargin = 120 * (j - marginCounter * splitCount) + 5;
            int setTopMargin = 400 - 110 * marginCounter;
            constraintSet.connect(clChild.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, setLeftMargin);
            constraintSet.connect(clChild.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, setTopMargin);
        }
        constraintSet.applyTo(constraintLayout);


        // Attempts View
        // and add in Constraint layout

        ConstraintLayout.LayoutParams endParams = new ConstraintLayout.LayoutParams(500, 120);
        TextView attemptsLeft = new TextView(this);

        attemptsLeft.setLayoutParams(endParams);
        attemptsLeft.setText("Attempts Left: " + String.valueOf(maxGuesses));
        attemptsLeft.setTextSize(15);
        attemptsLeft.setId((char) 2000);

        constraintLayout.addView(attemptsLeft);
        constraintSet.clone(constraintLayout);
        constraintSet.connect(attemptsLeft.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 10);
        constraintSet.connect(attemptsLeft.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 10);
        constraintSet.applyTo(constraintLayout);


        // Result View
        // and add in Constraint layout

        ConstraintLayout.LayoutParams winParams = new ConstraintLayout.LayoutParams(800, 120);
        TextView winLose = new TextView(this);

        winLose.setLayoutParams(winParams);
        winLose.setText("WinLose");
        winLose.setTextSize(15);
        winLose.setVisibility(View.INVISIBLE);
        winLose.setId((char) 3000);

        constraintLayout.addView(winLose);
        constraintSet.clone(constraintLayout);
        constraintSet.connect(winLose.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 450);
        constraintSet.connect(winLose.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 10);
        constraintSet.applyTo(constraintLayout);

        setContentView(constraintLayout);

    }

    View.OnClickListener handleOnClick(final ImageButton button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                numberOfGuesses++;
                System.out.println("Clicked" + v.getId());
                View clickedButton = findViewById(v.getId());
                //View replaceButton = findViewById(v.getId()+26);
                clickedButton.setVisibility(View.INVISIBLE);
                //replaceButton.setVisibility(View.VISIBLE);

                Intent movie = getIntent();
                String movieName = movie.getStringExtra("Movie_Name");
                uniqueChar = uniqueCharacters(movieName);
                System.out.println("Uniq:" + uniqueChar);
                boolean valuePresent = false;
                for (int val = 0; val < movieName.length(); val++) {
                    Button newChild = (Button) constraintLayout.getChildAt(val);
                    String movieCharVal = (String) newChild.getText();

                    if (movieCharVal.equals(String.valueOf((char) v.getId()))) {
                        valuePresent = true;
                        newChild.setTextColor(Color.BLACK);
                    }
                }
                if (valuePresent)
                    correctGuesses++;

                TextView attempts = findViewById((char) 2000);
                attempts.setText("Attempts Left: " + String.valueOf(maxGuesses - numberOfGuesses + correctGuesses));

                TextView winLose = findViewById((char) 3000);

                if (correctGuesses == uniqueChar) {
                    resultPop.putExtra("Result", "Winner" );
                    startActivity(resultPop);
                }
                if (numberOfGuesses - correctGuesses >= maxGuesses) {
                    resultPop.putExtra("Result", "Loser" );
                    startActivity(resultPop);
                }
            }
        };
    }
}
