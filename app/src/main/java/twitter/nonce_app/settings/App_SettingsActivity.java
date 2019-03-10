package twitter.nonce_app.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.Preference;
import android.preference.PreferenceManager;

import java.util.Locale;

import twitter.nonce_app.R;

/**
 * Created by zizo on 7/14/2016.
 */
public class App_SettingsActivity extends AppCompatPreferenceActivity {

    private static ConnectingWithSettings connecting = new ConnectingWithSettings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.app_settings_activity);


        bindPreferenceSummaryToValue(findPreference("nonce_lang"));

        SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(App_SettingsActivity.this);
        String defLang = pre.getString("nonce_lang" , "English");

        switch (defLang)
        {
            case "English":
                String languageToLoadEn  = "en"; // your language
                Locale localeEn1 = new Locale(languageToLoadEn);
                Locale.setDefault(localeEn1);
                Configuration configEn = new Configuration();
                configEn.locale = localeEn1;
                App_SettingsActivity.this.getResources().
                        updateConfiguration(configEn,App_SettingsActivity.this.getResources().getDisplayMetrics());
            break;

            case "العربية":

                String language = "ar";
                Locale locale = new Locale(language);
                locale.getCountry();
                Configuration config = new Configuration();
                config.locale = locale;
                App_SettingsActivity.this.getResources().updateConfiguration(config,
                        App_SettingsActivity.this.getResources().getDisplayMetrics());
                break;
            case "Italiano":
                String languageItlay = "It";
                Locale localeItaly = new Locale(languageItlay);
                localeItaly.getCountry();
                Configuration configItaly = new Configuration();
                configItaly.locale = localeItaly;
                App_SettingsActivity.this.getResources().updateConfiguration(configItaly,
                        App_SettingsActivity.this.getResources().getDisplayMetrics());
                break;
        }

    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey() , ""));



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

    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    @Override
    public void onBackPressed() {

        if (connecting.isIconEnabled(App_SettingsActivity.this)) {
            if (connecting.isVibrate(App_SettingsActivity.this)) {
                nonceSilentMode(App_SettingsActivity.this);
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(300);
            } else {
                nonceSilentMode(App_SettingsActivity.this);
            }
        } else {

        }
        Intent iHome = new Intent(App_SettingsActivity.this, SettingActivity.class);
        startActivity(iHome);
        finish();
    }

    public static void nonceSilentMode(final Context context) {

        if (connecting.isIconEnabled(context)) {
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    SharedPreferences getSoundEnabled = PreferenceManager.getDefaultSharedPreferences(context);
                    boolean isSoundEnabled = getSoundEnabled.getBoolean("notifications_new_message", true);

                    SharedPreferences getIconEnabled = PreferenceManager.getDefaultSharedPreferences(context);
                    boolean isIcon = getIconEnabled.getBoolean("nonce_icon_status", true);
                    if (isIcon) {
                        if (isSoundEnabled == true) {
                           // NonceNotification.notify(context, "Nonce in Ringing Mode", 0);
                      //      NonceNotification.cancel(context);
                        } else {
                       //     NonceNotification.notify(context, "Nonce in Silent Mode", 0);
                        }
                    } else {

                    }

                }
            }, 1000);
        }
    }
}

/*

        switch (Locale.getDefault().getDisplayLanguage())
        {
            case "English" :
                String languageEn = "en";
               // String countryEn = "US";
                Locale localeEn = new Locale(languageEn);
                localeEn.getCountry();

                String languageToLoadEn  = "en"; // your language
                Locale localeEn1 = new Locale(languageToLoadEn);
                Locale.setDefault(localeEn1);
                Configuration configEn = new Configuration();
                configEn.locale = localeEn1;
                App_SettingsActivity.this.getResources().
                        updateConfiguration(configEn,App_SettingsActivity.this.getResources().getDisplayMetrics());
                break;
            case "العربية" :
                String language = "ar";
              //  String country = "AR";
                Locale locale = new Locale(language);
                locale.getCountry();
                Configuration config = new Configuration();
                config.locale = locale;
                App_SettingsActivity.this.getResources().updateConfiguration(config,
                        App_SettingsActivity.this.getResources().getDisplayMetrics());

                break;
        }
 */