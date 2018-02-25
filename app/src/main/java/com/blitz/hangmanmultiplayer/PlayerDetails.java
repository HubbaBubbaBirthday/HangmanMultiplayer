package com.blitz.hangmanmultiplayer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class PlayerDetails extends AppCompatActivity implements OnClickListener{

    EditText first_player;
    EditText second_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        first_player = findViewById(R.id.first_player_name);
        second_player = findViewById(R.id.second_player_name);

        Intent googleDetails = getIntent();
        first_player.setText(googleDetails.getStringExtra("username"));

        second_player.requestFocus();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next: Intent movie = new Intent(this, EnterMovieName.class);

                if( first_player.getText().toString().length() == 0 )
                {
                    first_player.setError( "Player 1 Name is Required!" );
                    first_player.setHintTextColor(Color.RED);
                }else if( second_player.getText().toString().length() == 0 )
                {
                    second_player.setError( "Player 2 Name is Required!" );
                    second_player.setHintTextColor(Color.RED);
                }else {
                    movie.putExtra( "First", first_player.getText().toString());
                    movie.putExtra( "Second", second_player.getText().toString());
                    startActivity(movie);
                }

        }


    }
}
