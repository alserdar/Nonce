package twitter.nonce_app.hangout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import twitter.nonce_app.DetailsActivity;
import twitter.nonce_app.Home;
import twitter.nonce_app.R;

public class HangoutActivity extends AppCompatActivity {

    TextInputEditText hang_Destination , hang_City , hang_Details  ;
    Button next ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangout);

        hang_City = (TextInputEditText) findViewById(R.id.hangCity);
        final String country = this.getIntent().getExtras().getString("countryIs");
        if (country == null)
        {
            hang_City.setText("");
        }else
        {
            hang_City.setText(String.format(" , %s", country));
        }


        hang_Details = (TextInputEditText) findViewById(R.id.hangDetails);

        hang_Destination = (TextInputEditText) findViewById(R.id.phang_destination);

        hang_City.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (country == null)
                {
                    hang_City.setText("");
                }else
                {
                    hang_City.setText(String.format(" , %s", country));
                }
            }
        });

        hang_Destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hang_Destination.setText("");
            }
        });

        hang_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hang_Details.setText("");
            }
        });

        next = (Button)findViewById(R.id.nextForHangout);


        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (hang_City.getText().toString().equals("") ||
                        hang_Destination.getText().toString().equals("")||
                        hang_Details.getText().toString().equals(""))
                {
                    next.setClickable(false);
                    next.setBackgroundResource(R.drawable.gone_grey);
                }else
                {
                    next.setClickable(true);
                    next.setBackgroundResource(R.drawable.button_custom);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        hang_City.addTextChangedListener(watcher);
        hang_Details.addTextChangedListener(watcher);
        hang_Destination.addTextChangedListener(watcher);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (hang_City.getText().toString().equals("") ||
                                hang_Destination.getText().toString().equals("")||
                                hang_Details.getText().toString().equals(""))
                        {
                            next.setClickable(false);
                            next.setBackgroundResource(R.drawable.gone_grey);
                        }else
                        {
                            next.setClickable(true);

                            long id = -1;
                            Intent iHang = new Intent(getBaseContext() , DetailsActivity.class);
                            iHang.putExtra("id" , id);
                            iHang.putExtra("alarmName" , getString(R.string.hangout));
                            iHang.putExtra("theEvent", "Hangout");
                            iHang.putExtra("currentLocationForHang" , hang_City.getText().toString() );
                            iHang.putExtra("hangActivity" , hang_Details.getText().toString() );
                            iHang.putExtra("hangPlace" , hang_Destination.getText().toString() );
                            iHang.putExtra("extraInfo" , hang_Destination.getText().toString());
                            startActivity(iHang);
                        }

                    }
                }).start();
            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent iHang = new Intent(getBaseContext() , Home.class);
        startActivity(iHang);
    }
}
