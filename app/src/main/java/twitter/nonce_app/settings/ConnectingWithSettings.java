package twitter.nonce_app.settings;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by zizo on 7/21/2016.
 */
public class ConnectingWithSettings {


    public boolean isToneEnabled (Context context)
    {

        SharedPreferences getSoundEnabled = PreferenceManager.getDefaultSharedPreferences(context);
        return getSoundEnabled.getBoolean("mute_sound" , true);
    }

    public boolean isIconEnabled(Context context)
    {

        SharedPreferences getIconEnabled = PreferenceManager.getDefaultSharedPreferences(context);

        return getIconEnabled.getBoolean("nonce_status_icon" , true);
    }

    public boolean isVibrate (Context context)
    {

        SharedPreferences getVibrationEnabled = PreferenceManager.getDefaultSharedPreferences(context);
        return getVibrationEnabled.getBoolean("vibrate" , true);
    }

    public void toneLevel(Context context)
    {

        SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(context);
        AudioManager audio = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        String toneLevelUp = pre.getString("tone_level" , "Normal");

        switch (toneLevelUp) {
            case "Extremely High":
                audio.setStreamVolume(AudioManager.STREAM_ALARM, audio.getStreamMaxVolume(AudioManager.STREAM_ALARM) , AudioManager.FLAG_PLAY_SOUND);
                break;
            case "High":
                audio.setStreamVolume(AudioManager.STREAM_ALARM, (audio.getStreamMaxVolume(AudioManager.STREAM_ALARM)-2) , AudioManager.FLAG_PLAY_SOUND);
                break;
            case "Normal":
                audio.setStreamVolume(AudioManager.STREAM_ALARM, (audio.getStreamMaxVolume(AudioManager.STREAM_ALARM)/2) , AudioManager.FLAG_PLAY_SOUND);
                break;
            case "Low":
                audio.setStreamVolume(AudioManager.STREAM_ALARM, (audio.getStreamMaxVolume(AudioManager.STREAM_ALARM)/2) - 1 , AudioManager.FLAG_PLAY_SOUND);
                break;
            case "Lowest":
                audio.setStreamVolume(AudioManager.STREAM_ALARM, (audio.getStreamMaxVolume(AudioManager.STREAM_ALARM)/4) , AudioManager.FLAG_PLAY_SOUND);
                break;
            case "مرتفع جداً":
                audio.setStreamVolume(AudioManager.STREAM_ALARM, audio.getStreamMaxVolume(AudioManager.STREAM_ALARM) , AudioManager.FLAG_PLAY_SOUND);
                break;
            case "مرتفع":
                audio.setStreamVolume(AudioManager.STREAM_ALARM, (audio.getStreamMaxVolume(AudioManager.STREAM_ALARM)-2) , AudioManager.FLAG_PLAY_SOUND);
                break;
            case "متوسط":
                audio.setStreamVolume(AudioManager.STREAM_ALARM, (audio.getStreamMaxVolume(AudioManager.STREAM_ALARM)/2) , AudioManager.FLAG_PLAY_SOUND);
                break;
            case "منخفض":
                audio.setStreamVolume(AudioManager.STREAM_ALARM, (audio.getStreamMaxVolume(AudioManager.STREAM_ALARM)/2) - 1 , AudioManager.FLAG_PLAY_SOUND);
                break;
            case "منخفض جداً":
                audio.setStreamVolume(AudioManager.STREAM_ALARM, (audio.getStreamMaxVolume(AudioManager.STREAM_ALARM)/4) , AudioManager.FLAG_PLAY_SOUND);
                break;
        }
    }


    public String notificationTone(Context context) {

        SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(context);
        return pre.getString("notification" , null);
    }

    public String Ringtone (Context context)
    {
        SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(context);
        return pre.getString("ringtone" , null);
    }

    public boolean isSmsPermissionIsWork (Context context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context , Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            {
                return true ;
            }
        }else
        {
            if (ActivityCompat.checkSelfPermission(context , Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            {
                return true ;
            }
        }
        return false ;
    }

}
