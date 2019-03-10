package twitter.nonce_app.send_sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.SmsManager;

import twitter.nonce_app.R;
import twitter.nonce_app.notification_package.NotificationsNonceMessage;
import twitter.nonce_app.settings.ConnectingWithSettings;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SMSService extends Service {
    public SMSService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null ;
    }
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    String numb = intent.getStringExtra(SMSBroadCast.CONTACT_NUMBER_FOR_SMS);
                    String name = intent.getStringExtra(SMSBroadCast.CONTACT_NAME_FOR_SMS);
                    String sms = intent.getStringExtra(SMSBroadCast.MESSAGE_BODY_FOR_SMS);
                    ConnectingWithSettings connect = new ConnectingWithSettings();

                    if (connect.isSmsPermissionIsWork(getBaseContext())) {

                        Intent i = new Intent(getBaseContext(), SentSMSActivityShow.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("SMSName", name);
                        i.putExtra("SMSNumber", numb);
                        i.putExtra("SMSBody", sms);
                        i.putExtras(intent);
                        getApplication().startActivity(i);
                        SMSBroadCast.setAlarms(SMSService.this);

                    } else {
                        try {

                            String SENT = "sent";
                            String DELIVERED = "delivered";

                            Intent sentIntent = new Intent(SENT);
     /*Create Pending Intents*/
                            PendingIntent sentPI = PendingIntent.getBroadcast(
                                    getApplicationContext(), 0, sentIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);

                            Intent deliveryIntent = new Intent(DELIVERED);

                            PendingIntent deliverPI = PendingIntent.getBroadcast(
                                    getApplicationContext(), 0, deliveryIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
     /* Register for SMS send action */
                            registerReceiver(new BroadcastReceiver() {

                                @Override
                                public void onReceive(Context context, Intent intent) {
                                    String result = "";

                                    switch (getResultCode()) {

                                        case Activity.RESULT_OK:
                                            result = getString(R.string.smsSent);
                                            break;
                                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                            result = getString(R.string.smsFailed);
                                            break;
                                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                                            result = getString(R.string.smsFailed);
                                            break;
                                        case SmsManager.RESULT_ERROR_NULL_PDU:
                                            result = getString(R.string.smsFailed);
                                            break;
                                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                                            result = getString(R.string.smsFailed);
                                            break;
                                    }
                                    new NotificationsNonceMessage().OurNotification(context, result);

                                }

                            }, new IntentFilter(SENT));
     /* Register for Delivery event */
                            registerReceiver(new BroadcastReceiver() {

                                @Override
                                public void onReceive(Context context, Intent intent) {
                                    new NotificationsNonceMessage().OurNotification(context, getString(R.string.smsDelivered));
                                }

                            }, new IntentFilter(DELIVERED));

      /*Send SMS*/
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(numb, null, sms, sentPI,
                                    deliverPI);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                } catch (IllegalArgumentException e) {

                    e.getMessage();
                }


            }
        }).start();

        return Service.START_FLAG_RETRY;

    }
}
