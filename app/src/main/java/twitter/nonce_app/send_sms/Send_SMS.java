package twitter.nonce_app.send_sms;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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

public class Send_SMS extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_CONTACTS = 13;
    String contactNameForSMS;
    String contactNumberForSMS;
    private Uri _uriContact ;
    private static final String LAST_TEXT_Sms = "";

    TextInputEditText bodyMessage ;
    Button saveSendSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__sms);

        bodyMessage = (TextInputEditText)findViewById(R.id.messageBody);
        saveSendSMS = (Button)findViewById(R.id.saveSMS);

        //final SharedPreferences prefSMS = PreferenceManager.getDefaultSharedPreferences(Send_SMS.this);
        final SharedPreferences sharedPreferencesSMS = getPreferences(MODE_PRIVATE);
        bodyMessage.setText(sharedPreferencesSMS.getString(LAST_TEXT_Sms, ""));

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (bodyMessage.getText().toString().equals(""))
                {
                    saveSendSMS.setClickable(false);
                    saveSendSMS.setBackgroundResource(R.drawable.gone_grey);
                }else
                {
                    saveSendSMS.setClickable(true);
                    saveSendSMS.setBackgroundResource(R.drawable.button_custom);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                sharedPreferencesSMS.edit().putString(LAST_TEXT_Sms, editable.toString()).apply();
            }
        };

        bodyMessage.addTextChangedListener(watcher);


        saveSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferencesSMS.edit().remove(LAST_TEXT_Sms).apply();

                if (bodyMessage.getText().toString().equals(""))
                {
                    saveSendSMS.setClickable(false);
                    saveSendSMS.setBackgroundResource(R.drawable.gone_grey);
                }else
                {
                    saveSendSMS.setClickable(true);
                    saveSendSMS.setBackgroundResource(R.drawable.button_custom);
                    luanchToContacts();
                }

            }
        });
    }

    private void luanchToContacts ()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent pickNumber = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                pickNumber.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                pickNumber.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                pickNumber.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(pickNumber, REQUEST_CODE_PICK_CONTACTS);
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK)
        {
            _uriContact = data.getData();
            returnContactName();
            returnContactNumber();
            new OurToast().myToast(getBaseContext() , getString(R.string.contactSaved));
            lunchToDetails();
        }

    }

    public void lunchToDetails ()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                long id = -1 ;
                Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
                intent.putExtra("id" , id);
                intent.putExtra("alarmName", getString(R.string.sms));
                intent.putExtra("theEvent", "SMS");
                intent.putExtra("extraInfo" ,  contactNameForSMS );
                intent.putExtra("contactNameForSMS" , contactNameForSMS);
                intent.putExtra("contactNumberForSMS" , contactNumberForSMS);
                intent.putExtra("messageBodyForSMS", bodyMessage.getText().toString());
                startActivity(intent);
                finish();
            }
        }).start();
    }

    public void returnContactName ()
    {

        Cursor cursor = getContentResolver().query(_uriContact, null, null, null, null);

        assert cursor != null;
        if (cursor.moveToFirst()) {

            contactNameForSMS = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

        }

        cursor.close();

    }


    public void returnContactNumber ()
    {
        try {

            if (contactNameForSMS != null)
            {
                String [] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(_uriContact , projection , null , null , null);
                assert cursor != null;
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                contactNumberForSMS = cursor.getString(column);
            }

        }catch (Exception e)
        {
            e.getMessage();
        }
    }

    @Override
    public void onBackPressed() {
        if (bodyMessage.getText().toString().equals(""))
        {
            Intent i = new Intent(Send_SMS.this , Home.class);
            startActivity(i);
            finish();
        }else
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.discardSMS);
            builder.setCancelable(true);
            builder.setIcon(R.mipmap.post_office);
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