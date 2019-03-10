package twitter.nonce_app.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import twitter.nonce_app.Home;
import twitter.nonce_app.R;

public class AboutVersion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Button shareTheApp = (Button)findViewById(R.id.shareTheAppLink);

        shareTheApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "https://play.google.com/store/apps/details?id=twitter.nonce_app";
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share App Link");
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, getString(R.string.shareVia)));

                    }
                }).start();

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent iHome = new Intent(getBaseContext() , Home.class);
        startActivity(iHome);
        super.onBackPressed();
    }
}
