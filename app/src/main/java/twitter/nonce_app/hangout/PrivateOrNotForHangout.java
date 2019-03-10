package twitter.nonce_app.hangout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;

import java.util.Locale;

import twitter.nonce_app.Home;
import twitter.nonce_app.R;
import twitter.nonce_app.socail.LetUsLoginWithFacebook;
import twitter.nonce_app.socail.LetUsLoginWithTwitter;
import twitter.nonce_app.toast_package.OurToast;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class PrivateOrNotForHangout extends AppCompatActivity {


    private static final String TWITTER_KEY = "CH6HYXVeiSsB09wTTNB0JWt7x";
    private static final String TWITTER_SECRET = "AZtmzFpH4Kei4buw4XnUXTYmw57Lto9F1IaV4B8QIHDoKoG0zt";

    CheckBox facebookText , twitterText , bbmText , whatsappText ;
    Button keepItPrivate , shareIt ;
    ImageView twitterV , facebookV , bbmV , whatsappV;
    AlertDialog alertDialogActivities;
    String ConnectM  , ConnectW , currentLocationForHang ,
            hangActivity , hangPlace ;
         int hangRealMinute , hangRealHour , hangRealDay , hangRealMonth , hangRealYear;
    String hangMonthInLetters ;
    EditText BBMCode, WhatsAppCode ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_or_not);

        currentLocationForHang = getIntent().getExtras().getString("currentLocationForHang");
        hangActivity = getIntent().getExtras().getString("hangActivity");
        hangPlace = getIntent().getExtras().getString("hangPlace");

        hangRealDay = getIntent().getExtras().getInt("hangRealDay");
        hangRealMonth = getIntent().getExtras().getInt("hangRealMonth");
        hangRealYear = getIntent().getExtras().getInt("hangRealYear");
        hangRealHour = getIntent().getExtras().getInt("hangRealHour");
        hangRealMinute = getIntent().getExtras().getInt("hangRealMinute");

        SharedPreferences privateOrNotDetails = PreferenceManager.getDefaultSharedPreferences(PrivateOrNotForHangout.this);
        final SharedPreferences.Editor editor = privateOrNotDetails.edit();

        facebookText = (CheckBox) findViewById(R.id.ConnectWithFacebookForHangout);
        twitterText = (CheckBox) findViewById(R.id.ConnectWithTwitterForHangout);
        bbmText = (CheckBox) findViewById(R.id.ConnectWithBBMForHangout);
        whatsappText = (CheckBox) findViewById(R.id.ConnectWithWhatsAppForHangout);
        keepItPrivate = (Button) findViewById(R.id.keepItPrivate);
        shareIt = (Button) findViewById(R.id.shareIt);
        twitterV = (ImageView) findViewById(R.id.viewTwitter);
        facebookV = (ImageView) findViewById(R.id.viewFacebook);
        bbmV = (ImageView) findViewById(R.id.viewbbm);
        whatsappV = (ImageView) findViewById(R.id.viewwhatsapp);

        twitterV.setBackgroundResource(R.mipmap.twitter_off);
        facebookV.setBackgroundResource(R.mipmap.facebook_off);
        bbmV.setBackgroundResource(R.mipmap.bbm_off);
        whatsappV.setBackgroundResource(R.mipmap.whatsapp_off);

        twitterText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (twitterText.isChecked()) {
                    try {
                        SharedPreferences twitterUser = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        String restoredUserName = twitterUser.getString("TwitterUser", null);
                        if (restoredUserName != null) {

                            twitterText.setTextColor(Color.rgb(0  , 163 , 210));
                            twitterV.setBackgroundResource(R.mipmap.twitter);

                        } else {

                            String homeless = "hang";
                            Intent iTwitter = new Intent(getBaseContext(), LetUsLoginWithTwitter.class);
                            iTwitter.putExtra("amComingFrom" , homeless);
                            startActivity(iTwitter);
                        }
                    } catch (Exception e) {
                        e.getMessage();
                    }
                } else {
                    twitterText.setTextColor(Color.DKGRAY);
                    twitterV.setBackgroundResource(R.mipmap.twitter_off);
                }

            }
        });
        facebookText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (facebookText.isChecked()) {
                    SharedPreferences facebookUser = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    String facebookAccount = facebookUser.getString("FacebookUser", null);
                    if (facebookAccount != null) {
                        facebookText.setTextColor(Color.rgb(0  , 163 , 210));
                        facebookV.setBackgroundResource(R.mipmap.facebook);
                    } else {
                        String homeless = "hang";
                        Intent iFacebook = new Intent(getBaseContext(), LetUsLoginWithFacebook.class);
                        iFacebook.putExtra("amComingFrom" , homeless);
                        startActivity(iFacebook);
                    }

                } else {
                    facebookText.setTextColor(Color.DKGRAY);
                    facebookV.setBackgroundResource(R.mipmap.facebook_off);
                }
            }
        });

        bbmText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (bbmText.isChecked()) {
                    SharedPreferences bbmCode = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    String bbm = bbmCode.getString("bbm_pin", null);
                    if (bbm == null || bbm.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PrivateOrNotForHangout.this);
                        builder.setTitle(getString(R.string.YourBBMCode));
                        builder.setIcon(R.mipmap.bbm);
                        BBMCode = new EditText(PrivateOrNotForHangout.this);
                        int maxLength = 8;
                        InputFilter[] fArray = new InputFilter[1];
                        fArray[0] = new InputFilter.LengthFilter(maxLength);
                        BBMCode.setFilters(fArray);
                        builder.setView(BBMCode);
                        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (BBMCode.getText().toString().trim().equals(""))
                                {
                                    BBMCode.setError(getString(R.string.emptyField));
                                }else
                                {
                                    editor.putString("bbm_pin", BBMCode.getText().toString());
                                    editor.apply();
                                    bbmText.setTextColor(Color.rgb(0  , 163 , 210));
                                    bbmV.setImageResource(R.mipmap.bbm);
                                }


                            }
                        });
                        builder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialogActivities = builder.create();
                        alertDialogActivities.show();
                    } else {
                        bbmText.setTextColor(Color.rgb(0  , 163 , 210));
                        bbmV.setImageResource(R.mipmap.bbm);

                    }
                } else {
                    bbmText.setTextColor(Color.DKGRAY);
                    bbmV.setImageResource(R.mipmap.bbm_off);
                }
            }
        });

        whatsappText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (whatsappText.isChecked()) {
                    SharedPreferences whatsapp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    String watsp = whatsapp.getString("whatsapp_number", null);
                    if (watsp == null|| watsp.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PrivateOrNotForHangout.this);
                        builder.setTitle(getString(R.string.yourWhatsAppNumber));
                        builder.setIcon(R.mipmap.whatsapp);
                        WhatsAppCode = new EditText(PrivateOrNotForHangout.this);
                        int maxLength = 15;
                        InputFilter[] fArray = new InputFilter[1];
                        fArray[0] = new InputFilter.LengthFilter(maxLength);
                        WhatsAppCode.setFilters(fArray);
                        builder.setView(WhatsAppCode);
                        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (WhatsAppCode.getText().toString().trim().equals(""))
                                {
                                    WhatsAppCode.setError(getString(R.string.emptyField));
                                }else
                                {
                                    editor.putString("whatsapp_number", WhatsAppCode.getText().toString());
                                    editor.apply();
                                    whatsappText.setTextColor(Color.rgb(0  , 163 , 210));
                                    whatsappV.setImageResource(R.mipmap.whatsapp);
                                }

                            }
                        });
                        builder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialogActivities = builder.create();
                        alertDialogActivities.show();
                    } else {
                        whatsappText.setTextColor(Color.rgb(0  , 163 , 210));
                        whatsappV.setImageResource(R.mipmap.whatsapp);

                    }
                } else {
                    whatsappText.setTextColor(Color.DKGRAY);
                    whatsappV.setImageResource(R.mipmap.whatsapp_off);
                }

            }
        });

        keepItPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent iHome = new Intent(getBaseContext() , Home.class);
                startActivity(iHome);
                finish();
            }
        });

        shareIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (facebookText.getCurrentTextColor() != Color.rgb(0  , 163 , 210)
                        && twitterText.getCurrentTextColor() != Color.rgb(0  , 163 , 210))
                {
                    new OurToast().myToast(getBaseContext() , getString(R.string.selectWhereWillYouShare));
                }else if (facebookText.getCurrentTextColor() == Color.rgb(0  , 163 , 210)) {

                    facebookTravelShare();
                    Intent iHome = new Intent(getBaseContext() , Home.class);
                    startActivity(iHome);
                    finish();

                }else if (twitterText.getCurrentTextColor() == Color.rgb(0  , 163 , 210))
                {
                    twitterTravelShare();
                    Intent iHome = new Intent(getBaseContext() , Home.class);
                    startActivity(iHome);
                    finish();

                }

            }
        });
    }


    private String ConnectBBm ()
    {
        SharedPreferences bbmAndWhatsapp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String bbm = bbmAndWhatsapp.getString("bbm_pin", null);

        if (bbmText.getCurrentTextColor() == Color.rgb(0  , 163 , 210))
        {
            ConnectM = "\n"+"Contact me at BBM:" + bbm ;
        }

        return ConnectM ;
    }


    private String ConnectWhatsapp ()
    {
        SharedPreferences bbmAndWhatsapp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String whatsapp = bbmAndWhatsapp.getString("whatsapp_number", null);

        if (whatsappText.getCurrentTextColor() == Color.rgb(0  , 163 , 210))
        {
            ConnectW = "\n"+"Contact me at WhatsApp:" + whatsapp ;
        }
        return ConnectW ;
    }


    private void facebookTravelShare ()
    {
        try
        {

            String Message = currentLocationForHang + "\n"  + hangPlace + "\n" + hangActivity ;
            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(getApplication());
            Bundle params = new Bundle();
            params.putString("message", Message);
            GraphRequest request = new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/me/feed",
                    null ,
                    HttpMethod.POST,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {



                        }
                    });
            request.setParameters(params);
            request.executeAsync();
            new OurToast().myToast(getBaseContext() , getString(R.string.shareFacebookSucces));
        }catch (FacebookException e)
        {
            new OurToast().myToast(getBaseContext() , getString(R.string.shareFacebookFailed));
            Log.d("facebook " , e.getMessage());
        }

    }


    private void twitterTravelShare ()
    {
        // connect 50 letters
        // message 50
          String  tweetMessage =
                     hangPlace + hangActivity
                             + "\n"+
                             //9
                   " at " +String.format(Locale.getDefault() , "%d:%d" , hangRealHour , hangRealMinute) + "\n"+
                    //11
                    String.format(Locale.getDefault() , "%d %s %d", hangRealDay, monthInLetters(), hangRealYear);



        if (ConnectBBm() != null && ConnectWhatsapp() != null)
        {
            new updateTwitterStatus().execute(tweetMessage + ConnectBBm() + ConnectWhatsapp());
        }else if (ConnectBBm() != null)
        {
            new updateTwitterStatus().execute(tweetMessage + ConnectBBm());
        }else if (ConnectWhatsapp() != null)
        {
            new updateTwitterStatus().execute(tweetMessage + ConnectWhatsapp());
        }else if (ConnectBBm() == null && ConnectWhatsapp() == null)
        {
            new updateTwitterStatus().execute(tweetMessage);
        }

    }
    class updateTwitterStatus extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            String MyToken , MySecret ;

            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            MyToken = prefs.getString("Token", null);
            MySecret = prefs.getString("Secret" , null);


            /**
             * getting Places JSON
             * */
            Log.d("Tweet Text", "> " + args[0]);
            String status = args[0];
            try {
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey(TWITTER_KEY);
                builder.setOAuthConsumerSecret(TWITTER_SECRET);
                assert MyToken != null;
                assert MySecret != null;
                twitter4j.auth.AccessToken accessToken = new twitter4j.auth.AccessToken(MyToken , MySecret);


                twitter4j.Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
                // Update status
                twitter4j.Status response = twitter.updateStatus(status);

                Log.d("Status", "> " + response.getText());
                new OurToast().myToast(getBaseContext() , getString(R.string.shareTwitterSucces));
            } catch (TwitterException e) {
                // Error in updating status

                Log.d("Twitter Update Error", e.getMessage());
                new OurToast().myToast(getBaseContext() , getString(R.string.shareTwitterFailed));
            }
            return null;
        }
    }

    private String monthInLetters ()
    {
        switch (hangRealMonth+1)
        {
            case 1: hangMonthInLetters = "Jan";
                break;
            case 2: hangMonthInLetters = "Feb";
                break;
            case 3: hangMonthInLetters = "Mar";
                break;
            case 4:  hangMonthInLetters = "Apr";
                break;
            case 5:  hangMonthInLetters = "May";
                break;
            case 6: hangMonthInLetters = "Jun";
                break;
            case 7:  hangMonthInLetters = "Jul";
                break;
            case 8:hangMonthInLetters = "Aug";
                break;
            case 9:hangMonthInLetters = "Sep";
                break;
            case 10:hangMonthInLetters = "Oct";
                break;
            case 11:hangMonthInLetters = "Nov";
                break;
            case 12 : hangMonthInLetters = "Dec";
                break;

        }
        return hangMonthInLetters ;
    }

    @Override
    public void onBackPressed() {

        new OurToast().myToast(getBaseContext() , getString(R.string.yourDetailsWasSaved) + "\n" + getString(R.string.youCanNotEditDetailsFromHere) );
    }
}
