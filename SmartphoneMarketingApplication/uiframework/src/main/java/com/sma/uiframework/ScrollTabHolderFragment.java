package com.sma.uiframework;

/**
 * Created by longtran on 09/09/2017.
 */

import android.support.v4.app.Fragment;
import android.widget.ScrollView;

public abstract class ScrollTabHolderFragment extends Fragment implements ScrollTabHolder {

    protected ScrollTabHolder mScrollTabHolder;

    public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
        mScrollTabHolder = scrollTabHolder;
    }

    @Override
    public void onScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {
        // nothing
    }

}
