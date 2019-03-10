package twitter.nonce_app.free_time;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

import twitter.nonce_app.R;
import twitter.nonce_app.toast_package.OurToast;

public class MusicBroadCast extends BroadcastReceiver {
    public MusicBroadCast() {


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        setMusicNotification(context);
    }

    public static void setMusicNotification(Context context) {
        try {

            new OurToast().myToast(context , context.getString(R.string.saved));
            final SharedPreferences savedMusicDetails = PreferenceManager.getDefaultSharedPreferences(context);
            int selectedHour = savedMusicDetails.getInt("selectHour", 9);
            int selectedMinute = savedMusicDetails.getInt("selectMinute", 9);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
            calendar.set(Calendar.SECOND, 00);
            calendar.set(Calendar.MILLISECOND, 0);
            if (selectedMinute >= 10)
            {

                calendar.set(Calendar.MINUTE, selectedMinute);
            }else
            {
                int newMinuteSelected =  Integer.parseInt("0" + selectedMinute);
                calendar.set(Calendar.MINUTE, newMinuteSelected);
            }

            PendingIntent pIntent = createPendingIntent(context);
            for (long rightNow = calendar.getTimeInMillis();
                 rightNow > 0 && calendar.compareTo(Calendar.getInstance()) > 0 ; rightNow++)
            {
                if (rightNow > 0)
                {
                    setDaily(context , calendar , pIntent);
                    break;
                }else
                {

                }
            }

        } catch (Exception e) {
            e.getMessage();
        }
    }


    private static void setDaily(Context context, Calendar calendar, PendingIntent pIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , AlarmManager.INTERVAL_DAY, pIntent);
    }

    private static PendingIntent createPendingIntent(Context context) {
        Intent intent = new Intent(context, MusicService.class);
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


    }
}
