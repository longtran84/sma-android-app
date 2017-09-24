package com.github.florent37.materialviewpager.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.sma.mobile.R;
import com.sma.mobile.favourite.News;
import com.sma.mobile.favourite.NewsAdapter;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class PointConverterRecyclerViewFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 4;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static PointConverterRecyclerViewFragment newInstance() {
        return new PointConverterRecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        String[] title = new String[]{
                "Nhận miễn phí",
                "Đổi điểm thành",
                "Đổi điểm thành",
                "Đổi điểm thành"
        };
        String[] desc = new String[]{
                "Mã quà tặng",
                "Thẻ điện thọai",
                "Thẻ game",
                "Voucher"
        };
        String[] photoUrl = new String[]{
                "http://www.elleman.vn/wp-content/uploads/2015/12/15/qu%C3%A0-t%E1%BA%B7ng-gi%C3%A1ng-sinh-cho-n%C3%A0ng-heading-image-elleman.jpg",
                "http://laccoffee.com/wp-content/uploads/2017/05/The-cao-laccoffee.jpg",
                "http://dailygate.vn/uploads/article/dailygate-b44f6e890b.png",
                "http://www.vouchermienphi.vn/wp-content/uploads/2016/10/voucher-am-thuc-an-uong-8-767x445.jpg"
        };
        final List<News> items = new ArrayList<News>();

        for (int i = 0; i < ITEM_COUNT; ++i) {
            News news = new News(title[i], desc[i], photoUrl[i]);
            items.add(news);
        }


        //setup materialviewpager

        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);

        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
//        mRecyclerView.setAdapter(new NewsAdapter(getContext(), items, new RecyclerViewOnItemClickListener() {
//            @Override
//            public void onClick(View v, int position) {
//
//            }
//        }));
    }
}
