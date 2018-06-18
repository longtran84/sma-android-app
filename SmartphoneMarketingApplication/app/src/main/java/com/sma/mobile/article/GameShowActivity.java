package com.sma.mobile.article;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.ArticlesGameItem;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.activities.AbstractFragment;
import com.sma.mobile.article.adapters.GameViewerAdapter;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.game.LuckyWheelBingo;
import com.sma.mobile.utils.firebasenotifications.Config;
import com.tpcstld.twozerogame.Game2048Activity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chrisjluc.onesearch.ui.MenuActivity;
import kankan.wheel.widget.CandyWheelActivity;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Response;

public class GameShowActivity extends AbstractAppCompatActivity {

    private static final boolean GRID_LAYOUT = false;
    private List<ArticlesGameItem> items;
    private GameViewerAdapter gameViewerAdapter;
    private Unbinder mUnBinder;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_voucher_shortcut);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setTitle("Game");
        mUnBinder = ButterKnife.bind(this);
        items = new ArrayList<ArticlesGameItem>();
        ArticlesGameItem articlesGameItemJumpyBunny = new ArticlesGameItem();
        articlesGameItemJumpyBunny.setName("Vegas Jackpot Slots Casino");
        articlesGameItemJumpyBunny.setUrlToImage("https://lh3.googleusercontent.com/KsmijdNI3AhdJEj7MRsD7IPfviWneq6RKeEffs0BOCvEQQu5Ex3qblcYGJy48PB1fCq2=w300-rw");
        articlesGameItemJumpyBunny.setObjectGame(CandyWheelActivity.class.getName());
        items.add(articlesGameItemJumpyBunny);


        ArticlesGameItem articlesGameItem2048 = new ArticlesGameItem();
        articlesGameItem2048.setName("2048");
        articlesGameItem2048.setUrlToImage("https://lh3.googleusercontent.com/I-cDz4JCEufeRmvJCYLJO_p9i4xCcToKpOtzwvwaYoHU1HmcglEHejPceMeNYSDBXAo=w300-rw");
        articlesGameItem2048.setObjectGame(Game2048Activity.class.getName());
        items.add(articlesGameItem2048);

        ArticlesGameItem articlesGameItemGamePipePanic = new ArticlesGameItem();
        articlesGameItemGamePipePanic.setName("Pipe Panic");
        articlesGameItemGamePipePanic.setUrlToImage("http://www.users.waitrose.com/~thunor/pipepanic/images/pipepanic07.png");
        articlesGameItemGamePipePanic.setObjectGame(MenuActivity.class.getName());
        items.add(articlesGameItemGamePipePanic);


        ArticlesGameItem articlesGameItemLuckyWheelBingo = new ArticlesGameItem();
        articlesGameItemLuckyWheelBingo.setName("Lucky Wheel Bingo");
        articlesGameItemLuckyWheelBingo.setUrlToImage("https://www.unibet.eu/polopoly_fs/1.672975!/image/3471357066.jpg");
        articlesGameItemLuckyWheelBingo.setObjectGame(LuckyWheelBingo.class.getName());
        items.add(articlesGameItemLuckyWheelBingo);

//        FintechvietSdk.getInstance().games(new JCallback<List<ArticlesGameItem>>() {
//            @Override
//            public void onResponse(Call<List<ArticlesGameItem>> call, Response<List<ArticlesGameItem>> response) {
//                if (response.code() == 200) {
//                    List<ArticlesGameItem> listArticlesGameItem = response.body();
//                    for (ArticlesGameItem articlesGameItem : listArticlesGameItem) {
//                        items.add(articlesGameItem);
//                        gameViewerAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ArticlesGameItem>> call, Throwable t) {
//
//            }
//        });

        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
        mRecyclerView.setHasFixedSize(true);
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(0xFFCCCCCC);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getApplicationContext()).paint(paint).build());
        gameViewerAdapter = new GameViewerAdapter(getApplicationContext(), items, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
//                SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
//                String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
//                FintechvietSdk.getInstance().updateUserRewardPoint(getDeviceSerialNumber(), "READ", 10, new JCallback() {
//                    @Override
//                    public void onResponse(Call call, Response response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Call call, Throwable t) {
//
//                    }
//                });
                FintechvietSdk.getInstance().saveContentClick();
                Intent intent = new Intent();
                intent.setClassName(getApplicationContext(), items.get(position).getObjectGame());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(gameViewerAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }
}