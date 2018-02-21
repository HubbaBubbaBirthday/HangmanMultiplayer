package com.blitz.hangmanmultiplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class EnterMovieName extends AppCompatActivity implements OnClickListener{

    EditText movieName;
    TextView failedText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_movie_name);

        movieName = findViewById(R.id.enter_movie);

    }

    @Override
    public void onClick(View view) {
        Intent guess_movie = new Intent( this, GuessMovie.class );
        guess_movie.putExtra("Movie_Name", movieName.getText().toString());
        startActivity(guess_movie);
    }
}
