package twitter.nonce_app.socail;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import twitter.nonce_app.DetailsActivity;
import twitter.nonce_app.Home;
import twitter.nonce_app.R;
import twitter.nonce_app.toast_package.OurToast;

public class JustUpdateStatus extends AppCompatActivity {

    TextInputEditText updateStatusEditText ;
    private static final String LAST_TEXT_Status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_update_status);

        updateStatusEditText = (TextInputEditText)findViewById(R.id.updateStatusFacebook);
        final Button saveUpdateStatus = (Button)findViewById(R.id.saveUpdateStatus);
        final SharedPreferences facebookDetails = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String facebookUserName = facebookDetails.getString("FacebookUser" , null);
        new OurToast().myToast(getBaseContext() , getString(R.string.welome) + facebookUserName);


       // final SharedPreferences prefFacebook = PreferenceManager.getDefaultSharedPreferences(JustUpdateStatus.this);
        final SharedPreferences sharedPreferencesFacebook = getPreferences(MODE_PRIVATE);
        updateStatusEditText.setText(sharedPreferencesFacebook.getString(LAST_TEXT_Status, ""));

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (updateStatusEditText.getText().toString().trim().equals(""))
                {
                    saveUpdateStatus.setClickable(false);
                    saveUpdateStatus.setBackgroundResource(R.drawable.gone_grey);
                }else
                {
                    saveUpdateStatus.setClickable(true);
                    saveUpdateStatus.setBackgroundResource(R.drawable.button_custom);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                sharedPreferencesFacebook.edit().putString(LAST_TEXT_Status, editable.toString()).apply();

            }
        };
        updateStatusEditText.addTextChangedListener(watcher);

        saveUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferencesFacebook.edit().remove(LAST_TEXT_Status).apply();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (updateStatusEditText.getText().toString().trim().equals(""))
                        {
                            updateStatusEditText.setClickable(false);
                        }else
                        {
                            saveUpdateStatus.setClickable(true);
                            saveUpdateStatus.setBackgroundResource(R.drawable.button_custom);
                            long id = -1 ;
                            Intent i = new Intent(getBaseContext() , DetailsActivity.class);
                            i.putExtra("id" , id);
                            i.putExtra("alarmName" , getString(R.string.updateStatus));
                            i.putExtra("theEvent", "Update Status");
                            i.putExtra("updateStatus" , updateStatusEditText.getText().toString());
                            i.putExtra("extraInfo" , updateStatusEditText.getText().toString());
                            startActivity(i);
                        }

                    }
                }).start();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (updateStatusEditText.getText().toString().equals(""))
        {
            Intent iHome = new Intent(getBaseContext() , Home.class);
            startActivity(iHome);
        }else
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.discardPost);
            builder.setCancelable(true);
            builder.setIcon(R.mipmap.facebook);
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Intent iHome = new Intent(getBaseContext() , Home.class);
                            startActivity(iHome);
                            finish();
                        }
                    }).start();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
