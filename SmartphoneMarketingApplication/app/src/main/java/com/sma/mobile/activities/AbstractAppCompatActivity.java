package com.sma.mobile.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.floatingwidget.FloatingWidgetService;
import com.sma.mobile.R;
import com.sma.mobile.SMAApplication;
import com.sma.mobile.launcher.SplashScreen;
import com.sma.mobile.utils.firebasenotifications.Config;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by longtran on 14/01/2017.
 */

public abstract class AbstractAppCompatActivity extends SupportActivity {

    public SMAApplication _SMAApplication;
    private boolean hideKeyboard = true;
    private MaterialDialog.Builder materialDialogProgressBar;
    private MaterialDialog materialDialog;
//    private UnauthorizedBroadcastReceiver unauthorizedBroadcastReceiver;


    public boolean isHideKeyboard() {
        return hideKeyboard;
    }

    public void setHideKeyboard(boolean hideKeyboard) {
        this.hideKeyboard = hideKeyboard;
    }

    /****
     *
     */
    private class UnauthorizedBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (null == action) {
                return;
            }
//            signOut();
//            new MaterialDialog.Builder(AbstractAppCompatActivity.this)
//                    .title(R.string.app_name)
//                    .content("Can not find user on system")
//                    .positiveText(R.string.ok)
//                    .positiveColorRes(R.color.color_black)
//                    .onPositive(new MaterialDialog.SingleButtonCallback() {
//                        @Override
//                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                            signOut();
//                        }
//                    })
//                    .show();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _SMAApplication
                = (SMAApplication) getApplication();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        hideSoftKeyboard();
        materialDialogProgressBar = getMaterialDialogProgressBar();
        //sendBroadcastBadge();
//        final IntentFilter filter = new IntentFilter();
//        filter.addAction(RS3.action.UNAUTHORIZED_ACTION);
//        unauthorizedBroadcastReceiver = new UnauthorizedBroadcastReceiver();
//        registerReceiver(unauthorizedBroadcastReceiver, filter);
        Intent discoveryIntent = new Intent(this, FloatingWidgetService.class);
        stopService(discoveryIntent);
    }

    /***
     * @return
     */
    private MaterialDialog.Builder getMaterialDialogProgressBar() {
        MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(this)
                //.title(R.string.progress_dialog)
                //.content(R.string.please_wait)
                .customView(R.layout.progress_indicator_layout, true)
                //.progress(true, 0)
                .autoDismiss(false)
                .cancelable(false)
                .backgroundColorRes(R.color.transparent)
                .titleColorRes(R.color.transparent)
                .contentColor(Color.TRANSPARENT)
                .progressIndeterminateStyle(false);
        return materialDialog;
    }

    /***
     * @param appCompatActivity
     * @param title
     * @param content
     * @param positiveText
     * @return
     */
    public MaterialDialog getMaterialDialog(AppCompatActivity appCompatActivity, String title, String content, String positiveText) {
        return new MaterialDialog.Builder(appCompatActivity)
                .cancelable(true)
                .backgroundColorRes(R.color.color_white)
                .title(title)
                .titleColorRes(R.color.color_black)
                .content(content)
                .contentColor(0xFFCCCCCC)
                .positiveText(positiveText)
                .positiveColorRes(R.color.color_black)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /****
     * @return
     */
    public MaterialDialog getMaterialDialogAlert(Activity activity, String title, String content) {
        return new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .positiveText(R.string.ok)
                .positiveColorRes(R.color.colorAccent)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
    }

    /****
     * @return
     */
    public MaterialDialog getMaterialDialogAlert(Activity activity, String title, int layoutRes) {
        return new MaterialDialog.Builder(activity)
                .title(title)
                //.content(content)
                .positiveText(R.string.ok)
                .positiveColorRes(R.color.colorAccent)
                .customView(layoutRes/*R.layout.activity_reward_info*/, false)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
    }

    /****
     * @return
     */
    public MaterialDialog getMaterialDialogAlertError(Activity activity) {
        return new MaterialDialog.Builder(activity)
                .title("Lỗi mạng")
                .content("Vui lòng kiểm tra lại wifi hoặc 3G")
                .contentColorRes(R.color.white)
                .backgroundColorRes(R.color.colorAccent)
                .positiveText(R.string.ok)
                .positiveColorRes(R.color.white)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .build();
    }

    /****
     * @return
     */
    public MaterialDialog getMaterialDialogAlert(Activity activity, String title, String content,
                                                 MaterialDialog.SingleButtonCallback singleButtonCallback) {
        return new MaterialDialog.Builder(activity)
                .titleColorRes(R.color.color_black)
                .title(title)
                .content(content)
                .contentColor(Color.BLACK)
                .positiveColor(0xFF157efb)
                .positiveText(R.string.ok)
                .onPositive(singleButtonCallback)
                .build();
    }

    /***
     *
     */
    public void redirectSignIn(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName(activity, SplashScreen.class.getName());
        activity.startActivity(intent);
        activity.finish();
    }

    /****
     * @param newFragment
     * @param args
     * @param fragmentByTag
     */
    public void switchFragments(Fragment newFragment, Bundle args, String fragmentByTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragmentPrevious = getSupportFragmentManager().findFragmentByTag(fragmentByTag);
        if (fragmentPrevious != null) {
            transaction.remove(fragmentPrevious);
            //JVMRuntime.getVMRuntime().clearGrowthLimit();
        }
        if (null != args) {
            newFragment.setArguments(args);
        }
        /***
         * Replace whatever is in the fragment_container view with this fragment,
         * and add the transaction to the back stack so the user can navigate back
         */
        transaction.replace(getFragmentContainerViewId(), newFragment, fragmentByTag);
        /***
         * Commit the transaction
         */
        transaction.commit();
    }

    /**
     * Every fragment has to inflate a layout in the onCreateView method. We have added this method to
     * avoid duplicate all the inflate code in every fragment. You only have to return the layout to
     * inflate in this method when extends BaseFragment.
     */
    public abstract int getFragmentContainerViewId();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(Activity.RESULT_OK, new Intent());
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * @return
     */
    public String getDeviceSerialNumber() {
        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
        String deviceSerialNumber = pref.getString(Config.DEVICE_SERIAL, "ERROR");
        return deviceSerialNumber;
    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    public void showProcessing() {
        if (null != materialDialogProgressBar) {
            materialDialog = materialDialogProgressBar.build();
            materialDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            materialDialog.dismiss();
            materialDialog.show();
        }
    }

    public void hideProcessing() {
        if (null != materialDialogProgressBar && null != materialDialog && materialDialog.isShowing()) {
            materialDialog.dismiss();
            materialDialog = null;
            materialDialogProgressBar = null;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isHideKeyboard()) {
            View view = getCurrentFocus();
            if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof AppCompatEditText && !view.getClass().getName().startsWith("android.webkit.")) {
                int scrcoords[] = new int[2];
                view.getLocationOnScreen(scrcoords);
                float x = ev.getRawX() + view.getLeft() - scrcoords[0];
                float y = ev.getRawY() + view.getTop() - scrcoords[1];
                if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) {
                    ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onStop() {
        Log.d(AbstractAppCompatActivity.class.getName(), "onStop.................");
        //LRuntimeHack.get().clearGrowthLimit();
        super.onStop();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent discoveryIntent = new Intent(AbstractAppCompatActivity.this, FloatingWidgetService.class);
                stopService(discoveryIntent);
            }
        }, 2000);
    }

    @Override
    public void onDestroy() {
        //LRuntimeHack.get().clearGrowthLimit();
        //unregisterReceiver(unauthorizedBroadcastReceiver);
        Log.d(AbstractAppCompatActivity.class.getName(), "onDestroy.................");
        super.onDestroy();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent discoveryIntent = new Intent(AbstractAppCompatActivity.this, FloatingWidgetService.class);
                stopService(discoveryIntent);
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        Log.d(AbstractAppCompatActivity.class.getName(), "onResume.................");
        super.onResume();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent discoveryIntent = new Intent(AbstractAppCompatActivity.this, FloatingWidgetService.class);
                stopService(discoveryIntent);
            }
        }, 2000);
    }

    @Override
    protected void onPause() {
        Log.d(AbstractAppCompatActivity.class.getName(), "onPause.................");
        super.onPause();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent discoveryIntent = new Intent(AbstractAppCompatActivity.this, FloatingWidgetService.class);
                stopService(discoveryIntent);
            }
        }, 2000);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
