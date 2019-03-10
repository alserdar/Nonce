package twitter.nonce_app;


import android.content.Context;
import android.graphics.Color;
import android.support.design.internal.ForegroundLinearLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OurAdapter extends BaseAdapter implements Adapter {


    private Context c  ;
    private List<Model> list ;
    public static String alreadyTimeTaken ;
    public static String alreadyDateTaken ;

    public OurAdapter (Context context , List<Model> model ) {

        c = context ;
        list = model ;
    }

    public void setUp (List<Model> model)
    {
        list = model ;
    }

    @Override
    public int getCount() {
        if (list != null){
            return list.size();
        }
        return 0 ;
    }

    @Override
    public Object getItem(int i) {
        if (list != null){
            return list.get(i);
        }
        return null ;
    }

    @Override
    public long getItemId(int i)
    {
        if (list != null) {
            return list.get(i)._id;
        }
        return 0;
    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {



        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.our_adapter, parent, false);
        }

        final Model model = (Model) getItem(position);

        TextView am_pm = (TextView) view.findViewById(R.id.AM_PM);
        TextView txtTime = (TextView) view.findViewById(R.id.alarm_item_time);
        TextView theDate = (TextView) view.findViewById(R.id.theDate);
        TextView _eventName = (TextView) view.findViewById(R.id.eventName);
        TextView extraInfo = (TextView) view.findViewById(R.id.extraInfo);
        ImageView sunOrMoon = (ImageView)view.findViewById(R.id.sunOrMoon);
        ForegroundLinearLayout adapterBackground = (ForegroundLinearLayout)view.findViewById(R.id.adapterLayout);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, model.year);
        calendar.set(Calendar.MONTH, model.month);
        calendar.set(Calendar.DAY_OF_MONTH, model.dayInMonth);
        calendar.set(Calendar.HOUR_OF_DAY, model.hour);
        calendar.set(Calendar.MINUTE, model.minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.compareTo(Calendar.getInstance()) > 0)
        {
            adapterBackground.setBackgroundResource(R.drawable.background_adapter);
        }
        else
        {
            adapterBackground.setBackgroundResource(R.drawable.gone_grey);
        }

        alreadyDateTaken = String.format(Locale.getDefault() , "%02d/%02d/%01d",  model.dayInMonth, model.month+1 , model.year) ;
        theDate.setText(alreadyDateTaken);
        _eventName.setText(model.eventName);
        if (model.extraInfo == null)
        {
            extraInfo.setText("");
        }else {

            extraInfo.setText(model.extraInfo);
        }


        if ( model.hour == 13 ||model.hour == 14 ||model.hour == 15 || model.hour ==16
                ||model.hour == 17 ||model.hour == 18 || model.hour == 19 ||model.hour ==20
                ||model.hour == 21 ||model.hour == 22 ||model.hour == 23 )
        {
            int twelve = 12;
            int newHour = model.hour - twelve;
            alreadyTimeTaken = String.format(Locale.getDefault() ,  "%02d:%02d ", newHour, model.minute);
            txtTime.setText(alreadyTimeTaken);
            am_pm.setText(String.format("%s ", c.getString(R.string.pm)));
            sunOrMoon.setBackgroundResource(R.mipmap.moon);


        }else if (model.hour == 12 )
        {
            alreadyTimeTaken = String.format(Locale.getDefault() ,  "%02d:%02d ", model.hour, model.minute);
            txtTime.setText(alreadyTimeTaken);
            am_pm.setText(String.format("%s ", c.getString(R.string.pm)));
            sunOrMoon.setBackgroundResource(R.mipmap.moon);
        }
        else if (model.hour == 0 || model.hour ==1 || model.hour == 2 ||model.hour ==3
                ||model.hour ==4 ||model.hour ==5 ||model.hour ==6 || model.hour ==7 || model.hour ==8
                ||model.hour == 9 || model.hour ==10 ||model.hour == 11)
        {
            alreadyTimeTaken = String.format(Locale.getDefault() ,  "%02d:%02d ", model.hour, model.minute);
            txtTime.setText(alreadyTimeTaken);
            am_pm.setText(String.format("%s ", c.getString(R.string.am)));
            sunOrMoon.setBackgroundResource(R.mipmap.sun_2);

        }
        view.setBackgroundColor(Color.TRANSPARENT);

        return view;
    }
}