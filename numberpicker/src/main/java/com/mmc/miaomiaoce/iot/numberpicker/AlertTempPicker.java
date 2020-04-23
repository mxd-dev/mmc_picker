package com.mmc.miaomiaoce.iot.numberpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Yu Rui on 2016/6/3.
 */
public class AlertTempPicker extends LinearLayout {
    private NumberPicker mTempHigh;
    private NumberPicker mPoint;
    private NumberPicker mTempLow;
    TextView tv_temp_info;
    /* Max temperature could be set */
    private static final int VALUE_MAX_C = 60;
    /* Min temperature could be set */
    private static final int VALUE_MIN_C = -30;
    /* Default integer of temperature */

    /**
     * for FahrenheitOn
     */
    private static final int VALUE_MAX_F = (int) (VALUE_MAX_C * 9 / 5 + 32);
    private static final int VALUE_MIN_F = (int) (VALUE_MIN_C * 9 / 5 + 32);


    private static final int VALUE_DEFAULT = 38;
    boolean isUnitF = false;
    private Context mContext;

    public AlertTempPicker(Context context) {
        super(context);
    }

    public AlertTempPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlertTemp_Picker);
        // true for high alert; false for low alert
        boolean highAlert = a.getBoolean(R.styleable.AlertTemp_Picker_AlertHigh, true);
        isUnitF = a.getBoolean(R.styleable.AlertTemp_Picker_isUnitF, false);
        a.recycle();

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.numberpicker_view_alertpicker, this, true);

        mTempHigh = (NumberPicker) findViewById(R.id.valuehigh);
        mPoint = (NumberPicker) findViewById(R.id.point);
        mTempLow = (NumberPicker) findViewById(R.id.valuelow);
        tv_temp_info = (TextView) findViewById(R.id.tv_temp_info);
        setFont();
        mPoint.setDisplayedValues(new String[]{getResources().getString(R.string.owl_alert_setting_point)});

        if (isUnitF) {
            mTempHigh.setMaxValue(VALUE_MAX_F);
            mTempHigh.setMinValue(VALUE_MIN_F);
        } else

        {
            mTempHigh.setMaxValue(VALUE_MAX_C);
            mTempHigh.setMinValue(VALUE_MIN_C);
        }
        mTempLow.setMaxValue(9);
        mTempLow.setMinValue(0);
        mTempLow.setValue(5);
        int resID = isUnitF ? R.string.unit_ascii_f : R.string.unit_ascii_c;
        mTempLow.setLabel(getResources().getString(resID));
        //mTempLow.setTextGravityLeft();
    }

    public void setType(boolean highalert) {
        int color;
        int bg;
        float temperature;

        if (highalert) {
            color = Color.RED;
            bg = R.mipmap.bg_picker_high;
        } else {
            color = Color.BLUE;
            bg = R.mipmap.bg_picker_low;
        }
        LinearLayout mBG = (LinearLayout) findViewById(R.id.picker_area);
        //set bg of number pickers
        mBG.setBackgroundResource(bg);
        //set text color
        mTempHigh.setColor(color);
        mPoint.setColor(color);
        mTempLow.setColor(color);
        if (isUnitF) {
//            Log.e("isUnitF", "yes");
//            temperature = SettingUtils.convertToF(temperature);
        }
    }

    public void changeTheme(boolean nightMode) {
        mTempHigh.setNightMode(nightMode);
        mTempLow.setNightMode(nightMode);
    }

    public void setValue(float temperature) {
        int intPart = (int) temperature;
        mTempHigh.setValue(intPart);
        int floatPart = Math.round((Math.abs(temperature) - Math.abs(intPart)) * 10);
        mTempLow.setValue(floatPart);
    }

    public float getValue() {
        int high = mTempHigh.getValue();
        int low = mTempLow.getValue();
        if (high < 0) {//处理负数的情况
            low = -low;
        }
        float temperature = (float) (high * 10 + low) / 10;
        if (isUnitF) {
//            Log.e("isUnitF", "yes");
//            temperature = SettingUtils.convertToC(temperature);
        }
        return temperature;
    }

    public AlertTempPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void setFont() {
        // Font path
        String fontPath = "fonts/AriaRoundedBold.ttf";
        // Text face
        Typeface tf;
        // TODO: update Typeface for all views
        try {
            tf = Typeface.createFromAsset(mContext.getAssets(), fontPath);
        } catch (Exception e) {
            tf = null;
        }
        if (tf != null) {
            mTempHigh.setFont(tf);
            mPoint.setFont(tf);
            mTempLow.setFont(tf);
        }
    }

    public void setTitleType(boolean isHigh) {
        if (isHigh) {
            tv_temp_info.setText(R.string.title_h);
            Drawable drawable = getResources().getDrawable(R.mipmap.arrow_hightemp);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_temp_info.setCompoundDrawables(null, null, drawable, null);
            tv_temp_info.setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.picker_time_lable_left_margin));
        } else {
            tv_temp_info.setText(R.string.title_l);
            Drawable drawable = getResources().getDrawable(R.mipmap.arrow_lowtemp);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_temp_info.setCompoundDrawables(null, null, drawable, null);
            tv_temp_info.setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.picker_time_lable_left_margin));
        }
    }
}
