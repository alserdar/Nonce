package twitter.nonce_app;

/**
 * Created by zizo on 7/11/2016.
 */
public class Model {

    public long _id  = -1 ;

    public boolean enabled ;
    public boolean enabledCall ;
    public boolean enabledSMS ;
    public boolean enabledHangout ;
    public boolean enabledTravel ;
    public boolean enabledMovies ;
    public boolean enabledTweet ;
    public boolean enabledUpdateStatus ;

    public String eventName ;
    public String snoozeTime ;

    public int hour ;
    public int minute ;
    public int dayInMonth ;
    public int dayInWeek ;
    public int month ;
    public int year ;

    public String whyCall ;
    public String contactName ;
    public String contactNumber ;


    public String memTitle ;
    public String memOne ;
    public String memTwo ;
    public String memThree ;
    public String memFour ;
    public String memFive ;
    public String memSix ;


    public String contactNameForSMS ;
    public String messageBodyForSMS ;
    public String contactNumberForSMS ;

    public String countryBySimCard ;
    public String countryCode ;

    public String hangCurrentPlace;
    public String hangActivity;
    public String hangPlace;
    public String hangDetails;

    public String travelCurrentCountry ;
    public String travelCountry ;
    public String travelPurpose ;
    public String travelDays ;

    public String userNameTwitter ;
    public String myTweet ;
    public String userNameFacebook;
    public String myStatus;

    public String movieName ;

    public String extraInfo;


}
