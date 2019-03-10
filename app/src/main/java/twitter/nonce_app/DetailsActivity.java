package twitter.nonce_app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

import twitter.nonce_app.call_someone.CallBroadcast;
import twitter.nonce_app.call_someone.CallSomeoneActivity;
import twitter.nonce_app.free_time.MoviesAlarmBroadCast;
import twitter.nonce_app.get_country.GetCountryBySimCard;
import twitter.nonce_app.hangout.HangoutActivity;
import twitter.nonce_app.hangout.HangoutBroadcast;
import twitter.nonce_app.hangout.PrivateOrNotForHangout;
import twitter.nonce_app.make_memo.MakeMemo;
import twitter.nonce_app.send_sms.SMSBroadCast;
import twitter.nonce_app.socail.JustTweet;
import twitter.nonce_app.socail.JustUpdateStatus;
import twitter.nonce_app.socail.TweetBroadCast;
import twitter.nonce_app.socail.UpdateStatusBroadCast;
import twitter.nonce_app.toast_package.OurToast;
import twitter.nonce_app.travel.PrivateOrNotForTravel;
import twitter.nonce_app.travel.TravelBroadcast;

public class DetailsActivity extends AppCompatActivity {

    private DBHelper myDBHelper = DBHelper.getInstance(this);
    private Model model;
    private TimePicker _timePicker;
    private TextView datePicker ;
    private TextInputEditText detailsName ;
    String alarmName , defaultDate , costDate , theEvent;
    private Calendar ourCalendar = Calendar.getInstance();
    int realDayOfMonth = ourCalendar.get(Calendar.DAY_OF_MONTH);
    int realMonth = ourCalendar.get(Calendar.MONTH);
    int realYear = ourCalendar.get(Calendar.YEAR);
    int choosedDay ,choosedMonth ,choosedYear ;
    private DatePickerDialog datePickerDialog = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final TextView _save = (TextView) findViewById(R.id.save);
        _timePicker = (TimePicker) findViewById(R.id.timePicker);
        detailsName = (TextInputEditText) findViewById(R.id.detailsName);
        datePicker = (TextView)findViewById(R.id.datePickerContainer);

        datePicker.setText(R.string.today);

        final long id = getIntent().getExtras().getLong("id");
        alarmName = getIntent().getExtras().getString("alarmName");
        theEvent = getIntent().getExtras().getString("theEvent");
        detailsName.setText(alarmName);
        if (detailsName.getText().toString().equals("Other"))
        {
            detailsName.setHint("Other");
            detailsName.setBackgroundResource(R.drawable.text_custom);
            detailsName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    detailsName.setText("");
                }
            });
        }else
        {
            detailsName.setEnabled(false);
            detailsName.setTextColor(Color.BLACK);
        }

        if (id < 0) {
            model = new Model();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    model = myDBHelper.viewSingleData(id);
                    _timePicker.setCurrentHour(model.hour);
                    _timePicker.setCurrentMinute(model.minute);
                    detailsName.setText(model.eventName);
                }
            }, 0);
        }

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                datePickerDialog = new DatePickerDialog
                        (DetailsActivity.this, new DatePickerDialog.OnDateSetListener() {


                            public void onDateSet(DatePicker view, int pickedYear, int pickedMonthOfYear, int pickedDayOfMonth) {

                                choosedDay = pickedDayOfMonth;
                                choosedMonth = pickedMonthOfYear;
                                choosedYear = pickedYear;


                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (choosedYear < realYear ||
                                                choosedYear <= realYear && choosedMonth < realMonth ||
                                                choosedYear <= realYear && choosedMonth <= realMonth && choosedDay < realDayOfMonth)
                                        {
                                            DetailsActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    new OurToast().myToast(DetailsActivity.this , "Sir , Please set the upcoming Date !");
                                                    costDate = null ;
                                                    datePicker.setText(defaultDate);
                                                }
                                            });

                                            datePickerDialog.show();
                                            datePickerDialog.isShowing();

                                        }

                                    }
                                }, 0);

                                costDate = String.format(Locale.getDefault() ,"%d/%d/%d", choosedDay, choosedMonth + 1, choosedYear);
                                datePicker.setText(costDate);

                                costDate = String.format(Locale.getDefault() ,"%d/%d/%d", realDayOfMonth, realMonth + 1, realYear);
                                datePicker.setText(defaultDate);

                                datePicker.setText(String.format(Locale.getDefault() , "%d/%d/%d", pickedDayOfMonth, pickedMonthOfYear + 1, pickedYear));

                            }
                        }, realYear, realMonth, realDayOfMonth);

                datePickerDialog.show();

            }
        });

        assert _save != null;
        _save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {


                    @Override
                    public void run() {

                        Runnable run = new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                ourCalendar.set(Calendar.HOUR_OF_DAY, _timePicker.getCurrentHour());
                                ourCalendar.set(Calendar.MINUTE, _timePicker.getCurrentMinute());
                                ourCalendar.set(Calendar.SECOND, 00);
                                if ((costDate == null )&& ourCalendar.compareTo(Calendar.getInstance()) <= 0)
                                {
                                    DetailsActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new OurToast().myToast(DetailsActivity.this , getString(R.string.itIsNotUpcomingDay));

                                        }
                                    });
                                }else {

                                    switch (theEvent)
                                    {
                                        case "SMS":
                                            populateModelSMS();
                                            SMSBroadCast.cancelAlarms(DetailsActivity.this);
                                            if (model._id < 0) {
                                                myDBHelper.insertInfo(model);

                                            } else {
                                                myDBHelper.updateData(model);
                                            }
                                            SMSBroadCast.setAlarms(DetailsActivity.this);
                                            DetailsActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    new OurToast().myToast(DetailsActivity.this , getString(R.string.remindInDailyList));
                                                }
                                            });

                                            setResult(RESULT_OK);
                                            Intent refresh = new Intent(DetailsActivity.this, Home.class);
                                            startActivity(refresh);
                                            break;
                                        case "Tweet":
                                            populateModelTweet();
                                            TweetBroadCast.cancelAlarms(DetailsActivity.this);
                                            if (model._id < 0) {
                                                myDBHelper.insertInfo(model);

                                            } else {
                                                myDBHelper.updateData(model);
                                            }
                                            TweetBroadCast.setAlarms(DetailsActivity.this);
                                            DetailsActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    new OurToast().myToast(DetailsActivity.this , getString(R.string.remindInDailyList));
                                                }
                                            });

                                            setResult(RESULT_OK);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent refresh = new Intent(DetailsActivity.this, Home.class);
                                                    startActivity(refresh);
                                                }
                                            }).start();
                                            break;
                                        case "Update Status":

                                            populateModelUpdateStatus();
                                            UpdateStatusBroadCast.cancelAlarms(DetailsActivity.this);
                                            if (model._id < 0) {
                                                myDBHelper.insertInfo(model);

                                            } else {
                                                myDBHelper.updateData(model);
                                            }
                                            UpdateStatusBroadCast.setAlarms(DetailsActivity.this);
                                            DetailsActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    new OurToast().myToast(DetailsActivity.this , getString(R.string.remindInDailyList));
                                                }
                                            });

                                            setResult(RESULT_OK);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent refresh = new Intent(DetailsActivity.this, Home.class);
                                                    startActivity(refresh);
                                                }
                                            }).start();

                                            break;

                                        case "Travel":
                                            populateModelTravel();
                                            TravelBroadcast.cancelAlarms(DetailsActivity.this);
                                            if (model._id < 0) {
                                                myDBHelper.insertInfo(model);

                                            } else {
                                                myDBHelper.updateData(model);
                                            }
                                            TravelBroadcast.setAlarms(DetailsActivity.this);
                                            DetailsActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    new OurToast().myToast(DetailsActivity.this , getString(R.string.remindInDailyList));
                                                }
                                            });

                                            setResult(RESULT_OK);

                                            SharedPreferences facebookUser = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                            String facebookAccount = facebookUser.getString("FacebookUser", null);
                                            if (facebookAccount == null)
                                            {

                                                Intent iHome = new Intent(getBaseContext() , Home.class);
                                                startActivity(iHome);
                                            }else
                                            {
                                                Intent iTravel = new Intent(DetailsActivity.this , PrivateOrNotForTravel.class);
                                                iTravel.putExtra("travelFrom" , model.travelCurrentCountry);
                                                iTravel.putExtra("travelCountry" , model.travelCountry);
                                                iTravel.putExtra("travelPorpuse" , model.travelPurpose);
                                                iTravel.putExtra("travelLong" , model.travelDays);
                                                iTravel.putExtra("travelHour" , model.hour);
                                                iTravel.putExtra("travelMinute" , model.minute);
                                                iTravel.putExtra("travelDay" , model.dayInMonth);
                                                iTravel.putExtra("travelMonth" , model.month);
                                                iTravel.putExtra("travelYear" , model.year);
                                                startActivity(iTravel);

                                            }

                                            break;
                                        case "Hangout":
                                            populateModelForHangout();
                                            HangoutBroadcast.cancelAlarms(DetailsActivity.this);
                                            if (model._id < 0) {
                                                myDBHelper.insertInfo(model);

                                            } else {
                                                myDBHelper.updateData(model);
                                            }
                                            HangoutBroadcast.setAlarms(DetailsActivity.this);
                                            DetailsActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    new OurToast().myToast(DetailsActivity.this , getString(R.string.remindInDailyList));
                                                }
                                            });

                                            setResult(RESULT_OK);
                                            SharedPreferences facebookUser1 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                            String facebookAccount1 = facebookUser1.getString("FacebookUser", null);
                                            if (facebookAccount1 == null)
                                            {

                                                Intent iHome = new Intent(getBaseContext() , Home.class);
                                                startActivity(iHome);

                                            }else
                                            {
                                                Intent iHang = new Intent(DetailsActivity.this , PrivateOrNotForHangout.class);
                                                iHang.putExtra("currentLocationForHang" , model.hangCurrentPlace);
                                                iHang.putExtra("hangActivity" , model.hangActivity);
                                                iHang.putExtra("hangPlace" , model.hangPlace);
                                                iHang.putExtra("hangDetails" , model.hangDetails);
                                                iHang.putExtra("hangRealHour" , model.hour);
                                                iHang.putExtra("hangRealMinute" , model.minute);
                                                iHang.putExtra("hangRealDay" , model.dayInMonth);
                                                iHang.putExtra("hangRealMonth" , model.month);
                                                iHang.putExtra("hangRealYear" , model.year);
                                                startActivity(iHang);

                                            }

                                            break;
                                        case "Nonce Movies":
                                            populateModelMovie();
                                            MoviesAlarmBroadCast.cancelAlarms(DetailsActivity.this);
                                            if (model._id < 0) {
                                                myDBHelper.insertInfo(model);

                                            } else {
                                                myDBHelper.updateData(model);
                                            }
                                            MoviesAlarmBroadCast.setAlarms(DetailsActivity.this);
                                            DetailsActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    new OurToast().myToast(DetailsActivity.this , getString(R.string.remindInDailyList));
                                                }
                                            });

                                            setResult(RESULT_OK);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent refresh = new Intent(DetailsActivity.this, Home.class);
                                                    startActivity(refresh);
                                                }
                                            }).start();
                                            break;
                                        case "Call Someone":
                                            populateModelCallSomeone();
                                            CallBroadcast.cancelAlarms(DetailsActivity.this);
                                            if (model._id < 0) {
                                                myDBHelper.insertInfo(model);

                                            } else {
                                                myDBHelper.updateData(model);
                                            }
                                            CallBroadcast.setAlarms(DetailsActivity.this);
                                            DetailsActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    new OurToast().myToast(DetailsActivity.this , getString(R.string.remindInDailyList));
                                                }
                                            });

                                            setResult(RESULT_OK);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent refresh = new Intent(DetailsActivity.this, Home.class);
                                                    startActivity(refresh);
                                                }
                                            }).start();
                                            break;

                                        case "Memo":
                                            populateModelForToday();
                                            OurBroadCast.cancelAlarms(DetailsActivity.this);
                                            if (model._id < 0) {
                                                myDBHelper.insertInfo(model);

                                            } else {
                                                myDBHelper.updateData(model);
                                            }
                                            OurBroadCast.setAlarms(DetailsActivity.this);
                                            DetailsActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    new OurToast().myToast(DetailsActivity.this , getString(R.string.remindInDailyList));
                                                }
                                            });

                                            setResult(RESULT_OK);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent refresh = new Intent(DetailsActivity.this, Home.class);
                                                    startActivity(refresh);
                                                }
                                            }).start();
                                            break;

                                    }
                                    finish();
                                }
                            }
                        };
                        Thread thread = new Thread(run);
                        thread.start();

                    }
                }, 0);
            }
        });
    }

    private void populateModelMovie ()
    {
        model.hour = _timePicker.getCurrentHour();
        model.minute = _timePicker.getCurrentMinute();
        model.eventName = detailsName.getText().toString();
        model.enabledMovies = true;
        if (costDate == null)
        {
            model.dayInMonth = realDayOfMonth;
            model.month = realMonth;
            model.year = realYear;
        }else {
            model.dayInMonth = choosedDay;
            model.month = choosedMonth;
            model.year = choosedYear;
        }

        model.movieName = getIntent().getExtras().getString("MovieName");
        model.extraInfo = getIntent().getExtras().getString("extraInfo");
    }

    private void  populateModelForHangout ()
    {
        model.hour = _timePicker.getCurrentHour();
        model.minute = _timePicker.getCurrentMinute();
        model.eventName = detailsName.getText().toString();
        model.enabledHangout = true;
        if (costDate == null)
        {
            model.dayInMonth = realDayOfMonth;
            model.month = realMonth;
            model.year = realYear;
        }else {
            model.dayInMonth = choosedDay;
            model.month = choosedMonth;
            model.year = choosedYear;
        }

        model.extraInfo = getIntent().getExtras().getString("extraInfo");

        model.hangCurrentPlace = getIntent().getExtras().getString("currentLocationForHang");
        model.hangActivity = getIntent().getExtras().getString("hangActivity");
        model.hangPlace = getIntent().getExtras().getString("hangPlace");
    }

    private void populateModelTravel ()
    {
        model.hour = _timePicker.getCurrentHour();
        model.minute = _timePicker.getCurrentMinute();
        model.eventName = detailsName.getText().toString();
        model.enabledTravel = true;
        if (costDate == null)
        {
            model.dayInMonth = realDayOfMonth;
            model.month = realMonth;
            model.year = realYear;
        }else {
            model.dayInMonth = choosedDay;
            model.month = choosedMonth;
            model.year = choosedYear;
        }

        model.travelCurrentCountry = getIntent().getExtras().getString("travelCurrentCountry");
        model.travelCountry = getIntent().getExtras().getString("travelCountry");
        model.travelPurpose = getIntent().getExtras().getString("travelPurpose");
        model.travelDays = getIntent().getExtras().getString("travelDays");
        model.extraInfo = getIntent().getExtras().getString("extraInfo");

    }


    private void populateModelUpdateStatus()
    {

        model.hour = _timePicker.getCurrentHour();
        model.minute = _timePicker.getCurrentMinute();
        model.eventName = detailsName.getText().toString();
        model.enabledUpdateStatus = true;
        if (costDate == null)
        {
            model.dayInMonth = realDayOfMonth;
            model.month = realMonth;
            model.year = realYear;
        }else {
            model.dayInMonth = choosedDay;
            model.month = choosedMonth;
            model.year = choosedYear;
        }

        model.myStatus = getIntent().getExtras().getString("updateStatus");
        model.extraInfo = getIntent().getExtras().getString("extraInfo");

    }


    private void populateModelTweet()
    {

        model.hour = _timePicker.getCurrentHour();
        model.minute = _timePicker.getCurrentMinute();
        model.eventName = detailsName.getText().toString();
        model.enabledTweet = true;
        if (costDate == null)
        {
            model.dayInMonth = realDayOfMonth;
            model.month = realMonth;
            model.year = realYear;
        }else {
            model.dayInMonth = choosedDay;
            model.month = choosedMonth;
            model.year = choosedYear;
        }

        model.myTweet = getIntent().getExtras().getString("myTweet");
        model.extraInfo = getIntent().getExtras().getString("extraInfo");

    }

    private void populateModelSMS ()
    {
        model.hour = _timePicker.getCurrentHour();
        model.minute = _timePicker.getCurrentMinute();
        model.eventName = detailsName.getText().toString();
        model.enabledSMS = true;
        if (costDate == null)
        {
            model.dayInMonth = realDayOfMonth;
            model.month = realMonth;
            model.year = realYear;
        }else {
            model.dayInMonth = choosedDay;
            model.month = choosedMonth;
            model.year = choosedYear;
        }

        model.contactNameForSMS = getIntent().getExtras().getString("contactNameForSMS");
        model.contactNumberForSMS = getIntent().getExtras().getString("contactNumberForSMS");
        model.messageBodyForSMS = getIntent().getExtras().getString("messageBodyForSMS");
        model.extraInfo = getIntent().getExtras().getString("extraInfo");



    }

    private void populateModelCallSomeone ()
    {
        model.hour = _timePicker.getCurrentHour();
        model.minute = _timePicker.getCurrentMinute();
        model.eventName = detailsName.getText().toString();
        model.enabledCall = true;
        if (costDate == null)
        {
            model.dayInMonth = realDayOfMonth;
            model.month = realMonth;
            model.year = realYear;
        }else {
            model.dayInMonth = choosedDay;
            model.month = choosedMonth;
            model.year = choosedYear;
        }
        model.contactName = getIntent().getExtras().getString("contactName");
        model.whyCall = getIntent().getExtras().getString("whyCall");
        model.contactNumber = getIntent().getExtras().getString("contactNumber");
        model.extraInfo = getIntent().getExtras().getString("extraInfo");
    }

    private void populateModelForToday() {

        model.hour = _timePicker.getCurrentHour();
        model.minute = _timePicker.getCurrentMinute();
        model.eventName = detailsName.getText().toString();

    /*
    if (detailsName.getText().toString().trim().equals(""))
        {
            model.eventName = "Other";
        }
     */

        model.enabled = true;
        if (costDate == null)
        {
            model.dayInMonth = realDayOfMonth;
            model.month = realMonth;
            model.year = realYear;
        }else {
            model.dayInMonth = choosedDay;
            model.month = choosedMonth;
            model.year = choosedYear;
        }

        model.extraInfo = getIntent().getExtras().getString("extraInfo");
        model.memTitle = getIntent().getExtras().getString("memTitle");
        model.memOne = getIntent().getExtras().getString("memOne");
        model.memTwo = getIntent().getExtras().getString("memTwo");
        model.memThree = getIntent().getExtras().getString("memThree");
        model.memFour = getIntent().getExtras().getString("memFour");
        model.memFive = getIntent().getExtras().getString("memFive");
        model.memSix = getIntent().getExtras().getString("memSix");
    }

    @Override
    public void onBackPressed() {

        Runnable run = new Runnable() {
            @Override
            public void run() {

                if (!detailsName.getText().toString().equals("Call Someone") &&
                        !detailsName.getText().toString().equals("Hangout") &&
                        !detailsName.getText().toString().equals("Travel") &&
                        !detailsName.getText().toString().equals("Medical") &&
                        !detailsName.getText().toString().equals("To Watch") &&
                        !detailsName.getText().toString().equals("Twitter") &&
                        !detailsName.getText().toString().equals("Other"))
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Intent iHome = new Intent(DetailsActivity.this , Home.class);
                            startActivity(iHome);
                        }
                    }).start();

                }else {
                    switch (detailsName.getText().toString())
                    {
                        case "Other" :
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent iOther = new Intent(DetailsActivity.this , Home.class);
                                    startActivity(iOther);
                                }
                            }).start();

                            break;
                        case "Call Someone":

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent iCall = new Intent(DetailsActivity.this , CallSomeoneActivity.class);
                                    startActivity(iCall);
                                }
                            }).start();

                            break;
                        case "اتصال بأحد" :
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent iCall = new Intent(DetailsActivity.this , CallSomeoneActivity.class);
                                    startActivity(iCall);
                                }
                            }).start();
                            break;
                        case "Hangout" :
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent iHang = new Intent(DetailsActivity.this , HangoutActivity.class);
                                    startActivity(iHang);
                                }
                            }).start();

                            break;
                        case "تسكع" :

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent iHang = new Intent(DetailsActivity.this , HangoutActivity.class);
                                    startActivity(iHang);
                                }
                            }).start();


                            break;
                        case "Travel" :
                            String hangRat = "travelCountry";
                            Intent iTravel = new Intent(getBaseContext() , GetCountryBySimCard.class);
                            iTravel.putExtra("amComingFrom" , hangRat);
                            startActivity(iTravel);
                            break;
                        case "Tweet":
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent iTweet = new Intent(DetailsActivity.this , JustTweet.class);
                                    startActivity(iTweet);
                                }
                            }).start();
                            break;

                        case "تغريد":
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent iTweet = new Intent(DetailsActivity.this , JustTweet.class);
                                    startActivity(iTweet);
                                }
                            }).start();
                            break;

                        case "Update Status":
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent iTweet = new Intent(DetailsActivity.this , JustUpdateStatus.class);
                                    startActivity(iTweet);
                                }
                            }).start();
                            break;

                        case "تحديث المنشوارت":
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent iTweet = new Intent(DetailsActivity.this , JustUpdateStatus.class);
                                    startActivity(iTweet);
                                }
                            }).start();
                            break;

                        case "Memo":
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent iMemo = new Intent(DetailsActivity.this , MakeMemo.class);
                                    startActivity(iMemo);
                                }
                            }).start();
                            break;

                        case "مذكرات":
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent iMemo = new Intent(DetailsActivity.this , MakeMemo.class);
                                    startActivity(iMemo);
                                }
                            }).start();
                            break;

                    }
                }

            }
        };
        Thread thread = new Thread(run);
        thread.start();
    }
}


/*
new Thread(new Runnable() {
            @Override
            public void run() {
                switch (realMonth+1)
                {
                    case 1:defaultDate = realDayOfMonth + "-" + (getString(R.string.jan)) + "-" + realYear ;
                        datePicker.setText(defaultDate);
                        break;
                    case 2:defaultDate = realDayOfMonth + "-" + (getString(R.string.feb)) + "-" + realYear ;
                        datePicker.setText(defaultDate);
                        break;
                    case 3:defaultDate = realDayOfMonth + "-" + (getString(R.string.march)) + "-" + realYear ;
                        datePicker.setText(defaultDate);
                        break;
                    case 4:defaultDate = realDayOfMonth + "-" + "4" + "-" + realYear ;
                        datePicker.setText(defaultDate);
                        break;
                    case 5:defaultDate = realDayOfMonth + "-" + (getString(R.string.may)) + "-" + realYear ;
                        datePicker.setText(defaultDate);
                        break;
                    case 6:defaultDate = realDayOfMonth + "-" + (getString(R.string.june)) + "-" + realYear ;
                        datePicker.setText(defaultDate);
                        break;
                    case 7:defaultDate = realDayOfMonth + "-" + (getString(R.string.jul)) + "-" + realYear ;
                        datePicker.setText(defaultDate);
                        break;
                    case 8:defaultDate = realDayOfMonth + "-" + (getString(R.string.aug)) + "-" + realYear ;
                        datePicker.setText(defaultDate);
                        break;
                    case 9:defaultDate = realDayOfMonth + "-" + (getString(R.string.sep)) + "-" + realYear ;
                        datePicker.setText(defaultDate);
                        break;
                    case 10:defaultDate = realDayOfMonth + "-" + (getString(R.string.oct)) + "-" + realYear ;
                        datePicker.setText(defaultDate);
                        break;
                    case 11:defaultDate = realDayOfMonth + "-" + (getString(R.string.nov)) + "-" + realYear ;
                        datePicker.setText(defaultDate);
                        break;
                    case 12:defaultDate = realDayOfMonth + "-" + (getString(R.string.dec)) + "-" + realYear ;
                        datePicker.setText(defaultDate);
                        break;

                }
            }
        }).start();


 */