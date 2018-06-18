package com.sma.mobile.loginsignup;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.Message;
import com.sma.mobile.MessageEvent;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractFragment;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.messages.adapters.MessageAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class LoginFragment extends AbstractFragment {

    @BindView(R.id.appCompatImageViewFaceIDRecognition)
    AppCompatImageView appCompatImageViewFaceIDRecognition;

    @BindView(R.id.appCompatImageViewFingerprintRecognition)
    AppCompatImageView appCompatImageViewFingerprintRecognition;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        appCompatImageViewFaceIDRecognition.setColorFilter(ContextCompat.getColor(getContext(), R.color.white));
        appCompatImageViewFingerprintRecognition.setColorFilter(ContextCompat.getColor(getContext(), R.color.white));
    }

    /**
     *
     */
    private void fetchingData() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            fetchingData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchingData();
    }
}
