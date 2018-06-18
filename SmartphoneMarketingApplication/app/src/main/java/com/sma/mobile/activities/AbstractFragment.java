package com.sma.mobile.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sma.mobile.R;
import com.sma.mobile.utils.firebasenotifications.Config;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by QLong on 11/1/2017.
 */

public abstract class AbstractFragment extends SupportFragment {

    private MaterialDialog.Builder materialDialogProgressBar;
    private MaterialDialog materialDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        materialDialogProgressBar = getMaterialDialogProgressBar();
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

    /***
     * @return
     */
    private MaterialDialog.Builder getMaterialDialogProgressBar() {
        MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(getContext())
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

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    /**
     * @return
     */
    public String getDeviceSerialNumber() {
        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
        String deviceSerialNumber = pref.getString(Config.DEVICE_SERIAL, "ERROR");
        return deviceSerialNumber;
    }
}
