package com.sma.mobile.home;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.Message;
import com.fintechviet.android.sdk.user.User;
import com.google.gson.Gson;
import com.sma.mobile.Constants;
import com.sma.mobile.FavouriteActivity;
import com.sma.mobile.LatestNewsHeadlinesFragment;
import com.sma.mobile.MessageEvent;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.article.NewsTopicFragment;
import com.sma.mobile.jobs.NotificationHelper;
import com.sma.mobile.location.LocationGeolocationFragment;
import com.sma.mobile.loginsignup.LoginFragment;
import com.sma.mobile.messages.MessageFragment;
import com.sma.mobile.notification.FirebaseMessagingResponse;
import com.sma.mobile.settings.SettingFragment;
import com.sma.mobile.utils.firebasenotifications.Config;
import com.sma.mobile.widget.BottomNavigationViewEx;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.leolin.shortcutbadger.ShortcutBadger;
import me.yokeyword.fragmentation.SupportFragment;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by longtran on 02/10/2017.
 */

public class HomeActivity extends AbstractAppCompatActivity {

//    private ViewPager viewPager;
//    private ImageView image_view_icon_more;

    @BindView(R.id.bottom_navigation_view_id)
    BottomNavigationViewEx bottomNavigationViewEx;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.nav_view_right)
    NavigationView navigationViewRight;

    @BindView(R.id.drawerView)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnBinder;
    private LatestNewsHeadlinesFragment latestNewsHeadlinesFragment;
    private NewsTopicFragment newsTopicFragment;
    private LocationGeolocationFragment cashOutMainFragment;
    private SettingFragment settingFragment;
    private MessageFragment messageFragment;
    private LoginFragment loginFragment;
    private int hideFragment = Constants.TYPE_NEWS_TOPICS_FRAGMENT;
    private int showFragment = Constants.TYPE_NEWS_TOPICS_FRAGMENT;
    private String newsId;
    private int keyName = Integer.MIN_VALUE;
    private FirebaseMessagingResponse firebaseMessaging;
    private EditText passwordInput = null;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    /**
     * @param item
     * @return
     */
    private SupportFragment getTargetFragment(int item) {
        switch (item) {
            case Constants.TYPE_CASH_OUT_MAIN_FRAGMENT:
                return cashOutMainFragment;
            case Constants.TYPE_NEWS_HEAD_LINES_FRAGMENT:
                return latestNewsHeadlinesFragment;
            case Constants.TYPE_NEWS_TOPICS_FRAGMENT:
                return newsTopicFragment;
            case Constants.TYPE_MESSAGE_ALERT:
                return messageFragment;
            case Constants.TYPE_SETTING:
                return settingFragment;
            case Constants.TYPE_LOGIN:
                return loginFragment;
            default:
                return latestNewsHeadlinesFragment;
        }
    }

    /***
     *
     */
    public void redirectSettingActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName(activity, SettingFragment.class.getName());
        activity.startActivity(intent);
    }

    /***
     *
     */
    public void redirectFavouriteActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName(activity, FavouriteActivity.class.getName());
        activity.startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // Activity.RESULT_OK
            if (null != data) {
                // get String data from Intent
                int returnKeyNamePoint = data.getIntExtra("KEY_NAME_RESULT", Integer.MIN_VALUE);
                switch (returnKeyNamePoint) {
                    case 1:
                        bottomNavigationViewEx.setCurrentItem(0);
                        break;
                    case 2:
                        bottomNavigationViewEx.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * @param view
     */
    private void disableShiftMode(BottomNavigationViewEx view) {
//        BottomNavigationViewEx menuView = (BottomNavigationViewEx) view.getChildAt(0);
//        try {
//            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
//            shiftingMode.setAccessible(true);
//            shiftingMode.setBoolean(menuView, false);
//            shiftingMode.setAccessible(false);
//            for (int i = 0; i < menuView.getChildCount(); i++) {
//                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
//                //noinspection RestrictedApi
//                item.setShiftingMode(false);
//                // set once again checked value, so view will be updated
//                //noinspection RestrictedApi
//                item.setChecked(item.getItemData().isChecked());
//            }
//        } catch (NoSuchFieldException e) {
//            LogHelper.e("BNVHelper", "Unable to get shift mode field", e);
//        } catch (IllegalAccessException e) {
//            LogHelper.e("BNVHelper", "Unable to change value of shift mode", e);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window wind = this.getWindow();
        wind.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        wind.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        wind.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        // Retrieve sent bundle
        Bundle bundle = this.getIntent().getExtras();
        if (null != bundle) {
            newsId = bundle.getString("NEWS_ID");
            keyName = bundle.getInt("KEY_NAME_RESULT", Integer.MIN_VALUE);
            firebaseMessaging = bundle.getParcelable(FirebaseMessagingResponse.class.getName());
            System.err.println(new Gson().toJson(firebaseMessaging) + " : firebaseMessaging");
        }
        setContentView(R.layout.activity_home);
        mUnBinder = ButterKnife.bind(this);
        bottomNavigationViewEx.getMenu().clear();
        bottomNavigationViewEx.inflateMenu(R.menu.bottom_navigation);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(true);
        disableShiftMode(bottomNavigationViewEx);
        //bottomNavigationViewEx.setIconSize(28, 28);
        bottomNavigationViewEx.setTextSize(9f);
        latestNewsHeadlinesFragment = LatestNewsHeadlinesFragment.newInstance();
        newsTopicFragment = NewsTopicFragment.newInstance();
        cashOutMainFragment = LocationGeolocationFragment.newInstance(firebaseMessaging);
        settingFragment = SettingFragment.newInstance();
        messageFragment = MessageFragment.newInstance();
        loginFragment = LoginFragment.newInstance();

        showFragment = Constants.TYPE_NEWS_HEAD_LINES_FRAGMENT;
        hideFragment = Constants.TYPE_NEWS_HEAD_LINES_FRAGMENT;
        showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
        bottomNavigationViewEx.setCurrentItem(2);
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_cashout:
                        showFragment = Constants.TYPE_CASH_OUT_MAIN_FRAGMENT;
                        break;
                    case R.id.navigation_news_topic:
                        showFragment = Constants.TYPE_NEWS_TOPICS_FRAGMENT;
                        break;
                    case R.id.navigation_news_head_lines_fragment:
                        showFragment = Constants.TYPE_NEWS_HEAD_LINES_FRAGMENT;
                        break;
                    case R.id.navigation_message:
                        showFragment = Constants.TYPE_MESSAGE_ALERT;
                        break;
                    case R.id.navigation_setting:
                        showFragment = Constants.TYPE_SETTING;
                        break;
                }
                //menuItem.setChecked(true);
                //toolbar.setTitle(menuItem.getTitle());
                showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                hideFragment = showFragment;
                return true;
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.closeDrawer(GravityCompat.END);
                showFragment = Constants.TYPE_LOGIN;
                showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                hideFragment = showFragment;
                return true;
            }
        });

        navigationViewRight.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.closeDrawer(GravityCompat.END);
                showFragment = Constants.TYPE_LOGIN;
                showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                hideFragment = showFragment;
                return true;
            }
        });

        NotificationHelper.scheduleRepeatingElapsedNotification(this);
        loadMultipleRootFragment(R.id.fl_main_content, 0, latestNewsHeadlinesFragment,
                cashOutMainFragment, newsTopicFragment, messageFragment, settingFragment, loginFragment);
    }

    /**
     *
     */
    private void showInvitationCodes() {
        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
        int firstTimeUsed = pref.getInt("MY_FIRST_TIME", Integer.MIN_VALUE);
        if (firstTimeUsed > 0) {
            return;
        }
        MaterialDialog dialog =
                new MaterialDialog.Builder(this)
                        .title("Mã giới thiệu")
                        .customView(R.layout.dialog_invitation_codes, false)
                        .positiveText("Xác nhận")
                        .cancelable(false)
                        .autoDismiss(false)
                        .negativeText("Hủy")
                        .onNegative((dialog1, which) -> {
                            dialog1.dismiss();
                        })
                        .onPositive(
                                (dialog1, which) -> {
                                    if (!StringUtils.isBlank(passwordInput.getText().toString())) {
                                        FintechvietSdk.getInstance().updateInviteCode(getDeviceSerialNumber(), passwordInput.getText().toString(), new JCallback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                if (response.code() == 200 && !response.body().equalsIgnoreCase("InviteCodeInvalid")) {
                                                    dialog1.dismiss();
                                                } else {
                                                    passwordInput.setError("Mã giới thiệu không đúng");
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                passwordInput.setError("Mã giới thiệu không đúng");
                                            }
                                        });
                                    } else {
                                        passwordInput.setError("Mã giới thiệu không đúng");
                                    }
                                })
                        .build();
        passwordInput = dialog.getCustomView().findViewById(R.id.password);
        passwordInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        dialog.show();
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("MY_FIRST_TIME", 1);
        editor.commit();
    }

    private Badge badge;

    /**
     *
     */
    private void bindingBadge() {
        if (null != bottomNavigationViewEx) {
            View v = bottomNavigationViewEx.getBottomNavigationItemView(3); // number of menu from left
            if (null != v) {
                if (null == badge) {
                    badge = new QBadgeView(getApplicationContext()).bindTarget(v);
                }
                FintechvietSdk.getInstance().getMessages(getDeviceSerialNumber(), new JCallback<List<Message>>() {
                    @Override
                    public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                        if (response.code() == 200) {
                            List<Message> listMessage = response.body();
                            int count = 0;
                            for (Message message : listMessage) {
                                if (message.getRead() <= 0) {
                                    count++;
                                }
                            }
                            ShortcutBadger.applyCount(getApplicationContext(), count);
                            badge.setBadgeNumber(count == 0 ? -1 : count);
                            badge.setBadgeTextSize(14, true);
                            badge.setBadgePadding(6, true);
                            badge.setShowShadow(true);
                            badge.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                                @Override
                                public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                                    if (dragState == STATE_SUCCEED) {

                                    }
                                }
                            });
                            if (count <= 0) {
                                badge.hide(true);
                                bottomNavigationViewEx.refreshDrawableState();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Message>> call, Throwable t) {

                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().register(this);
        if (keyName >= 0) {
            bottomNavigationViewEx.setCurrentItem(keyName);
        }
        keyName = Integer.MIN_VALUE;
        FintechvietSdk.getInstance().getUserInfo(getDeviceSerialNumber(), new JCallback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    if (null != user) {
                        if (StringUtils.isBlank(user.getInviteCodeUsed())) {
                            showInvitationCodes();
                        }
                    }
                }
                hideProcessing();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                hideProcessing();
                t.printStackTrace();
            }
        });
        bindingBadge();
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        bindingBadge();
    }

    @Override
    public void onDestroy() {
        mUnBinder.unbind();
        super.onDestroy();
    }


    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        finish();
    }
}
