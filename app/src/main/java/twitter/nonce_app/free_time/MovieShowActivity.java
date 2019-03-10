package twitter.nonce_app.free_time;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import twitter.nonce_app.R;
import twitter.nonce_app.notification_package.NotificationsNonceMessage;
import twitter.nonce_app.settings.ConnectingWithSettings;

import static java.lang.String.format;

public class MovieShowActivity extends AppCompatActivity {

    TextView extraUp , extraDown , extraInformation , tvName  , tvTime , tvDate;

    Button dismissButton , snoozeButton ;
    String alarms = null ;

    private MediaPlayer mPlayer = new MediaPlayer();
    Vibrator vibrator ;
    String moviesName ;
    ConnectingWithSettings connecting = new ConnectingWithSettings();
    final Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_show);



        KeyguardManager kgMgr = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        boolean showing = kgMgr.inKeyguardRestrictedInputMode();

        if (showing)
        {
            MovieShowActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
            MovieShowActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            MovieShowActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            MovieShowActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            View v = getLayoutInflater().inflate(R.layout.activity_movie_show , null);
            v.setKeepScreenOn(true);
            playTheRingtone();

        }else
        {

            new NotificationsNonceMessage().OurNotification(MovieShowActivity.this , getString(R.string.timeToWatch) + moviesName);
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

        moviesName = getIntent().getExtras().getString(MoviesAlarmBroadCast.MoviesName);

        int timeMinute = cal.get(Calendar.MINUTE);
        int timeHour = cal.get(Calendar.HOUR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        final int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String  boomTime = format(Locale.getDefault() , "%02d:%02d", timeHour, timeMinute);
        tvTime.setText(boomTime);
        tvDate.setText(format(Locale.getDefault(), "%02d/%02d/%01d", day, month + 1, year));
        tvName.setText(R.string.nonce_movies);


        extraUp.setText(R.string.timeToWatchMovie);
        extraDown.setText(moviesName);

        playTheRingtone();
        startVibrate();
        dismissAfterMinute();





        dismissButton.setText(R.string.dismiss);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mPlayer.stop();
                        finish();
                    }
                }).start();
            }
        });


        snoozeButton.setText(R.string.search);
        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mPlayer.stop();
                        String url = "http://www.google.com/search?q="+ moviesName;
                        Intent iSearch = new Intent(Intent.ACTION_VIEW);
                        iSearch.setData(Uri.parse(url));
                        startActivity(iSearch);
                        finish();
                    }
                }).start();
            }
        });
    }

    private void playTheRingtone() {

        if (connecting.isToneEnabled(MovieShowActivity.this)) {
            alarms = connecting.Ringtone(MovieShowActivity.this);

            mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            if (alarms == null) {

                Uri def = Settings.System.DEFAULT_ALARM_ALERT_URI;

                try {
                    mPlayer.reset();
                    mPlayer.setDataSource(MovieShowActivity.this, def);

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
                    mPlayer.setDataSource(MovieShowActivity.this , Uri.parse(alarms));
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
        if (vibrator.hasVibrator() && connecting.isVibrate(MovieShowActivity.this))
        {
            long[] pattern = { 0, 200, 500 };
            vibrator.vibrate(pattern, 0);
            if (MovieShowActivity.this.isFinishing())
            {
                vibrator.cancel();
            }
        }else
        {

        }
    }


    private void cleanYourShit() {

        if (MovieShowActivity.this.isFinishing())
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
    public void onBackPressed() {
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

    private void dismissAfterMinute() {
        int TheMinute = 60000;

        Handler handler = new Handler();
        Runnable run = new Runnable() {

            @Override
            public void run() {

                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                }

                if (MovieShowActivity.this.isFinishing()) {

                } else
                {
                    new NotificationsNonceMessage().OurNotification(MovieShowActivity.this , getString(R.string.timeToWatch) + moviesName);
                    finish();
                }
            }
        };
        handler.postDelayed(run, TheMinute);
    }
    private void vibrateForWakeup()
    {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator.hasVibrator() && connecting.isVibrate(MovieShowActivity.this))
        {
            long[] pattern = { 0, 200, 100 };
            vibrator.vibrate(pattern, 0);
            vibrator.cancel();
            if (MovieShowActivity.this.isFinishing())
            {
                vibrator.cancel();
            }
        }else
        {

        }
    }
}
