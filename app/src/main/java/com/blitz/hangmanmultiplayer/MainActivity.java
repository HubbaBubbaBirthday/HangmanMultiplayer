package com.blitz.hangmanmultiplayer;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoginStatusCallback;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
                .logInWithReadPermissions(this,Arrays.asList("public_profile"));
    }

    private void onFacebookLoginSuccessful(LoginResult loginResult) {
        String currentUserId = profile.getId();
        String app_Id = "206115383302010";
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + app_Id + "/scores",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        System.out.println("Scores: " + response);
                    }
                }
        ).executeAsync();
    }
}
