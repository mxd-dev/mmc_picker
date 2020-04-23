package com.mmc.miaomiaoce.iot.numberpicker;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/3.
 */
public class DatePicker extends LinearLayout {
    private NumberPicker mFirst;
    private NumberPicker mSecond;
    private NumberPicker mThird;

    private Calendar mCalendar;

    public DatePicker(Context context) {
        super(context);
    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.numberpicker_view_datepicker, this, true);

        mCalendar = Calendar.getInstance();
        mCalendar.setTime(new Date());

        /**
         * max is current year, default is last year, min is 100 year before
          */
        mFirst = (NumberPicker)findViewById(R.id.first);
        int year = mCalendar.get(Calendar.YEAR);
        mFirst.setMaxValue(year);
        mFirst.setMinValue(year-100);
        mFirst.setValue(year);
        mFirst.setLabel(getResources().getString(R.string.date_picker_year));
        mFirst.setColor(Color.BLACK);
        mFirst.setWrapSelectorWheel(false);
        /**
         * max is Dec, min is Jan, default is current month, month should be presented as two digit
         */
        mSecond = (NumberPicker)findViewById(R.id.second);
        mSecond.setMaxValue(mCalendar.getMaximum(Calendar.MONTH)+1);
        mSecond.setMinValue(mCalendar.getMinimum(Calendar.MONTH)+1);
        mSecond.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        mSecond.setValue(mCalendar.get(Calendar.MONTH)+1);
        mSecond.setLabel(getResources().getString(R.string.date_picker_month));
        mSecond.setColor(Color.BLACK);
        /**
         *
         */
        mThird = (NumberPicker)findViewById(R.id.third);
        mThird.setMaxValue(mCalendar.getMaximum(Calendar.DAY_OF_MONTH));
        mThird.setMinValue(mCalendar.getMinimum(Calendar.DAY_OF_MONTH));
        mThird.setValue(mCalendar.get(Calendar.DAY_OF_MONTH));
        mThird.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        mThird.setLabel(getResources().getString(R.string.date_picker_day));
        mThird.setColor(Color.BLACK);
    }

    public boolean setValue(Calendar cal){
        if (cal==null || mCalendar.before(cal)){
            return false;
        }

        mFirst.setValue(cal.get(Calendar.YEAR));
        mSecond.setValue(cal.get(Calendar.MONTH));
        mThird.setValue(cal.get(Calendar.DAY_OF_MONTH));
        return true;
    }

    public Calendar getValue(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, mFirst.getValue());
        cal.set(Calendar.MONTH, mSecond.getValue()-1);
        cal.set(Calendar.DAY_OF_MONTH, mThird.getValue());
        //float temperature = (float)(mTempHigh.getValue()*10 + mTempLow.getValue())/10;
        return checkValue(cal)?cal:null;
    }

    public DatePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private boolean checkValue(Calendar cal){
        boolean result = true;
        boolean isCurDate=(cal.get(Calendar.YEAR)==mCalendar.get(Calendar.YEAR))?(cal.get(Calendar.MONTH)==mCalendar.get(Calendar.MONTH)?(cal.get(Calendar.DAY_OF_MONTH)==mCalendar.get(Calendar.DAY_OF_MONTH)):false):false;
        if(isCurDate){
            return true;
        }
        if (cal==null || cal.after(mCalendar)){
            result =  false;
        }else{
            int day = cal.get(Calendar.DAY_OF_MONTH);
            if (day>cal.getActualMaximum(Calendar.DAY_OF_MONTH)){
                result = false;
            }
        }
        return result;
    }
}
