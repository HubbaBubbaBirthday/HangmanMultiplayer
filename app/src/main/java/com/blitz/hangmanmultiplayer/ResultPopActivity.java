package com.blitz.hangmanmultiplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class ResultPopActivity extends AppCompatActivity{

    Intent moviePage;
    Intent mainMenuPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_pop);

        moviePage = new Intent(this, EnterMovieName.class);
        mainMenuPage = new Intent(this, MainActivity.class);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        TextView winLose = findViewById(R.id.win_lose_button);
        final Button playAgain = findViewById(R.id.playAgain);
        Button mainMenu = findViewById(R.id.mainMenu);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(moviePage);
            }
        });
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(mainMenuPage);
            }
        });

        getWindow().setLayout((int)(width*0.6),(int)(height * 0.2));

        Intent result = getIntent();
        if(result.getStringExtra("Result").equals("Winner" )){
               winLose.setText( R.string.Win );
               winLose.setTextSize(20);
        }
        if(result.getStringExtra("Result").equals("Loser" )){
            winLose.setText( R.string.Lose );
            winLose.setTextSize(20);
        }
    }

}
