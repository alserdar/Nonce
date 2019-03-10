package twitter.nonce_app.notification_package;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import twitter.nonce_app.Home;
import twitter.nonce_app.R;
import twitter.nonce_app.settings.ConnectingWithSettings;

/**
 * Created by ALAZIZY on 2/12/2017.
 */
public class NotificationsNonceMessage {

    private static final String NOTIFICATION_TAG = "twitter.nonce_app.notification_package";
    ConnectingWithSettings connecting = new ConnectingWithSettings();

    public void OurNotification(Context context , String Message) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon_custom)
                .setContentTitle("Nonce : " + context.getString(R.string.notifications))
                .setContentText(Message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColor(Color.WHITE)
                .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                .setAutoCancel(true)
                .extend(new NotificationCompat.Extender() {
                    @Override
                    public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                        return null;
                    }
                })
                .setContentIntent(
                        PendingIntent.getActivity(
                                context,
                                0,
                                new Intent(context, Home.class),
                                PendingIntent.FLAG_UPDATE_CURRENT))

                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle("Nonce : " + context.getString(R.string.notifications)));
        if (connecting.isToneEnabled(context)) {

            Uri defNote = Settings.System.DEFAULT_NOTIFICATION_URI;
            String setTone = connecting.notificationTone(context);
           // connecting.toneLevel(context);
            if (setTone == null)
            {
                builder.setSound(defNote);
            }else
            {
                builder.setSound(Uri.parse(setTone));
            }
        }
        notify(context, builder.build());
    }


    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_TAG, 0, notification);
    }

    public void cancel(Context context) {
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }

}
