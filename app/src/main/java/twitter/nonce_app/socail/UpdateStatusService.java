package twitter.nonce_app.socail;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;

import twitter.nonce_app.R;
import twitter.nonce_app.network_connected.NetworkUtil;
import twitter.nonce_app.notification_package.NotificationsNonceMessage;

public class UpdateStatusService extends Service {

    public UpdateStatusService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null ;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
        final String Message = intent.getExtras().getString(UpdateStatusBroadCast.UpdateStatus);

        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.getMessage();
                } finally {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try
                            {

                                Bundle params = new Bundle();
                                params.putString("message", Message);
                                final GraphRequest request = new GraphRequest(
                                        AccessToken.getCurrentAccessToken(),
                                        "/me/feed",
                                        params ,
                                        HttpMethod.POST,
                                        new GraphRequest.Callback() {
                                            public void onCompleted(GraphResponse response) {


                                            }
                                        });
                                request.setParameters(params);
                                request.executeAsync();

                                if (NetworkUtil.getConnectivityStatusString(getBaseContext()).equals("Not connected to Internet")) {

                                    updateFailed();
                                }else
                                {
                                    updateSuccessfully();
                                }


                            }catch (FacebookException e )
                            {
                                updateFailed();
                                e.getMessage();
                                Log.d("facebook " , e.getMessage());
                            }

                        }
                    }).start();
                }
            }
        };
        timer.start();
        return START_FLAG_RETRY ;
    }

    private void updateSuccessfully ()
    {

        try {

            new NotificationsNonceMessage().OurNotification(getBaseContext() , getString(R.string.postSucces) );

        }catch (Exception e)
        {
            e.getMessage();
        }

    }

    private void updateFailed ()
    {
        try {
            new NotificationsNonceMessage().OurNotification(getBaseContext() , getString(R.string.postFailed) );
        }catch (Exception e)
        {
            e.getMessage();
        }

    }
}
