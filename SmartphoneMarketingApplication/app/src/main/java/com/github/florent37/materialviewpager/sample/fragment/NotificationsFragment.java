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
public class NotificationsFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 7;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
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
                "Mã dự thưởng ngày 15/09/2017 của bạn là 23998",
                "Mã dự thưởng ngày 14/09/2017 của bạn là 13998",
                "Mã dự thưởng ngày 13/09/2017 của bạn là 33998",
                "Mã dự thưởng ngày 12/09/2017 của bạn là 63998",
                "Mã dự thưởng ngày 11/09/2017 của bạn là 73998",
                "Mã dự thưởng ngày 10/09/2017 của bạn là 83998",
                "Mã dự thưởng ngày 09/09/2017 của bạn là 31998"
        };
        String[] desc = new String[]{
                "Chương trình quay thưởng diến ra vào ngày 25/09/2017. Chơi và rinh quà ngay nào. ... còn nguyên vẹn, rõ ràng các thông tin về giải thưởng: mã số dự thưởng qua SMS, trúng thưởng chai miễn phí."
        };
        String[] photoUrl = new String[]{
                "https://cdn-images-1.medium.com/max/712/1*c3cQvYJrVezv_Az0CoDcbA.jpeg"
        };
        final List<News> items = new ArrayList<News>();

        for (int i = 0; i < ITEM_COUNT; ++i) {
            News news = new News(title[i], desc[0], photoUrl[0]);
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
