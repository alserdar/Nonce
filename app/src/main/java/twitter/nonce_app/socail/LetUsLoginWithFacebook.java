package twitter.nonce_app.socail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;
import java.util.Collections;

import twitter.nonce_app.Home;
import twitter.nonce_app.R;
import twitter.nonce_app.hangout.PrivateOrNotForHangout;
import twitter.nonce_app.travel.PrivateOrNotForTravel;

public class LetUsLoginWithFacebook extends AppCompatActivity {

    LoginButton loginButton ;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(LetUsLoginWithFacebook.this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(getApplication());
        setContentView(R.layout.activity_let_us_login_with_facebook);

        loginButton = (LoginButton)findViewById(R.id.login_button);


        loginButton.setReadPermissions(Arrays.asList("public_profile" , "user_posts"));
        LoginManager.getInstance().logInWithPublishPermissions(LetUsLoginWithFacebook.this ,
                Collections.singletonList("publish_actions"));


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(LoginResult loginResult) {
                if(Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            // profile2 is the new profile
                            Log.v("facebook - profile", profile2.getFirstName());
                            Log.v("facebook - id", profile2.getId());
                            SharedPreferences privateOrNotDetails = PreferenceManager.getDefaultSharedPreferences(LetUsLoginWithFacebook.this);
                            final SharedPreferences.Editor editor = privateOrNotDetails.edit();
                            editor.putString("FacebookUser" , profile2.getFirstName());
                            editor.putString("user_id" , profile2.getId());
                            editor.apply();
                            mProfileTracker.stopTracking();
                        }
                    };
                    // no need to call startTracking() on mProfileTracker
                    // because it is called by its constructor, internally.
                }
                else {
                    Profile profile = Profile.getCurrentProfile();
                    Log.v("facebook - profile", profile.getFirstName());
                    Log.v("facebook - id", profile.getId());
                    SharedPreferences privateOrNotDetails = PreferenceManager.getDefaultSharedPreferences(LetUsLoginWithFacebook.this);
                    final SharedPreferences.Editor editor = privateOrNotDetails.edit();
                    editor.putString("FacebookUser" , profile.getFirstName());
                    editor.putString("user_id" , profile.getId());
                    editor.apply();
                }
            }

            @Override
            public void onCancel()
            {
                Log.v("facebook - onCancel", "cancelled");
            }

            @Override
            public void onError(FacebookException e) {
                Log.v("facebook - onError", e.getMessage());
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
        reverse();
    }

    private void reverse ()
    {
        String whoIsComing = this.getIntent().getExtras().getString("amComingFrom") ;
        if (whoIsComing != null) {
            switch (whoIsComing) {
                case "Home": {
                    Intent i = new Intent(getBaseContext(), Home.class);
                    startActivity(i);
                    finish();
                    break;
                }
                case "travel": {
                    Intent i = new Intent(getBaseContext(), PrivateOrNotForTravel.class);
                    i.putExtra("countryIs", "");
                    startActivity(i);
                    finish();
                    break;
                }
                case "hang": {
                    Intent i = new Intent(getBaseContext(), PrivateOrNotForHangout.class);
                    i.putExtra("countryIs", "");
                    startActivity(i);
                    finish();
                    break;
                }
            }
        }else
        {
            Intent i = new Intent(getBaseContext(), Home.class);
            startActivity(i);
            finish();
        }
        finish();
    }

    @Override
    public void onBackPressed()
    {
        reverse();
        super.onBackPressed();
    }
}
