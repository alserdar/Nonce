package twitter.nonce_app.call_someone;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CallService extends Service {
    public CallService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null ;

    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        Intent alarmIntent = new Intent(getBaseContext(), CallShowTime.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.putExtras(intent);
        getApplication().startActivity(alarmIntent);
        CallBroadcast.setAlarms(CallService.this);
        return Service.START_FLAG_RETRY;
    }

}
