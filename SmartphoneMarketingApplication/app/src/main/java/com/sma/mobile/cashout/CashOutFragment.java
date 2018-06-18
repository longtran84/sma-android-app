package com.sma.mobile.cashout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractFragment;
import com.sma.mobile.article.ArticlesActivity;
import com.sma.mobile.cashout.adapters.CashOutAdapter;
import com.sma.mobile.cashout.models.CashOut;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class CashOutFragment extends AbstractFragment {

    private static final boolean GRID_LAYOUT = false;
    private List<CashOut> items;
    private CashOutAdapter cashOutAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static CashOutFragment newInstance() {
        return new CashOutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cashout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        items = new ArrayList<CashOut>();
        String[] title = new String[]{
                /*"Nhận miễn phí",*/
                /*"Đổi điểm thành",*/
                "Đổi",
                "Đổi",
                "Đổi"
        };
        String[] desc = new String[]{
                /*"Mua quà tặng",*/
                "thẻ điện thọai",
                "thẻ game",
                "voucher"
        };
        String[] photoUrl = new String[]{
                /*"http://www.elleman.vn/wp-content/uploads/2015/12/15/qu%C3%A0-t%E1%BA%B7ng-gi%C3%A1ng-sinh-cho-n%C3%A0ng-heading-image-elleman.jpg",*/
                "http://laccoffee.com/wp-content/uploads/2017/05/The-cao-laccoffee.jpg",
                "http://dailygate.vn/uploads/article/dailygate-b44f6e890b.png",
                "http://www.vouchermienphi.vn/wp-content/uploads/2016/10/voucher-am-thuc-an-uong-8-767x445.jpg"
        };

        for (int i = 0; i < title.length; ++i) {
            CashOut news = new CashOut();
            news.setId(i);
            news.setName(title[i]);
            news.setDesc(desc[i]);
            news.setIcon(photoUrl[i]);
            items.add(news);
        }
        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(Color.TRANSPARENT);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());
        cashOutAdapter = new CashOutAdapter(getContext(), items, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                switch (items.get(position).getId()) {
                   /* case 0:
                        redirectGiftCodeActivity(getActivity());
                        break;*/
                    case 0:
                        redirectPhoneCardActivity(getActivity());
                        break;
                    case 1:
                        redirectGameCardActivity(getActivity());
                        break;
                    case 2:
                        redirectVoucherActivity(getActivity());
                        break;
                }
            }
        });
        mRecyclerView.setAdapter(cashOutAdapter);
    }

    /***
     *
     */
    private void redirectVoucherActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName(activity, VoucherActivity.class.getName());
        activity.startActivity(intent);
    }
    /***
     *
     */
    private void redirectPhoneCardActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName(activity, PhoneCardActivity.class.getName());
        activity.startActivity(intent);
    }
    /***
     *
     */
    private void redirectGameCardActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName(activity, GameCardActivity.class.getName());
        activity.startActivity(intent);
    }
    /***
     *
     */
    private void redirectGiftCodeActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName(activity, GiftCodeActivity.class.getName());
        activity.startActivity(intent);
    }
}
