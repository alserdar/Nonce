package twitter.nonce_app.socail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.internal.ForegroundLinearLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import twitter.nonce_app.DetailsActivity;
import twitter.nonce_app.Home;
import twitter.nonce_app.R;

public class SocislBars extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socisl_bars);


        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(mViewPager);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.getTabAt(0).setIcon(R.mipmap.facebook);
            tabLayout.getTabAt(1).setIcon(R.mipmap.twitter);
        }

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            FacebookActivityForLogin facebookActivity = FacebookActivityForLogin.newInstance(position + 1);
            TwitterActivity twitterActivity = TwitterActivity.newInstance(position + 1);
            switch (position)
            {
                case 0 :
                      return  facebookActivity ;
                case 1 :
                    return twitterActivity ;

            }
            return null ;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:

                    return getString(R.string.updateStatus);
                case 1:
                    return getString(R.string.tweet);
            }
            return null;
        }

    }

    public static class FacebookActivityForLogin extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public FacebookActivityForLogin() {
        }

        public static FacebookActivityForLogin newInstance(int sectionNumber) {
            FacebookActivityForLogin fragment = new FacebookActivityForLogin();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.facebook_socisl_bars, container, false);
            Button letFacebook = (Button)rootView.findViewById(R.id.letsLoginFacebook);
            ForegroundLinearLayout layoutStatus = (ForegroundLinearLayout)rootView.findViewById(R.id.layoutUpdateStatus);
            ForegroundLinearLayout layoutSaveStatus = (ForegroundLinearLayout)rootView.findViewById(R.id.layoutSaveUpdateStatus);
            ForegroundLinearLayout facebookLayout = (ForegroundLinearLayout)rootView.findViewById(R.id.facebookSocialLayout);
            ForegroundLinearLayout loginFacebookLayout = (ForegroundLinearLayout)rootView.findViewById(R.id.gotoLoginFacebookLayout);
            final EditText updateStatusEditText = (EditText) rootView.findViewById(R.id.updateStatusFacebook);
            final Button saveUpdateStatus = (Button)rootView.findViewById(R.id.saveUpdateStatus);
            final SharedPreferences facebookDetails = PreferenceManager.getDefaultSharedPreferences(getContext());
            String facebookUserName = facebookDetails.getString("FacebookUser" , null);

            if (facebookUserName == null)
            {
                loginFacebookLayout.setVisibility(View.VISIBLE);
                layoutSaveStatus.setVisibility(View.GONE);
                layoutStatus.setVisibility(View.GONE);
                facebookLayout.setGravity(Gravity.CENTER);

            }else
            {
                loginFacebookLayout.setVisibility(View.GONE);
                layoutSaveStatus.setVisibility(View.VISIBLE);
                layoutStatus.setVisibility(View.VISIBLE);
            }

            letFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String homeless = "Home";
                            Intent iSocial = new Intent(getContext() , LetUsLoginWithFacebook.class);
                            iSocial.putExtra("amComingFrom" , homeless);
                            startActivity(iSocial);
                        }
                    }).start();
                }
            });


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

                }
            };
            updateStatusEditText.addTextChangedListener(watcher);

            saveUpdateStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (updateStatusEditText.getText().toString().trim().equals(""))
                    {
                        saveUpdateStatus.setClickable(false);
                        saveUpdateStatus.setBackgroundResource(R.drawable.gone_grey);
                    }else
                    {
                        saveUpdateStatus.setClickable(true);
                        saveUpdateStatus.setBackgroundResource(R.drawable.button_custom);

                        long id = -1 ;
                        Intent i = new Intent(getContext() , DetailsActivity.class);
                        i.putExtra("id" , id);
                        i.putExtra("alarmName" , "Update Status");
                        i.putExtra("updateStatus" , updateStatusEditText.getText().toString());
                        i.putExtra("extraInfo" , updateStatusEditText.getText().toString());
                        startActivity(i);
                    }
                }
            });
            return rootView;
        }
    }


    public static class TwitterActivity extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public TwitterActivity() {
        }

        public static TwitterActivity newInstance(int sectionNumber) {
            TwitterActivity fragment = new TwitterActivity();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.twitter_social_bars, container, false);
            Button letTwitter = (Button)rootView.findViewById(R.id.letsLoginTwitter);
            ForegroundLinearLayout layoutTweet = (ForegroundLinearLayout)rootView.findViewById(R.id.layoutTweet);
            ForegroundLinearLayout layoutSaveTweet = (ForegroundLinearLayout)rootView.findViewById(R.id.layoutSaveTweet);
            ForegroundLinearLayout twitterLayout = (ForegroundLinearLayout)rootView.findViewById(R.id.twitterSocialLayout);
            ForegroundLinearLayout loginTwitterLayout = (ForegroundLinearLayout)rootView.findViewById(R.id.gotoLoginTwitterLayout);
            final EditText tweet = (EditText) rootView.findViewById(R.id.tweetTwitter);
            final TextView count = (TextView) rootView.findViewById(R.id.countTweet);
            final Button saveTweets = (Button)rootView.findViewById(R.id.saveTweet);
            SharedPreferences twitterDetails = PreferenceManager.getDefaultSharedPreferences(getContext());
            String twitterUserName = twitterDetails.getString("TwitterUser" , null);

            if (twitterUserName == null)
            {
                loginTwitterLayout.setVisibility(View.VISIBLE);
                layoutSaveTweet.setVisibility(View.GONE);
                layoutTweet.setVisibility(View.GONE);
                twitterLayout.setGravity(Gravity.CENTER);

            }else
            {
                loginTwitterLayout.setVisibility(View.GONE);
                layoutSaveTweet.setVisibility(View.VISIBLE);
                layoutTweet.setVisibility(View.VISIBLE);
            }

            letTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String homeless = "Home";
                    Intent iSocial = new Intent(getContext() , LetUsLoginWithTwitter.class);
                    iSocial.putExtra("amComingFrom" , homeless);
                    startActivity(iSocial);
                }
            });


            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    count.setText(String.valueOf(charSequence.length()));
                    if (tweet.getText().toString().trim().equals(""))
                    {
                        saveTweets.setClickable(false);
                        saveTweets.setBackgroundResource(R.drawable.gone_grey);
                    }else
                    {
                        saveTweets.setClickable(true);
                        saveTweets.setBackgroundResource(R.drawable.button_custom);
                    }


                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };
            tweet.addTextChangedListener(textWatcher);


            saveTweets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            if (tweet.getText().toString().trim().equals(""))
                            {
                                saveTweets.setClickable(false);
                            }else
                            {
                                saveTweets.setClickable(true);
                                saveTweets.setBackgroundResource(R.drawable.button_custom);
                                long id = -1 ;
                                Intent i = new Intent(getContext() , DetailsActivity.class);
                                i.putExtra("id" , id);
                                i.putExtra("alarmName" , "Tweet");
                                i.putExtra("myTweet" , tweet.getText().toString());
                                i.putExtra("extraInfo" , tweet.getText().toString());
                                startActivity(i);
                            }

                        }
                    }).start();
                }
            });
            return rootView;
        }


    }

    @Override
    public void onBackPressed() {

        Intent iHome = new Intent(getBaseContext() , Home.class);
        startActivity(iHome);
    }
}
