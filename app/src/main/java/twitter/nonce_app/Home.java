package twitter.nonce_app;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import twitter.nonce_app.call_someone.CallBroadcast;
import twitter.nonce_app.call_someone.CallSomeoneActivity;
import twitter.nonce_app.free_time.FreeTimeBars;
import twitter.nonce_app.free_time.MoviesAlarmBroadCast;
import twitter.nonce_app.get_country.GetCountryBySimCard;
import twitter.nonce_app.hangout.HangoutBroadcast;
import twitter.nonce_app.make_memo.DelayBroadcast;
import twitter.nonce_app.make_memo.MakeMemo;
import twitter.nonce_app.send_sms.SMSBroadCast;
import twitter.nonce_app.send_sms.Send_SMS;
import twitter.nonce_app.settings.SettingActivity;
import twitter.nonce_app.socail.JustTweet;
import twitter.nonce_app.socail.JustUpdateStatus;
import twitter.nonce_app.socail.LetUsLoginWithFacebook;
import twitter.nonce_app.socail.LetUsLoginWithTwitter;
import twitter.nonce_app.socail.TweetBroadCast;
import twitter.nonce_app.socail.UpdateStatusBroadCast;
import twitter.nonce_app.toast_package.OurToast;
import twitter.nonce_app.travel.TravelBroadcast;

public class Home extends AppCompatActivity {

    private final static String TAG = "twitter.nonce_app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(mViewPager);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.getTabAt(0).setIcon(R.mipmap.home);
            tabLayout.getTabAt(1).setIcon(R.mipmap.list);
        }

        FloatingActionButton floatSetting = (FloatingActionButton)findViewById(R.id.fab);
        floatSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Intent iSetting = new Intent(Home.this , SettingActivity.class);
                        startActivity(iSetting);
                    }
                }).start();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent iSetting = new Intent(Home.this , SettingActivity.class);
                    startActivity(iSetting);
                }
            }).start();
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            Fragment PlaceHolder = OurHome.newInstance(position + 1);
            OurList list = OurList.newInstance();


            switch (position) {
                case 0:
                    return PlaceHolder;
                case 1:
                    return list;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.home);
                case 1:
                    return getString(R.string.list);
            }
            return null;
        }
    }

    public static class OurHome extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public OurHome() {
        }

        public static OurHome newInstance(int sectionNumber) {
            OurHome fragment = new OurHome();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.home, container, false);

            Runnable run = new Runnable() {
                @Override
                public void run() {

                    TextView callSomeoneText = (TextView) rootView.findViewById(R.id.callSomeoneTextView);

                    callSomeoneText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    Log.d(TAG, "Permission is not granted, requesting");
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent callSomeone = new Intent(getContext(), CallSomeoneActivity.class);
                                            startActivity(callSomeone);
                                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 123);
                                        }
                                    }).start();

                                } else {
                                    Log.d(TAG, "Permission granted, requesting");

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent callSomeone = new Intent(getContext(), CallSomeoneActivity.class);
                                            startActivity(callSomeone);
                                        }
                                    }).start();

                                }
                            }else
                            {
                                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    Log.d(TAG, "Permission is not granted, requesting");
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent callSomeone = new Intent(getContext(), CallSomeoneActivity.class);
                                            startActivity(callSomeone);
                                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 123);
                                        }
                                    }).start();

                                } else {
                                    Log.d(TAG, "Permission granted, requesting");

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent callSomeone = new Intent(getContext(), CallSomeoneActivity.class);
                                            startActivity(callSomeone);
                                        }
                                    }).start();

                                }

                            }
                        }
                    });

                    TextView postOfficeTextView = (TextView) rootView.findViewById(R.id.postOfficeTextView);
                    postOfficeTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS)
                                    != PackageManager.PERMISSION_GRANTED) {
                                Log.d(TAG, "Permission is not granted, requesting");
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent callSomeone = new Intent(getContext() , Send_SMS.class);
                                        startActivity(callSomeone);
                                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 1230);
                                    }
                                }).start();

                            }else
                            {
                                Log.d(TAG, "Permission granted, requesting");

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent callSomeone = new Intent(getContext() , Send_SMS.class);
                                        startActivity(callSomeone);
                                    }
                                }).start();

                            }
                        }
                    });

                    // hangout stuff
                    TextView hangoutTextView = (TextView) rootView.findViewById(R.id.hangTextView);
                    hangoutTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try{
                                        String hangRat = "hangoutCountry";
                                        Intent iHang = new Intent(getContext() , GetCountryBySimCard.class);
                                        iHang.putExtra("amComingFrom" , hangRat);
                                        startActivity(iHang);
                                    }catch (Exception e)
                                    {
                                        e.getMessage();
                                    }
                                }
                            }).start();
                        }
                    });


                    //free stuff
                    TextView freeTextView = (TextView) rootView.findViewById(R.id.freeTimeTextView);

                    freeTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent iFree = new Intent(getContext(), FreeTimeBars.class);
                                    startActivity(iFree);
                                }
                            }).start();
                        }
                    });


                    //travel stuff
                    TextView travelTextView = (TextView) rootView.findViewById(R.id.travelTextView);
                    travelTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {


                                    String hangRat = "travelCountry";
                                    Intent iTravel = new Intent(getContext() , GetCountryBySimCard.class);
                                    iTravel.putExtra("amComingFrom" , hangRat);
                                    startActivity(iTravel);
                                }
                            }).start();
                        }
                    });

                    //twitter stuff

                    TextView twitterText = (TextView)rootView.findViewById(R.id.twitterTextView);

                    twitterText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    SharedPreferences twitterDetails = PreferenceManager.getDefaultSharedPreferences(getContext());
                                    String twitterUserName = twitterDetails.getString("TwitterUser" , null);

                                    if (twitterUserName == null)
                                    {
                                        String homeless = "Home";
                                        Intent iSocial = new Intent(getContext() , LetUsLoginWithTwitter.class);
                                        iSocial.putExtra("amComingFrom" , homeless);
                                        startActivity(iSocial);
                                    }else
                                    {
                                        Intent iSocial = new Intent(getContext() , JustTweet.class);
                                        startActivity(iSocial);
                                    }
                                }
                            }).start();
                        }
                    });

                    //Facebook stuff

                    TextView facebookText = (TextView)rootView.findViewById(R.id.updateStatusTextView);

                    facebookText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    SharedPreferences twitterDetails = PreferenceManager.getDefaultSharedPreferences(getContext());
                                    String facebookUserName = twitterDetails.getString("FacebookUser" , null);

                                    if (facebookUserName == null)
                                    {
                                        String homeless = "Home";
                                        Intent iSocial = new Intent(getContext() , LetUsLoginWithFacebook.class);
                                        iSocial.putExtra("amComingFrom" , homeless);
                                        startActivity(iSocial);
                                    }else
                                    {
                                        Intent iSocial = new Intent(getContext() , JustUpdateStatus.class);
                                        startActivity(iSocial);
                                    }
                                }
                            }).start();
                        }
                    });


                    //other stuff
                    final TextView otherText = (TextView) rootView.findViewById(R.id.otherTextView);
                    otherText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent otherIntent = new Intent(getContext(), MakeMemo.class);
                                    startActivity(otherIntent);
                                }
                            }).start();
                        }
                    });
                }
            };
            Thread thread = new Thread(run);
            thread.start();
            return rootView;
        }
    }

    public static class OurList extends ListFragment {

        private DBHelper dbHelper;
        private OurAdapter mAdapter;

        public static OurList newInstance() {

            OurList fragment = new OurList();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        public OurList() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            try
            {
                dbHelper =  DBHelper.getInstance(getContext());
                mAdapter = new OurAdapter(getContext(), dbHelper.viewAllData());
                setListAdapter(mAdapter);
            }catch (Exception e)
            {
                e.getMessage();
            }

            return inflater.inflate(R.layout.daily_list, container, false);

        }


        @Override
        public void onListItemClick(ListView l, View v, int position, final long id) {

            final Model model = (Model)mAdapter.getItem(position);
            try {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.deleteOrEdit);
                builder.setCancelable(true);
                builder.setIcon(R.mipmap.delete);
                builder.setNegativeButton(R.string.editTime, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                switch (model.eventName)
                                {

                                    case "Call Someone" :
                                        Intent iCall = new Intent(getContext() , DetailsActivity.class);
                                        iCall.putExtra("id", id);
                                        iCall.putExtra("alarmName", "Call Someone" );
                                        iCall.putExtra("theEvent" , "Call Someone");
                                        iCall.putExtra("extraInfo" ,  model.contactName );
                                        iCall.putExtra("contactName" , model.contactName);
                                        iCall.putExtra("whyCall", model.whyCall);
                                        iCall.putExtra("contactNumber" , model.contactNumber);
                                        startActivityForResult(iCall , 212);
                                        break;
                                    case "اتصال بأحد" :
                                        Intent iCallAr = new Intent(getContext() , DetailsActivity.class);
                                        iCallAr.putExtra("id", id);
                                        iCallAr.putExtra("alarmName", "Call Someone" );
                                        iCallAr.putExtra("theEvent" , "Call Someone");
                                        iCallAr.putExtra("extraInfo" ,  model.contactName );
                                        iCallAr.putExtra("contactName" , model.contactName);
                                        iCallAr.putExtra("whyCall", model.whyCall);
                                        iCallAr.putExtra("contactNumber" , model.contactNumber);
                                        startActivityForResult(iCallAr , 212);
                                        break;

                                    case "SMS":
                                        Intent iSMS = new Intent(getContext() , DetailsActivity.class);
                                        iSMS.putExtra("id" , id);
                                        iSMS.putExtra( "alarmName", "SMS" );
                                        iSMS.putExtra( "theEvent", "SMS" );
                                        iSMS.putExtra("extraInfo" ,  model.contactNameForSMS );
                                        iSMS.putExtra("contactNameForSMS" , model.contactNameForSMS);
                                        iSMS.putExtra("messageBodyForSMS", model.messageBodyForSMS);
                                        iSMS.putExtra("contactNumberForSMS" , model.contactNumberForSMS);
                                        startActivityForResult(iSMS , 212);
                                        break;
                                    case "رسالة":
                                        Intent iSMSAr = new Intent(getContext() , DetailsActivity.class);
                                        iSMSAr.putExtra("id" , id);
                                        iSMSAr.putExtra( "alarmName", "SMS" );
                                        iSMSAr.putExtra( "theEvent", "SMS" );
                                        iSMSAr.putExtra("extraInfo" ,  model.contactNameForSMS );
                                        iSMSAr.putExtra("contactNameForSMS" , model.contactNameForSMS);
                                        iSMSAr.putExtra("messageBodyForSMS", model.messageBodyForSMS);
                                        iSMSAr.putExtra("contactNumberForSMS" , model.contactNumberForSMS);
                                        startActivityForResult(iSMSAr , 212);
                                        break;

                                    case "Hangout" :
                                        Intent iHang = new Intent(getContext() , DetailsActivity.class);
                                        iHang.putExtra("id" , id);
                                        iHang.putExtra("alarmName" , "Hangout");
                                        iHang.putExtra("theEvent" , "Hangout");
                                        iHang.putExtra("extraInfo" , model.hangPlace);
                                        iHang.putExtra("currentLocationForHang" , model.hangCurrentPlace);
                                        iHang.putExtra("hangActivity" , model.hangActivity);
                                        iHang.putExtra("hangPlace" , model.hangPlace);
                                        iHang.putExtra("hangDetails" , model.hangDetails);
                                        startActivityForResult(iHang , 212);
                                        break;
                                    case "تسكع" :
                                        Intent iHangAr = new Intent(getContext() , DetailsActivity.class);
                                        iHangAr.putExtra("id" , id);
                                        iHangAr.putExtra("alarmName" , "Hangout");
                                        iHangAr.putExtra("theEvent" , "Hangout");
                                        iHangAr.putExtra("extraInfo" , model.hangPlace);
                                        iHangAr.putExtra("currentLocationForHang" , model.hangCurrentPlace);
                                        iHangAr.putExtra("hangActivity" , model.hangActivity);
                                        iHangAr.putExtra("hangPlace" , model.hangPlace);
                                        iHangAr.putExtra("hangDetails" , model.hangDetails);
                                        startActivityForResult(iHangAr , 212);
                                        break;

                                    case "Travel":
                                        Intent iTravel = new Intent(getContext() , DetailsActivity.class);
                                        iTravel.putExtra("id" , id);
                                        iTravel.putExtra("alarmName" , "Travel");
                                        iTravel.putExtra("theEvent" , "Travel");
                                        iTravel.putExtra("extraInfo" , model.travelCountry);
                                        iTravel.putExtra("travelFrom" , model.travelCurrentCountry);
                                        iTravel.putExtra("travelCountry" , model.travelCountry);
                                        iTravel.putExtra("travelPorpuse" , model.travelPurpose);
                                        iTravel.putExtra("travelLong" , model.travelDays);
                                        startActivityForResult(iTravel , 212);
                                        break;

                                    case "السفر":
                                        Intent iTravelAr = new Intent(getContext() , DetailsActivity.class);
                                        iTravelAr.putExtra("id" , id);
                                        iTravelAr.putExtra("alarmName" , "Travel");
                                        iTravelAr.putExtra("theEvent" , "Travel");
                                        iTravelAr.putExtra("extraInfo" , model.travelCountry);
                                        iTravelAr.putExtra("travelFrom" , model.travelCurrentCountry);
                                        iTravelAr.putExtra("travelCountry" , model.travelCountry);
                                        iTravelAr.putExtra("travelPorpuse" , model.travelPurpose);
                                        iTravelAr.putExtra("travelLong" , model.travelDays);
                                        startActivityForResult(iTravelAr , 212);
                                        break;

                                    case "Nonce Movies":
                                        Intent iMovies = new Intent(getContext() , DetailsActivity.class);
                                        iMovies.putExtra("id" , id);
                                        iMovies.putExtra( "alarmName", "Nonce Movies" );
                                        iMovies.putExtra( "theEvent", "Nonce Movies" );
                                        iMovies.putExtra("MovieName" , model.movieName);
                                        iMovies.putExtra("extraInfo" , model.movieName);
                                        startActivityForResult(iMovies , 212);
                                        break;
                                    case "الافلام":
                                        Intent iMoviesAr = new Intent(getContext() , DetailsActivity.class);
                                        iMoviesAr.putExtra("id" , id);
                                        iMoviesAr.putExtra( "alarmName", "Nonce Movies" );
                                        iMoviesAr.putExtra( "theEvent", "Nonce Movies" );
                                        iMoviesAr.putExtra("MovieName" , model.movieName);
                                        iMoviesAr.putExtra("extraInfo" , model.movieName);
                                        startActivityForResult(iMoviesAr , 212);
                                        break;

                                    case "Tweet":
                                        Intent it = new Intent(getContext() ,  DetailsActivity.class);
                                        it.putExtra("id" , id);
                                        it.putExtra( "alarmName", "Tweet");
                                        it.putExtra( "theEvent", "Tweet");
                                        it.putExtra("myTweet" , model.myTweet);
                                        it.putExtra("extraInfo" , model.myTweet);
                                        startActivityForResult(it , 212);
                                        break;
                                    case "تغريد":
                                        Intent itAr = new Intent(getContext() ,  DetailsActivity.class);
                                        itAr.putExtra("id" , id);
                                        itAr.putExtra( "alarmName", "Tweet");
                                        itAr.putExtra( "theEvent", "Tweet");
                                        itAr.putExtra("myTweet" , model.myTweet);
                                        itAr.putExtra("extraInfo" , model.myTweet);
                                        startActivityForResult(itAr , 212);
                                        break;
                                    case "Update Status":
                                        Intent iStatus = new Intent(getContext() , DetailsActivity.class);
                                        iStatus.putExtra("id" , id);
                                        iStatus.putExtra("alarmName" , "Update Status");
                                        iStatus.putExtra("theEvent" , "Update Status");
                                        iStatus.putExtra("updateStatus" , model.myStatus);
                                        iStatus.putExtra("extraInfo" , model.myStatus);
                                        startActivityForResult(iStatus , 212);
                                        break;
                                    case "تحديث المنشورات":
                                        Intent iStatusAr = new Intent(getContext() , DetailsActivity.class);
                                        iStatusAr.putExtra("id" , id);
                                        iStatusAr.putExtra("alarmName" , "Update Status");
                                        iStatusAr.putExtra("theEvent" , "Update Status");
                                        iStatusAr.putExtra("updateStatus" , model.myStatus);
                                        iStatusAr.putExtra("extraInfo" , model.myStatus);
                                        startActivityForResult(iStatusAr , 212);
                                        break;
                                    case "Memo" :
                                        Intent im = new Intent(getContext() , DetailsActivity.class);
                                        im.putExtra("id" ,id);
                                        im.putExtra("alarmName" , "Memo");
                                        im.putExtra("theEvent" , "Memo");
                                        im.putExtra("memTitle" , model.memTitle);
                                        im.putExtra("extraInfo" , model.memTitle);
                                        im.putExtra("memOne" , model.memOne);
                                        im.putExtra("memTwo" , model.memTwo);
                                        im.putExtra("memThree" , model.memThree);
                                        im.putExtra("memFour" , model.memFour);
                                        im.putExtra("memFive" , model.memFive);
                                        im.putExtra("memSix" , model.memSix);
                                        startActivityForResult(im , 212);
                                        break;

                                    case "مذكرات" :
                                        Intent imAr = new Intent(getContext() , DetailsActivity.class);
                                        imAr.putExtra("id" ,id);
                                        imAr.putExtra("alarmName" , "Memo");
                                        imAr.putExtra("theEvent" , "Memo");
                                        imAr.putExtra("memTitle" , model.memTitle);
                                        imAr.putExtra("extraInfo" , model.memTitle);
                                        imAr.putExtra("memOne" , model.memOne);
                                        imAr.putExtra("memTwo" , model.memTwo);
                                        imAr.putExtra("memThree" , model.memThree);
                                        imAr.putExtra("memFour" , model.memFour);
                                        imAr.putExtra("memFive" , model.memFive);
                                        imAr.putExtra("memSix" , model.memSix);
                                        startActivityForResult(imAr , 212);
                                        break;

                                    default:
                                        Intent intent = new Intent(getContext() , DetailsActivity.class);
                                        intent.putExtra("id", id);
                                        startActivityForResult(intent, 212);
                                        break;
                                }
                            }
                        }).start();
                    }
                });
                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        OurBroadCast.cancelAlarms(getContext());
                        SMSBroadCast.cancelAlarms(getContext());
                        TweetBroadCast.cancelAlarms(getContext());
                        MoviesAlarmBroadCast.cancelAlarms(getContext());
                        HangoutBroadcast.cancelAlarms(getContext());
                        TravelBroadcast.cancelAlarms(getContext());
                        CallBroadcast.cancelAlarms(getContext());
                        DelayBroadcast.cancelAlarms(getContext());
                        UpdateStatusBroadCast.cancelAlarms(getContext());
                        //Delete alarm from DB by id
                        dbHelper.deleteData(id);
                        //Refresh the list of the alarms in the adaptor
                        mAdapter.setUp(dbHelper.viewAllData());
                        //Notify the adapter the data has changed
                        mAdapter.notifyDataSetChanged();
                        OurBroadCast.setAlarms(getContext());
                        SMSBroadCast.setAlarms(getContext());
                        TweetBroadCast.setAlarms(getContext());
                        MoviesAlarmBroadCast.setAlarms(getContext());
                        HangoutBroadcast.setAlarms(getContext());
                        TravelBroadcast.setAlarms(getContext());
                        CallBroadcast.setAlarms(getContext());
                        DelayBroadcast.setAlarms(getContext());
                        UpdateStatusBroadCast.setAlarms(getContext());
                        dbHelper.close();
                    }
                });

                builder.show();
            } catch (Exception e) {
                e.getMessage();
            }

        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK && requestCode == 212) {
                dbHelper.deleteData(data.getExtras().getLong("id"));
                mAdapter.setUp(dbHelper.viewAllData());
                mAdapter.notifyDataSetChanged();
            }
        }
    }



    // press back twice
    boolean doubleBackToExitPressedOnce = false ;

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {

            super.onBackPressed();
            return;
        }
        new OurToast().myToast(getBaseContext() , getString(R.string.pressBackAgainToExit));
        this.doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 30000);

    }
}


/*

just twitter lets Tweet

 twitterText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    SharedPreferences twitterDetails = PreferenceManager.getDefaultSharedPreferences(getContext());
                                    String twitterUserName = twitterDetails.getString("TwitterUser" , null);

                                    if (twitterUserName == null)
                                    {
                                        String homeless = "Home";
                                        Intent iSocial = new Intent(getContext() , LetUsLoginWithTwitter.class);
                                        iSocial.putExtra("amComingFrom" , homeless);
                                        startActivity(iSocial);
                                    }else
                                    {
                                        Intent iSocial = new Intent(getContext() , JustTweet.class);
                                        startActivity(iSocial);
                                    }
                                }
                            }).start();
                        }
                    });



 */