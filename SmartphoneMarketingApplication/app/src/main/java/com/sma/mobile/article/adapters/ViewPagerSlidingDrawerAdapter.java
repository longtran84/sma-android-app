package com.sma.mobile.article.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sma.mobile.article.NewsTopicFragment;
import com.sma.mobile.article.SlidingDrawerSocialNotificationShortcutFragment;

/**
 * Created by longtran on 03/10/2017.
 */

public class ViewPagerSlidingDrawerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 3;

    public ViewPagerSlidingDrawerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NewsTopicFragment.newInstance();
            case 1:
                return SlidingDrawerSocialNotificationShortcutFragment.newInstance();
            default: return SlidingDrawerSocialNotificationShortcutFragment.newInstance();
//            case 2:
//                return GameShowActivity.newInstance();
//            default:
//                return GameShowActivity.newInstance();
        }
    }
}
