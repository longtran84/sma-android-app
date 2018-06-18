package com.sma.mobile.article;

import android.content.SharedPreferences;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.ArticlesItem;
import com.sma.mobile.R;
import com.sma.mobile.article.adapters.NewsArticleViewerAdapter;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.utils.firebasenotifications.Config;
import com.thefinestartist.finestwebview.FinestWebView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class SlidingDrawerSocialNotificationShortcutFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;
    private List<ArticlesItem> items;
    private NewsArticleViewerAdapter newsArticleViewerAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_social_notification_shortcut, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        items = new ArrayList<ArticlesItem>();
        FintechvietSdk.getInstance().adNews(1, new JCallback<List<ArticlesItem>>() {
            @Override
            public void onResponse(Call<List<ArticlesItem>> call, Response<List<ArticlesItem>> response) {
                if (response.code() == 200) {
                    List<ArticlesItem> listArticlesItem = response.body();
                    for (ArticlesItem articlesItem : listArticlesItem) {
                        items.add(articlesItem);
                        newsArticleViewerAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ArticlesItem>> call, Throwable t) {

            }
        });

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
        newsArticleViewerAdapter = new NewsArticleViewerAdapter(getContext(), items, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
                String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
                FintechvietSdk.getInstance().updateUserRewardPoint(registrationToken, "READ", 10, new JCallback() {
                    @Override
                    public void onResponse(Call call, Response response) {

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });
                FintechvietSdk.getInstance().saveContentClick();
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
                        //                    .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
                        .setCustomAnimations(R.anim.slide_left_in, R.anim.hold, R.anim.hold,
                                R.anim.slide_right_out)
                        //                    .setCustomAnimations(R.anim.fade_in_fast, R.anim.fade_out_medium, R.anim.fade_in_medium, R.anim.fade_out_fast)
                        .disableIconBack(false)
                        .disableIconClose(false)
                        .disableIconForward(true)
                        .disableIconMenu(true)
                        .show(items.get(position).getSource());
            }
        });
        mRecyclerView.setAdapter(newsArticleViewerAdapter);
    }

    public static SlidingDrawerSocialNotificationShortcutFragment newInstance() {
        return new SlidingDrawerSocialNotificationShortcutFragment();
    }
}