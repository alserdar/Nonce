package twitter.nonce_app;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.fabric.sdk.android.Fabric;

public class Launcher extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "CH6HYXVeiSsB09wTTNB0JWt7x";
    private static final String TWITTER_SECRET = "AZtmzFpH4Kei4buw4XnUXTYmw57Lto9F1IaV4B8QIHDoKoG0zt";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_launcher);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "twitter.nonce_app",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        TextView luncherText = (TextView)findViewById(R.id.welcomeText);
        luncherText.setText(R.string.EvolvesTogether);
        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.getMessage();
                } finally {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Intent lunch = new Intent(Launcher.this, Home.class);
                            startActivity(lunch);
                            finish();
                        }
                    }).start();
                }
            }
        };
        timer.start();
    }

    @Override
    public void onBackPressed() {

    }
}
