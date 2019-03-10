package twitter.nonce_app.free_time;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import java.util.Random;

import twitter.nonce_app.DetailsActivity;
import twitter.nonce_app.R;
import twitter.nonce_app.settings.ConnectingWithSettings;

public class MoviesService extends Service {
    String randomAllGenres , randomAction  , randomAdventure , randomAnimation , randomBiography ,
            randomComedy, randomCrime , randomDrama , randomHorror ,
            randomMystery , randomRomance , randomSCI_FI , randomWar;
    final String NOTIFICATION_TAG = "Nonce Movies";


    public MoviesService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null ;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Resources res = this.getResources();
        long id = -1;
        final Random r = new Random();

        final String allGenres [] = res.getStringArray(R.array.AllGenres);
        final String action [] = res.getStringArray(R.array.Action);
        final String adventure [] = res.getStringArray(R.array.Adventure);
        final String animation [] = res.getStringArray(R.array.Animation);
        final String biography [] = res.getStringArray(R.array.Biography);
        final String comedy [] = res.getStringArray(R.array.Comedy);
        final String crime [] = res.getStringArray(R.array.Crime);
        final String drama [] = res.getStringArray(R.array.Drama);
        final String horror [] = res.getStringArray(R.array.Horror);
        final String mystery [] = res.getStringArray(R.array.Mystery);
        final String romance [] = res.getStringArray(R.array.Romance);
        final String scifi [] = res.getStringArray(R.array.Sci_Fi);
        final String war [] = res.getStringArray(R.array.War);

        randomAllGenres = allGenres[r.nextInt(allGenres.length)];
        randomAction = action[r.nextInt(action.length)];
        randomAdventure = adventure[r.nextInt(adventure.length)];
        randomAnimation = animation[r.nextInt(animation.length)];
        randomBiography = biography[r.nextInt(biography.length)];
        randomComedy = comedy[r.nextInt(comedy.length)];
        randomCrime = crime [r.nextInt(crime.length)];
        randomDrama = drama[r.nextInt(drama.length)];
        randomHorror = horror[r.nextInt(horror.length)];
        randomMystery = mystery[r.nextInt(mystery.length)];
        randomRomance = romance[r.nextInt(romance.length)];
        randomSCI_FI = scifi[r.nextInt(scifi.length)];
        randomWar = war[r.nextInt(war.length)];

        ConnectingWithSettings connect = new ConnectingWithSettings();
        String notTone = connect.notificationTone(getBaseContext());
        Uri defNote = Settings.System.DEFAULT_NOTIFICATION_URI;
       // connect.toneLevel(getBaseContext());
        SharedPreferences getThemAll = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        getThemAll.getBoolean("Action" , true);
        if (getThemAll.getBoolean("switchMoviesIsOn" , true))
        {
            if (getThemAll.getBoolean("Action" , true))
            {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.icon_custom)
                        .setContentTitle("Nonce : " + getString(R.string.movies))
                        .setContentText(randomAction)
                        // .setSound(notTone)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setNumber(0)
                        .setColor(Color.WHITE)
                        .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                        .setContentIntent(PendingIntent.getActivity(getBaseContext(), 101 ,new Intent
                                        (Intent.ACTION_VIEW , Uri.parse("http://www.google.com/search?q=" + randomAction)),
                                PendingIntent.FLAG_UPDATE_CURRENT))

                        .addAction(R.mipmap.share, res.getString(R.string.action_share),
                                PendingIntent.getActivity( getBaseContext(),2120, Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                                .setType("text/plain")
                                                .putExtra(Intent.EXTRA_TEXT, getString(R.string.nonceSuggestToWatch) + randomAction), "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT))

                        .addAction(R.mipmap.add, res.getString(R.string.action_list),
                                PendingIntent.getActivity( getBaseContext(),0, Intent.createChooser(new Intent(getBaseContext() , DetailsActivity.class)
                                                .putExtra("alarmName" , getString(R.string.nonce_movies)).putExtra("theEvent", "Nonce Movies")
                                                .putExtra("MovieName" , randomAction)
                                                .putExtra("extraInfo" , randomAction)
                                                .putExtra("id" , id),
                                        "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT));
                if (connect.isToneEnabled(this)) {

                    if (notTone == null)
                    {
                        builder.setSound(defNote);
                    }else
                    {
                        builder.setSound(Uri.parse(notTone));
                    }
                }
                notify(getBaseContext(), builder.build());
            } else if (getThemAll.getBoolean("Adventure" , true))
            {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.icon_custom)
                        .setContentTitle("Nonce : " + getString(R.string.movies))
                        .setContentText(randomAdventure)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setNumber(0)
                        .setColor(Color.WHITE)
                        .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                        .setContentIntent(PendingIntent.getActivity(getBaseContext(),102,new Intent
                                        (Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" + randomAdventure)),
                                PendingIntent.FLAG_UPDATE_CURRENT))


                        .addAction(R.mipmap.share, res.getString(R.string.action_share),
                                PendingIntent.getActivity( getBaseContext(),2121, Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                                .setType("text/plain")
                                                .putExtra(Intent.EXTRA_TEXT, getString(R.string.nonceSuggestToWatch) + randomAdventure), "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT))

                        .addAction(R.mipmap.add, res.getString(R.string.action_list),
                                PendingIntent.getActivity( getBaseContext(),0, Intent.createChooser(new Intent(getBaseContext() , DetailsActivity.class)
                                                .putExtra("alarmName" , getString(R.string.nonce_movies)).putExtra("theEvent", "Nonce Movies")
                                                .putExtra("MovieName" , randomAdventure)
                                                .putExtra("extraInfo" , randomAdventure)
                                                .putExtra("id" , id),
                                        "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT));

                if (connect.isToneEnabled(this)) {

                    if (notTone == null)
                    {
                        builder.setSound(defNote);
                    }else
                    {
                        builder.setSound(Uri.parse(notTone));
                    }
                }
                notify(getBaseContext(), builder.build());
            }else if (getThemAll.getBoolean("Animation" , true))
            {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.icon_custom)
                        .setContentTitle("Nonce : " + getString(R.string.movies))
                        .setContentText(randomAnimation)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setNumber(0)
                        .setColor(Color.WHITE)
                        .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                        .setContentIntent(PendingIntent.getActivity(getBaseContext(),103,new Intent
                                        (Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" + randomAnimation)),
                                PendingIntent.FLAG_UPDATE_CURRENT))


                        .addAction(R.mipmap.share, res.getString(R.string.action_share),
                                PendingIntent.getActivity( getBaseContext(),2122, Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                                .setType("text/plain")
                                                .putExtra(Intent.EXTRA_TEXT, getString(R.string.nonceSuggestToWatch) + randomAnimation), "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT))

                        .addAction(R.mipmap.add, res.getString(R.string.action_list),
                                PendingIntent.getActivity( getBaseContext(),0, Intent.createChooser(new Intent(getBaseContext() , DetailsActivity.class)
                                                .putExtra("alarmName" , getString(R.string.nonce_movies)).putExtra("theEvent", "Nonce Movies")
                                                .putExtra("MovieName" , randomAnimation)
                                                .putExtra("extraInfo" , randomAnimation)
                                                .putExtra("id" , id),
                                        "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT));

                if (connect.isToneEnabled(this)) {

                    if (notTone == null)
                    {
                        builder.setSound(defNote);
                    }else
                    {
                        builder.setSound(Uri.parse(notTone));
                    }
                }
                notify(getBaseContext(), builder.build());
            }else if (getThemAll.getBoolean("Biography" , true))
            {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.icon_custom)
                        .setContentTitle("Nonce : " + getString(R.string.movies))
                        .setContentText(randomBiography)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setNumber(0)
                        .setColor(Color.WHITE)
                        .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                        .setContentIntent(PendingIntent.getActivity(getBaseContext(),104,new Intent
                                        (Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q="+randomBiography)),
                                PendingIntent.FLAG_UPDATE_CURRENT))


                        .addAction(R.mipmap.share, res.getString(R.string.action_share),
                                PendingIntent.getActivity( getBaseContext(),2123, Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                                .setType("text/plain")
                                                .putExtra(Intent.EXTRA_TEXT, getString(R.string.nonceSuggestToWatch) + randomBiography), "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT))

                        .addAction(R.mipmap.add, res.getString(R.string.action_list),
                                PendingIntent.getActivity( getBaseContext(),0, Intent.createChooser(new Intent(getBaseContext() , DetailsActivity.class)
                                                .putExtra("alarmName" , getString(R.string.nonce_movies)).putExtra("theEvent", "Nonce Movies")
                                                .putExtra("MovieName" , randomBiography)
                                                .putExtra("extraInfo" , randomBiography)
                                                .putExtra("id" , id),
                                        "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT));

                if (connect.isToneEnabled(this)) {

                    if (notTone == null)
                    {
                        builder.setSound(defNote);
                    }else
                    {
                        builder.setSound(Uri.parse(notTone));
                    }
                }
                notify(getBaseContext(), builder.build());

            }else if (getThemAll.getBoolean("Comedy" , true))
            {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.icon_custom)
                        .setContentTitle("Nonce : " + getString(R.string.movies))
                        .setContentText(randomComedy)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setNumber(0)
                        .setColor(Color.WHITE)
                        .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                        .setContentIntent(PendingIntent.getActivity(getBaseContext(),105,new Intent
                                        (Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" + randomComedy)),
                                PendingIntent.FLAG_UPDATE_CURRENT))


                        .addAction(R.mipmap.share, res.getString(R.string.action_share),
                                PendingIntent.getActivity( getBaseContext(),2124, Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                                .setType("text/plain")
                                                .putExtra(Intent.EXTRA_TEXT, getString(R.string.nonceSuggestToWatch) + randomComedy), "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT))

                        .addAction(R.mipmap.add, res.getString(R.string.action_list),
                                PendingIntent.getActivity( getBaseContext(),0, Intent.createChooser(new Intent(getBaseContext() , DetailsActivity.class)
                                                .putExtra("alarmName" , getString(R.string.nonce_movies)).putExtra("theEvent", "Nonce Movies")
                                                .putExtra("MovieName" , randomComedy)
                                                .putExtra("extraInfo" , randomComedy)
                                                .putExtra("id" , id),
                                        "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT)) ;

                if (connect.isToneEnabled(this)) {

                    if (notTone == null)
                    {
                        builder.setSound(defNote);
                    }else
                    {
                        builder.setSound(Uri.parse(notTone));
                    }
                }
                notify(getBaseContext(), builder.build());

            }else if (getThemAll.getBoolean("Crime" , true))
            {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.icon_custom)
                        .setContentTitle("Nonce : " +getString(R.string.movies))
                        .setContentText(randomCrime)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setNumber(0)
                        .setColor(Color.WHITE)
                        .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                        .setContentIntent(PendingIntent.getActivity(getBaseContext(),106,new Intent
                                        (Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" + randomCrime)),
                                PendingIntent.FLAG_UPDATE_CURRENT))


                        .addAction(R.mipmap.share, res.getString(R.string.action_share),
                                PendingIntent.getActivity( getBaseContext(),2125, Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                                .setType("text/plain")
                                                .putExtra(Intent.EXTRA_TEXT, getString(R.string.nonceSuggestToWatch) + randomCrime), "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT))

                        .addAction(R.mipmap.add, res.getString(R.string.action_list),
                                PendingIntent.getActivity( getBaseContext(),0, Intent.createChooser(new Intent(getBaseContext() , DetailsActivity.class)
                                                .putExtra("alarmName" , getString(R.string.nonce_movies)).putExtra("theEvent", "Nonce Movies")
                                                .putExtra("MovieName" , randomCrime)
                                                .putExtra("extraInfo" , randomCrime)
                                                .putExtra("id" , id),
                                        "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT));

                if (connect.isToneEnabled(this)) {

                    if (notTone == null)
                    {
                        builder.setSound(defNote);
                    }else
                    {
                        builder.setSound(Uri.parse(notTone));
                    }
                }
                notify(getBaseContext(), builder.build());
            }else if (getThemAll.getBoolean("Drama" , true))
            {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.icon_custom)
                        .setContentTitle("Nonce : " + getString(R.string.movies))
                        .setContentText(randomDrama)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setNumber(0)
                        .setColor(Color.WHITE)
                        .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                        .setContentIntent(PendingIntent.getActivity(getBaseContext(),107,new Intent
                                        (Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q="+randomDrama)),
                                PendingIntent.FLAG_UPDATE_CURRENT))


                        .addAction(R.mipmap.share, res.getString(R.string.action_share),
                                PendingIntent.getActivity( getBaseContext(),2126, Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                                .setType("text/plain")
                                                .putExtra(Intent.EXTRA_TEXT, getString(R.string.nonceSuggestToWatch) + randomDrama), "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT))

                        .addAction(R.mipmap.add, res.getString(R.string.action_list),
                                PendingIntent.getActivity( getBaseContext(),0, Intent.createChooser(new Intent(getBaseContext() , DetailsActivity.class)
                                                .putExtra("alarmName" , getString(R.string.nonce_movies)).putExtra("theEvent", "Nonce Movies")
                                                .putExtra("MovieName" , randomDrama)
                                                .putExtra("extraInfo" , randomDrama)
                                                .putExtra("id" , id),
                                        "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT));


                if (connect.isToneEnabled(this)) {

                    if (notTone == null)
                    {
                        builder.setSound(defNote);
                    }else
                    {
                        builder.setSound(Uri.parse(notTone));
                    }
                }
                notify(getBaseContext(), builder.build());
            }else if (getThemAll.getBoolean("Horror" , true))
            {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.icon_custom)
                        .setContentTitle("Nonce : " + getString(R.string.movies))
                        .setContentText(randomHorror)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setNumber(0)
                        .setColor(Color.WHITE)
                        .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                        .setContentIntent(PendingIntent.getActivity(getBaseContext(),108,new Intent
                                        (Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q="+randomHorror)),
                                PendingIntent.FLAG_UPDATE_CURRENT))


                        .addAction(R.mipmap.share, res.getString(R.string.action_share),
                                PendingIntent.getActivity( getBaseContext(),2127, Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                                .setType("text/plain")
                                                .putExtra(Intent.EXTRA_TEXT, getString(R.string.nonceSuggestToWatch) + randomHorror), "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT))

                        .addAction(R.mipmap.add, res.getString(R.string.action_list),
                                PendingIntent.getActivity( getBaseContext(),0, Intent.createChooser(new Intent(getBaseContext() , DetailsActivity.class)
                                                .putExtra("alarmName" , getString(R.string.nonce_movies)).putExtra("theEvent", "Nonce Movies")
                                                .putExtra("MovieName" , randomHorror)
                                                .putExtra("extraInfo" , randomHorror)
                                                .putExtra("id" , id),
                                        "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT));

                if (connect.isToneEnabled(this)) {

                    if (notTone == null)
                    {
                        builder.setSound(defNote);
                    }else
                    {
                        builder.setSound(Uri.parse(notTone));
                    }
                }
                notify(getBaseContext(), builder.build());
            }else if (getThemAll.getBoolean("Mystery" , true))
            {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.icon_custom)
                        .setContentTitle("Nonce : " + getString(R.string.movies))
                        .setContentText(randomMystery)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setNumber(0)
                        .setColor(Color.WHITE)
                        .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                        .setContentIntent(PendingIntent.getActivity(getBaseContext(),109,new Intent
                                        (Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q="+randomMystery)),
                                PendingIntent.FLAG_UPDATE_CURRENT))


                        .addAction(R.mipmap.share, res.getString(R.string.action_share),
                                PendingIntent.getActivity( getBaseContext(),2128, Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                                .setType("text/plain")
                                                .putExtra(Intent.EXTRA_TEXT, getString(R.string.nonceSuggestToWatch) +randomMystery), "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT))

                        .addAction(R.mipmap.add, res.getString(R.string.action_list),
                                PendingIntent.getActivity( getBaseContext(),0, Intent.createChooser(new Intent(getBaseContext() , DetailsActivity.class)
                                                .putExtra("alarmName" , getString(R.string.nonce_movies)).putExtra("theEvent", "Nonce Movies")
                                                .putExtra("MovieName" , randomMystery)
                                                .putExtra("extraInfo" , randomMystery)
                                                .putExtra("id" , id),
                                        "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT)) ;

                if (connect.isToneEnabled(this)) {

                    if (notTone == null)
                    {
                        builder.setSound(defNote);
                    }else
                    {
                        builder.setSound(Uri.parse(notTone));
                    }
                }
                notify(getBaseContext(), builder.build());
            }else if (getThemAll.getBoolean("Romance" , true))
            {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.icon_custom)
                        .setContentTitle("Nonce : " + getString(R.string.movies))
                        .setContentText(randomRomance)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setNumber(0)
                        .setColor(Color.WHITE)
                        .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                        .setContentIntent(PendingIntent.getActivity(getBaseContext(),110,new Intent
                                        (Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q="+randomRomance)),
                                PendingIntent.FLAG_UPDATE_CURRENT))


                        .addAction(R.mipmap.share, res.getString(R.string.action_share),
                                PendingIntent.getActivity( getBaseContext(),2129, Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                                .setType("text/plain")
                                                .putExtra(Intent.EXTRA_TEXT, getString(R.string.nonceSuggestToWatch) + randomRomance), "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT))

                        .addAction(R.mipmap.add, res.getString(R.string.action_list),
                                PendingIntent.getActivity( getBaseContext(),0, Intent.createChooser(new Intent(getBaseContext() , DetailsActivity.class)
                                                .putExtra("alarmName" , getString(R.string.nonce_movies)).putExtra("theEvent", "Nonce Movies")
                                                .putExtra("MovieName" , randomRomance)
                                                .putExtra("extraInfo" , randomRomance)
                                                .putExtra("id" , id),
                                        "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT));

                if (connect.isToneEnabled(this)) {

                    if (notTone == null)
                    {
                        builder.setSound(defNote);
                    }else
                    {
                        builder.setSound(Uri.parse(notTone));
                    }
                }
                notify(getBaseContext(), builder.build());
            }else if (getThemAll.getBoolean("SCI-FI" , true))
            {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.icon_custom)
                        .setContentTitle("Nonce : " + getString(R.string.movies))
                        .setContentText(randomSCI_FI)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setNumber(0)
                        .setColor(Color.WHITE)
                        .setLights(Color.rgb(0 , 163 , 210) , 100 , 100)
                        .setContentIntent(PendingIntent.getActivity(getBaseContext(),111,new Intent
                                        (Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" + randomSCI_FI)),
                                PendingIntent.FLAG_UPDATE_CURRENT))


                        .addAction(R.mipmap.share, res.getString(R.string.action_share),
                                PendingIntent.getActivity( getBaseContext(),2130, Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                                .setType("text/plain")
                                                .putExtra(Intent.EXTRA_TEXT, getString(R.string.nonceSuggestToWatch) +randomSCI_FI), ""),
                                        PendingIntent.FLAG_UPDATE_CURRENT))

                        .addAction(R.mipmap.add, res.getString(R.string.action_list),
                                PendingIntent.getActivity( getBaseContext(),0, Intent.createChooser(new Intent(getBaseContext() , DetailsActivity.class)
                                                .putExtra("alarmName" , getString(R.string.nonce_movies)).putExtra("theEvent", "Nonce Movies")
                                                .putExtra("MovieName" , randomSCI_FI)
                                                .putExtra("extraInfo" , randomSCI_FI)
                                                .putExtra("id" , id),
                                        "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT));

                if (connect.isToneEnabled(this)) {

                    if (notTone == null)
                    {
                        builder.setSound(defNote);
                    }else
                    {
                        builder.setSound(Uri.parse(notTone));
                    }
                }
                notify(getBaseContext(), builder.build());
            }else if (getThemAll.getBoolean("War" , true))
            {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.icon_custom)
                        .setContentTitle("Nonce : " + getString(R.string.movies))
                        .setContentText(randomWar)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setNumber(0)
                        .setColor(Color.WHITE)
                        .setContentIntent(PendingIntent.getActivity(getBaseContext(),0,new Intent
                                        (Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q="+randomWar)),
                                PendingIntent.FLAG_UPDATE_CURRENT))


                        .addAction(R.mipmap.share, res.getString(R.string.action_share),
                                PendingIntent.getActivity( getBaseContext(),2131, Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                                .setType("text/plain")
                                                .putExtra(Intent.EXTRA_TEXT, getString(R.string.nonceSuggestToWatch) + randomWar), "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT))

                        .addAction(R.mipmap.add, res.getString(R.string.action_list),
                                PendingIntent.getActivity( getBaseContext(),0, Intent.createChooser(new Intent(getBaseContext() , DetailsActivity.class)
                                                .putExtra("alarmName" , getString(R.string.nonce_movies)).putExtra("theEvent", "Nonce Movies")
                                                .putExtra("MovieName" , randomWar)
                                                .putExtra("extraInfo" , randomWar)
                                                .putExtra("id" , id),
                                        "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT))
                        .setAutoCancel(true);

                if (connect.isToneEnabled(this)) {

                    if (notTone == null)
                    {
                        builder.setSound(defNote);
                    }else
                    {
                        builder.setSound(Uri.parse(notTone));
                    }
                }
                notify(getBaseContext(), builder.build());
            }else if (getThemAll.getBoolean("AllGenres" , true))
            {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.icon_custom)
                        .setContentTitle("Nonce : " + getString(R.string.movies))
                        .setContentText(randomAllGenres)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setNumber(0)
                        .setColor(Color.WHITE)
                        .setContentIntent(PendingIntent.getActivity(getBaseContext(),0,new Intent
                                        (Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q="+randomAllGenres)),
                                PendingIntent.FLAG_UPDATE_CURRENT))


                        .addAction(R.mipmap.share, res.getString(R.string.action_share),
                                PendingIntent.getActivity( getBaseContext(),2131, Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                                .setType("text/plain")
                                                .putExtra(Intent.EXTRA_TEXT, getString(R.string.nonceSuggestToWatch) + randomAllGenres), "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT))

                        .addAction(R.mipmap.add, res.getString(R.string.action_list),
                                PendingIntent.getActivity( getBaseContext(),0, Intent.createChooser(new Intent(getBaseContext() , DetailsActivity.class)
                                        .putExtra("alarmName" , getString(R.string.nonce_movies))
                                        .putExtra("theEvent", "Nonce Movies")
                                        .putExtra("MovieName" , randomAllGenres)
                                        .putExtra("extraInfo" , randomAllGenres)
                                        .putExtra("id" , id),
                                        "Dummy title"),
                                        PendingIntent.FLAG_UPDATE_CURRENT))
                        .setAutoCancel(true);

                if (connect.isToneEnabled(this)) {

                    if (notTone == null)
                    {
                        builder.setSound(defNote);
                    }else
                    {
                        builder.setSound(Uri.parse(notTone));
                    }
                }
                notify(getBaseContext(), builder.build());
            }

        }else
        {

        }

        return Service.START_FLAG_RETRY;
    }

    private void notify(Context context, Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, 0, notification);
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification);
        }
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0);
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode());
        }
    }
}
