package twitter.nonce_app.travel;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TravelService extends Service {
    public TravelService() {
    }
    @Override
    public IBinder onBind(Intent intent) {

        return null ;

    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        Intent alarmIntent = new Intent(getBaseContext(), TravelShowTime.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.putExtras(intent);
        getApplication().startActivity(alarmIntent);
        TravelBroadcast.setAlarms(TravelService.this);
        return Service.START_FLAG_RETRY;
    }
}
