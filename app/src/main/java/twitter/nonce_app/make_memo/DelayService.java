package twitter.nonce_app.make_memo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import twitter.nonce_app.ShowTimeActivity;

public class DelayService extends Service {
    public DelayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null ;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        Intent alarmIntent = new Intent(getBaseContext(), ShowTimeActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.putExtras(intent);
        getApplication().startActivity(alarmIntent);
        DelayBroadcast.setAlarms(DelayService.this);
        return Service.START_FLAG_RETRY;
    }
}
