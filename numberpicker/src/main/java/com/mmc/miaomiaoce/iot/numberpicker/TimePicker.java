package com.mmc.miaomiaoce.iot.numberpicker;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/3.
 */
public class TimePicker extends LinearLayout {
    private NumberPicker mFirst;
    private NumberPicker mSecond;
    private NumberPicker mThird;

    private Calendar mCalendar;

    private String[] weekDaysStr;

    private static final int MAX = 15; //show max 15 days

    public TimePicker(Context context) {
        this(context,null);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.numberpicker_view_datepicker, this, true);

        mCalendar = Calendar.getInstance();
        mCalendar.setTime(new Date());

        weekDaysStr = this.getResources().getStringArray(R.array.time_picker_weekday);
        /**
         * max is current year, default is last year, min is 100 year before
          */
        mFirst = (NumberPicker)findViewById(R.id.first);
        //mFirst.setMaxValue(mCalendar.getMaximum(Calendar.DAY_OF_WEEK));
        //mFirst.setMinValue(mCalendar.getMinimum(Calendar.DAY_OF_WEEK));
        //Calendar calMax = (Calendar) mCalendar.clone();
        //calMax.add(Calendar.DAY_OF_YEAR, 15);
        setFirstValue(mCalendar);
        mFirst.setColor(Color.BLACK);
        /**
         * max is Dec, min is Jan, default is current month, month should be presented as two digit
         */
        mSecond = (NumberPicker)findViewById(R.id.second);
        mSecond.setMaxValue(mCalendar.getMaximum(Calendar.HOUR_OF_DAY));
        mSecond.setMinValue(mCalendar.getMinimum(Calendar.HOUR_OF_DAY));
        mSecond.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        mSecond.setValue(mCalendar.get(Calendar.HOUR_OF_DAY));
        mSecond.setLabel(getResources().getString(R.string.time_picker_hour));
        mSecond.setColor(Color.BLACK);
        /**
         *
         */
        mThird = (NumberPicker)findViewById(R.id.third);
        mThird.setMaxValue(mCalendar.getMaximum(Calendar.MINUTE));
        mThird.setMinValue(mCalendar.getMinimum(Calendar.MINUTE));
        mThird.setValue(mCalendar.get(Calendar.MINUTE));
        mThird.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        mThird.setLabel(getResources().getString(R.string.time_picker_minute));
        mThird.setColor(Color.BLACK);
    }

    public boolean setValue(Calendar cal){
        if (cal==null){
            return false;
        }
        mCalendar = (Calendar) cal.clone();
        //First.setValue(mCalendar.get(Calendar.DAY_OF_WEEK));
        setFirstValue(mCalendar);
        mSecond.setValue(mCalendar.get(Calendar.HOUR_OF_DAY));
        mThird.setValue(mCalendar.get(Calendar.MINUTE));
        return true;
    }

    public Calendar getValue(){
        //Calendar cal = Calendar.getInstance();
        //cal.setTime(new Date());

        //cal.set(Calendar.DAY_OF_WEEK, mFirst.getValue()-1);
        // today's index is 7, middle of the value
        if (mFirst.getValue()!=7) {
            mCalendar.set(Calendar.DAY_OF_YEAR, mFirst.getValue()-7);
        }
        mCalendar.set(Calendar.HOUR_OF_DAY, mSecond.getValue());
        mCalendar.set(Calendar.MINUTE, mThird.getValue());
        //float temperature = (float)(mTempHigh.getValue()*10 + mTempLow.getValue())/10;
        return mCalendar;
    }

    public TimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void setFirstValue(Calendar cal){
        Calendar calMin = (Calendar)cal.clone();
        calMin.add(Calendar.DAY_OF_YEAR, -7);
        //we have 7 days before today and 7 days after today
        mFirst.setMaxValue(MAX-1);
        mFirst.setMinValue(0);
        mFirst.setValue(7);
        String[] displayStr = new String[MAX];
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        for (int i=0; i<MAX; i++){
            StringBuffer strTmp = new StringBuffer(sdf.format(calMin.getTime()).toString());
            //strTmp.append(sdf.format(calMin).toString());
            strTmp.append(" ");
            strTmp.append(weekDaysStr[calMin.get(Calendar.DAY_OF_WEEK)-1]);
            displayStr[i] = strTmp.toString();
            calMin.add(Calendar.DAY_OF_YEAR, 1);
        }
        mFirst.setDisplayedValues(displayStr);
    }
}
