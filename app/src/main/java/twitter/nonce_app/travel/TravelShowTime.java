package twitter.nonce_app.travel;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import twitter.nonce_app.Home;
import twitter.nonce_app.R;
import twitter.nonce_app.settings.ConnectingWithSettings;

import static java.lang.String.format;

public class TravelShowTime extends AppCompatActivity {

    private static final String NOTIFICATION_TAG = "Nonce";
    TextView extraUp , extraDown , extraInformation , tvName  , tvTime , tvDate;

    Button dismissButton , snoozeButton ;
    String alarms = null ;

    private MediaPlayer mPlayer = new MediaPlayer();
    ConnectingWithSettings connecting = new ConnectingWithSettings();
    Vibrator vibrator ;
    String travelCurrentCountry;
    String travelCountry;
    String travelCity;
    String travelPurpose;
    String travelDays;
    Locale locale = new Locale("en_EN");
    final Calendar cal = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_show_time);



        KeyguardManager kgMgr = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        boolean showing = kgMgr.inKeyguardRestrictedInputMode();

        if (showing)
        {

            TravelShowTime.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
            TravelShowTime.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            TravelShowTime.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            TravelShowTime.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            View v = getLayoutInflater().inflate(R.layout.activity_travel_show_time , null);
            v.setKeepScreenOn(true);

            playTheRingtone();

        }else
        {

            vibrateForWakeup();
        }

        extraUp = (TextView) findViewById(R.id.extraDataUp);
        extraDown = (TextView) findViewById(R.id.extraDataDown);
        extraInformation = (TextView) findViewById(R.id.extraInfo);
        tvName = (TextView) findViewById(R.id.alarm_screen_name);
        tvTime = (TextView) findViewById(R.id.alarm_screen_time);
        tvDate = (TextView) findViewById(R.id.alarm_screen_date);
        dismissButton = (Button) findViewById(R.id.alarm_screen_button);
        snoozeButton = (Button) findViewById(R.id.repeat_screen_button);
        travelCurrentCountry = this.getIntent().getStringExtra(TravelBroadcast.TRAVEL_CURRENT_COUNTRY);
        travelCountry = this.getIntent().getStringExtra(TravelBroadcast.TRAVEL_COUNTRY);
        travelCity = this.getIntent().getStringExtra(TravelBroadcast.TRAVEL_CITY);
        travelPurpose = this.getIntent().getStringExtra(TravelBroadcast.TRAVEL_PURPOSE);
        travelDays = this.getIntent().getStringExtra(TravelBroadcast.TRAVEL_DAYS);

        int timeMinute = cal.get(Calendar.MINUTE);
        int timeHour = cal.get(Calendar.HOUR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        final int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String  boomTime = format(locale , "%02d:%02d", timeHour, timeMinute);
        tvTime.setText(boomTime);
        tvDate.setText(format(locale, "%02d-%02d-%01d", day, month + 1, year));
        tvName.setText(R.string.travel);


        extraUp.setText(R.string.WishYouAgoodTravel);
        extraDown.setText(String.format("%s ", travelCountry));
        extraInformation.setText(travelCity);

        startVibrate();
        dismissAfterMinute();

        dismissButton.setText(R.string.dismiss);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.stop();
                finish();
            }
        });
        snoozeButton.setText(R.string.openMap);
        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?&daddr=%f,%f (%s)", 12f, 2f, travelCity);
                mPlayer.stop();
                try {

                    Intent map = new Intent(Intent.ACTION_VIEW , Uri.parse(uri));
                    map.addCategory(Intent.CATEGORY_APP_MAPS);
                    map.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(map);
                }catch (ActivityNotFoundException ex)
                {
                    try
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                    }
                    catch(ActivityNotFoundException innerEx)
                    {
                        Toast.makeText(getBaseContext(), getString(R.string.installMapApp), Toast.LENGTH_LONG).show();
                    }
                }

                finish();
            }
        });
    }


    private void playTheRingtone() {


        if (connecting.isToneEnabled(TravelShowTime.this)) {
            alarms = connecting.Ringtone(TravelShowTime.this);

            mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            if (alarms == null) {

                Uri def = Settings.System.DEFAULT_ALARM_ALERT_URI;

                try {
                    mPlayer.reset();
                    mPlayer.setDataSource(TravelShowTime.this, def);
                } catch (IOException e) {

                    e.printStackTrace();
                }
                try {
                    mPlayer.prepare();
                    mPlayer.setLooping(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mPlayer.start();
            } else {

                try {
                    mPlayer.reset();
                    mPlayer.setDataSource(TravelShowTime.this , Uri.parse(alarms));
                } catch (IOException e) {

                    e.printStackTrace();
                }
                try {
                    mPlayer.prepare();
                    mPlayer.setLooping(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mPlayer.start();

            }
        } else {

        }
    }



    private void startVibrate()
    {
        ConnectingWithSettings connecting = new ConnectingWithSettings();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator.hasVibrator() && connecting.isVibrate(TravelShowTime.this))
        {
            long[] pattern = { 0, 200, 500 };
            vibrator.vibrate(pattern, 0);
            if (TravelShowTime.this.isFinishing())
            {
                vibrator.cancel();
            }
        }else
        {

        }
    }

    private void vibrateForWakeup()
    {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator.hasVibrator() && connecting.isVibrate(TravelShowTime.this))
        {
            long[] pattern = { 0, 200, 100 };
            vibrator.vibrate(pattern, 0);
            vibrator.cancel();
            if (TravelShowTime.this.isFinishing())
            {
                vibrator.cancel();
            }
        }else
        {

        }
    }



    private void cleanYourShit() {

        if (TravelShowTime.this.isFinishing())
        {

        }else
        {
            if (mPlayer.isPlaying())
            {
                mPlayer.stop();
                mPlayer.release();
            }

            vibrator.cancel();
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        cleanYourShit();
        vibrator.cancel();
        super.onDestroy();
    }

    public void OurNotification(Context context , String Message) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon_custom)
                .setContentTitle("Nonce : " + getString(R.string.travel))
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
                                PendingIntent.FLAG_UPDATE_CURRENT));
        if (connecting.isToneEnabled(context)) {

            Uri defNote = Settings.System.DEFAULT_NOTIFICATION_URI;
            String setTone = connecting.notificationTone(context);
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


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void onBackPressed() {
    }

    private void dismissAfterMinute() {
        int TheMinute = 60000;

        Handler handler = new Handler();
        Runnable run = new Runnable() {

            @Override
            public void run() {

                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                }

                if (TravelShowTime.this.isFinishing()) {

                } else
                {
                    OurNotification(TravelShowTime.this ,String.format("%s %s , %s" , getString(R.string.doNotMissYourTravel) , travelCountry , travelCity));
                    finish();
                }
            }
        };
        handler.postDelayed(run, TheMinute);
    }
}
