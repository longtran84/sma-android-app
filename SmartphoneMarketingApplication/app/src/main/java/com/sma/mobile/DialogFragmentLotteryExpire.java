package com.sma.mobile;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.ad.Decision;
import com.fintechviet.android.sdk.ad.DecisionResponse;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.PromotionMessage;
import com.github.ybq.android.spinkit.SpinKitView;
import com.sma.mobile.utils.firebasenotifications.Config;
import com.thefinestartist.finestwebview.FinestWebView;

import org.apache.commons.lang3.StringUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by longtran on 30/09/2017.
 */

public class DialogFragmentLotteryExpire extends DialogFragment {

    public static final String TAG = "DialogFragmentLotteryExpire";

    private TextView textView;
    private AppCompatButton button;
    private LinearLayout linearLayout;
    private PromotionMessage promotionMessage;

    public DialogFragmentLotteryExpire() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        promotionMessage = (PromotionMessage) getArguments().getSerializable("PROMOTION");
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
        dialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state) {
        super.onCreateView(inflater, parent, state);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_layout_lottery_expire, parent, true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        textView = (TextView) getView().findViewById(R.id.text_view_content_id);
        button = (AppCompatButton) getView().findViewById(R.id.button_id);
        linearLayout = (LinearLayout)getView().findViewById(R.id.relative_layout_lottery_expire_id);
        int tint = Color.parseColor("#4d79c1");
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        Drawable icon = getResources().getDrawable(R.drawable.ic_bg_lottery_expire);
        icon.setColorFilter(tint,mode);
        linearLayout.setBackground(icon);
        if (!StringUtils.isBlank(promotionMessage.getBody())) {
            textView.setText(promotionMessage.getBody());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FintechvietSdk.getInstance().updateMessagePromotion(promotionMessage.getId(), new JCallback() {
                    @Override
                    public void onResponse(Call call, Response response) {

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });
                dismiss();
            }
        });
    }
}
