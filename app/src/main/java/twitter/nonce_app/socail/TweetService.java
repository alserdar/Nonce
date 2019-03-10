package twitter.nonce_app.socail;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import twitter.nonce_app.R;
import twitter.nonce_app.network_connected.NetworkUtil;
import twitter.nonce_app.notification_package.NotificationsNonceMessage;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class TweetService extends Service {


    private static final String TWITTER_KEY = "CH6HYXVeiSsB09wTTNB0JWt7x";
    private static final String TWITTER_SECRET = "AZtmzFpH4Kei4buw4XnUXTYmw57Lto9F1IaV4B8QIHDoKoG0zt";

    String tweet ;
    String MyToken , MySecret ;

    public TweetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null ;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        tweet = intent.getExtras().getString(TweetBroadCast.Tweet);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        MyToken = prefs.getString("Token", null);
        MySecret = prefs.getString("Secret" , null);

        new updateTwitterStatus().execute(tweet);
        return START_FLAG_RETRY ;
    }
    class updateTwitterStatus extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            Log.d("Tweet Text", "> " + args[0]);
            String status = args[0];
            try {
                if (status != null)
                {
                    ConfigurationBuilder builder = new ConfigurationBuilder();
                    builder.setOAuthConsumerKey(TWITTER_KEY);
                    builder.setOAuthConsumerSecret(TWITTER_SECRET);
                    twitter4j.auth.AccessToken accessToken = new AccessToken(MyToken , MySecret);


                    twitter4j.Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
                    // Update status
                    twitter4j.Status response = twitter.updateStatus(status);




                    if (NetworkUtil.getConnectivityStatusString(getBaseContext()).equals("Not connected to Internet"))
                    {
                        twitterNotificationsFailTweet();
                    }else
                    {
                        twitterNotificationsSuccessTweet();
                    }

                    Log.d("Status", "> " + response.getText());
                }else
                {

                }

            } catch (TwitterException e) {
                // Error in updating status
                twitterNotificationsFailTweet();
                Log.d("Twitter Update Error", e.getMessage());
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog and show
         * the data in UI Always use runOnUiThread(new Runnable()) to update UI
         * from background thread, otherwise you will get error
         **/
        protected void onPostExecute(String file_url) {

        }
    }
    private void twitterNotificationsSuccessTweet()
    {
        try {

            new NotificationsNonceMessage().OurNotification(getBaseContext() , getString(R.string.tweetSent) );
        }catch (Exception e)
        {
            e.getMessage();
        }
    }


    private void twitterNotificationsFailTweet() {
        try {

            new NotificationsNonceMessage().OurNotification(getBaseContext() , getString(R.string.tweetFaild) );
        }catch (Exception e)
        {
            e.getMessage();
        }
    }
}
