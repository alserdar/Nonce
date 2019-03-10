package twitter.nonce_app.free_time;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import twitter.nonce_app.R;
import twitter.nonce_app.settings.ConnectingWithSettings;

public class MusicService extends Service {
    final String NOTIFICATION_TAG = "Nonce Music";


    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null ;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ConnectingWithSettings connect = new ConnectingWithSettings();
        String notTone = connect.notificationTone(getBaseContext());
        Uri defNote = Settings.System.DEFAULT_NOTIFICATION_URI;

        final SharedPreferences getMusicDetails = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String getTheLink = getMusicDetails.getString("saveRadioLink", "saveRadioLink");
        if (getTheLink.isEmpty() || getTheLink.equals("saveRadioLink"))
        {
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                    .setSmallIcon(R.drawable.icon_custom)
                    .setContentTitle("Nonce : "+ getString(R.string.radio))
                    .setContentText("http://radio.garden/live/")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setNumber(0)
                    .setColor(Color.WHITE)
                    .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                    .setContentIntent(PendingIntent.getActivity(getBaseContext(),0,new Intent
                                    (Intent.ACTION_VIEW, Uri.parse("http://radio.garden/live/")),
                            PendingIntent.FLAG_UPDATE_CURRENT))
                    .setAutoCancel(true);
            if (connect.isToneEnabled(this)) {

                if (notTone == null)
                {
                    builder.setSound(defNote);
                }else
                {
                    builder.setSound(Uri.parse(notTone));
                }
            }
            notify(getBaseContext(), builder.build());
        }else
        {
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                    .setSmallIcon(R.drawable.icon_custom)
                    .setContentTitle("Nonce : " + getString(R.string.radio))
                    .setContentText(getTheLink)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setNumber(0)
                    .setColor(Color.WHITE)
                    .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                    .setContentIntent(PendingIntent.getActivity(getBaseContext(),0,new Intent
                                    (Intent.ACTION_VIEW, Uri.parse(getTheLink)),
                            PendingIntent.FLAG_UPDATE_CURRENT))
                    .setAutoCancel(true);
            if (connect.isToneEnabled(this)) {

                if (notTone == null)
                {
                    builder.setSound(defNote);
                }else
                {
                    builder.setSound(Uri.parse(notTone));
                }
            }
            notify(getBaseContext(), builder.build());
        }

        return Service.START_FLAG_RETRY;
    }

    private void notify(Context context, Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, 0, notification);
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification);
        }
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0);
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode());
        }
    }
}
