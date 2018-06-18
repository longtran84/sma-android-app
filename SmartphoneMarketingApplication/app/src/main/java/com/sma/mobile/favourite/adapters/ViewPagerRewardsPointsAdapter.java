package com.sma.mobile.favourite.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sma.mobile.favourite.PageRewardPointFriendInvitationFragment;
import com.sma.mobile.favourite.PageRewardPointReadNewsFragment;

/**
 * Created by longtran on 03/10/2017.
 */

public class ViewPagerRewardsPointsAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 1;

    public ViewPagerRewardsPointsAdapter(FragmentManager fragmentManager) {
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
                return PageRewardPointReadNewsFragment.newInstance("Page Number " + position);
            case 1:
              //  return PageRewardPointFriendInvitationFragment.newInstance("Page Number " + position);
            default:
                return PageRewardPointReadNewsFragment.newInstance("Page Number " + position);
        }
    }
}
