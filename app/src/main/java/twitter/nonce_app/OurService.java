package twitter.nonce_app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class OurService extends Service {
    public OurService() {
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
        OurBroadCast.setAlarms(OurService.this);
            return Service.START_FLAG_RETRY;
    }

}
