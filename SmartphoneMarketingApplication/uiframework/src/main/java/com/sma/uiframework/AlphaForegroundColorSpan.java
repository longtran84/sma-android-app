package com.sma.uiframework;

/**
 * Created by longtran on 09/09/2017.
 */

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;

public class AlphaForegroundColorSpan extends ForegroundColorSpan {

    private float mAlpha;

    public AlphaForegroundColorSpan(int color) {
        super(color);
    }

    public AlphaForegroundColorSpan(Parcel src) {
        super(src);
        mAlpha = src.readFloat();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public AlphaForegroundColorSpan createFromParcel(Parcel in) {
            return new AlphaForegroundColorSpan(in);
        }

        public AlphaForegroundColorSpan[] newArray(int size) {
            return new AlphaForegroundColorSpan[size];
        }
    };

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeFloat(mAlpha);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(getAlphaColor());
    }

    public void setAlpha(float alpha) {
        mAlpha = alpha;
    }

    public float getAlpha() {
        return mAlpha;
    }

    private int getAlphaColor() {
        int foregroundColor = getForegroundColor();
        return Color.argb((int) (mAlpha * 255), Color.red(foregroundColor), Color.green(foregroundColor), Color.blue(foregroundColor));
    }


}
