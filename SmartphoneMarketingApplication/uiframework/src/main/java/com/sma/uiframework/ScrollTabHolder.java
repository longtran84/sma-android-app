package com.sma.uiframework;

/**
 * Created by longtran on 09/09/2017.
 */

import android.widget.ScrollView;

public interface ScrollTabHolder {

    void adjustScroll(int scrollHeight, int headerTranslationY);

    void onScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition);

}
