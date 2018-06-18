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
import android.widget.ProgressBar;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.ad.AdMob;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.ArticlesItem;
import com.fintechviet.android.sdk.model.ListNewsByCategory;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractFragment;
import com.sma.mobile.article.adapters.NewsArticleViewerAdapter;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.utils.firebasenotifications.Config;
import com.sma.mobile.utils.listener.OnRcvScrollListener;
import com.thefinestartist.finestwebview.FinestWebView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by QLong on 10/5/2017.
 */

public class NewsArticleViewerFragment extends AbstractFragment {

    private static final boolean GRID_LAYOUT = false;
    private List<ArticlesItem> items;
    private NewsArticleViewerAdapter newsArticleViewerAdapter;
    private String categoryCode;
    private final int totalDocumentOfPage = 30;
    private int currentPage = 1;
    private int count = Integer.MAX_VALUE;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar_loading)
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_article_viewer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        items = new ArrayList<ArticlesItem>();
        Bundle args = getArguments();
        if (args != null) {
            this.categoryCode = args.getString("CATEGORY_CODE");
        }
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
//                SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
//                String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
                FintechvietSdk.getInstance().updateUserRewardPoint(getDeviceSerialNumber(), "READ", 10, new JCallback() {
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
        fetchingData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            fetchingData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && null != categoryCode) {
            fetchingData();
        }
    }

    /**
     *
     */
    private void fetchingData() {
        if (StringUtils.isBlank(categoryCode)) {
            return;
        }
        FintechvietSdk.getInstance().getNewsByCategory(getDeviceSerialNumber(), currentPage, categoryCode, new JCallback<ListNewsByCategory>() {
            @Override
            public void onResponse(Call<ListNewsByCategory> call, Response<ListNewsByCategory> response) {
                if (response.code() == 200) {
                    items.clear();
                    List<ArticlesItem> listArticlesItem = response.body().getListArticlesItem();
                    for (int i = 0; i < listArticlesItem.size(); i++) {
                        ArticlesItem articlesItem = listArticlesItem.get(i);
                        articlesItem.setDescription(StringUtils.abbreviate(articlesItem.getDescription(), 150));
                        items.add(articlesItem);
                        newsArticleViewerAdapter.notifyDataSetChanged();
                    }
                    mRecyclerView.addOnScrollListener(new OnRcvScrollListener() {
                        @Override
                        public void onLoadMore(int totalItemCount) {
                            super.onLoadMore(totalItemCount);
                            currentPage++;
                            items.add(null);
                            newsArticleViewerAdapter.notifyItemInserted(items.size() - 1);
                            FintechvietSdk.getInstance().getNewsByCategory(getDeviceSerialNumber(), currentPage, categoryCode, new JCallback<ListNewsByCategory>() {
                                @Override
                                public void onResponse(Call<ListNewsByCategory> call, Response<ListNewsByCategory> response) {
                                    if (response.code() == 200) {
                                        List<ArticlesItem> listArticlesItemLoadMore = response.body().getListArticlesItem();
                                        int size = items.size();
                                        //remove progress item
                                        items.remove(items.size() - 1);
                                        newsArticleViewerAdapter.notifyItemRemoved(items.size());
                                        for (int i = 0; i < listArticlesItemLoadMore.size(); i++) {
                                            ArticlesItem articlesItem = listArticlesItemLoadMore.get(i);
                                            articlesItem.setDescription(StringUtils.abbreviate(articlesItem.getDescription(), 150));
                                            items.add(articlesItem);
                                        }
                                        newsArticleViewerAdapter.notifyItemRangeChanged(items.size()
                                                - 1, items.size() - size);
                                    } else {
                                        items.remove(null);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ListNewsByCategory> call, Throwable t) {
                                    items.remove(null);
                                    newsArticleViewerAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ListNewsByCategory> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    /***
     *
     * @param categoryCode
     * @return
     */
    public static NewsArticleViewerFragment newInstance(String categoryCode) {
        NewsArticleViewerFragment newsArticleViewerFragment = new NewsArticleViewerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("CATEGORY_CODE", categoryCode);
        newsArticleViewerFragment.setArguments(bundle);
        return newsArticleViewerFragment;
    }
}