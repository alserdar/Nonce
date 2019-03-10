package twitter.nonce_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Nonce.db1";
    public static final String TABLE_NAME = "Nonce_table1";
    public static final String COL_ID = "ID";

    public static final String EVENT_NAME = "EVENT_NAME";
    public static final String SNOOZE_TIME = "SNOOZE_TIME";
    public static final String HOUR = "HOUR";
    public static final String MINUTE = "MINUTE";

    public static final String ENABLED = "ENABLED";

    public static final String ENABLED_CALL = "ENABLED_CALL";
    public static final String ENABLED_STATUS = "ENABLED_STATUS";

    public static final String ENABLED_SMS = "ENABLED_SMS";
    public static final String ENABLED_HANGOUT = "ENABLED_HANGOUT";
    public static final String ENABLED_TRAVEL = "ENABLED_TRAVEL";
    public static final String ENABLED_TWEET = "ENABLED_TWEET";
    public static final String ENABLED_MOVIES = "ENABLED_MOVIES";

    public static final String DAY_IN_WEEK = "DAY_IN_WEEK";
    public static final String DAY = "DAY";
    public static final String MONTH = "MONTH";
    public static final String YEAR = "YEAR";

    public static final String EXTRA_INFO = "EXTRA_INFO";
    public static final String WHY_CALL = "WHY_CALL";
    public static final String CONTACT_NAME = "CONTACT_NAME";
    public static final String CONTACT_NUMBER = "CONTACT_NUMBER";

    public static final String MEM_TITLE = "MEM_TITLE";
    public static final String MEM_ONE = "MEM_ONE";
    public static final String MEM_TWO = "MEM_TWO";
    public static final String MEM_THREE = "MEM_THREE";
    public static final String MEM_FOUR = "MEM_FOUR";
    public static final String MEM_FIVE = "MEM_FIVE";
    public static final String MEM_SIX = "MEM_SIX";

    public static final String CONTACT_NAME_FOR_SMS = "CONTACT_NAME_FOR_SMS";
    public static final String CONTACT_NUMBER_FOR_SMS = "CONTACT_NUMBER_FOR_SMS";
    public static final String MESSAGE_BODY_FOR_SMS = "MESSAGE_BODY_FOR_SMS";


    public static final String TRAVEL_CURRENT_COUNTRY = "TRAVEL_CURRENT_COUNTRY";
    public static final String TRAVEL_COUNTRY = "TRAVEL_COUNTRY";
    public static final String TRAVEL_PURPOSE = "TRAVEL_PURPOSE";
    public static final String TRAVEL_DAYS = "TRAVEL_DAYS";


    public static final String GET_COUNTRY_BY_SIM = "GET_COUNTRY_BY_SIM";
    public static final String GET_COUNTRY_CODE_BY_SIM = "GET_COUNTRY_CODE_BY_SIM";

    public static final String HANG_CURRENT_PLACE = "HANG_CURRENT_PLACE";
    public static final String HANG_ACTIVITY = "HANG_ACTIVITY";
    public static final String HANG_DETAILS = "HANG_DETAILS";
    public static final String HANG_PLACE = "HANG_PLACE";


    public static final String USER_NAME_FACEBOOK = "USER_NAME_FACEBOOK";
    public static final String MY_STATUS = "MY_STATUS";

    public static final String USER_NAME_TWITTER = "USER_NAME_TWITTER";
    public static final String MY_TWEET = "MY_TWEET";

    public static final String MovieName = "MOVIE_NAME";

    private static DBHelper mInstance;

    public static DBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }


    @Override
    public void onCreate(final SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT ," +
                " WHY_CALL TEXT , CONTACT_NAME TEXT , CONTACT_NUMBER TEXT , CONTACT_NUMBER_FOR_SMS TEXT ," +
                " CONTACT_NAME_FOR_SMS TEXT , MESSAGE_BODY_FOR_SMS TEXT , MEM_TITLE TEXT ," +
                " MEM_ONE TEXT , MEM_TWO TEXT , MEM_THREE TEXT , MEM_FOUR TEXT , MEM_FIVE TEXT , MEM_SIX TEXT ," +
                " GET_COUNTRY_BY_SIM TEXT , GET_COUNTRY_CODE_BY_SIM TEXT , HANG_CURRENT_PLACE TEXT ," +
                " HANG_DETAILS TEXT , HANG_ACTIVITY TEXT , HANG_PLACE TEXT ," +
                " TRAVEL_CURRENT_COUNTRY TEXT ," +
                " TRAVEL_COUNTRY TEXT , TRAVEL_PURPOSE TEXT , TRAVEL_DAYS TEXT ," +
                " EVENT_NAME TEXT , SNOOZE_TIME TEXT , HOUR INTEGER , MINUTE INTEGER ," +
                " ENABLED BOOLEAN , ENABLED_SMS BOOLEAN , ENABLED_CALL BOOLEAN , ENABLED_STATUS BOOLEAN , ENABLED_HANGOUT BOOLEAN ," +
                " ENABLED_TRAVEL BOOLEAN , ENABLED_TWEET BOOLEAN , ENABLED_MOVIES BOOLEAN , DAYS_OF_WEEK TEXT ," +
                " DAY_IN_WEEK TEXT, DAY TEXT , MONTH TEXT , YEAR TEXT , EXTRA_INFO TEXT ," +
                " USER_NAME_TWITTER TEXT , MY_TWEET TEXT ," +
                " USER_NAME_FACEBOOK TEXT , MY_STATUS TEXT , MOVIE_NAME TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }


    private Model populateModel(Cursor cursor) {
        Model model = new Model();
        model._id = cursor.getLong(cursor.getColumnIndex(COL_ID));
        model.eventName = cursor.getString(cursor.getColumnIndex(EVENT_NAME));
        model.whyCall = cursor.getString(cursor.getColumnIndex(WHY_CALL));
        model.contactName = cursor.getString(cursor.getColumnIndex(CONTACT_NAME));
        model.contactNumber = cursor.getString(cursor.getColumnIndex(CONTACT_NUMBER));

        model.memTitle = cursor.getString(cursor.getColumnIndex(MEM_TITLE));
        model.memOne = cursor.getString(cursor.getColumnIndex(MEM_ONE));
        model.memTwo = cursor.getString(cursor.getColumnIndex(MEM_TWO));
        model.memThree = cursor.getString(cursor.getColumnIndex(MEM_THREE));
        model.memFour = cursor.getString(cursor.getColumnIndex(MEM_FOUR));
        model.memFive = cursor.getString(cursor.getColumnIndex(MEM_FIVE));
        model.memSix = cursor.getString(cursor.getColumnIndex(MEM_SIX));

        model.contactNameForSMS = cursor.getString(cursor.getColumnIndex(CONTACT_NAME_FOR_SMS));
        model.contactNumberForSMS = cursor.getString(cursor.getColumnIndex(CONTACT_NUMBER_FOR_SMS));
        model.messageBodyForSMS = cursor.getString(cursor.getColumnIndex(MESSAGE_BODY_FOR_SMS));

        model.countryBySimCard = cursor.getString(cursor.getColumnIndex(GET_COUNTRY_BY_SIM));
        model.countryCode = cursor.getString(cursor.getColumnIndex(GET_COUNTRY_CODE_BY_SIM));

        model.travelCurrentCountry = cursor.getString(cursor.getColumnIndex(TRAVEL_CURRENT_COUNTRY));
        model.travelCountry = cursor.getString(cursor.getColumnIndex(TRAVEL_COUNTRY));
        model.travelPurpose = cursor.getString(cursor.getColumnIndex(TRAVEL_PURPOSE));
        model.travelDays = cursor.getString(cursor.getColumnIndex(TRAVEL_DAYS));

        model.snoozeTime = cursor.getString(cursor.getColumnIndex(SNOOZE_TIME));
        model.hour = cursor.getInt(cursor.getColumnIndex(HOUR));
        model.minute = cursor.getInt(cursor.getColumnIndex(MINUTE));
        model.enabled = cursor.getInt(cursor.getColumnIndex(ENABLED)) == 0 ? false : true;

        model.enabledCall = cursor.getInt(cursor.getColumnIndex(ENABLED_CALL)) == 0 ? false : true;
        model.enabledUpdateStatus = cursor.getInt(cursor.getColumnIndex(ENABLED_STATUS)) == 0 ? false : true;
        model.enabledSMS = cursor.getInt(cursor.getColumnIndex(ENABLED_SMS)) == 0 ? false : true;
        model.enabledHangout = cursor.getInt(cursor.getColumnIndex(ENABLED_HANGOUT)) == 0 ? false : true;
        model.enabledTravel = cursor.getInt(cursor.getColumnIndex(ENABLED_TRAVEL)) == 0 ? false : true;
        model.enabledTweet = cursor.getInt(cursor.getColumnIndex(ENABLED_TWEET)) == 0 ? false : true;
        model.enabledMovies = cursor.getInt(cursor.getColumnIndex(ENABLED_MOVIES)) == 0 ? false : true;

        model.dayInWeek = cursor.getInt(cursor.getColumnIndex(DAY_IN_WEEK));
        model.dayInMonth = cursor.getInt(cursor.getColumnIndex(DAY));
        model.month = cursor.getInt(cursor.getColumnIndex(MONTH));
        model.year = cursor.getInt(cursor.getColumnIndex(YEAR));

        model.extraInfo = cursor.getString(cursor.getColumnIndex(EXTRA_INFO));

        model.hangCurrentPlace = cursor.getString(cursor.getColumnIndex(HANG_CURRENT_PLACE));
        model.hangActivity = cursor.getString(cursor.getColumnIndex(HANG_ACTIVITY));
        model.hangPlace = cursor.getString(cursor.getColumnIndex(HANG_PLACE));
        model.hangDetails = cursor.getString(cursor.getColumnIndex(HANG_DETAILS));

        model.userNameTwitter = cursor.getString(cursor.getColumnIndex(USER_NAME_TWITTER));
        model.myTweet = cursor.getString(cursor.getColumnIndex(MY_TWEET));

        model.userNameFacebook = cursor.getString(cursor.getColumnIndex(USER_NAME_FACEBOOK));
        model.myStatus = cursor.getString(cursor.getColumnIndex(MY_STATUS));

        model.movieName = cursor.getString(cursor.getColumnIndex(MovieName));


        return model;

    }


    private ContentValues populateContent(Model model) {
        ContentValues values = new ContentValues();

        values.put(EVENT_NAME, model.eventName);
        values.put(WHY_CALL, model.whyCall);
        values.put(CONTACT_NAME, model.contactName);
        values.put(CONTACT_NUMBER, model.contactNumber);

        values.put(MEM_TITLE, model.memTitle);
        values.put(MEM_ONE, model.memOne);
        values.put(MEM_TWO, model.memTwo);
        values.put(MEM_THREE, model.memThree);
        values.put(MEM_FOUR, model.memFour);
        values.put(MEM_FIVE, model.memFive);
        values.put(MEM_SIX, model.memSix);

        values.put(CONTACT_NAME_FOR_SMS , model.contactNameForSMS);
        values.put(CONTACT_NUMBER_FOR_SMS , model.contactNumberForSMS);
        values.put(MESSAGE_BODY_FOR_SMS , model.messageBodyForSMS);

        values.put(GET_COUNTRY_BY_SIM , model.countryBySimCard);
        values.put(GET_COUNTRY_CODE_BY_SIM , model.countryCode);

        values.put(HANG_CURRENT_PLACE, model.hangCurrentPlace);
        values.put(HANG_ACTIVITY, model.hangActivity);
        values.put(HANG_PLACE, model.hangPlace);
        values.put(HANG_DETAILS, model.hangDetails);


        values.put(TRAVEL_CURRENT_COUNTRY , model.travelCurrentCountry);
        values.put(TRAVEL_COUNTRY , model.travelCountry);
        values.put(TRAVEL_PURPOSE , model.travelPurpose);
        values.put(TRAVEL_DAYS , model.travelDays);

        values.put(SNOOZE_TIME, model.snoozeTime);
        values.put(HOUR, model.hour);
        values.put(MINUTE, model.minute);
        values.put(ENABLED, model.enabled);

        values.put(ENABLED_HANGOUT, model.enabledHangout);
        values.put(ENABLED_MOVIES, model.enabledMovies);
        values.put(ENABLED_SMS, model.enabledSMS);
        values.put(ENABLED_TRAVEL, model.enabledTravel);
        values.put(ENABLED_TWEET, model.enabledTweet);
        values.put(ENABLED_CALL, model.enabledCall);
        values.put(ENABLED_STATUS, model.enabledUpdateStatus);

        values.put(DAY_IN_WEEK, model.dayInWeek);
        values.put(DAY, model.dayInMonth);
        values.put(MONTH, model.month);
        values.put(YEAR, model.year);

        values.put(EXTRA_INFO, model.extraInfo);

        values.put(USER_NAME_TWITTER, model.userNameTwitter);
        values.put(MY_TWEET, model.myTweet);

        values.put(USER_NAME_FACEBOOK , model.userNameFacebook);
        values.put(MY_STATUS , model.myStatus);

        values.put(MovieName , model.movieName);

        return values;
    }

    public Model viewSingleData(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = " + id;
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToNext()) {

            return populateModel(cursor);

        }
        return null;
    }

    public long insertInfo(Model model) {

        ContentValues values = populateContent(model);
        getWritableDatabase().insert(TABLE_NAME, null, values);
        return model._id;
    }

    public List<Model> viewAllData() {

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String select = "SELECT * FROM " + TABLE_NAME;

            Cursor c = db.rawQuery(select, null);


            List<Model> alarmList = new ArrayList<>();

            while (c.moveToNext()) {
                alarmList.add(populateModel(c));
            }

            if (!alarmList.isEmpty()) {
                return alarmList;
            }
            c.close();

        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }


    public long updateData(Model model) {

        ContentValues values = populateContent(model);
        getWritableDatabase().update(TABLE_NAME, values, "ID = ?", new String[]{String.valueOf(model._id)});
        return model._id;
    }

    public int deleteData(long id) {
        return getWritableDatabase().delete(TABLE_NAME, "ID = ?", new String[]{(String.valueOf(id))});
    }
}