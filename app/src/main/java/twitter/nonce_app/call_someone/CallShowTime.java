package twitter.nonce_app.call_someone;

import android.Manifest;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import twitter.nonce_app.Home;
import twitter.nonce_app.R;
import twitter.nonce_app.settings.ConnectingWithSettings;

import static java.lang.String.format;

public class CallShowTime extends AppCompatActivity {


    private static final String NOTIFICATION_TAG = "Nonce";
    MediaPlayer mPlayer = new MediaPlayer();
    Vibrator vibrator ;
    ConnectingWithSettings connecting = new ConnectingWithSettings();
    Button dismissButton, snoozeButton;
    String name , contactNumber ,  whyCall ,  contactName ,boomTime , snoozing , extraInfo ;
    private TextView extraUp ,extraDown , tvName , extraInformation;
    private final Calendar cal = Calendar.getInstance();
    int timeMinute, timeHour, originalMinute, originalHour, originalDay, originalMonth, originalYear;
    long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_show_time);



        KeyguardManager kgMgr = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        boolean showing = kgMgr.inKeyguardRestrictedInputMode();

        if (showing)
        {
            CallShowTime.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
            CallShowTime.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            CallShowTime.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            CallShowTime.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            View v = getLayoutInflater().inflate(R.layout.activity_call_show_time , null);
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
        TextView tvTime = (TextView) findViewById(R.id.alarm_screen_time);
        TextView tvDate = (TextView) findViewById(R.id.alarm_screen_date);
        dismissButton = (Button) findViewById(R.id.alarm_screen_button);
        snoozeButton = (Button) findViewById(R.id.repeat_screen_button);

        //refrences
        id = this.getIntent().getExtras().getLong(CallBroadcast.ID);
        originalHour = this.getIntent().getIntExtra(CallBroadcast.TIME_HOUR, 0);
        originalMinute = this.getIntent().getIntExtra(CallBroadcast.TIME_MINUTE, 0);
        originalDay = this.getIntent().getIntExtra(CallBroadcast.DAY, 0);
        originalMonth = this.getIntent().getIntExtra(CallBroadcast.MONTH, 0);
        originalYear = this.getIntent().getIntExtra(CallBroadcast.YEAR, 0);
        name = this.getIntent().getStringExtra(CallBroadcast.NAME);
        snoozing = this.getIntent().getStringExtra(CallBroadcast.SNOOZE_TIME);

        whyCall = this.getIntent().getStringExtra(CallBroadcast.WHY_CALL);
        contactName = this.getIntent().getStringExtra(CallBroadcast.CONTACT_NAME);
        contactNumber = this.getIntent().getStringExtra(CallBroadcast.CONTACT_NUMBER);
        extraInfo = this.getIntent().getStringExtra(CallBroadcast.EXTRA_INFO);

        timeMinute = cal.get(Calendar.MINUTE);
        timeHour = cal.get(Calendar.HOUR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        final int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        boomTime = format(Locale.getDefault() , "%02d:%02d", timeHour, timeMinute);
        tvTime.setText(boomTime);
        tvDate.setText(format(Locale.getDefault(), "%02d/%02d/%01d", day, month + 1, year));
        tvName.setText(R.string.call_someone);

        if (whyCall == null) {

            extraUp.setText(String.format("%s %s", getString(R.string.timeToCall), contactName));

        } else {
            extraUp.setText(String.format("%s %s", getString(R.string.timeToCall), contactName));
            extraDown.setText(String.format(" %s ", whyCall));
        }

        dismissButton.setText(R.string.dismiss);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.stop();
                cleanYourShit();
                finish();
            }
        });
        snoozeButton.setText(R.string.call);
        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPlayer.stop();
                if (contactName != null) {
                    startCall();
                } else {
                    Intent getPhone = new Intent(Intent.ACTION_DIAL);
                    startActivity(getPhone);
                }
                cleanYourShit();
                finish();
            }
        });

        startVibrate();
        dismissAfterMinute();

    }

    private void cleanYourShit() {

        if (CallShowTime.this.isFinishing())
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

    private void vibrateForWakeup()
    {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator.hasVibrator() && connecting.isVibrate(CallShowTime.this))
        {
            long[] pattern = { 0, 200, 100 };
            vibrator.vibrate(pattern, 0);
            vibrator.cancel();
            if (CallShowTime.this.isFinishing())
            {
                vibrator.cancel();
            }
        }else
        {

        }
    }


    private void startVibrate()
    {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator.hasVibrator() && connecting.isVibrate(CallShowTime.this))
        {
            long[] pattern = { 0, 200, 500 };
            vibrator.vibrate(pattern, 0);
            if (CallShowTime.this.isFinishing())
            {
                vibrator.cancel();
            }
        }else
        {

        }
    }


    private void playTheRingtone() {

        ConnectingWithSettings connecting = new ConnectingWithSettings();
        if (connecting.isToneEnabled(CallShowTime.this)) {
            String alarms = connecting.Ringtone(CallShowTime.this);
            mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            if (alarms == null) {

                Uri def = Settings.System.DEFAULT_ALARM_ALERT_URI;

                try {
                    mPlayer.reset();
                    mPlayer.setDataSource(CallShowTime.this, def);
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
                connecting.toneLevel(CallShowTime.this);
            } else {

                try {
                    mPlayer.reset();
                    mPlayer.setDataSource(CallShowTime.this , Uri.parse(alarms));
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
                connecting.toneLevel(CallShowTime.this);
            }
        } else {

        }
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

                if (CallShowTime.this.isFinishing())
                {

                }else
                {
                    OurNotification(CallShowTime.this , contactName + getString(R.string.theNumberOnTheBoard));
                    Intent getPhone = new Intent(Intent.ACTION_DIAL);
                    getPhone.setData(Uri.parse("tel:" + contactNumber));
                    startActivity(getPhone);
                    cleanYourShit();
                    finish();
                }
            }
        };
        handler.postDelayed(run, TheMinute);
    }


    @Override
    protected void onDestroy() {
        cleanYourShit();
        vibrator.cancel();
        super.onDestroy();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    //start call method
    private void startCall() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(CallShowTime.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    Intent getPhone = new Intent(Intent.ACTION_DIAL);
                    getPhone.setData(Uri.parse("tel:" + contactNumber));
                    startActivity(getPhone);
                } else {

                    Intent getPhone = new Intent(Intent.ACTION_CALL);
                    getPhone.setData(Uri.parse("tel:" + contactNumber));
                    startActivity(getPhone);
                }
            }
        }).start();

    }

    public void OurNotification(Context context , String Message) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon_custom)
                .setContentTitle("Nonce : "+ getString(R.string.call_someone))
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
                        .setBigContentTitle("Nonce : "+ getString(R.string.call_someone)));
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

}
