package com.sma.mobile.cashout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.activities.AbstractFragment;
import com.sma.mobile.home.DemoFragment;

import net.sourceforge.PagerSlidingTabStrip;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class CashOutMainActivity extends AbstractAppCompatActivity {

    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    private MyPagerAdapter adapter;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cashout_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setTitle("Đổi điểm");
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        tabs.setRoundRect(true);
    }
    /**
     *
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Đổi điểm", "Lịch sử đổi điểm"};

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return CashOutFragment.newInstance();
                case 1:
                    return OrderLoyaltyFragment.newInstance();
                default:
                    return DemoFragment.newInstance();
            }
        }
    }
}
