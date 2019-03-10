package twitter.nonce_app.travel;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import twitter.nonce_app.DetailsActivity;
import twitter.nonce_app.Home;
import twitter.nonce_app.R;

public class TravelActivity extends AppCompatActivity {

    TextInputEditText  travelDetailsET , travelLongET ;
    Button travelNext ;
    String travelFrom ;
    AutoCompleteTextView travelCountryET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        travelFrom = this.getIntent().getExtras().getString("countryIs");

        travelCountryET = (AutoCompleteTextView)findViewById(R.id.travelCountry);
        travelDetailsET = (TextInputEditText)findViewById(R.id.travelDetails);
        travelLongET = (TextInputEditText)findViewById(R.id.travelLong);
        travelNext = (Button) findViewById(R.id.nextForTravel);

        travelCountryET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                travelCountryET.setText("");
            }
        });

        travelDetailsET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                travelDetailsET.setText("");
            }
        });

        travelLongET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                travelLongET.setText("");
            }
        });

        Resources res = this.getResources();
        String countries []= res.getStringArray(R.array.countries_array);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, countries);

        travelCountryET.setAdapter(adapter);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (travelCountryET.getText().toString().trim().equals("") ||
                        travelDetailsET.getText().toString().trim().equals(""))
                {
                    travelNext.setClickable(false);
                    travelNext.setBackgroundResource(R.drawable.gone_grey);
                }else
                {
                    travelNext.setClickable(true);
                    travelNext.setBackgroundResource(R.drawable.button_custom);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        travelCountryET.addTextChangedListener(watcher);
        travelDetailsET.addTextChangedListener(watcher);

        travelNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (travelCountryET.getText().toString().trim().equals("") ||
                                travelDetailsET.getText().toString().trim().equals(""))
                        {
                            travelNext.setClickable(false);
                            travelNext.setBackgroundResource(R.drawable.gone_grey);
                        }else {
                            travelNext.setClickable(true);
                            long id = -1;
                            Intent iTravel = new Intent(getBaseContext(), DetailsActivity.class);
                            iTravel.putExtra("id", id);
                            iTravel.putExtra("alarmName", getString(R.string.travel));
                            iTravel.putExtra("theEvent", "Travel");
                            iTravel.putExtra("travelCurrentCountry", travelFrom);
                            iTravel.putExtra("extraInfo", travelCountryET.getText().toString());
                            iTravel.putExtra("travelCountry", travelCountryET.getText().toString());
                            iTravel.putExtra("travelPurpose", travelDetailsET.getText().toString());
                            iTravel.putExtra("travelDays", travelLongET.getText().toString());
                            startActivity(iTravel);
                            finish();
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent iHome = new Intent(getBaseContext() , Home.class);
        startActivity(iHome);
        super.onBackPressed();
    }
}
