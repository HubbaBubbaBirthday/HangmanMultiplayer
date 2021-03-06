package com.blitz.hangmanmultiplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import java.util.ResourceBundle;

public class PlayerDetails extends AppCompatActivity implements OnClickListener{

    EditText first_player;
    EditText second_player;
    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        first_player = findViewById(R.id.first_player_name);
        second_player = findViewById(R.id.second_player_name);

        //Check if Logged In

        if(AccessToken.getCurrentAccessToken() != null)
        {
            profile = Profile.getCurrentProfile();
            first_player.setText(profile.getFirstName());

            second_player.requestFocus();

        }

        /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/{app-id}/scores",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        System.out.println("Player Id: " + profile.getId());
                    }
                }
        ).executeAsync();
    }

    @Override
    public void onClick(View view) {
        Intent movie = new Intent(this, EnterMovieName.class);


        movie.putExtra( "First", first_player.getText().toString());
        movie.putExtra( "Second", second_player.getText().toString());

        startActivity(movie);
    }
}
