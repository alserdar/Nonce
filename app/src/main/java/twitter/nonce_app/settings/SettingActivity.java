package twitter.nonce_app.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import twitter.nonce_app.Home;
import twitter.nonce_app.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView goToPrivate = (TextView)findViewById(R.id.private_user);
        goToPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SettingActivity.this , PersonalActivity.class);
                startActivity(i);

            }
        });



        TextView goToSound = (TextView)findViewById(R.id.sounds);
        goToSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SettingActivity.this , SoundActivity.class);
                startActivity(i);

            }
        });


        TextView goToSettings = (TextView)findViewById(R.id.app_settings);
        goToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SettingActivity.this , App_SettingsActivity.class);
                startActivity(i);
            }
        });


        TextView contactUs = (TextView)findViewById(R.id.contactUs);
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SettingActivity.this , ContactUs.class);
                startActivity(i);
            }
        });

        TextView aboutVer = (TextView)findViewById(R.id.aboutVersion);
        aboutVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SettingActivity.this , AboutVersion.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SettingActivity.this , Home.class);
        startActivity(i);
        finish();
    }
}