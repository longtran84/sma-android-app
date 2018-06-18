//package com.sma.mobile;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.DashPathEffect;
//import android.graphics.Paint;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.view.ViewPager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.LinearSmoothScroller;
//import android.support.v7.widget.RecyclerView;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RadioGroup;
//import android.widget.RelativeLayout;
//
//import com.fintechviet.android.sdk.FintechvietSdk;
//import com.fintechviet.android.sdk.listener.JCallback;
//import com.fintechviet.android.sdk.model.ArticlesItem;
//import com.fintechviet.android.sdk.model.ArticlesResponse;
//import com.fintechviet.android.sdk.model.PromotionMessage;
//import com.fintechviet.android.sdk.user.User;
//import com.floatingwidget.FloatingWidgetService;
//import com.google.gson.Gson;
////import com.guidebee.game.tutorial.flappybird.FlappyBirdGameActivity;
//import com.sma.mobile.activities.AbstractFragment;
//import com.sma.mobile.article.adapters.ViewPagerSlidingDrawerAdapter;
//import com.sma.mobile.autoscroll.OnItemClickListener;
//import com.sma.mobile.autoscroll.ScrollCustomAdapter;
//import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
//import com.sma.mobile.utils.firebasenotifications.Config;
//import com.thefinestartist.finestwebview.FinestWebView;
////import com.tpcstld.twozerogame.Game2048Activity;
//import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
//
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import cn.trinea.android.view.autoscrollviewpager.adapter.NewsArticleViewerPagerAdapter;
//import hollowsoft.slidingdrawer.OnDrawerOpenListener;
//import hollowsoft.slidingdrawer.SlidingDrawer;
//import retrofit2.Call;
//import retrofit2.Response;
//import se.emilsjolander.flipview.FlipAdapter;
//import se.emilsjolander.flipview.FlipView;
//import se.emilsjolander.flipview.OverFlipMode;
//
///**
// * Created by florentchampigny on 24/04/15.
// */
//public class AdMobRewardedFragment extends AbstractFragment implements FlipAdapter.Callback, FlipView.OnFlipListener, FlipView.OnOverFlipListener {
//
//    private static final boolean GRID_LAYOUT = false;
//    private static final int ITEM_COUNT = 8;
//    private final int TOTAL_DOCUMENT_OF_PAGE = 30;
//    private int currentPage = 1;
//    private String registrationToken = "";
//
//    @BindView(R.id.flip_view)
//    FlipView mFlipView;
//
//    @BindView(R.id.sliding_drawer_id)
//    SlidingDrawer slidingDrawer;
//
//    @BindView(R.id.linear_layout_handle_id)
//    RelativeLayout relativeLayout;
//
//    @BindView(R.id.view_pager_sliding_drawer_id)
//    ViewPager viewPagerSlidingDrawer;
//
//    @BindView(R.id.radio_group_sliding_drawer_id)
//    RadioGroup radioGroup;
//
//    private FlipAdapter mAdapter;
//    private List<ArticlesItem> items;
//    private String newsId;
//
//    @BindView(R.id.rec_scroll_stock)
//    RecyclerView rec_scroll_stock;
//
//    private List<ArticlesItem> listArticlesResponse;
//    private ScrollCustomAdapter scrollStockAdapter;
//    //new count added
//    int scrollCount = 0;
//
//    public static AdMobRewardedFragment newInstance() {
//        return new AdMobRewardedFragment();
//    }
//
//    //new auto scroll
//    public void autoScrollAnother() {
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
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_articles, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        ButterKnife.bind(this, view);
//        listArticlesResponse = new ArrayList<ArticlesItem>();
//        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
//        registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
//        scrollStockAdapter = new ScrollCustomAdapter(getActivity(), listArticlesResponse) {
//            @Override
//            public void load() {
//                stockListModels.addAll(stockListModels);
//            }
//        };
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
//        scrollStockAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(ArticlesItem item) {
//                new FinestWebView.Builder(getActivity()).theme(R.style.FinestWebViewTheme)
//                        .titleDefault("SMA")
//                        .toolbarScrollFlags(0)
//                        .statusBarColorRes(R.color.blackPrimaryDark)
//                        .toolbarColorRes(R.color.blackPrimary)
//                        .titleColorRes(R.color.finestWhite)
//                        .urlColorRes(R.color.blackPrimaryLight)
//                        .iconDefaultColorRes(R.color.finestWhite)
//                        .progressBarColorRes(R.color.finestWhite)
//                        .swipeRefreshColorRes(R.color.blackPrimaryDark)
//                        .menuSelector(R.drawable.selector_light_theme)
//                        .menuTextGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT)
//                        .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
//                        .dividerHeight(0)
//                        .gradientDivider(false)
//                        //                    .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
//                        .setCustomAnimations(R.anim.slide_left_in, R.anim.hold, R.anim.hold,
//                                R.anim.slide_right_out)
//                        //                    .setCustomAnimations(R.anim.fade_in_fast, R.anim.fade_out_medium, R.anim.fade_in_medium, R.anim.fade_out_fast)
//                        .disableIconBack(false)
//                        .disableIconClose(false)
//                        .disableIconForward(true)
//                        .disableIconMenu(true)
//                        .show(item.getSource());
//            }
//
//            @Override
//            public void onChildItemClick(ArticlesItem item) {
//
//            }
//        });
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
//
//            @Override
//            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
//                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {
//                    private static final float SPEED = 10000f;// Change this value (default=25f)
//
//                    @Override
//                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
//                        return SPEED / displayMetrics.densityDpi;
//                    }
//                };
//                smoothScroller.setTargetPosition(position);
//                startSmoothScroll(smoothScroller);
//            }
//        };
//        autoScrollAnother();
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rec_scroll_stock.setLayoutManager(layoutManager);
//        rec_scroll_stock.setHasFixedSize(true);
//        rec_scroll_stock.setItemViewCacheSize(3);
//        rec_scroll_stock.setDrawingCacheEnabled(true);
//        rec_scroll_stock.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
//        rec_scroll_stock.setAdapter(scrollStockAdapter);
//        Paint paint = new Paint();
//        paint.setStrokeWidth(1);
//        paint.setColor(0xFFCCCCCC);
//        paint.setAntiAlias(true);
//        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
//        rec_scroll_stock.addItemDecoration(
//                new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());
//
//        viewPagerSlidingDrawer.setAdapter(new ViewPagerSlidingDrawerAdapter(getActivity().getSupportFragmentManager()));
////        viewPagerSlidingDrawer.setOffscreenPageLimit(3);
//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (slidingDrawer.isOpened()) {
//                    radioGroup.clearCheck();
//                    slidingDrawer.close();
//                    slidingDrawer.unlock();
//                }
//            }
//        });
//        slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
//            @Override
//            public void onDrawerOpened() {
//                slidingDrawer.lock();
//                int radioButtonId = radioGroup.getChildAt(0).getId();
//                radioGroup.check(radioButtonId);
//            }
//        });
//        viewPagerSlidingDrawer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                int radioButtonId = radioGroup.getChildAt(position).getId();
//                radioGroup.check(radioButtonId);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        items = new ArrayList<ArticlesItem>();
//        mAdapter = new FlipAdapter(getActivity(), items, new RecyclerViewOnItemClickListener() {
//            @Override
//            public void onClick(View v, int position) {
//                new FinestWebView.Builder(getActivity()).theme(R.style.FinestWebViewTheme)
//                        .titleDefault("SMA")
//                        .toolbarScrollFlags(0)
//                        .statusBarColorRes(R.color.blackPrimaryDark)
//                        .toolbarColorRes(R.color.blackPrimary)
//                        .titleColorRes(R.color.finestWhite)
//                        .urlColorRes(R.color.blackPrimaryLight)
//                        .iconDefaultColorRes(R.color.finestWhite)
//                        .progressBarColorRes(R.color.finestWhite)
//                        .swipeRefreshColorRes(R.color.blackPrimaryDark)
//                        .menuSelector(R.drawable.selector_light_theme)
//                        .menuTextGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT)
//                        .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
//                        .dividerHeight(0)
//                        .gradientDivider(false)
//                        //                    .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
//                        .setCustomAnimations(R.anim.slide_left_in, R.anim.hold, R.anim.hold,
//                                R.anim.slide_right_out)
//                        //                    .setCustomAnimations(R.anim.fade_in_fast, R.anim.fade_out_medium, R.anim.fade_in_medium, R.anim.fade_out_fast)
//                        .disableIconBack(false)
//                        .disableIconClose(false)
//                        .disableIconForward(true)
//                        .disableIconMenu(true)
//                        .show(items.get(position).getSource());
//                FintechvietSdk.getInstance().saveContentClick();
//                SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
//                String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
//                FintechvietSdk.getInstance().updateUserRewardPoint(registrationToken, "READ", 10, new JCallback() {
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
//            }
//        });
//
//        mAdapter.setCallback(this);
//        mFlipView.setAdapter(mAdapter);
//        mFlipView.setOnFlipListener(this);
//        mFlipView.peakNext(false);
//        mFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
//        //mFlipView.setEmptyView(findViewById(R.id.empty_view));
//        mFlipView.setOnOverFlipListener(this);
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                slidingDrawer.open();
//                switch (checkedId) {
//                    case R.id.radio_button_news_shortcut_id:
//                        // do operations specific to this selection
//                        viewPagerSlidingDrawer.setCurrentItem(0);
//                        break;
//                    case R.id.radio_button_social_noti_shortcut_id:
//                        // do operations specific to this selection
//                        viewPagerSlidingDrawer.setCurrentItem(1);
//                        break;
//                    case R.id.radio_button_voucher_shortcut_id:
//                        // do operations specific to this selection
//                        viewPagerSlidingDrawer.setCurrentItem(2);
//                        break;
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        showProcessing();
//        FintechvietSdk.getInstance().getArticlesResponse(registrationToken, currentPage, newsId, new JCallback<ArticlesResponse>() {
//            @Override
//            public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
//                if (response.code() == 200) {
//                    final ArticlesResponse articlesResponse = response.body();
//                    FintechvietSdk.getInstance().getUserInfo(registrationToken, new JCallback<User>() {
//                        @Override
//                        public void onResponse(Call<User> call, Response<User> response) {
//                            if (response.code() == 200) {
//                                items.clear();
//                                mAdapter.notifyDataSetChanged();
//                                User user = response.body();
//                                if (null != user && articlesResponse.getArticles().size() > 0) {
//                                    for (ArticlesItem articlesItem : articlesResponse.getArticles()) {
//                                        articlesItem.setEarning(user.getEarning());
//                                        items.add(articlesItem);
//                                        mAdapter.notifyDataSetChanged();
//                                    }
//                                }
//                            }
//                            hideProcessing();
//                        }
//
//                        @Override
//                        public void onFailure(Call<User> call, Throwable t) {
//                            hideProcessing();
//                            t.printStackTrace();
//                        }
//                    });
//                } else {
//                    hideProcessing();
//                    getMaterialDialogAlertError(getActivity()).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArticlesResponse> call, Throwable t) {
//                hideProcessing();
//                getMaterialDialogAlertError(getActivity()).show();
//            }
//        });
//        FintechvietSdk.getInstance().getPromotionMessage(registrationToken, "PROMOTION",
//                new JCallback<List<PromotionMessage>>() {
//                    @Override
//                    public void onResponse(Call<List<PromotionMessage>> call, Response<List<PromotionMessage>> response) {
//                        if (response.code() == 200) {
//                            List<PromotionMessage> listPromotionMessage = response.body();
//                            if (null != listPromotionMessage && listPromotionMessage.size() > 0) {
//                                PromotionMessage promotionMessage = listPromotionMessage.get(0);
//                                DialogFragmentLotteryExpire dialogFragmentLotteryExpire = new DialogFragmentLotteryExpire();
//                                Bundle args = new Bundle();
//                                args.putSerializable("PROMOTION", promotionMessage);
//                                dialogFragmentLotteryExpire.setArguments(args);
//                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                                dialogFragmentLotteryExpire.show(fragmentTransaction, FullScreenDialog.TAG);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<PromotionMessage>> call, Throwable t) {
//
//                    }
//                });
//        Log.d(AdMobRewardedFragment.class.getName(), "this fragment is now visible");
//        if (null != slidingDrawer && slidingDrawer.isOpened() && null != radioGroup) {
//            radioGroup.clearCheck();
//            slidingDrawer.close();
//            slidingDrawer.unlock();
//        }
//        Log.d(AdMobRewardedFragment.class.getName(), "this fragment is now invisible");
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//    }
//
//    @Override
//    public void onPageRequested(int page) {
//        mFlipView.smoothFlipTo(page);
//    }
//
//    private int random = 0;
//
//    @Override
//    public void onFlippedToPage(FlipView v, int position, long id) {
//        Log.i("pageflip", "Page: " + position);
//        if (position == (mAdapter.getCount() - 5)) {
//            if ((currentPage * TOTAL_DOCUMENT_OF_PAGE) < 1000) {
//                currentPage++;
//                FintechvietSdk.getInstance().getArticlesResponse(registrationToken, currentPage, newsId, new JCallback<ArticlesResponse>() {
//                    @Override
//                    public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
//                        final ArticlesResponse articlesResponse = response.body();
//                        FintechvietSdk.getInstance().getUserInfo(registrationToken, new JCallback<User>() {
//                            @Override
//                            public void onResponse(Call<User> call, Response<User> response) {
//                                if (response.code() == 200) {
//                                    User user = response.body();
//                                    if (null != user && articlesResponse.getArticles().size() > 0) {
//                                        for (ArticlesItem articlesItem : articlesResponse.getArticles()) {
//                                            articlesItem.setEarning(user.getEarning());
//                                            items.add(articlesItem);
//                                            mAdapter.notifyDataSetChanged();
//                                        }
//                                    }
//                                }
//                                hideProcessing();
//                            }
//
//                            @Override
//                            public void onFailure(Call<User> call, Throwable t) {
//                                hideProcessing();
//                                t.printStackTrace();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onFailure(Call<ArticlesResponse> call, Throwable t) {
//
//                    }
//                });
//            }
//        } else {
//
//        }
//        String typeAdmob = template(Math.max(0, new Random().nextInt(4)));
//        if (position > 0 && position % 5 == 0 && position % 20 != 0) {
//            if (!StringUtils.isBlank(typeAdmob)) {
//                FullScreenDialog dialog = new FullScreenDialog();
//                dialog.setTemplate(typeAdmob);
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                dialog.show(ft, FullScreenDialog.TAG);
//            }
//        } else if (position > 0 && position % 20 == 0) {
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
//        FintechvietSdk.getInstance().saveContentImpression();
//    }
//
//    private String template(int number) {
//        if (number == 1 || number == 2 || number == 0) {
//            return "image";
//        } else {
//            return "video";
//        }
//    }
//
//    @Override
//    public void onOverFlip(FlipView v, OverFlipMode mode,
//                           boolean overFlippingPrevious, float overFlipDistance,
//                           float flipDistancePerPage) {
//        Log.i("overflip", "overFlipDistance = " + overFlipDistance);
//    }
//}
