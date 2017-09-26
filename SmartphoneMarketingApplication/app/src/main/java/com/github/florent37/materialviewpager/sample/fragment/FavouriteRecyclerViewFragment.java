package com.github.florent37.materialviewpager.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.Favourite;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.sma.mobile.R;
import com.sma.mobile.favourite.ConversationAdapter;
import com.sma.mobile.favourite.FavouriteAdapter;
import com.sma.mobile.favourite.News;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class FavouriteRecyclerViewFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 20;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerViewFavourite;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private FavouriteAdapter favouriteAdapter;

    public static FavouriteRecyclerViewFragment newInstance() {
        return new FavouriteRecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        final List<Favourite> items = new ArrayList<Favourite>();
        if (GRID_LAYOUT) {
            recyclerViewFavourite.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerViewFavourite.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        recyclerViewFavourite.setHasFixedSize(true);
        //Use this now
        recyclerViewFavourite.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        favouriteAdapter = new FavouriteAdapter(getContext(), items, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        recyclerViewFavourite.setAdapter(favouriteAdapter);
        FintechvietSdk.getInstance().getListFavourite("123123123213", new JCallback<List<Favourite>>() {
            @Override
            public void onResponse(Call<List<Favourite>> call, Response<List<Favourite>> response) {
                List<Favourite> listFavourite = response.body();
                for (Favourite favourite : listFavourite) {
                    items.add(favourite);
                    favouriteAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Favourite>> call, Throwable t) {

            }
        });
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                FintechvietSdk.getInstance().getListFavourite("123123123213", new JCallback<List<Favourite>>() {
                    @Override
                    public void onResponse(Call<List<Favourite>> call, Response<List<Favourite>> response) {
                        items.clear();
                        List<Favourite> listFavourite = response.body();
                        for (Favourite favourite : listFavourite) {
                            items.add(favourite);
                            favouriteAdapter.notifyDataSetChanged();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<Favourite>> call, Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }
}
