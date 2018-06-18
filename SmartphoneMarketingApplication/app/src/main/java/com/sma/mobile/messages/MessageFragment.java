package com.sma.mobile.messages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.fintechviet.android.sdk.ad.AdMob;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.Message;
import com.sma.mobile.MessageEvent;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractFragment;
import com.sma.mobile.admob.AdMobRewardedAdapter;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.messages.adapters.MessageAdapter;
import com.sma.mobile.utils.firebasenotifications.Config;
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
public class MessageFragment extends AbstractFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private final boolean GRID_LAYOUT = false;
    private MessageAdapter messageAdapter;
    private List<Message> items;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        items = new ArrayList<Message>();
        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(0xFFCCCCCC);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());
        messageAdapter = new MessageAdapter(getContext(), items, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Message message = items.get(position);
                message.setRead(1);
                FintechvietSdk.getInstance().updateMessage(message.getId(), new JCallback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        EventBus.getDefault().post(new MessageEvent());
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });
                messageAdapter.notifyDataSetChanged();
                final View customView;
                try {
                    customView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_webview, null);
                } catch (InflateException e) {
                    throw new IllegalStateException("This device does not support Web Views.");
                }
                final WebView webView = (WebView) customView.findViewById(R.id.webview);
                final AppCompatTextView title = (AppCompatTextView) customView.findViewById(R.id.title_id);
                title.setText(message.getSubject());
                webView.loadData(message.getBody(), "text/html; charset=utf-8", "utf-8");
                MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
//                        .title(message.getSubject())
                        .backgroundColor(getResources().getColor(R.color.colorPrimary))
                        .customView(customView, false)
//                        .positiveText(R.string.ok)
//                        .positiveColor(Color.BLACK)
//                        .contentLineSpacing(1.6f)
//                        .onPositive((dialog, which) -> {
//                            dialog.dismiss();
//                        })
                        .build();
                materialDialog.dismiss();
                materialDialog.show();
                final AppCompatTextView appCompatTextViewOK = (AppCompatTextView) customView.findViewById(R.id.ok_id);
                appCompatTextViewOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != materialDialog) {
                            materialDialog.dismiss();
                        }
                    }
                });
            }
        });
        mRecyclerView.setAdapter(messageAdapter);
    }

    /**
     *
     */
    private void fetchingData() {
        FintechvietSdk.getInstance().getMessages(getDeviceSerialNumber(), new JCallback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.code() == 200) {
                    items.clear();
                    List<Message> listMessage = response.body();
                    for (Message message : listMessage) {
                        items.add(message);
                        messageAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            fetchingData();
            EventBus.getDefault().post(new MessageEvent());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchingData();
    }
}
