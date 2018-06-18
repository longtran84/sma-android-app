package com.sma.mobile;

import android.content.SharedPreferences;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.ArticlesItem;
import com.fintechviet.android.sdk.model.ArticlesResponse;
import com.fintechviet.android.sdk.model.PromotionMessage;
import com.fintechviet.android.sdk.user.User;
//import com.guidebee.game.tutorial.flappybird.FlappyBirdGameActivity;
import com.google.gson.Gson;
import com.sma.mobile.activities.AbstractFragment;
import com.sma.mobile.autoscroll.OnItemClickListener;
import com.sma.mobile.autoscroll.ScrollCustomAdapter;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.utils.firebasenotifications.Config;
import com.thefinestartist.finestwebview.FinestWebView;
//import com.tpcstld.twozerogame.Game2048Activity;
//import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
//import kankan.wheel.widget.CandyWheelActivity;
import retrofit2.Call;
import retrofit2.Response;
import se.emilsjolander.flipview.FlipAdapter;
import se.emilsjolander.flipview.FlipView;
import se.emilsjolander.flipview.OverFlipMode;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class LatestNewsHeadlinesFragment extends AbstractFragment implements FlipAdapter.Callback, FlipView.OnFlipListener, FlipView.OnOverFlipListener {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 8;
    private final int TOTAL_DOCUMENT_OF_PAGE = 30;
    private int currentPage = 1;
//    private String registrationToken = "";

    @BindView(R.id.flip_view)
    FlipView mFlipView;

    @BindView(R.id.text_view_earn1)
    AppCompatTextView textViewEarn;

    @BindView(R.id.text_view_reward1)
    AppCompatTextView textViewReward;

    private FlipAdapter mAdapter;
    private List<ArticlesItem> items;
    private String newsId;

//    @BindView(R.id.rec_scroll_stock)
//    RecyclerView rec_scroll_stock;

    private List<ArticlesItem> listArticlesResponse;
    private ScrollCustomAdapter scrollStockAdapter;
    //new count added
    int scrollCount = 0;

    public static LatestNewsHeadlinesFragment newInstance() {
        return new LatestNewsHeadlinesFragment();
    }

    //new auto scroll
    public void autoScrollAnother() {
//        scrollCount = 0;
//        final int speedScroll = 1 * 1000;
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (scrollCount == scrollStockAdapter.getItemCount()) {
//                    scrollStockAdapter.load();
//                    scrollStockAdapter.notifyDataSetChanged();
//                }
//                rec_scroll_stock.smoothScrollToPosition((scrollCount++));
//                handler.postDelayed(this, speedScroll);
//            }
//        };
//        handler.postDelayed(runnable, speedScroll);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_articles, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        listArticlesResponse = new ArrayList<ArticlesItem>();
//        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
//        registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
        scrollStockAdapter = new ScrollCustomAdapter(getActivity(), listArticlesResponse) {
            @Override
            public void load() {
                stockListModels.addAll(stockListModels);
            }
        };
//        FintechvietSdk.getInstance().getArticlesResponse(registrationToken, 1, "", new JCallback<ArticlesResponse>() {
//            @Override
//            public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
//                if (response.code() == 200) {
//                    rec_scroll_stock.setVisibility(View.VISIBLE);
//                    ArticlesResponse articlesResponse = response.body();
//                    for (ArticlesItem articlesItem : articlesResponse.getArticles()) {
//                        listArticlesResponse.add(articlesItem);
//                    }
//                    scrollStockAdapter.notifyDataSetChanged();
//                } else {
//                    rec_scroll_stock.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArticlesResponse> call, Throwable t) {
//
//            }
//        });
        scrollStockAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ArticlesItem item) {
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
                        .show(item.getSource());
            }

            @Override
            public void onChildItemClick(ArticlesItem item) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {
                    private static final float SPEED = 10000f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        };
        autoScrollAnother();
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rec_scroll_stock.setLayoutManager(layoutManager);
//        rec_scroll_stock.setHasFixedSize(true);
//        rec_scroll_stock.setItemViewCacheSize(3);
//        rec_scroll_stock.setDrawingCacheEnabled(true);
//        rec_scroll_stock.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
//        rec_scroll_stock.setAdapter(scrollStockAdapter);
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(0xFFCCCCCC);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
//        rec_scroll_stock.addItemDecoration(
//                new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());
        items = new ArrayList<ArticlesItem>();
        mAdapter = new FlipAdapter(getActivity(), items, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
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
                FintechvietSdk.getInstance().saveContentClick();
                FintechvietSdk.getInstance().contentClick(getDeviceSerialNumber(), items.get(position).getArticleId(), items.get(position).getRewardPoint(), new JCallback() {
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
        dataBinding();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            dataBinding();
        }
    }

    /**
     *
     */
    private void dataBinding() {
        FintechvietSdk.getInstance().getUserInfo(getDeviceSerialNumber(), new JCallback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    if (null != user) {
                        textViewEarn.setText(String.format("%s điểm", user.getEarning()));
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

        FintechvietSdk.getInstance().getArticlesResponse(getDeviceSerialNumber(), currentPage, newsId, new JCallback<ArticlesResponse>() {
            @Override
            public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
                if (response.code() == 200) {
                    items.clear();
                    final ArticlesResponse articlesResponse = response.body();
                    for (int i = 0; i < articlesResponse.getArticles().size(); i++) {
                        ArticlesItem articlesItem = articlesResponse.getArticles().get(i);
                        articlesItem.setDescription(StringUtils.abbreviate(articlesItem.getDescription(), 300));
                        items.add(articlesItem);
                        mAdapter.notifyDataSetChanged();
                    }
                    hideProcessing();
                } else {
                    hideProcessing();
                }
            }

            @Override
            public void onFailure(Call<ArticlesResponse> call, Throwable t) {
                hideProcessing();
            }
        });

        FintechvietSdk.getInstance().getPromotionMessage(getDeviceSerialNumber(), "PROMOTION",
                new JCallback<List<PromotionMessage>>() {
                    @Override
                    public void onResponse(Call<List<PromotionMessage>> call, Response<List<PromotionMessage>> response) {
                        if (response.code() == 200) {
                            List<PromotionMessage> listPromotionMessage = response.body();
                            if (null != listPromotionMessage && listPromotionMessage.size() > 0) {
                                PromotionMessage promotionMessage = listPromotionMessage.get(0);
                                DialogFragmentLotteryExpire dialogFragmentLotteryExpire = new DialogFragmentLotteryExpire();
                                Bundle args = new Bundle();
                                args.putSerializable("PROMOTION", promotionMessage);
                                dialogFragmentLotteryExpire.setArguments(args);
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                dialogFragmentLotteryExpire.show(fragmentTransaction, FullScreenDialog.TAG);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PromotionMessage>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        Log.d(LatestNewsHeadlinesFragment.class.getName(), "this fragment is now visible");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onPageRequested(int page) {
        mFlipView.smoothFlipTo(page);
    }

    private int random = 0;

    @Override
    public void onFlippedToPage(FlipView v, int position, long id) {
        Log.i("pageflip", "Page: " + position);
        if (items.get(position).getRewardPoint() > 0) {
            textViewReward.setText(String.format("+%d điểm", items.get(position).getRewardPoint()));
            textViewReward.setVisibility(View.VISIBLE);
        } else {
            textViewReward.setVisibility(View.GONE);
        }
        FintechvietSdk.getInstance().getUserInfo(getDeviceSerialNumber(), new JCallback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    if (null != user) {
                        textViewEarn.setText(String.format("%s điểm", user.getEarning()));
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
        if (position == (mAdapter.getCount() - 5)) {
            if ((currentPage * TOTAL_DOCUMENT_OF_PAGE) < 1000) {
                currentPage++;
                FintechvietSdk.getInstance().getArticlesResponse(getDeviceSerialNumber(), currentPage, newsId, new JCallback<ArticlesResponse>() {
                    @Override
                    public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
                        final ArticlesResponse articlesResponse = response.body();
                        for (int i = 0; i < articlesResponse.getArticles().size(); i++) {
                            ArticlesItem articlesItem = articlesResponse.getArticles().get(i);
                            articlesItem.setDescription(StringUtils.abbreviate(articlesItem.getDescription(), 300));
                            textViewEarn.setText(String.format("%s điểm", articlesItem.getEarning()));
                            items.add(articlesItem);
                            mAdapter.notifyDataSetChanged();
                        }
                        hideProcessing();
                    }

                    @Override
                    public void onFailure(Call<ArticlesResponse> call, Throwable t) {
                        hideProcessing();
                    }
                });
            }
        } else {

        }
        if(items.get(position).getType().equalsIgnoreCase("NEWS")){
            String typeAdmob = template(position);
            if (position > 0 && position % 3 == 0 /*&& position % 20 != 0*/) {
                if (!StringUtils.isBlank(typeAdmob)) {
                    FullScreenDialog dialog = new FullScreenDialog();
                    dialog.setTemplate(typeAdmob);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    dialog.show(ft, FullScreenDialog.TAG);
                }
            }
        }
//        else if (position > 0 && position % 20 == 0) {
//            random++;
//            if (random > 4) {
//                random = 1;
//            }
//            Log.i("pageflip", "random: " + random);
//            if (random == 4) {
////                Intent intent = new Intent();
////                intent.setClassName(getActivity(), CandyWheelActivity.class.getName());
////                startActivity(intent);
//            } else if (random == 2) {
////                Intent intent = new Intent();
////                intent.setClassName(getActivity(), Game2048Activity.class.getName());
////                startActivity(intent);
//            } else if (random == 3) {
////                Intent intent = new Intent();
////                intent.setClassName(getActivity(), FlappyBirdGameActivity.class.getName());
////                startActivity(intent);
//            } else if (random == 1) {
////                Intent intent = new Intent();
////                intent.setClassName(getActivity(), FlappyBirdGameActivity.class.getName());
////                startActivity(intent);
//            }
//        }
        FintechvietSdk.getInstance().saveContentImpression();
    }

    private String template(int number) {
//        if (number % 2 == 0) {
//            return "image";
//        } else {
            return "video";
//        }
    }

    @Override
    public void onOverFlip(FlipView v, OverFlipMode mode,
                           boolean overFlippingPrevious, float overFlipDistance,
                           float flipDistancePerPage) {
        Log.i("overflip", "overFlipDistance = " + overFlipDistance);
    }
}
