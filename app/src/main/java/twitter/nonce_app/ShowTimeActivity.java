package twitter.nonce_app;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import twitter.nonce_app.make_memo.ListedMemo;
import twitter.nonce_app.notification_package.NotificationsNonceMessage;
import twitter.nonce_app.settings.ConnectingWithSettings;

import static java.lang.String.format;

public class ShowTimeActivity extends AppCompatActivity {


    private static final String NOTIFICATION_TAG = "Nonce";
    MediaPlayer mPlayer = new MediaPlayer();
    Vibrator vibrator ;
    ConnectingWithSettings connecting = new ConnectingWithSettings();
    Button dismissButton, snoozeButton;
    String name , boomTime , snoozing , extraInfo , memTitle , memOne , memTwo , memThree , memFour , memFive , memSix;
    private TextView extraUp ,extraDown , tvName , extraInformation;
    private final Calendar cal = Calendar.getInstance();
    int timeMinute, timeHour, originalMinute, originalHour, originalDay, originalMonth, originalYear;
    long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_show_time);
        KeyguardManager kgMgr = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        boolean showing = kgMgr.inKeyguardRestrictedInputMode();

        if (showing)
        {
            ShowTimeActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
            ShowTimeActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            ShowTimeActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            ShowTimeActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            View v = getLayoutInflater().inflate(R.layout.activity_show_time , null);
            v.setKeepScreenOn(true);
            playTheRingtone();

        }else
        {

            new NotificationsNonceMessage().OurNotification(getBaseContext() , getString(R.string.yourMemoOnBoard));
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
        id = this.getIntent().getExtras().getLong(OurBroadCast.ID);
        originalHour = this.getIntent().getIntExtra(OurBroadCast.TIME_HOUR, 0);
        originalMinute = this.getIntent().getIntExtra(OurBroadCast.TIME_MINUTE, 0);
        originalDay = this.getIntent().getIntExtra(OurBroadCast.DAY, 0);
        originalMonth = this.getIntent().getIntExtra(OurBroadCast.MONTH, 0);
        originalYear = this.getIntent().getIntExtra(OurBroadCast.YEAR, 0);
        name = this.getIntent().getStringExtra(OurBroadCast.NAME);
        snoozing = this.getIntent().getStringExtra(OurBroadCast.SNOOZE_TIME);

        memTitle = this.getIntent().getStringExtra(OurBroadCast.MEM_TITLE);
        memOne = this.getIntent().getStringExtra(OurBroadCast.MEM_ONE);
        memTwo = this.getIntent().getStringExtra(OurBroadCast.MEM_TWO);
        memThree = this.getIntent().getStringExtra(OurBroadCast.MEM_THREE);
        memFour = this.getIntent().getStringExtra(OurBroadCast.MEM_FOUR);
        memFive = this.getIntent().getStringExtra(OurBroadCast.MEM_FIVE);
        memSix = this.getIntent().getStringExtra(OurBroadCast.MEM_SIX);

        timeMinute = cal.get(Calendar.MINUTE);
        timeHour = cal.get(Calendar.HOUR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        final int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        boomTime = format(Locale.getDefault() , "%02d:%02d", timeHour, timeMinute);
        tvTime.setText(boomTime);
        tvDate.setText(format(Locale.getDefault(), "%02d/%02d/%01d", day, month + 1, year));
        tvName.setText(name);

        playWithTexts();
        startVibrate();
        dismissAfterMinute();
        // start views

    }

    private void cleanYourShit() {

        if (ShowTimeActivity.this.isFinishing())
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
        if (vibrator.hasVibrator() && connecting.isVibrate(ShowTimeActivity.this))
        {
            long[] pattern = { 0, 200, 100 };
            vibrator.vibrate(pattern, 0);
            vibrator.cancel();
            if (ShowTimeActivity.this.isFinishing())
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
        if (vibrator.hasVibrator() && connecting.isVibrate(ShowTimeActivity.this))
        {
            long[] pattern = { 0, 200, 500 };
            vibrator.vibrate(pattern, 0);
            if (ShowTimeActivity.this.isFinishing())
            {
                vibrator.cancel();
            }
        }else
        {

        }
    }

    private void playWithTexts() {

        if (memTitle == null)
        {

        }else
        {
            extraUp.setText(String.format("%s %s", getString(R.string.memo), memTitle));
            extraUp.setTextSize(18);
        }
        dismissButton.setText(R.string.dismiss);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.stop();
                cancel(ShowTimeActivity.this);
                cleanYourShit();
                finish();
            }
        });

        snoozeButton.setText(R.string.view_memo);
        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.stop();
                cleanYourShit();
                Intent i = new Intent(getBaseContext() , ListedMemo.class);
                i.putExtra("memTitle" , memTitle);
                i.putExtra("memOne" , memOne);
                i.putExtra("memTwo" , memTwo);
                i.putExtra("memThree" , memThree);
                i.putExtra("memFour" , memFour);
                i.putExtra("memFive" , memFive);
                i.putExtra("memSix" , memSix);
                startActivity(i);
                finish();
            }
        });

    }


    private void playTheRingtone() {

        ConnectingWithSettings connecting = new ConnectingWithSettings();
        if (connecting.isToneEnabled(ShowTimeActivity.this)) {
            String alarms = connecting.Ringtone(ShowTimeActivity.this);
            mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            if (alarms == null) {

                Uri def = Settings.System.DEFAULT_ALARM_ALERT_URI;

                try {
                    mPlayer.reset();
                    mPlayer.setDataSource(ShowTimeActivity.this, def);
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
                connecting.toneLevel(ShowTimeActivity.this);
            } else {

                try {
                    mPlayer.reset();
                    mPlayer.setDataSource(ShowTimeActivity.this , Uri.parse(alarms));
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
                connecting.toneLevel(ShowTimeActivity.this);
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

                if (ShowTimeActivity.this.isFinishing())
                {

                }else
                {
                    OurNotification(ShowTimeActivity.this);
                    cleanYourShit();
                    finish();
                }
            }
        };
        handler.postDelayed(run, TheMinute);
    }

    public void OurNotification(Context context) {

        String notTone = connecting.notificationTone(getBaseContext());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon_custom)
                .setContentTitle("Nonce : " + name)
                .setContentText(getString(R.string.yourMemoOnTheHold))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColor(Color.WHITE)
                .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                .setTicker(getString(R.string.donNotMissYourMemo))
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
                                new Intent(getBaseContext() , ListedMemo.class)
                                        .putExtra("memTitle" , memTitle)
                                        .putExtra("memOne" , memOne)
                                        .putExtra("memTwo" , memTwo)
                                        .putExtra("memThree" , memThree)
                                        .putExtra("memFour" , memFour)
                                        .putExtra("memFive" , memFive)
                                        .putExtra("memSix" , memSix)

                                ,PendingIntent.FLAG_UPDATE_CURRENT))

                .setStyle(new NotificationCompat.BigTextStyle()
                       // .bigText("Nonce was ringing worthlessly , will be right back in a few minutes")
                        .setBigContentTitle("Nonce : " + name));
        Uri defTone = Settings.System.DEFAULT_NOTIFICATION_URI;
        if (connecting.isToneEnabled(this)) {

            if (notTone == null)
            {
                builder.setSound(defTone);
            }else
            {
                builder.setSound(Uri.parse(notTone));
            }

        }
        notify(context, builder.build());
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

    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_TAG, 0, notification);
    }

    public void cancel(Context context) {
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }
}
