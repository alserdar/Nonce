package twitter.nonce_app.socail;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import twitter.nonce_app.DetailsActivity;
import twitter.nonce_app.Home;
import twitter.nonce_app.R;
import twitter.nonce_app.toast_package.OurToast;

public class JustTweet extends AppCompatActivity {

    TextInputEditText tweet ;
    private static final String LAST_TEXT_Tweet = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_tweet);

        SharedPreferences twitterDetails = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String twitterUserName = twitterDetails.getString("TwitterUser" , null);
        new OurToast().myToast(getBaseContext() , getString(R.string.welome) + twitterUserName);

        tweet = (TextInputEditText) findViewById(R.id.tweetTwitter);
        final TextView count = (TextView) findViewById(R.id.countTweet);
        final Button saveTweets = (Button)findViewById(R.id.saveTweet);

       // final SharedPreferences prefTweet = PreferenceManager.getDefaultSharedPreferences(JustTweet.this);
        final SharedPreferences sharedPreferencesTweet = getPreferences(MODE_PRIVATE);
        tweet.setText(sharedPreferencesTweet.getString(LAST_TEXT_Tweet, ""));

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                count.setText(String.valueOf(charSequence.length()));
                if (tweet.getText().toString().trim().equals(""))
                {
                    saveTweets.setClickable(false);
                    saveTweets.setBackgroundResource(R.drawable.gone_grey);
                }else
                {
                    saveTweets.setClickable(true);
                    saveTweets.setBackgroundResource(R.drawable.button_custom);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                sharedPreferencesTweet.edit().putString(LAST_TEXT_Tweet, editable.toString()).apply();
            }
        };
        tweet.addTextChangedListener(textWatcher);


        saveTweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferencesTweet.edit().remove(LAST_TEXT_Tweet).apply();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (tweet.getText().toString().trim().equals(""))
                        {
                            saveTweets.setClickable(false);
                        }else
                        {
                            saveTweets.setClickable(true);
                            saveTweets.setBackgroundResource(R.drawable.button_custom);
                            long id = -1 ;
                            Intent i = new Intent(getBaseContext() , DetailsActivity.class);
                            i.putExtra("id" , id);
                            i.putExtra("alarmName" , getString(R.string.tweet));
                            i.putExtra("theEvent", "Tweet");
                            i.putExtra("myTweet" , tweet.getText().toString());
                            i.putExtra("extraInfo" , tweet.getText().toString());
                            startActivity(i);

                        }

                    }
                }).start();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (tweet.getText().toString().equals(""))
        {
            Intent iHome = new Intent(getBaseContext() , Home.class);
            startActivity(iHome);
        }else
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.discardTweet);
            builder.setCancelable(true);
            builder.setIcon(R.mipmap.twitter);
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Intent iHome = new Intent(getBaseContext() , Home.class);
                            startActivity(iHome);
                            finish();
                        }
                    }).start();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
