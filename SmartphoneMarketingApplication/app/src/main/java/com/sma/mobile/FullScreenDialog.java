package com.sma.mobile;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.ad.Decision;
import com.fintechviet.android.sdk.ad.DecisionResponse;
import com.fintechviet.android.sdk.listener.JCallback;
import com.github.ybq.android.spinkit.SpinKitView;
import com.sma.mobile.utils.firebasenotifications.Config;
import com.thefinestartist.finestwebview.FinestWebView;

import org.apache.commons.lang3.StringUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by longtran on 30/09/2017.
 */

public class FullScreenDialog extends DialogFragment {

    public static final String TAG = "FullScreenDialog";
    private EasyVideoPlayer videoView;
    private ImageView imageView;
    private SwitchCompat switchCompat;
    private ImageView imageViewClose;
    private RelativeLayout relativeLayout;
    private RelativeLayout relativeLayoutVideo;
    private RelativeLayout relativeLayoutImage;
    private String template;
    private boolean pausedInOnStop = false;
    private Decision decision;
    private SpinKitView spinKitView;
    private AppCompatTextView appCompatTextViewTitleAdvertising;

    /**
     * @return
     */
    private String getDeviceSerialNumber() {
        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
        String deviceSerialNumber = pref.getString(Config.DEVICE_SERIAL, "ERROR");
        return deviceSerialNumber;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);

    }

    @Override
    public void onStart() {
        super.onStart();
//        Dialog dialog = getDialog();
//        if (dialog != null) {
//            int width = ViewGroup.LayoutParams.MATCH_PARENT;
//            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
//            dialog.getWindow().setLayout(width, height);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state) {
        super.onCreateView(inflater, parent, state);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_layout, parent, true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        relativeLayout = (RelativeLayout) getView().findViewById(R.id.relative_layout_admob_id);
        relativeLayoutVideo = (RelativeLayout) getView().findViewById(R.id.relative_layout_admob_viedeo_id);
        relativeLayoutVideo.setVisibility(View.GONE);
        relativeLayoutImage = (RelativeLayout) getView().findViewById(R.id.relative_layout_admob_image_id);
        relativeLayoutImage.setVisibility(View.GONE);

        appCompatTextViewTitleAdvertising = (AppCompatTextView) getView().findViewById(R.id.text_view_title_advertising);
        imageViewClose = (ImageView) getView().findViewById(R.id.image_view_close_id);
        imageView = (ImageView) getView().findViewById(R.id.image_view_admob_id);
        videoView = (EasyVideoPlayer) getView().findViewById(R.id.video_view_id);
        spinKitView = (SpinKitView) getView().findViewById(R.id.spin_kit_dialog_id);
        videoView.setCallback(new EasyVideoCallback() {
            @Override
            public void onStarted(EasyVideoPlayer player) {

            }

            @Override
            public void onPaused(EasyVideoPlayer player) {

            }

            @Override
            public void onPreparing(EasyVideoPlayer player) {

            }

            @Override
            public void onPrepared(EasyVideoPlayer player) {

            }

            @Override
            public void onBuffering(int percent) {

            }

            @Override
            public void onError(EasyVideoPlayer player, Exception e) {

            }

            @Override
            public void onCompletion(EasyVideoPlayer player) {
                FintechvietSdk.getInstance().saveAdActivity(decision.getViewUrl());
            }

            @Override
            public void onRetry(EasyVideoPlayer player, Uri source) {

            }

            @Override
            public void onSubmit(EasyVideoPlayer player, Uri source) {

            }

            @Override
            public void onClickVideoFrame(EasyVideoPlayer player) {

            }
        });
        // All further configuration is done from the XML layout.
        videoView.hideControls();
        videoView.disableControls();
        // Sets a theme color used to color the controls and labels. Defaults to your activity's primary theme color.
        videoView.setThemeColor(Color.RED);

        // Sets whether or not the player will toggle fullscreen for its Activity when tapped.
        videoView.setAutoFullscreen(false);
        // Sets whether or not the player will start playback over when reaching the end.
        videoView.setLoop(true);
        videoView.setAutoPlay(true);
        videoView.setBackgroundColor(Color.WHITE);
        switchCompat = (SwitchCompat) getView().findViewById(R.id.switch_compat_video_control_id);
        switchCompat.setChecked(true);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    videoView.start();
                    FintechvietSdk.getInstance().saveAdActivity(decision.getImpressionUrl());
                } else {
                    videoView.pause();
                }
            }
        });
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        FintechvietSdk.getInstance().requestPlacement(getTemplate(), getDeviceSerialNumber(), new JCallback<DecisionResponse>() {
            @Override
            public void onResponse(Call<DecisionResponse> call, Response<DecisionResponse> response) {
                if (response.code() == 200) {
                    decision = response.body().getDecision();
                    String videoUrl = response.body().getDecision().getContent().getVideoUrl();
                    String imageUrl = response.body().getDecision().getContent().getImageUrl();
                    appCompatTextViewTitleAdvertising.setText(response.body().getDecision().getContent().getBody());
                    if (!StringUtils.isBlank(videoUrl)) {
                        Uri video = Uri.parse(videoUrl/*"http://www.html5videoplayer.net/videos/toystory.mp4"*/);
                        // Sets the source to the HTTP URL held in the TEST_URL variable.
                        // To play files, you can use Uri.fromFile(new File("..."))
                        videoView.setSource(video);
                        //videoView.setZOrderOnTop(true);
                        //videoView.setVideoURI(Uri.parse("https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4"));
                        relativeLayoutVideo.setVisibility(View.VISIBLE);
                        relativeLayoutImage.setVisibility(View.GONE);
                    } else if (!StringUtils.isBlank(imageUrl)) {
                        RequestOptions options = new RequestOptions();
                        if (null != getContext() && !StringUtils.isBlank(imageUrl)) {
                            Glide.with(getContext())
                                    .load(imageUrl)
                                    .apply(options)
                                    .into(imageView);
                            relativeLayoutVideo.setVisibility(View.GONE);
                            relativeLayoutImage.setVisibility(View.VISIBLE);
                        }
                    } else {
                        relativeLayoutVideo.setVisibility(View.GONE);
                        relativeLayoutImage.setVisibility(View.GONE);
                    }
                    if (!StringUtils.isBlank(decision.getImpressionUrl())) {
                        FintechvietSdk.getInstance().saveAdActivity(decision.getImpressionUrl());
                    }
                } else {
                    dismiss();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        spinKitView.setVisibility(View.GONE);
                    }
                }, 2000);
            }

            @Override
            public void onFailure(Call<DecisionResponse> call, Throwable t) {
                spinKitView.setVisibility(View.GONE);
            }
        });
        FintechvietSdk.getInstance().saveContentImpression();
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != decision && decision.getContent() != null && !StringUtils.isBlank(decision.getContent().getImageUrl())) {
                    new FinestWebView.Builder(getActivity()).theme(R.style.FinestWebViewTheme)
                            .titleDefault("SMA")
                            .toolbarScrollFlags(0)
                            .statusBarColorRes(R.color.blackPrimaryDark)
                            .toolbarColorRes(R.color.blackPrimary)
                            .titleColorRes(R.color.finestWhite)
                            .urlColorRes(R.color.blackPrimaryLight)
                            .iconDefaultColorRes(R.color.finestWhite)
                            .progressBarColorRes(R.color.finestWhite)
                            .swipeRefreshColorRes(R.color.blackPrimaryDark)
                            .menuSelector(R.drawable.selector_light_theme)
                            .menuTextGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT)
                            .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                            .dividerHeight(0)
                            .gradientDivider(false)
                            .setCustomAnimations(R.anim.slide_left_in, R.anim.hold, R.anim.hold, R.anim.slide_right_out)
                            .disableIconBack(false)
                            .disableIconClose(false)
                            .disableIconForward(true)
                            .disableIconMenu(true)
                            .show(decision.getClickUrl());
                    FintechvietSdk.getInstance().updateUserRewardPoint(getDeviceSerialNumber(), "READ", 10, new JCallback() {
                        @Override
                        public void onResponse(Call call, Response response) {

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                        }
                    });
                    dismiss();
                    FintechvietSdk.getInstance().saveAdActivity(decision.getTrackingUrl());
                }
            }
        });
    }
}
