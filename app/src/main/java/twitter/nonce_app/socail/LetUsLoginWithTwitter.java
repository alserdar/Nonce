package twitter.nonce_app.socail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;
import twitter.nonce_app.Home;
import twitter.nonce_app.R;
import twitter.nonce_app.hangout.PrivateOrNotForHangout;
import twitter.nonce_app.toast_package.OurToast;
import twitter.nonce_app.travel.PrivateOrNotForTravel;


public class LetUsLoginWithTwitter extends AppCompatActivity {


    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "CH6HYXVeiSsB09wTTNB0JWt7x";
    private static final String TWITTER_SECRET = "AZtmzFpH4Kei4buw4XnUXTYmw57Lto9F1IaV4B8QIHDoKoG0zt";

    private TwitterLoginButton loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_let_us_login_with_twitter);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                Twitter.getInstance().core.getSessionManager().getActiveSession();
                TwitterSession session = result.data;


                SharedPreferences twitterDetails = PreferenceManager.getDefaultSharedPreferences(LetUsLoginWithTwitter.this);
                final SharedPreferences.Editor editor = twitterDetails.edit();
                editor.putString("TwitterUser", session.getUserName());
                editor.putString("Token", session.getAuthToken().token);
                editor.putString("Secret", session.getAuthToken().secret);
                editor.apply();
                new OurToast().myToast(getBaseContext() , getString(R.string.welome) + session.getUserName());
                reverse();
                finish();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
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
    public void onBackPressed() {

        reverse();
        finish();
    }
}
