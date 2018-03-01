package com.blitz.hangmanmultiplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private static final String TAG = "Facebook_Sign_In";
    boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
    LoginButton loginButton;
    Profile profile;
    private boolean publishPermissionsRequested = false;
    private LoginResult readResultSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginWithFB(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        profile = Profile.getCurrentProfile();
        System.out.println("Profile:" + profile.getName() );

                super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play_button:
                Intent details = new Intent(this, PlayerDetails.class);
                startActivity(details);
            case R.id.login_button:
                System.out.println("Clicked Facebook");

        }
    }

    public void loginWithFB(final MainActivity mainActivity){
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        System.out.println("Successful Login" + AccessToken.getCurrentAccessToken());
                        if(!publishPermissionsRequested){
                            publishPermissionsRequested = true;
                            readResultSuccess = loginResult;
                            LoginManager.getInstance().logInWithPublishPermissions(mainActivity, Arrays.asList("publish_actions"));
                            return;
                        }
                        onFacebookLoginSuccessful(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("Cancel Login" + AccessToken.getCurrentAccessToken());
                        if(publishPermissionsRequested && readResultSuccess != null)
                            onFacebookLoginSuccessful(readResultSuccess);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("Error Login" + AccessToken.getCurrentAccessToken());
                        if(publishPermissionsRequested && readResultSuccess != null)
                            onFacebookLoginSuccessful(readResultSuccess);
                    }
                }
        );
        LoginManager.getInstance()
                .logInWithReadPermissions(this,Arrays.asList("public_profile", "user_friends"));
    }

    private void onFacebookLoginSuccessful(LoginResult loginResult) {
        String currentUserId = profile.getId();
        String app_Id = AccessToken.getCurrentAccessToken().getApplicationId();
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + app_Id + "/scores",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {

                        try {

                            JSONObject json = response.getJSONObject();
                            JSONArray values = json.getJSONArray("data");

                            System.out.println("Player Current Score: " + values.getJSONObject(0).getInt("score"));
                        } catch (JSONException e) {
                            System.out.println("No scores for Player yet");
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }
}
