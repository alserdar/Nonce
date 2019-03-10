package twitter.nonce_app.call_someone;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import twitter.nonce_app.DetailsActivity;
import twitter.nonce_app.Home;
import twitter.nonce_app.R;
import twitter.nonce_app.toast_package.OurToast;

public class CallSomeoneActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_CONTACTS = 12;
    private Uri _uriContact ;
    String _contactName ;
    String contactNumber ;
    TextInputEditText callingFor ;
    Button getContacts ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_someone);

        callingFor = (TextInputEditText) findViewById(R.id.callingFor);
        getContacts = (Button)findViewById(R.id.getContactsForCallSomeone);
        getContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                luanchToContacts();
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
            lunchToDetails();
            new OurToast().myToast(getBaseContext() , getString(R.string.contactSaved));
            finish();
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
                intent.putExtra("alarmName", getString(R.string.call_someone));
                intent.putExtra("theEvent", "Call Someone");
                intent.putExtra("extraInfo" ,  _contactName );
                intent.putExtra("contactName" , _contactName);
                intent.putExtra("whyCall", callingFor.getText().toString());
                intent.putExtra("contactNumber" , contactNumber);
                startActivity(intent);
            }
        }).start();
    }

    public void returnContactName ()
    {

        Cursor cursor = getContentResolver().query(_uriContact, null, null, null, null);

        assert cursor != null;
        if (cursor.moveToFirst()) {

            _contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

        }

        cursor.close();

    }


    public void returnContactNumber ()
    {
        try {

            if (_contactName != null)
            {
                String [] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(_uriContact , projection , null , null , null);
                assert cursor != null;
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                contactNumber = cursor.getString(column);
            }

        }catch (Exception e)
        {
            e.getMessage();
        }
    }

    @Override
    public void onBackPressed() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(CallSomeoneActivity.this , Home.class);
                startActivity(i);
                finish();
            }
        }).start();

    }
}
