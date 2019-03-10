package twitter.nonce_app.make_memo;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import twitter.nonce_app.R;
import twitter.nonce_app.settings.ConnectingWithSettings;
import twitter.nonce_app.toast_package.OurToast;

public class ListedMemo extends ListActivity {

    private static final String NOTIFICATION_TAG = "Nonce";
    String memTitle , memOne , memTwo , memThree , memFour , memFive , memSix;
    TextView memTitleL ;
    Button hold , dismiss ;
    InputFilter[] fArray ;
    AlertDialog.Builder builder ;
    AlertDialog alertDialog ;
    EditText hours ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listed_memo);

        memTitle = this.getIntent().getExtras().getString("memTitle");
        memOne = this.getIntent().getExtras().getString("memOne");
        memTwo = this.getIntent().getExtras().getString("memTwo");
        memThree = this.getIntent().getExtras().getString("memThree");
        memFour = this.getIntent().getExtras().getString("memFour");
        memFive = this.getIntent().getExtras().getString("memFive");
        memSix = this.getIntent().getExtras().getString("memSix");


        memTitleL = (TextView)findViewById(R.id.memTitleList);
        hold = (Button) findViewById(R.id.holdList);
        dismiss = (Button) findViewById(R.id.dismissList);
        memTitleL.setText(getString(R.string.memo));


        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel(ListedMemo.this);
                finish();
            }
        });


        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OurNotification(ListedMemo.this);
                finish();
            }
        });

        setListAdapter(new MyAdapterSecret());

    }

    public class Secret {

        public final String[] Nothingness = {memOne , memTwo , memThree , memFour , memFive , memSix};
    }

    public class MyAdapterSecret extends BaseAdapter {
        @Override
        public int getCount()

        {
            Secret secret = new Secret();
            return secret.Nothingness.length ;
        }

        @Override
        public String getItem(int position)

        {
            Secret secret = new Secret();
            return secret.Nothingness[position];
        }

        @Override
        public long getItemId(int position) {
            Secret secret = new Secret();
            return secret.Nothingness[position].hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.checkable_list_item, container, false);
            }

            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(getItem(position));
            return convertView;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }

    public void OurNotification(Context context) {

        ConnectingWithSettings connecting = new ConnectingWithSettings();
        String notTone = connecting.notificationTone(getBaseContext());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon_custom)
                .setContentTitle("Nonce : " + getString(R.string.memo) + memTitle)
                .setContentText(getString(R.string.memoOnHold))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColor(Color.WHITE)
                .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                .setLights(Color.BLUE , 100 , 100)
                .setOngoing(false)
                .setAutoCancel(false)
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

                                ,PendingIntent.FLAG_UPDATE_CURRENT));
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

    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_TAG, 0, notification);
    }

    public void cancel(Context context) {
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }

   private void delay (Context context)
   {
       hours = new EditText(context);
       builder = new AlertDialog.Builder(context);
       hours.setHint("2 Hours");
       builder.setIcon(R.mipmap.today);
       int maxLength = 2;
       hours.setInputType(InputType.TYPE_CLASS_NUMBER);
       fArray = new InputFilter[1];
       fArray[0] = new InputFilter.LengthFilter(maxLength);
       hours.setFilters(fArray);
       hours.setGravity(Gravity.CENTER);
       builder.setTitle("Delay your Memo ");
       builder.setView(hours);
       builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

               if (hours.getText().toString().equals(""))
               {
                   new OurToast().myToast(getBaseContext() , getString(R.string.emptyField));
               }else if (Integer.parseInt(hours.getText().toString()) > 24)
               {
                   new OurToast().myToast(getBaseContext() , "24 hours Maximum");
               }else
               {
                   Intent i = new Intent(getBaseContext() , DelayBroadcast.class);
                   i.putExtra("hoursDelay" , Integer.parseInt(hours.getText().toString()));
                   sendBroadcast(i);
                   finish();
               }

           }
       });
       builder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

           }
       });
       alertDialog = builder.create();
       alertDialog.show();
   }

    @Override
    public void onBackPressed() {
        OurNotification(ListedMemo.this);
        super.onBackPressed();
    }
}
