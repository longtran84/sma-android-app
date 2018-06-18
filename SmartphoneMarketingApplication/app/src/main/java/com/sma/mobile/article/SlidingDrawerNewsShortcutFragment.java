package com.sma.mobile.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.ArticlesItem;
import com.fintechviet.android.sdk.model.Favourite;
import com.sma.mobile.R;
import com.sma.mobile.home.DemoFragment;

import net.sourceforge.PagerSlidingTabStrip;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Response;

public class SlidingDrawerNewsShortcutFragment extends Fragment {

    @BindView(R.id.tabs_sliding_drawer_news_shortcut_id)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.view_pager_sliding_drawer_news_shortcut_id)
    ViewPager pager;
    @BindView(R.id.image_view_app_icon_id)
    ImageView imageView;
    @BindView(R.id.text_view_description)
    TextView textView;

    private String registrationToken;
    private List<Favourite> items;
    private NewsCategoriesPagerAdapter newsCategoriesPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sliding_drawer_news_shortcut, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        items = new ArrayList<Favourite>();
        newsCategoriesPagerAdapter = new NewsCategoriesPagerAdapter(getFragmentManager(), items);
        //tabs.setMode(PagerSlidingTabStrip.Mode.DEFAULT);
        pager.setAdapter(newsCategoriesPagerAdapter);
        tabs.setViewPager(pager);
        pager.setOffscreenPageLimit(newsCategoriesPagerAdapter.getCount());
        FintechvietSdk.getInstance().getListFavourite(registrationToken, new JCallback<List<Favourite>>() {
            @Override
            public void onResponse(Call<List<Favourite>> call, final Response<List<Favourite>> response) {
                if (response.code() == 200) {
                    List<Favourite> listFavourite = response.body();
                    for (Favourite favourite : listFavourite) {
                        items.add(favourite);
                        newsCategoriesPagerAdapter.notifyDataSetChanged();
                    }
                    if (null != listFavourite && listFavourite.size() > 0) {
//                        FintechvietSdk.getInstance().topNewsByCategory(listFavourite.get(0).getCode(), new JCallback<List<ArticlesItem>>() {
//                            @Override
//                            public void onResponse(Call<List<ArticlesItem>> call, Response<List<ArticlesItem>> response) {
//                                if (response.code() == 200) {
//                                    List<ArticlesItem> listArticlesItem = response.body();
//                                    if (null != listArticlesItem && listArticlesItem.size() > 0) {
//                                        ArticlesItem articlesItem = listArticlesItem.get(0);
//                                        textView.setText(articlesItem.getDescription());
//                                        if (!StringUtils.isBlank(articlesItem.getUrlToImage())) {
//                                            Glide.with(getActivity())
//                                                    .load(articlesItem.getUrlToImage())
//                                                    .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
//                                                            new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL))))
//                                                    .into(imageView);
//
//                                        }
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<List<ArticlesItem>> call, Throwable t) {
//
//                            }
//                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Favourite>> call, Throwable t) {

            }
        });
    }

    public static SlidingDrawerNewsShortcutFragment newInstance() {
        return new SlidingDrawerNewsShortcutFragment();
    }

    /***
     *
     */
    private class NewsCategoriesPagerAdapter extends FragmentPagerAdapter {

        private List<Favourite> listFavourite;

        /***
         *
         * @param fragmentManager
         * @param listFavourite
         */
        public NewsCategoriesPagerAdapter(FragmentManager fragmentManager, List<Favourite> listFavourite) {
            super(fragmentManager);
            this.listFavourite = listFavourite;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return listFavourite.size();
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            return NewsArticleViewerFragment.newInstance(listFavourite.get(position).getCode());
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return listFavourite.get(position).getTitle();
        }

    }
}