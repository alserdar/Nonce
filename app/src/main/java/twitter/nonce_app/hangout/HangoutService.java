package twitter.nonce_app.hangout;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class HangoutService extends Service {
    public HangoutService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null ;

    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        Intent alarmIntent = new Intent(getBaseContext(), HangoutShowTime.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.putExtras(intent);
        getApplication().startActivity(alarmIntent);
        HangoutBroadcast.setAlarms(HangoutService.this);
        return Service.START_FLAG_RETRY;
    }
}
