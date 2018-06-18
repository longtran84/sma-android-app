//package com.sma.mobile;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//
//import com.sma.mobile.profile.UserProfilesFragment;
//
//import net.sourceforge.PagerSlidingTabStrip;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class GamePipepanic extends AppCompatActivity {
//
//    @BindView(R.id.tabs)
//    PagerSlidingTabStrip tabs;
//    @BindView(R.id.pager)
//    ViewPager pager;
//    private MyPagerAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
//        adapter = new MyPagerAdapter(getSupportFragmentManager());
//        pager.setAdapter(adapter);
//        tabs.setViewPager(pager);
//        pager.setOffscreenPageLimit(adapter.getCount());
//    }
//
//    public class MyPagerAdapter extends FragmentPagerAdapter {
//
//        private final String[] TITLES = {"Tin tức ưa thích", "Tải ứng dụng", "Dành cho bạn", "Đổi điển", "Nhận thẻ", "Thông báo","Thông tin cá nhân"};
//
//        MyPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return TITLES[position];
//        }
//
//        @Override
//        public int getCount() {
//            return TITLES.length;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            switch (position) {
//                case 0:
//                    return FavouriteRecyclerViewFragment.newInstance();
//                case 1:
//                    return AdMobRewardedFragment.newInstance();
//                case 2:
//                    return RecyclerViewFragment.newInstance();
//                case 3:
//                    return PointConverterRecyclerViewFragment.newInstance();
//                case 4:
//                    return CardReceiptFragment.newInstance();
//                case 5:
//                    return NotificationsFragment.newInstance();
//                case 6:
//                    return UserProfilesFragment.newInstance();
//                default:
//                    return RecyclerViewFragment.newInstance();
//            }
//        }
//    }
//}
