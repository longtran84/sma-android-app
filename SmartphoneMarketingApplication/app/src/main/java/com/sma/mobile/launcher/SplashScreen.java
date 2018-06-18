package com.sma.mobile.launcher;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
//import com.fastaccess.permission.base.PermissionHelper;
//import com.fastaccess.permission.base.callback.OnPermissionCallback;
import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.user.User;
import com.sma.mobile.R;
import com.sma.mobile.SMAApplication;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.home.HomeActivity;
import com.sma.mobile.loginsignup.LoginActivity;
import com.sma.mobile.loginsignup.SignupActivity;
import com.sma.mobile.utils.firebasenotifications.Config;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import net.sourceforge.service.ScreenListenerService;

import java.util.Arrays;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * https://github.com/k0shk0sh/PermissionHelper/blob/master/permission/src/main/java/com/fastaccess/permission/base/PermissionHelper.java
 * Created by longtran on 14/01/2017.
 */

public class SplashScreen extends AbstractAppCompatActivity /*implements OnPermissionCallback*/ {

    private final String TAG = SplashScreen.class.getName();

    /**
     * The package name of your app
     */
    private int currentVersion = 0;
    private int responseVersion = 0;
    private SMAApplication mSMAApplication;

    //    private PermissionHelper permissionHelper;
    private boolean isSingle;
    private AlertDialog builder;
    private String[] neededPermission;

    private final static String SINGLE_PERMISSION = Manifest.permission.GET_ACCOUNTS;


    private final static String[] MULTI_PERMISSIONS = new String[]{Settings.ACTION_MANAGE_OVERLAY_PERMISSION
    };

    /*  Permission request code to draw over other apps  */
    private static final int DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE = 0x1401;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    @SuppressWarnings("deprecation")
    private String getIMEINumber() {
        String IMEINumber = UUID.randomUUID().toString();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                IMEINumber = telephonyMgr.getImei();
            } else {
                IMEINumber = telephonyMgr.getDeviceId();
            }
        }
        return IMEINumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        permissionHelper = PermissionHelper.getInstance(this);
        mSMAApplication = (SMAApplication) getApplication();
//        permissionHelper
//                .setForceAccepting(false) // default is false. its here so you know that it exists.
//                .request(Manifest.permission.SYSTEM_ALERT_WINDOW);
        TedRx2Permission.with(this)
                .setRationaleTitle(R.string.rationale_title)
                .setRationaleMessage(R.string.rationale_message) // "we need permission for read contact and find your location"
                .setPermissions(
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.DISABLE_KEYGUARD,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .request()
                .subscribe(tedPermissionResult -> {
                    if (tedPermissionResult.isGranted()) {
                        //Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                        startFloatingWidgetService();
                    } else {
//                        Toast.makeText(getApplicationContext(),
//                                "Permission Denied\n" + tedPermissionResult.getDeniedPermissions().toString(), Toast.LENGTH_SHORT)
//                                .show();
                    }
                }, throwable -> {
                }, () -> {
                });
    }

    /**
     * Start Floating widget service and finish current activity
     */
    private void startFloatingWidgetService() {
        String deviceId = getIMEINumber();
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Config.DEVICE_SERIAL, deviceId);
        editor.commit();
        Intent newIntent = new Intent(this, ScreenListenerService.class);
        startService(newIntent);
        FintechvietSdk.getInstance().registerUser(deviceId, new JCallback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    redirectHomeActivity(SplashScreen.this);
                } else {
                    redirectHomeActivity(SplashScreen.this);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                hideProcessing();
                redirectHomeActivity(SplashScreen.this);
            }
        });
    }

    /****
     * @return
     */
//    public MaterialDialog getMaterialDialogAlert(Activity activity, String title, String content) {
//        return new MaterialDialog.Builder(activity)
//                .title(title)
//                .content(content)
//                .positiveText(R.string.ok)
//                .positiveColorRes(R.color.color_black)
//                .onAny(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        dialog.dismiss();
//                        finish();
//                    }
//                })
//                .build();
//    }

//    /***
//     *
//     */
//    public void redirectHome(Activity activity) {
//
//    }

    /***
     * @param activity
     */
    public void redirectSignUp(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName(activity, SignupActivity.class.getName());
        activity.startActivity(intent);
        activity.finish();
    }

    /***
     * @param activity
     */
    public void redirectHomeActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName(activity, HomeActivity.class.getName());
        activity.startActivity(intent);
        activity.finish();
    }


    /**
     * Used to determine if the user accepted {@link android.Manifest.permission#SYSTEM_ALERT_WINDOW} or no.
     * <p/>
     * if you never passed the permission this method won't be called.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        permissionHelper.onActivityForResult(requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    @Override
//    public void onPermissionGranted(@NonNull String[] permissionName) {
////        result.setText("Permission(s) " + Arrays.toString(permissionName) + " Granted");
//        Log.i("onPermissionGranted", "Permission(s) " + Arrays.toString(permissionName) + " Granted");
//        startFloatingWidgetService();
//    }

//    @Override
//    public void onPermissionDeclined(@NonNull String[] permissionName) {
////        result.setText("Permission(s) " + Arrays.toString(permissionName) + " Declined");
//        Log.i("onPermissionDeclined", "Permission(s) " + Arrays.toString(permissionName) + " Declined");
//        startFloatingWidgetService();
//    }

//    @Override
//    public void onPermissionPreGranted(@NonNull String permissionsName) {
////        result.setText("Permission( " + permissionsName + " ) preGranted");
//        Log.i("onPermissionPreGranted", "Permission( " + permissionsName + " ) preGranted");
//        startFloatingWidgetService();
//    }

//    @Override
//    public void onPermissionNeedExplanation(@NonNull String permissionName) {
//        Log.i("NeedExplanation", "Permission( " + permissionName + " ) needs Explanation");
//        if (!isSingle) {
//            neededPermission = PermissionHelper.declinedPermissions(this, MULTI_PERMISSIONS);
//            StringBuilder builder = new StringBuilder(neededPermission.length);
//            if (neededPermission.length > 0) {
//                for (String permission : neededPermission) {
//                    builder.append(permission).append("\n");
//                }
//            }
////            result.setText("Permission( " + builder.toString() + " ) needs Explanation");
//            AlertDialog alert = getAlertDialog(neededPermission, builder.toString());
//            if (!alert.isShowing()) {
//                alert.show();
//            }
//        } else {
////            result.setText("Permission( " + permissionName + " ) needs Explanation");
//            getAlertDialog(permissionName).show();
//        }
//    }

//    @Override
//    public void onPermissionReallyDeclined(@NonNull String permissionName) {
////        result.setText("Permission " + permissionName + " can only be granted from SettingsScreen");
//        Log.i("ReallyDeclined", "Permission " + permissionName + " can only be granted from settingsScreen");
//        /** you can call  {@link PermissionHelper#openSettingsScreen(Context)} to open the settings screen */
//    }

//    @Override
//    public void onNoPermissionNeeded() {
////        result.setText("Permission(s) not needed");
//        Log.i("onNoPermissionNeeded", "Permission(s) not needed");
//    }


//    public AlertDialog getAlertDialog(final String[] permissions, final String permissionName) {
//        if (builder == null) {
//            builder = new AlertDialog.Builder(this)
//                    .setTitle("Permission Needs Explanation")
//                    .create();
//        }
//        builder.setButton(DialogInterface.BUTTON_POSITIVE, "Request", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                permissionHelper.requestAfterExplanation(permissions);
//            }
//        });
//        builder.setMessage("Permissions need explanation (" + permissionName + ")");
//        return builder;
//    }

//    public AlertDialog getAlertDialog(final String permission) {
//        if (builder == null) {
//            builder = new AlertDialog.Builder(this)
//                    .setTitle("Permission Needs Explanation")
//                    .create();
//        }
//        builder.setButton(DialogInterface.BUTTON_POSITIVE, "Request", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                permissionHelper.requestAfterExplanation(permission);
//            }
//        });
//        builder.setMessage("Permission need explanation (" + permission + ")");
//        return builder;
//    }

}
