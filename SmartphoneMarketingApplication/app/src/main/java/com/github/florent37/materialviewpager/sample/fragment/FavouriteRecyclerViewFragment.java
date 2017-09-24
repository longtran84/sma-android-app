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

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.Favourite;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.sma.mobile.R;
import com.sma.mobile.favourite.ConversationAdapter;
import com.sma.mobile.favourite.News;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class FavouriteRecyclerViewFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 20;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private ConversationAdapter conversationAdapter;

    public static FavouriteRecyclerViewFragment newInstance() {
        return new FavouriteRecyclerViewFragment();
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
                "Tin địa phương",
                "Thời sự - tin hot",
                "Thể thao", "Showbiz",
                "Công nghê",
                "Sức khỏe",
                "Người đẹp",
                "Hài =))",
                "Độc & Lạ",
                "Ẩm thực",
                "Du lịch",
                "Thời trang - Make up",
                "Mẹo vặt - DIY",
                "Nhạc",
                "Sách - Truyện",
                "Tâm sự - Tình Yêu",
                "Phim",
                "Chiêm tinh - Quizz",
                "Game",
                "Xe"};
        String[] desc = new String[]{"Tin địa phương", "Thời sự - tin hot", "Thể thao", "Showbiz", "Công nghê", "Sức khỏe", "Người đẹp", "Hài =))",
                "Độc & Lạ", "Ẩm thực", "Du lịch", "Thời trang - Make up", "Mẹo vặt - DIY", "Nhạc", "Sách - Truyện", "Tâm sự - Tình Yêu",
                "Phim", "Chiêm tinh - Quizz", "Game", "Xe"};
        String[] photoUrl = new String[]{
                "http://image.24h.com.vn/upload/3-2017/images/2017-09-17/1505633233-anh-1-1505625888602.jpg",
                "http://image.24h.com.vn/upload/3-2017/images/2017-09-17/1505610100-150560995680049-2-bat-tho.jpg",
                "http://image.24h.com.vn/upload/3-2017/images/2017-09-17/1505641628-arsenal-v-afc-bournemouth-premier-league.jpg",
                "http://anh.24h.com.vn/upload/4-2016/images/2016-11-08/1478571145-minh-thu-4.jpg",
                "http://image.24h.com.vn/upload/3-2017/images/2017-09-15/1505410510-15053951577847-1.jpg",
                "http://image.24h.com.vn/upload/3-2017/images/2017-09-17/1505621296-1.jpg",
                "http://anh.24h.com.vn/upload/1-2016/images/2016-03-08/1457403677-1457086663-12779148_1116255175051804_1125152726833277093_o.jpg",
                "http://image.24h.com.vn/upload/3-2017/images/2017-09-17/1505641708-xit-son-den.jpg",
                "http://image.24h.com.vn/upload/3-2017/images/2017-09-15/1505466141-150544641842718-anh-3.jpg",
                "http://image.24h.com.vn/upload/3-2017/images/2017-09-15/1505483957-150548358715221-ct4.jpg",
                "http://image.24h.com.vn/upload/3-2017/images/2017-08-21/1503316469-150313323971378-1.jpg",
                "http://image.24h.com.vn/upload/3-2017/images/2017-09-17/1505615453-150529362830062-d4-1819.jpg",
                "https://i2.wp.com/thongtincamau.com/wp-content/uploads/2017/03/80-meovat.jpg",
                "http://www.lienkhucnhac.net/data/upload/LKDamCuoi-1461339837-.png",
                "http://thanhngutienganh.com/uploads/001-thanhngutienganh/1-Thanh-ngu/Nhung-cham-ngon-y-nghia-ve-sach/Nhung-cham-ngon-y-nghia-ve-sach-1.jpg",
                "http://2.bp.blogspot.com/-nvEYzOY_yk0/VdivxRdrPXI/AAAAAAAAEio/Q77Se3MfxBU/s1600/11903722_10153578385467733_1590506384939997396_n.jpg",
                "https://dantricdn.com/k:7a7517334e/2016/01/19/121-1453175977538/10-phim-vo-thuat-kinh-dien-cua-dien-anh-hong-kong.jpg",
                "http://phongthuytuongminh.com/sites/default/files/languages/chiem_tinh_3.jpg",
                "http://cdn.addictinggames.com/newGames/game-links/must-a-mine-dupe-links/must-a-mine-dupe-links.png?c=40",
                "http://media.doisongphapluat.com/127/2015/8/25/nhung-chiec-xe-oto-dep-nhat-the-gioi7.jpg"
        };
        final List<Favourite> items = new ArrayList<Favourite>();

        for (int i = 0; i < ITEM_COUNT; ++i) {
            News news = new News(title[i], desc[i], photoUrl[i]);
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
        conversationAdapter = new ConversationAdapter(getContext(), items, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        mRecyclerView.setAdapter(conversationAdapter);

        FintechvietSdk.getInstance().getListFavourite("123123123213", new JCallback<List<Favourite>>() {
            @Override
            public void onResponse(Call<List<Favourite>> call, Response<List<Favourite>> response) {
                List<Favourite> listFavourite = response.body();
                for(Favourite favourite : listFavourite){
                    items.add(favourite);
                    conversationAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Favourite>> call, Throwable t) {

            }
        });
    }
}
