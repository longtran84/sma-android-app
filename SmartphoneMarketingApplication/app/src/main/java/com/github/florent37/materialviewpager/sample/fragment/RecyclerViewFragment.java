package com.github.florent37.materialviewpager.sample.fragment;

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
import com.fintechviet.android.sdk.model.ArticlesResponse;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.github.florent37.materialviewpager.sample.TestRecyclerViewAdapter;
import com.google.gson.Gson;
import com.sma.mobile.R;
import com.sma.mobile.favourite.ConversationAdapter;
import com.sma.mobile.favourite.News;
import com.sma.mobile.favourite.NewsAdapter;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 8;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private NewsAdapter newsAdapter;

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        final List<ArticlesItem> items = new ArrayList<ArticlesItem>();
        FintechvietSdk.getInstance().getArticlesResponse("token_android", "2017-09-22 00:00:00", "2017-09-22 12:00:00", new JCallback<ArticlesResponse>() {
            @Override
            public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
                ArticlesResponse articlesResponse = response.body();
                for (ArticlesItem articlesItem : articlesResponse.getArticles()) {
                    items.add(articlesItem);
                    newsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArticlesResponse> call, Throwable t) {

            }
        });
        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        newsAdapter = new NewsAdapter(getContext(), items, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                new FinestWebView.Builder(getActivity()).theme(R.style.FinestWebViewTheme)
                        .titleDefault("Dribbble")
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
        mRecyclerView.setAdapter(newsAdapter);
    }
}
