package twitter.nonce_app.free_time;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MoviesAlarmService extends Service {
    public MoviesAlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null ;

    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        Intent alarmIntent = new Intent(getBaseContext(), MovieShowActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.putExtras(intent);
        getApplication().startActivity(alarmIntent);
        MoviesAlarmBroadCast.setAlarms(MoviesAlarmService.this);
        return Service.START_FLAG_RETRY;
    }
}
