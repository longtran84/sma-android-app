package com.sma.mobile.article;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.ArticlesItem;
import com.fintechviet.android.sdk.model.ArticlesResponse;
import com.fintechviet.android.sdk.model.Favourite;
import com.fintechviet.android.sdk.user.User;
//import com.guidebee.game.tutorial.flappybird.FlappyBirdGameActivity;
import com.sma.mobile.DialogFragmentLotteryExpire;
import com.sma.mobile.FullScreenDialog;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.article.adapters.ViewPagerSlidingDrawerAdapter;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.utils.firebasenotifications.Config;
import com.thefinestartist.finestwebview.FinestWebView;
//import com.tpcstld.twozerogame.Game2048Activity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import hollowsoft.slidingdrawer.SlidingDrawer;
//import kankan.wheel.widget.CandyWheelActivity;
import retrofit2.Call;
import retrofit2.Response;
import se.emilsjolander.flipview.FlipAdapter;
import se.emilsjolander.flipview.FlipView;
import se.emilsjolander.flipview.OverFlipMode;

/**
 * Created by longtran on 24/09/2017.
 */

public class ArticlesActivity extends AbstractAppCompatActivity implements FlipAdapter.Callback, FlipView.OnFlipListener, FlipView.OnOverFlipListener {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 8;
    private final int TOTAL_DOCUMENT_OF_PAGE = 30;
    private int currentPage = 1;
    private String registrationToken = "";

    @BindView(R.id.flip_view)
    FlipView mFlipView;

    @BindView(R.id.sliding_drawer_id)
    SlidingDrawer slidingDrawer;

    @BindView(R.id.linear_layout_handle_id)
    RelativeLayout relativeLayout;

    @BindView(R.id.view_pager_sliding_drawer_id)
    ViewPager viewPagerSlidingDrawer;

    @BindView(R.id.radio_group_sliding_drawer_id)
    RadioGroup radioGroup;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    private FlipAdapter mAdapter;
    private List<ArticlesItem> items;
    private String newsId;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    /***
     *
     * @param list
     * @param delim
     * @return
     */
    private String join(List<Favourite> list, String delim) {
        int len = list.size();
        if (len == 0)
            return "*";
        StringBuilder sb = new StringBuilder();
        if (list.size() > 0) {
            for (int i = 0; i < len; i++) {
                if (null != list.get(i) && list.get(i).isInterest()) {
                    sb.append(list.get(i).getCode());
                    sb.append(delim);
                }
            }
        } else {
            sb.append("*");
        }
        if (StringUtils.isBlank(sb)) {
            sb.append("*");
        }
        return sb.toString();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            newsId = String.valueOf(getIntent().getExtras().getLong("NEWS_ID"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_articles);
        ButterKnife.bind(this);
//        slidingDrawer.lock();
        viewPagerSlidingDrawer.setAdapter(new ViewPagerSlidingDrawerAdapter(getSupportFragmentManager()));
        viewPagerSlidingDrawer.setOffscreenPageLimit(3);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingDrawer.isOpened()) {
                    radioGroup.clearCheck();
                    slidingDrawer.close();
                }
            }
        });
        viewPagerSlidingDrawer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int radioButtonId = radioGroup.getChildAt(position).getId();
                radioGroup.check(radioButtonId);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        items = new ArrayList<ArticlesItem>();
        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
        registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
        showProcessing();
        FintechvietSdk.getInstance().getArticlesResponse(registrationToken, currentPage, newsId, new JCallback<ArticlesResponse>() {
            @Override
            public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
                if (response.code() == 200) {
                    final ArticlesResponse articlesResponse = response.body();
                    FintechvietSdk.getInstance().getUserInfo(registrationToken, new JCallback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.code() == 200) {
                                User user = response.body();
                                if (null != user && articlesResponse.getArticles().size() > 0) {
                                    for (ArticlesItem articlesItem : articlesResponse.getArticles()) {
                                        articlesItem.setEarning(user.getEarning());
                                        items.add(articlesItem);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                            hideProcessing();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            hideProcessing();
                            t.printStackTrace();
                        }
                    });
                } else {
                    hideProcessing();
                    getMaterialDialogAlertError(ArticlesActivity.this).show();
                }
            }

            @Override
            public void onFailure(Call<ArticlesResponse> call, Throwable t) {
                hideProcessing();
                getMaterialDialogAlertError(ArticlesActivity.this).show();
            }
        });

        mAdapter = new FlipAdapter(this, items, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                new FinestWebView.Builder(getApplicationContext()).theme(R.style.FinestWebViewTheme)
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
                FintechvietSdk.getInstance().saveContentClick();
                SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
                String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
                FintechvietSdk.getInstance().updateUserRewardPoint(registrationToken, "READ", 10, new JCallback() {
                    @Override
                    public void onResponse(Call call, Response response) {

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });
            }
        });
        mAdapter.setCallback(this);
        mFlipView.setAdapter(mAdapter);
        mFlipView.setOnFlipListener(this);
        mFlipView.peakNext(false);
        mFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
        //mFlipView.setEmptyView(findViewById(R.id.empty_view));
        mFlipView.setOnOverFlipListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                slidingDrawer.open();
                switch (checkedId) {
                    case R.id.radio_button_news_shortcut_id:
                        // do operations specific to this selection
                        viewPagerSlidingDrawer.setCurrentItem(0);
                        break;
                    case R.id.radio_button_social_noti_shortcut_id:
                        // do operations specific to this selection
                        viewPagerSlidingDrawer.setCurrentItem(1);
                        break;
                    case R.id.radio_button_voucher_shortcut_id:
                        // do operations specific to this selection
                        viewPagerSlidingDrawer.setCurrentItem(2);
                        break;
                }
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(new Random().nextInt(2) == 1){
            DialogFragmentLotteryExpire dialogFragmentLotteryExpire = new DialogFragmentLotteryExpire();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            dialogFragmentLotteryExpire.show(fragmentTransaction, FullScreenDialog.TAG);
        }
    }

    @Override
    public void onPageRequested(int page) {
        mFlipView.smoothFlipTo(page);
    }

    private int random = 0;

    @Override
    public void onFlippedToPage(FlipView v, int position, long id) {
        Log.i("pageflip", "Page: " + position);
        if (position == (mAdapter.getCount() - 5)) {
            if ((currentPage * TOTAL_DOCUMENT_OF_PAGE) < 1000) {
                currentPage++;
                FintechvietSdk.getInstance().getArticlesResponse(registrationToken, currentPage, newsId, new JCallback<ArticlesResponse>() {
                    @Override
                    public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
                        final ArticlesResponse articlesResponse = response.body();
                        FintechvietSdk.getInstance().getUserInfo(registrationToken, new JCallback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.code() == 200) {
                                    User user = response.body();
                                    if (null != user && articlesResponse.getArticles().size() > 0) {
                                        for (ArticlesItem articlesItem : articlesResponse.getArticles()) {
                                            articlesItem.setEarning(user.getEarning());
                                            items.add(articlesItem);
                                            mAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                                hideProcessing();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                hideProcessing();
                                t.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ArticlesResponse> call, Throwable t) {

                    }
                });
            }
        } else {

        }
        String typeAdmob = template(Math.max(0, new Random().nextInt(4)));
        if (position > 0 && position % 5 == 0 && position % 20 != 0) {
            if (!StringUtils.isBlank(typeAdmob)) {
                FullScreenDialog dialog = new FullScreenDialog();
                dialog.setTemplate(typeAdmob);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                dialog.show(ft, FullScreenDialog.TAG);
            }
        } else if (position > 0 && position % 20 == 0) {
            random++;
            if (random > 4) {
                random = 1;
            }
            Log.i("pageflip", "random: " + random);
            if (random == 4) {
//                Intent intent = new Intent();
//                intent.setClassName(getApplicationContext(), CandyWheelActivity.class.getName());
//                startActivity(intent);
            } else if (random == 2) {
//                Intent intent = new Intent();
//                intent.setClassName(getApplicationContext(), Game2048Activity.class.getName());
//                startActivity(intent);
            } else if (random == 3) {
//                Intent intent = new Intent();
//                intent.setClassName(getApplicationContext(), FlappyBirdGameActivity.class.getName());
//                startActivity(intent);
            } else if (random == 1) {
//                Intent intent = new Intent();
//                intent.setClassName(getApplicationContext(), FlappyBirdGameActivity.class.getName());
//                startActivity(intent);
            }
        }
        FintechvietSdk.getInstance().saveContentImpression();
    }

    private String template(int number) {
        if (number == 1 || number == 2 || number == 0) {
            return "image";
        } else {
            return "video";
        }
    }

    @Override
    public void onOverFlip(FlipView v, OverFlipMode mode,
                           boolean overFlippingPrevious, float overFlipDistance,
                           float flipDistancePerPage) {
        Log.i("overflip", "overFlipDistance = " + overFlipDistance);
    }
}
