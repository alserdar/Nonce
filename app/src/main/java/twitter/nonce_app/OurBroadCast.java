package twitter.nonce_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class OurBroadCast extends BroadcastReceiver {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SNOOZE_TIME = "snooze_time";
    public static final String TIME_HOUR = "timeHour";
    public static final String TIME_MINUTE = "timeMinute";
    public static final String DAY = "day";
    public static final String MONTH = "month";
    public static final String YEAR = "year";

    public static final String MEM_TITLE = "mem_title";
    public static final String MEM_ONE = "mem_one";
    public static final String MEM_TWO = "mem_two";
    public static final String MEM_THREE = "mem_three";
    public static final String MEM_FOUR = "mem_four";
    public static final String MEM_FIVE = "mem_five";
    public static final String MEM_SIX = "mem_six";

    private static final long minute = 60000;
    private static final long[] timesRandomly =
            {
                    minute * 2, minute * 3, minute * 4 , minute * 5, minute * 6

            };

    private static Random r = new Random();
    private static long randomly = timesRandomly[r.nextInt(4)];
    private static long snoozeTime = randomly / 60000;

    @Override
    public void onReceive(Context context, Intent intent)

    {
        setAlarms(context);
    }

    public static void setAlarms(Context context) {
        try {

            cancelAlarms(context);
            DBHelper dbHelper = DBHelper.getInstance(context);

            List<Model> alarms = dbHelper.viewAllData();
            for (Model alarm : alarms) {
                if (alarm.enabled) {

                    PendingIntent pIntent = createPendingIntent(context, alarm);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, alarm.year);
                    calendar.set(Calendar.MONTH, alarm.month);
                    calendar.set(Calendar.DAY_OF_MONTH, alarm.dayInMonth);
                    calendar.set(Calendar.HOUR_OF_DAY, alarm.hour);
                    calendar.set(Calendar.MINUTE, alarm.minute);
                    calendar.set(Calendar.SECOND , 0);

                    for (long rightNow = calendar.getTimeInMillis();
                         rightNow > 0 && calendar.compareTo(Calendar.getInstance()) > 0 ; rightNow++)
                    {
                        if (rightNow > 0)
                        {
                            setAlarm(context , calendar , pIntent);
                            break;
                        }

                    }
                }
            }
        }catch (Exception e)
        {
            e.getMessage();
        }
    }


    private static void setAlarm(Context context , Calendar calendar , PendingIntent pIntent)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }else
        {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);

        }
    }

    public static void snoozeAlarm(Context context , String alarmName) {

        try {
            DBHelper dbHelper = DBHelper.getInstance(context);
            List<Model> modelList = dbHelper.viewAllData();
            for (Model model : modelList) {

                for (int i = 0; i < model._id; i++) {
                    if (i < model._id) {
                        model.eventName = alarmName ;
                        model.snoozeTime = "We Snoozed for " + snoozeTime + " Minutes";
                        PendingIntent pendingIntent = createPendingIntent(context, model);
                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + randomly,0,
                                pendingIntent);
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void cancelAlarms(Context context) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        List<Model> alarms = dbHelper.viewAllData();
        if (alarms != null) {
            for (Model alarm : alarms) {
                if (alarm.enabled) {
                    PendingIntent pIntent = createPendingIntent(context, alarm);

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pIntent);
                }
            }
        }
    }

    private static PendingIntent createPendingIntent(Context context, Model model) {



        Intent intent = new Intent(context, OurService.class);
        intent.putExtra(ID, model._id);
        intent.putExtra(NAME, model.eventName);
        intent.putExtra(SNOOZE_TIME , model.snoozeTime);
        intent.putExtra(TIME_HOUR, model.hour);
        intent.putExtra(TIME_MINUTE, model.minute);
        intent.putExtra(DAY, model.dayInMonth);
        intent.putExtra(MONTH, model.month);
        intent.putExtra(YEAR, model.year);


        intent.putExtra(MEM_TITLE , model.memTitle);
        intent.putExtra(MEM_ONE , model.memOne);
        intent.putExtra(MEM_TWO , model.memTwo);
        intent.putExtra(MEM_THREE , model.memThree);
        intent.putExtra(MEM_FOUR , model.memFour);
        intent.putExtra(MEM_FIVE , model.memFive);
        intent.putExtra(MEM_SIX , model.memSix);


        return PendingIntent.getService(context, (int) model._id, intent, PendingIntent.FLAG_UPDATE_CURRENT);


    }
}
