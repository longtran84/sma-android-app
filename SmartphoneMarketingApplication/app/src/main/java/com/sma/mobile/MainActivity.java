package com.sma.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.github.florent37.materialviewpager.sample.DrawerActivity;
import com.github.florent37.materialviewpager.sample.fragment.CardReceiptFragment;
import com.github.florent37.materialviewpager.sample.fragment.FavouriteRecyclerViewFragment;
import com.github.florent37.materialviewpager.sample.fragment.NotificationsFragment;
import com.github.florent37.materialviewpager.sample.fragment.PointConverterRecyclerViewFragment;
import com.github.florent37.materialviewpager.sample.fragment.RecyclerViewFragment;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sma.mobile.notification.AdViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends DrawerActivity {

    @BindView(R.id.materialViewPager)
    MaterialViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        ButterKnife.bind(this);

        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return FavouriteRecyclerViewFragment.newInstance();
                    case 1:
                        return RecyclerViewFragment.newInstance();
                    case 2:
                        return PointConverterRecyclerViewFragment.newInstance();
                    case 3:
                        return CardReceiptFragment.newInstance();
                    case 4:
                        return NotificationsFragment.newInstance();
                    default:
                        return RecyclerViewFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Tin tức ưa thích";
                    case 1:
                        return "Dành cho bạn";
                    case 2:
                        return "Đổi điển";
                    case 3:
                        return "Nhận thẻ";
                    case 4:
                        return "Thông báo";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                            R.color.green,
                            "http://static.pulzo.com/images/20170320105148/gettyimages-84745752-1-914x607.jpg?itok=1490028239");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                            R.color.blue,
                            "https://media2.s-nbcnews.com/j/newscms/2017_08/1909516/170221-norway-cr-1134_01_d4adb537bd8e5608acf2f9ceb29956fb.nbcnews-fp-1240-520.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                            R.color.cyan,
                            "https://img.huffingtonpost.com/asset/58cfea212c00002100fef4b2.jpeg?cache=ptypfgqoap&ops=1910_1000");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                            R.color.red,
                            "http://i.telegraph.co.uk/multimedia/archive/02688/SWEDEN-HAPPY_2688213k.jpg");
                    case 4:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://static4.uk.businessinsider.com/image/56f0292591058428008b7c82-480/norway.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        final View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
        }
        subscribeToPushService();
    }

    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("ads");
    }
}
