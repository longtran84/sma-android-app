//package com.sma.mobile;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.fintechviet.android.sdk.FintechvietSdk;
//import com.fintechviet.android.sdk.listener.JCallback;
//import com.fintechviet.android.sdk.model.ArticlesItem;
//import com.fintechviet.android.sdk.model.ArticlesResponse;
//import com.fintechviet.android.sdk.model.Favourite;
//import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
//import com.sma.mobile.utils.firebasenotifications.Config;
//import com.thefinestartist.finestwebview.FinestWebView;
//
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import io.realm.Realm;
//import io.realm.RealmResults;
//import retrofit2.Call;
//import retrofit2.Response;
//import se.emilsjolander.flipview.FlipAdapter;
//import se.emilsjolander.flipview.FlipView;
//import se.emilsjolander.flipview.OverFlipMode;
//
///**
// * Created by florentchampigny on 24/04/15.
// */
//public class RecyclerViewFragment extends Fragment implements FlipAdapter.Callback, FlipView.OnFlipListener, FlipView.OnOverFlipListener {
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
//    private FlipAdapter mAdapter;
//    private List<ArticlesItem> items;
//
//    public static RecyclerViewFragment newInstance() {
//        return new RecyclerViewFragment();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
//    }
//
//    /***
//     *
//     * @param list
//     * @param delim
//     * @return
//     */
//    private String join(List<Favourite> list, String delim) {
//        int len = list.size();
//        if (len == 0)
//            return "*";
//        StringBuilder sb = new StringBuilder();
//        if (list.size() > 0) {
//            for (int i = 0; i < len; i++) {
//                if (null != list.get(i) && list.get(i).isInterest()) {
//                    sb.append(list.get(i).getCode());
//                    sb.append(delim);
//                }
//            }
//        } else {
//            sb.append("*");
//        }
//        if (StringUtils.isBlank(sb)) {
//            sb.append("*");
//        }
//        return sb.toString();
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        ButterKnife.bind(this, view);
//        items = new ArrayList<ArticlesItem>();
//        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
//        registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
//        FintechvietSdk.getInstance().getArticlesResponse(registrationToken, currentPage, new JCallback<ArticlesResponse>() {
//            @Override
//            public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
//                ArticlesResponse articlesResponse = response.body();
//                for (ArticlesItem articlesItem : articlesResponse.getArticles()) {
//                    items.add(articlesItem);
//                    mAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArticlesResponse> call, Throwable t) {
//
//            }
//        });
//
//        mAdapter = new FlipAdapter(getActivity(), items, new RecyclerViewOnItemClickListener() {
//            @Override
//            public void onClick(View v, int position) {
//                FintechvietSdk.getInstance().contentClick(new JCallback() {
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
//                FintechvietSdk.getInstance().contentImpression(new JCallback() {
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
//                new FinestWebView.Builder(getActivity()).theme(R.style.FinestWebViewTheme)
//                        .titleDefault("Dribbble")
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
//            }
//        });
//        mAdapter.setCallback(this);
//        mFlipView.setAdapter(mAdapter);
//        mFlipView.setOnFlipListener(this);
//        mFlipView.peakNext(false);
//        mFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
//        //mFlipView.setEmptyView(findViewById(R.id.empty_view));
//        mFlipView.setOnOverFlipListener(this);
//
//    }
//
//    @Override
//    public void onPageRequested(int page) {
//        mFlipView.smoothFlipTo(page);
//    }
//
//    @Override
//    public void onFlippedToPage(FlipView v, int position, long id) {
//        Log.i("pageflip", "Page: " + position);
//        if (position == (mAdapter.getCount() - 1)) {
//            if ((currentPage * TOTAL_DOCUMENT_OF_PAGE) < 1000) {
//                currentPage++;
//                FintechvietSdk.getInstance().getArticlesResponse(registrationToken, currentPage, new JCallback<ArticlesResponse>() {
//                    @Override
//                    public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
//                        ArticlesResponse articlesResponse = response.body();
//                        for (ArticlesItem articlesItem : articlesResponse.getArticles()) {
//                            items.add(articlesItem);
//                            mAdapter.notifyDataSetChanged();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ArticlesResponse> call, Throwable t) {
//
//                    }
//                });
//            }
//        } else {
//            if (position > 0 && position % 10 == 0) {
//                if (!StringUtils.isBlank(template(position))) {
//                    FullScreenDialog dialog = new FullScreenDialog();
//                    dialog.setTemplate(template(position));
//                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                    dialog.show(ft, FullScreenDialog.TAG);
//                }
//            }
//        }
//    }
//
//    private String template(int number) {
//        if (number % 20 != 0 && number % 10 == 0) {
//            return "image";
//        } else if (number % 20 == 0) {
//            return "video";
//        }
//        return null;
//    }
//
//    @Override
//    public void onOverFlip(FlipView v, OverFlipMode mode,
//                           boolean overFlippingPrevious, float overFlipDistance,
//                           float flipDistancePerPage) {
//        Log.i("overflip", "overFlipDistance = " + overFlipDistance);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            currentPage = 1;
//            items.clear();
//            mAdapter.notifyDataSetChanged();
//            FintechvietSdk.getInstance().getArticlesResponse(registrationToken, currentPage, new JCallback<ArticlesResponse>() {
//                @Override
//                public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
//                    ArticlesResponse articlesResponse = response.body();
//                    for (ArticlesItem articlesItem : articlesResponse.getArticles()) {
//                        items.add(articlesItem);
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ArticlesResponse> call, Throwable t) {
//
//                }
//            });
//        }
//    }
//}