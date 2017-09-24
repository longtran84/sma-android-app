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
public class CardReceiptFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 1;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static CardReceiptFragment newInstance() {
        return new CardReceiptFragment();
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
                "Nơi lưu các thẻ điện thoại, thẻ game...mà bạn đổi được điểm thành công"
        };
        String[] desc = new String[]{
                "Nơi lưu các thẻ điện thoại, thẻ game...mà bạn đổi được điểm thành công"
        };
        String[] photoUrl = new String[]{
                "https://i.pinimg.com/originals/df/65/12/df6512bdebad16f6f8511b87b7d5f576.jpg"
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
