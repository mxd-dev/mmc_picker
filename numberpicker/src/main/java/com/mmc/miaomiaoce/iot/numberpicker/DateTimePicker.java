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
public class DateTimePicker extends LinearLayout {
    private NumberPicker mZero;
    private NumberPicker mFirst;
    private NumberPicker mSecond;
    private NumberPicker mThird;
    private NumberPicker mFour;

    private Calendar mCalendar;

    public DateTimePicker(Context context) {
        super(context);
    }

    public DateTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.numberpicker_view_date_time_picker, this, true);

        mCalendar = Calendar.getInstance();
        mCalendar.setTime(new Date());

        mZero = (NumberPicker) findViewById(R.id.zero);
        mZero.setMaxValue(mCalendar.get(Calendar.YEAR));
        mZero.setMinValue(mCalendar.get(Calendar.YEAR) - 1);
        mZero.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        mZero.setValue(mCalendar.get(Calendar.YEAR));
        mZero.setLabel(getResources().getString(R.string.date_picker_year));
        mZero.setColor(Color.BLACK);

        mFirst = (NumberPicker) findViewById(R.id.first);
        mFirst.setMaxValue(mCalendar.getMaximum(Calendar.MONTH) + 1);
        mFirst.setMinValue(mCalendar.getMinimum(Calendar.MONTH) + 1);
        mFirst.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        mFirst.setValue(mCalendar.get(Calendar.MONTH) + 1);
        mFirst.setLabel(getResources().getString(R.string.date_picker_month));
        mFirst.setColor(Color.BLACK);


        mSecond = (NumberPicker) findViewById(R.id.second);
        mSecond.setMaxValue(mCalendar.getMaximum(Calendar.DAY_OF_MONTH));
        mSecond.setMinValue(mCalendar.getMinimum(Calendar.DAY_OF_MONTH));
        mSecond.setValue(mCalendar.get(Calendar.DAY_OF_MONTH));
        mSecond.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        mSecond.setLabel(getResources().getString(R.string.date_picker_day));
        mSecond.setColor(Color.BLACK);
        /**
         *
         */
        mThird = (NumberPicker) findViewById(R.id.third);
        mThird.setMaxValue(mCalendar.getMaximum(Calendar.HOUR_OF_DAY));
        mThird.setMinValue(mCalendar.getMinimum(Calendar.HOUR_OF_DAY));
        mThird.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        mThird.setValue(mCalendar.get(Calendar.HOUR_OF_DAY));
        mThird.setLabel(getResources().getString(R.string.time_picker_hour));
        mThird.setColor(Color.BLACK);

        mFour = (NumberPicker) findViewById(R.id.four);
        mFour.setMaxValue(mCalendar.getMaximum(Calendar.MINUTE));
        mFour.setMinValue(mCalendar.getMinimum(Calendar.MINUTE));
        mFour.setValue(mCalendar.get(Calendar.MINUTE));
        mFour.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        mFour.setLabel(getResources().getString(R.string.time_picker_minute));
        mFour.setColor(Color.BLACK);
    }

    public boolean setValue(Calendar cal) {
        if (cal == null || mCalendar.before(cal)) {
            return false;
        }
        mFirst.setValue(cal.get(Calendar.MONTH));
        mSecond.setValue(cal.get(Calendar.DAY_OF_MONTH));
        mThird.setValue(cal.get(Calendar.HOUR_OF_DAY));
        mFour.setValue(cal.get(Calendar.MINUTE));
        return true;
    }

    public Calendar getValue() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, mZero.getValue());
        cal.set(Calendar.MONTH, mFirst.getValue() - 1);
        cal.set(Calendar.DAY_OF_MONTH, mSecond.getValue());
        cal.set(Calendar.HOUR_OF_DAY, mThird.getValue());
        cal.set(Calendar.MINUTE, mFour.getValue());
        //float temperature = (float)(mTempHigh.getValue()*10 + mTempLow.getValue())/10;
        return cal;
//        return checkValue(cal)?cal:null;
    }

    public DateTimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private boolean checkValue(Calendar cal) {
        boolean result = true;
        boolean isCurDate = (cal.get(Calendar.YEAR) == mCalendar.get(Calendar.YEAR))
                ? (cal.get(Calendar.MONTH) == mCalendar.get(Calendar.MONTH)
                ? (cal.get(Calendar.DAY_OF_MONTH) == mCalendar.get(Calendar.DAY_OF_MONTH)) : false) : false;
        if (isCurDate) {
            return true;
        }
        if (cal == null || cal.after(mCalendar)) {
            result = false;
        } else {
            int day = cal.get(Calendar.DAY_OF_MONTH);
            if (day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                result = false;
            }
        }
        return result;
    }
}
