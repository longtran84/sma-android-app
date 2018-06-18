package com.sma.mobile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.Favourite;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.article.ArticlesActivity;
import com.sma.mobile.favourite.FavouriteAdapter;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.favourite.adapters.ViewPagerRewardsPointsAdapter;
import com.sma.mobile.utils.firebasenotifications.Config;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class FavouriteActivity extends AbstractAppCompatActivity {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 20;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerViewFavourite;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.linear_layout_id)
    LinearLayout linearLayout;

    @BindView(R.id.app_compat_button_saving_interest)
    LinearLayout appCompatButton;

    private FavouriteAdapter favouriteAdapter;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favourite);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setTitle("Sở thích");
        final List<Favourite> items = new ArrayList<Favourite>();
        if (GRID_LAYOUT) {
            recyclerViewFavourite.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerViewFavourite.setLayoutManager(new LinearLayoutManager(this));
        }
        recyclerViewFavourite.setHasFixedSize(true);
        //Use this now
        //recyclerViewFavourite.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        favouriteAdapter = new FavouriteAdapter(this, items, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, final int position) {
                appCompatButton.setVisibility(View.VISIBLE);
                final Favourite favourite = items.get(position);
                items.get(position).setInterest(favourite.isInterest() ? false : true);
                favouriteAdapter.notifyDataSetChanged();
                Realm realm = null;
                try {
                    realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealmOrUpdate(items.get(position));
                        }
                    });
                } finally {
                    if (realm != null) {
                        realm.close();
                    }
                }
            }
        });
        recyclerViewFavourite.setAdapter(favouriteAdapter);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder favouriteList = new StringBuilder();
                for (Favourite favourite : items) {
                    if (favourite.isInterest()) {
                        favouriteList.append(favourite.getFavouriteID());
                        favouriteList.append(",");
                    }
                }
                if (StringUtils.isBlank(favouriteList)) {
                    favouriteList.append("0");
                }
                FintechvietSdk.getInstance().updateFavouriteCategoriesByDevice(favouriteList.toString(), getDeviceSerialNumber(), new JCallback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        Intent returnIntent = getIntent();
                        returnIntent.putExtra("KEY_NAME_RESULT", 2);
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Intent returnIntent = getIntent();
                        returnIntent.putExtra("KEY_NAME_RESULT", 2);
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    }
                });
            }
        });
        FintechvietSdk.getInstance().getListFavourite(getDeviceSerialNumber(), new JCallback<List<Favourite>>() {
            @Override
            public void onResponse(Call<List<Favourite>> call, final Response<List<Favourite>> response) {
                if (response.code() == 200) {
                    Realm realm = null;
                    try {
                        realm = Realm.getDefaultInstance();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmResults<Favourite> realmResultsFavourite = realm.where(Favourite.class).findAll();
                                List<Favourite> listFavouriteRealm = realmResultsFavourite;
                                HashMap<String, Boolean> hashMapFavouriteRealm = new HashMap<String, Boolean>();
                                for (Favourite favourite : listFavouriteRealm) {
                                    hashMapFavouriteRealm.put(favourite.getCode(), favourite.isInterest());
                                }
                                if (null != listFavouriteRealm && listFavouriteRealm.size() > 0) {
                                    List<Favourite> listFavourite = response.body();
                                    for (Favourite favourite : listFavourite) {
                                        if (null != hashMapFavouriteRealm.get(favourite.getCode())) {
                                            favourite.setInterest(hashMapFavouriteRealm.get(favourite.getCode()).booleanValue());
                                        }
                                        items.add(favourite);
                                        favouriteAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    List<Favourite> listFavourite = response.body();
                                    for (Favourite favourite : listFavourite) {
                                        items.add(favourite);
                                        favouriteAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
                    } finally {
                        if (realm != null) {
                            realm.close();
                        }
                    }
                } else {
                    //getMaterialDialogAlertError().show();
                }

            }

            @Override
            public void onFailure(Call<List<Favourite>> call, Throwable t) {
                //getMaterialDialogAlertError().show();
            }
        });
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                FintechvietSdk.getInstance().getListFavourite(getDeviceSerialNumber(), new JCallback<List<Favourite>>() {
                    @Override
                    public void onResponse(Call<List<Favourite>> call, final Response<List<Favourite>> response) {
                        items.clear();
                        Realm realm = null;
                        try {
                            realm = Realm.getDefaultInstance();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    RealmResults<Favourite> realmResultsFavourite = realm.where(Favourite.class).findAll();
                                    List<Favourite> listFavouriteRealm = realmResultsFavourite;
                                    HashMap<String, Boolean> hashMapFavouriteRealm = new HashMap<String, Boolean>();
                                    for (Favourite favourite : listFavouriteRealm) {
                                        hashMapFavouriteRealm.put(favourite.getCode(), favourite.isInterest());
                                    }
                                    if (null != listFavouriteRealm && listFavouriteRealm.size() > 0) {
                                        List<Favourite> listFavourite = response.body();
                                        for (Favourite favourite : listFavourite) {
                                            if (null != hashMapFavouriteRealm.get(favourite.getCode())) {
                                                favourite.setInterest(hashMapFavouriteRealm.get(favourite.getCode()).booleanValue());
                                            }
                                            items.add(favourite);
                                            favouriteAdapter.notifyDataSetChanged();
                                        }
                                    } else {
                                        List<Favourite> listFavourite = response.body();
                                        for (Favourite favourite : listFavourite) {
                                            items.add(favourite);
                                            favouriteAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            });
                        } finally {
                            if (realm != null) {
                                realm.close();
                            }
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
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectArticlesActivity(FavouriteActivity.this);
            }
        });
    }

    /***
     *
     * @return
     */
    public MaterialDialog getMaterialDialogAlertError() {
        return new MaterialDialog.Builder(this)
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
                        finish();
                    }
                })
                .build();
    }

    /***
     *
     */
    public void redirectArticlesActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName(activity, ArticlesActivity.class.getName());
        activity.startActivity(intent);
    }
}
