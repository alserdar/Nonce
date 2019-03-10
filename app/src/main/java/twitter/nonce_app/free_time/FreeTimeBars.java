package twitter.nonce_app.free_time;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

import twitter.nonce_app.Home;
import twitter.nonce_app.R;

public class FreeTimeBars extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_bars);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(mViewPager);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.getTabAt(0).setIcon(R.mipmap.movie);
            tabLayout.getTabAt(1).setIcon(R.mipmap.music);
        }

        FloatingActionButton floatSetting = (FloatingActionButton)findViewById(R.id.saveBars);
        floatSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences getTheDetails = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                if (getTheDetails.getBoolean("switchIsOn" , true))
                {
                    MusicBroadCast.setMusicNotification(getBaseContext());

                }else
                {

                }
                if (getTheDetails.getBoolean("switchMoviesIsOn" , true))
                {
                    MoviesBroadCast.setMoviesNotification(getBaseContext());
                    //  CenimaBroadcast.setCenimaNotification(getBaseContext());

                }else
                {

                }
            }
        });

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            MoviesFragment moviesFragment = new MoviesFragment();
            MusicFragment musicFragment = new MusicFragment();
            switch (position) {
                case 0:
                    return moviesFragment;
                case 1 :
                    return musicFragment;
                case 2:
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
                    return getString(R.string.movies);
                case 1:
                    return getString(R.string.musicOnline);
            }
            return null;
        }
    }



    // ================================================== music fragment ======================================
    public static class MusicFragment extends Fragment {

        TextInputEditText radioLinkEditText;
        TextInputEditText musicTime;
        Switch switchMusic ;
        int selectedH , selectedM ;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_music, container, false);

            //refrences the views
            switchMusic = (Switch) rootView.findViewById(R.id.switchMusic);
            radioLinkEditText = (TextInputEditText) rootView.findViewById(R.id.radioLinkForMusic);
            musicTime = (TextInputEditText) rootView.findViewById(R.id.timeForMusic);

            loadDetails();

            // shared preferences
            final SharedPreferences musicDetails = PreferenceManager.getDefaultSharedPreferences(getContext());
            final SharedPreferences.Editor edit = musicDetails.edit();

            // the Switch
            switchMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if (switchMusic.isChecked())
                    {
                        musicTime.setEnabled(true);
                        radioLinkEditText.setEnabled(true);
                        musicTime.setTextColor(Color.BLACK);
                        radioLinkEditText.setTextColor(Color.BLACK);
                        edit.putBoolean("switchIsOn" , true);
                        edit.apply();
                    }else
                    {
                        radioLinkEditText.setEnabled(false);
                        musicTime.setEnabled(false);
                        musicTime.setTextColor(Color.GRAY);
                        radioLinkEditText.setTextColor(Color.GRAY);
                        edit.putBoolean("switchIsOn" , false);
                        edit.apply();
                    }

                }
            });

            // the radio link
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    String savedRadioLink = charSequence.toString();
                    edit.putString("saveRadioLink", savedRadioLink);
                    edit.apply();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };

            radioLinkEditText.addTextChangedListener(textWatcher);

            // the music time
            musicTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    musicTime.setText("");
                    Calendar currentTime = Calendar.getInstance();
                    int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = currentTime.get(Calendar.MINUTE);

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                            if (selectedHour == 0||selectedHour == 1 || selectedHour == 2 || selectedHour == 3 ||
                                    selectedHour == 4 || selectedHour == 5 || selectedHour == 6 ||
                                    selectedHour == 7 || selectedHour == 8 || selectedHour == 9||
                                    selectedHour == 10 || selectedHour == 11) {
                                musicTime.setText(String.format(Locale.getDefault() , "%02d:%02d  %s"  ,selectedHour , selectedMinute , getString(R.string.am)));
                                edit.putInt("selectHour", selectedHour);

                            } else if (selectedHour == 13 || selectedHour == 14 || selectedHour == 15 ||
                                    selectedHour == 16 || selectedHour == 17 || selectedHour == 18 ||
                                    selectedHour == 19 || selectedHour == 20 || selectedHour == 21 ||
                                    selectedHour == 22 || selectedHour == 23)
                            {
                                musicTime.setText(String.format(Locale.getDefault() ,"%02d:%02d  %s"  ,selectedHour - 12 , selectedMinute , getString(R.string.pm)));
                                edit.putInt("selectHour", selectedHour);
                            }else if (selectedHour == 12)
                            {
                                musicTime.setText(String.format(Locale.getDefault() ,"%02d:%02d  %s"  ,selectedHour , selectedMinute , getString(R.string.pm)));
                                edit.putInt("selectHour", selectedHour);
                            }
                            edit.putInt("selectMinute", selectedMinute);
                            edit.putBoolean("timeSetup" , true);
                            edit.apply();
                            //     Toast.makeText(getContext() , selectedHour + ":" + selectedMinute , Toast.LENGTH_SHORT).show();
                        }
                    }, hour, minute, false);//Yes 12 hour time
                    mTimePicker.setTitle(getString(R.string.selectTime));
                    mTimePicker.show();

                }
            });
            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            if (radioLinkEditText.getText().toString().isEmpty())
            {
                radioLinkEditText.setError(getString(R.string.emptyFieldForLink));
            }
            if (musicTime.getText().toString().isEmpty())
            {
                musicTime.setError(getString(R.string.emptyFiledForTime));
            }

        }

        private void loadDetails () {

            SharedPreferences getTheDetails = PreferenceManager.getDefaultSharedPreferences(getContext());
            if (getTheDetails.getBoolean("switchIsOn", true)) {
                switchMusic.setChecked(true);
                musicTime.setEnabled(true);
                musicTime.setTextColor(Color.BLACK);
                radioLinkEditText.setEnabled(true);
                radioLinkEditText.setTextColor(Color.BLACK);

                // get the music Time
                selectedH = getTheDetails.getInt("selectHour", 9);
                selectedM = getTheDetails.getInt("selectMinute", 9);

                musicTime.setFocusable(false);
                musicTime.setClickable(true);

                if (selectedH == 1 || selectedH == 2 || selectedH == 3 || selectedH == 4 ||
                        selectedH == 5 || selectedH == 6 ||
                        selectedH == 7 || selectedH == 8 ||
                        selectedH == 9 || selectedH == 10 ||
                        selectedH == 11) {
                    musicTime.setText(String.format(Locale.getDefault(), "%02d:%02d  %s" , selectedH, selectedM, getString(R.string.am)));
                } else if (selectedH == 13 || selectedH == 14 ||
                        selectedH == 15 || selectedH == 16 ||
                        selectedH == 17 || selectedH == 18 ||
                        selectedH == 19 || selectedH == 20 ||
                        selectedH == 21 || selectedH == 22 ||
                        selectedH == 23) {
                    selectedH = selectedH - 12;
                    musicTime.setText(String.format(Locale.getDefault(), "%02d:%02d  %s" , selectedH, selectedM, getString(R.string.pm)));
                } else if (selectedH == 12) {
                    musicTime.setText(String.format(Locale.getDefault(), "%02d:%02d  %s" , selectedH, selectedM, getString(R.string.pm)));
                } else if (selectedH == 0) {
                    selectedH = selectedH + 12;
                    musicTime.setText(String.format(Locale.getDefault(), "%02d:%02d  %s" , selectedH, selectedM, getString(R.string.am)));
                }


                // get The Link
                String getTheLink = getTheDetails.getString("saveRadioLink", "saveRadioLink");
                if (getTheLink.isEmpty() || getTheLink.equals("saveRadioLink")) {
                    radioLinkEditText.setText(R.string.gardenRadio);
                } else {
                    radioLinkEditText.setText(getTheLink);
                }
            } else if (getTheDetails.getBoolean("switchIsOn", false)) {
                switchMusic.setChecked(false);
            }
        }

    }


    // ====================================================== fragment movies ================================================
    public static class MoviesFragment extends Fragment {

        Switch switchMovies ;
        Button checkGenres ;
        EditText moviesTime ;
        AlertDialog alertDialogActivities ;
        int selectedH , selectedM ;



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_free_bars, container, false);

            switchMovies = (Switch)rootView.findViewById(R.id.switchMovies);
            checkGenres = (Button) rootView.findViewById(R.id.genresButtonSelected);
            moviesTime = (EditText) rootView.findViewById(R.id.timeForMovies);


            loadDetails();

            // shared preferences
            final SharedPreferences moviesDetails = PreferenceManager.getDefaultSharedPreferences(getContext());
            final SharedPreferences.Editor edit = moviesDetails.edit();

            // the Switch
            switchMovies.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if (switchMovies.isChecked())
                    {
                        moviesTime.setEnabled(true);
                        checkGenres.setEnabled(true);
                        moviesTime.setTextColor(Color.BLACK);
                        checkGenres.setTextColor(Color.WHITE);
                        edit.putBoolean("switchMoviesIsOn" , true);
                        edit.apply();
                    }else
                    {
                        checkGenres.setEnabled(false);
                        moviesTime.setEnabled(false);
                        moviesTime.setTextColor(Color.GRAY);
                        checkGenres.setTextColor(Color.GRAY);
                        edit.putBoolean("switchMoviesIsOn" , false);
                        edit.apply();
                    }

                }
            });

            // the movies genres

            checkGenres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle(R.string.chooseGenres);


                    final CharSequence[] aboutTickets = {getString(R.string.allGenres) , getString(R.string.actionMovies)
                            , getString(R.string.adventureMovies) , getString(R.string.animationMovies)
                            , getString(R.string.biographyMovies) , getString(R.string.comedyMovies) , getString(R.string.crimeMovies)
                            , getString(R.string.dramaMovies) , getString(R.string.horrorMovies) ,  getString(R.string.mysteryMovies)
                            , getString(R.string.romanceMovies) , getString(R.string.scifiMovies) , getString(R.string.warMovies) };

                    builder.setSingleChoiceItems(aboutTickets, -1, new DialogInterface.OnClickListener() {


                        public void onClick(DialogInterface dialog, int item) {
                            switch(item)
                            {
                                case 0:
                                    checkGenres.setText(R.string.allGenres);
                                    edit.putString("GenresMovies" , getString(R.string.allGenres));
                                    edit.putBoolean("AllGenres" , true);
                                    edit.putBoolean("Action" , false);
                                    edit.putBoolean("Adventure" , false);
                                    edit.putBoolean("Animation" , false);
                                    edit.putBoolean("Biography" , false);
                                    edit.putBoolean("Comedy" , false );
                                    edit.putBoolean("Crime" , false );
                                    edit.putBoolean("Drama" , false);
                                    edit.putBoolean("Horror" , false);
                                    edit.putBoolean("Mystery" , false);
                                    edit.putBoolean("Romance" , false);
                                    edit.putBoolean("SCI-FI" , false);
                                    edit.putBoolean("War" , false);
                                    edit.apply();
                                    break;

                                case 1:
                                    checkGenres.setText(R.string.actionMovies);
                                    edit.putString( "GenresMovies" , getString(R.string.actionMovies));
                                    edit.putBoolean("Action" , true);
                                    edit.putBoolean("AllGenres" , false);
                                    edit.putBoolean("Adventure" , false);
                                    edit.putBoolean("Animation" , false);
                                    edit.putBoolean("Biography" , false);
                                    edit.putBoolean("Comedy" , false );
                                    edit.putBoolean("Crime" , false );
                                    edit.putBoolean("Drama" , false);
                                    edit.putBoolean("Horror" , false);
                                    edit.putBoolean("Mystery" , false);
                                    edit.putBoolean("Romance" , false);
                                    edit.putBoolean("SCI-FI" , false);
                                    edit.putBoolean("War" , false);
                                    edit.apply();
                                    break;
                                case 2:
                                    checkGenres.setText(R.string.adventureMovies);
                                    edit.putString( "GenresMovies" , getString(R.string.adventureMovies));
                                    edit.putBoolean("Action" , false);
                                    edit.putBoolean("Adventure" , true);
                                    edit.putBoolean("AllGenres" , false);
                                    edit.putBoolean("Animation" , false);
                                    edit.putBoolean("Biography" , false);
                                    edit.putBoolean("Comedy" , false );
                                    edit.putBoolean("Crime" , false );
                                    edit.putBoolean("Drama" , false);
                                    edit.putBoolean("Horror" , false);
                                    edit.putBoolean("Mystery" , false);
                                    edit.putBoolean("Romance" , false);
                                    edit.putBoolean("SCI-FI" , false);
                                    edit.putBoolean("War" , false);
                                    edit.apply();
                                    break;
                                case 3:
                                    checkGenres.setText(R.string.animationMovies);
                                    edit.putString( "GenresMovies" , getString(R.string.animationMovies));
                                    edit.putBoolean("Action" , false);
                                    edit.putBoolean("AllGenres" , false);
                                    edit.putBoolean("Adventure" , false);
                                    edit.putBoolean("Animation" , true);
                                    edit.putBoolean("Biography" , false);
                                    edit.putBoolean("Comedy" , false );
                                    edit.putBoolean("Crime" , false );
                                    edit.putBoolean("Drama" , false);
                                    edit.putBoolean("Horror" , false);
                                    edit.putBoolean("Mystery" , false);
                                    edit.putBoolean("Romance" , false);
                                    edit.putBoolean("SCI-FI" , false);
                                    edit.putBoolean("War" , false);
                                    edit.apply();
                                    break;
                                case 4:
                                    checkGenres.setText(R.string.biographyMovies);
                                    edit.putString( "GenresMovies" , getString(R.string.biographyMovies));
                                    edit.putBoolean("Action" , false);
                                    edit.putBoolean("AllGenres" , false);
                                    edit.putBoolean("Adventure" , false);
                                    edit.putBoolean("Animation" , false);
                                    edit.putBoolean("Biography" , true);
                                    edit.putBoolean("Comedy" , false );
                                    edit.putBoolean("Crime" , false );
                                    edit.putBoolean("Drama" , false);
                                    edit.putBoolean("Horror" , false);
                                    edit.putBoolean("Mystery" , false);
                                    edit.putBoolean("Romance" , false);
                                    edit.putBoolean("SCI-FI" , false);
                                    edit.putBoolean("War" , false);
                                    edit.apply();
                                    break;
                                case 5:
                                    checkGenres.setText(R.string.comedyMovies);
                                    edit.putString( "GenresMovies" , getString(R.string.comedyMovies));
                                    edit.putBoolean("Action" , false);
                                    edit.putBoolean("AllGenres" , false);
                                    edit.putBoolean("Adventure" , false);
                                    edit.putBoolean("Animation" , false);
                                    edit.putBoolean("Biography" , false);
                                    edit.putBoolean("Comedy" , true );
                                    edit.putBoolean("Crime" , false );
                                    edit.putBoolean("Drama" , false);
                                    edit.putBoolean("Horror" , false);
                                    edit.putBoolean("Mystery" , false);
                                    edit.putBoolean("Romance" , false);
                                    edit.putBoolean("SCI-FI" , false);
                                    edit.putBoolean("War" , false);
                                    edit.apply();
                                    break;
                                case 6:
                                    checkGenres.setText(R.string.crimeMovies);
                                    edit.putString( "GenresMovies" , getString(R.string.crimeMovies));
                                    edit.putBoolean("Action" , false);
                                    edit.putBoolean("Adventure" , false);
                                    edit.putBoolean("AllGenres" , false);
                                    edit.putBoolean("Animation" , false);
                                    edit.putBoolean("Biography" , false);
                                    edit.putBoolean("Comedy" , false );
                                    edit.putBoolean("Crime" , true );
                                    edit.putBoolean("Drama" , false);
                                    edit.putBoolean("Horror" , false);
                                    edit.putBoolean("Mystery" , false);
                                    edit.putBoolean("Romance" , false);
                                    edit.putBoolean("SCI-FI" , false);
                                    edit.putBoolean("War" , false);
                                    edit.apply();
                                    break;
                                case 7:
                                    checkGenres.setText(R.string.dramaMovies);
                                    edit.putString( "GenresMovies" , getString(R.string.dramaMovies));
                                    edit.putBoolean("Action" , false);
                                    edit.putBoolean("Adventure" , false);
                                    edit.putBoolean("Animation" , false);
                                    edit.putBoolean("AllGenres" , false);
                                    edit.putBoolean("Biography" , false);
                                    edit.putBoolean("Comedy" , false );
                                    edit.putBoolean("Crime" , false );
                                    edit.putBoolean("Drama" , true);
                                    edit.putBoolean("Horror" , false);
                                    edit.putBoolean("Mystery" , false);
                                    edit.putBoolean("Romance" , false);
                                    edit.putBoolean("SCI-FI" , false);
                                    edit.putBoolean("War" , false);
                                    edit.apply();
                                    break;
                                case 8 : checkGenres.setText(R.string.horrorMovies);
                                    edit.putString( "GenresMovies" , getString(R.string.horrorMovies));
                                    edit.putBoolean("Action" , false);
                                    edit.putBoolean("Adventure" , false);
                                    edit.putBoolean("AllGenres" , false);
                                    edit.putBoolean("Animation" , false);
                                    edit.putBoolean("Biography" , false);
                                    edit.putBoolean("Comedy" , false );
                                    edit.putBoolean("Crime" , false );
                                    edit.putBoolean("Drama" , false);
                                    edit.putBoolean("Horror" , true);
                                    edit.putBoolean("Mystery" , false);
                                    edit.putBoolean("Romance" , false);
                                    edit.putBoolean("SCI-FI" , false);
                                    edit.putBoolean("War" , false);
                                    edit.apply();
                                    break;
                                case 9 : checkGenres.setText(R.string.mysteryMovies);
                                    edit.putString( "GenresMovies" , getString(R.string.mysteryMovies));
                                    edit.putBoolean("Action" , false);
                                    edit.putBoolean("Adventure" , false);
                                    edit.putBoolean("Animation" , false);
                                    edit.putBoolean("Biography" , false);
                                    edit.putBoolean("Comedy" , false );
                                    edit.putBoolean("Crime" , false );
                                    edit.putBoolean("AllGenres" , false);
                                    edit.putBoolean("Drama" , false);
                                    edit.putBoolean("Horror" , false);
                                    edit.putBoolean("Mystery" , true);
                                    edit.putBoolean("Romance" , false);
                                    edit.putBoolean("SCI-FI" , false);
                                    edit.putBoolean("War" , false);
                                    edit.apply();
                                    break;
                                case 10 : checkGenres.setText(R.string.romanceMovies);
                                    edit.putString( "GenresMovies" , getString(R.string.romanceMovies));
                                    edit.putBoolean("Action" , false);
                                    edit.putBoolean("Adventure" , false);
                                    edit.putBoolean("Animation" , false);
                                    edit.putBoolean("Biography" , false);
                                    edit.putBoolean("Comedy" , false );
                                    edit.putBoolean("Crime" , false );
                                    edit.putBoolean("Drama" , false);
                                    edit.putBoolean("All Genres" , false);
                                    edit.putBoolean("Horror" , false);
                                    edit.putBoolean("Mystery" , false);
                                    edit.putBoolean("Romance" , true);
                                    edit.putBoolean("SCI-FI" , false);
                                    edit.putBoolean("War" , false);
                                    edit.apply();
                                    break;
                                case 11 : checkGenres.setText(R.string.scifiMovies);
                                    edit.putString( "GenresMovies" , getString(R.string.scifiMovies));
                                    edit.putBoolean("Action" , false);
                                    edit.putBoolean("Adventure" , false);
                                    edit.putBoolean("Animation" , false);
                                    edit.putBoolean("Biography" , false);
                                    edit.putBoolean("Comedy" , false );
                                    edit.putBoolean("Crime" , false );
                                    edit.putBoolean("AllGenres" , false);
                                    edit.putBoolean("Drama" , false);
                                    edit.putBoolean("Horror" , false);
                                    edit.putBoolean("Mystery" , false);
                                    edit.putBoolean("Romance" , false);
                                    edit.putBoolean("SCI-FI" , true);
                                    edit.putBoolean("War" , false);
                                    edit.apply();
                                    break;
                                case 12 : checkGenres.setText(R.string.warMovies);
                                    edit.putString( "GenresMovies" , getString(R.string.warMovies));
                                    edit.putBoolean("Action" , false);
                                    edit.putBoolean("Adventure" , false);
                                    edit.putBoolean("Animation" , false);
                                    edit.putBoolean("Biography" , false);
                                    edit.putBoolean("Comedy" , false );
                                    edit.putBoolean("Crime" , false );
                                    edit.putBoolean("Drama" , false);
                                    edit.putBoolean("AllGenres" , false);
                                    edit.putBoolean("Horror" , false);
                                    edit.putBoolean("Mystery" , false);
                                    edit.putBoolean("Romance" , false);
                                    edit.putBoolean("SCI-FI" , false);
                                    edit.putBoolean("War" , true);
                                    edit.apply();
                                    break;
                            }

                            alertDialogActivities.dismiss();
                        }
                    });
                    alertDialogActivities = builder.create();
                    alertDialogActivities.show();

                }
            });


            // the movie time
            moviesTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    moviesTime.setText("");
                    Calendar currentTime = Calendar.getInstance();
                    int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = currentTime.get(Calendar.MINUTE);

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                            if (selectedHour == 0||selectedHour == 1 || selectedHour == 2 || selectedHour == 3 ||
                                    selectedHour == 4 || selectedHour == 5 || selectedHour == 6 ||
                                    selectedHour == 7 || selectedHour == 8 || selectedHour == 9||
                                    selectedHour == 10 || selectedHour == 11) {
                                moviesTime.setText(String.format(Locale.getDefault() ,"%02d:%02d  %s" ,selectedHour , selectedMinute , getString(R.string.am)));
                                edit.putInt("selectHourMovies", selectedHour);

                            } else if (selectedHour == 13 || selectedHour == 14 || selectedHour == 15 ||
                                    selectedHour == 16 || selectedHour == 17 || selectedHour == 18 ||
                                    selectedHour == 19 || selectedHour == 20 || selectedHour == 21 ||
                                    selectedHour == 22 || selectedHour == 23)
                            {
                                moviesTime.setText(String.format(Locale.getDefault() ,"%02d:%02d  %s"  ,selectedHour - 12 , selectedMinute , getString(R.string.pm)));
                                edit.putInt("selectHourMovies", selectedHour);
                            }else if (selectedHour == 12)
                            {
                                moviesTime.setText(String.format(Locale.getDefault() ,"%02d:%02d  %s"  ,selectedHour , selectedMinute , getString(R.string.pm)));
                                edit.putInt("selectHourMovies", selectedHour);
                            }
                            edit.putInt("selectMinuteMovies", selectedMinute);
                            edit.apply();
                            //   Toast.makeText(getContext() , selectedHour + ":" + selectedMinute , Toast.LENGTH_SHORT).show();
                        }
                    }, hour, minute, false);//Yes 12 hour time
                    mTimePicker.setTitle(getString(R.string.selectTime));
                    mTimePicker.show();

                }
            });
            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            if (checkGenres.getText().toString().isEmpty())
            {
                checkGenres.setError("Empty link field  means that Nonce will put the default Genre");
            }
            if (moviesTime.getText().toString().isEmpty())
            {
                moviesTime.setError(getString(R.string.emptyFiledForTime));
            }

        }

        private void loadDetails () {

            // get the switch Button

            SharedPreferences getTheDetails = PreferenceManager.getDefaultSharedPreferences(getContext());
            if (getTheDetails.getBoolean("switchMoviesIsOn", true)) {
                switchMovies.setChecked(true);
                moviesTime.setEnabled(true);
                moviesTime.setTextColor(Color.BLACK);
                checkGenres.setEnabled(true);
                checkGenres.setTextColor(Color.WHITE);

            } else if (getTheDetails.getBoolean("switchMoviesIsOn", false)) {
                switchMovies.setChecked(false);
            }


            // get The Link
            String getGenres = getTheDetails.getString("GenresMovies", "GenresMovies");
            if (getGenres.equals("GenresMovies")) {
                checkGenres.setText(R.string.actionMovies);
            } else {
                checkGenres.setText(getGenres);
            }

            // get the music Time

            selectedH = getTheDetails.getInt("selectHourMovies", 21);
            selectedM = getTheDetails.getInt("selectMinuteMovies", 9);
            moviesTime.setFocusable(false);
            moviesTime.setClickable(true);
            if (selectedH == 0 || selectedH == 1 || selectedH == 2 || selectedH == 3 || selectedH == 4 ||
                    selectedH == 5 || selectedH == 6 ||
                    selectedH == 7 || selectedH == 8 ||
                    selectedH == 9 || selectedH == 10 ||
                    selectedH == 11) {
                moviesTime.setText(String.format(Locale.getDefault(), "%02d:%02d  %s" , selectedH, selectedM, getString(R.string.am)));
            } else if (selectedH == 13 || selectedH == 14 ||
                    selectedH == 15 || selectedH == 16 ||
                    selectedH == 17 || selectedH == 18 ||
                    selectedH == 19 || selectedH == 20 ||
                    selectedH == 21 || selectedH == 22 ||
                    selectedH == 23) {
                selectedH = selectedH - 12;
                moviesTime.setText(String.format(Locale.getDefault(), "%02d:%02d  %s" , selectedH, selectedM, getString(R.string.pm)));
            } else if (selectedH == 12) {
                moviesTime.setText(String.format(Locale.getDefault(), "%02d:%02d  %s" , selectedH, selectedM, getString(R.string.pm)));
            }

        }
    }

    @Override
    public void onBackPressed() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getBaseContext() , Home.class);
                startActivity(i);
            }
        }).start();

    }
}
