package twitter.nonce_app.settings;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.Toast;

import twitter.nonce_app.R;

/**
 * Created by zizo on 7/14/2016.
 */
public class ContactUs extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.contact_us);


        Preference getEmail = findPreference("email_inventor");
        getEmail.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                String deviceEmail =  UserEmailFetcher.getEmail(getBaseContext());
                if (deviceEmail!= null && deviceEmail.contains("@"))
                {
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.setType("message/rfc822");
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{"alserdar89@gmail.com"});
                    startActivity(Intent.createChooser(email , deviceEmail));
                }else
                {
                    Toast.makeText(getBaseContext() , "Login with your email first" , Toast.LENGTH_SHORT).show();

                   /*
                    thats work with google service api

                     Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                            new String[] {"com.google" , "com.google.android.legacyimap"},
                            false, null, null, null, null);

                    startActivity(intent);
                    */

                }


                return false;
            }
        });

        Preference getTwitter = findPreference("twitter_inventor");
        getTwitter.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/nonce_app"));
                startActivity(i);
                return false;
            }
        });

        Preference getFacebook = findPreference("facebook_inventor");
        getFacebook.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/3bdl3azez"));
                startActivity(i);
                return false;
            }
        });

    }


    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener =
            new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object value) {
                    String stringValue = value.toString();
                    preference.setSummary(stringValue);

                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        Intent iHome = new Intent(ContactUs.this , SettingActivity.class);
        startActivity(iHome);
        finish();
    }
}
