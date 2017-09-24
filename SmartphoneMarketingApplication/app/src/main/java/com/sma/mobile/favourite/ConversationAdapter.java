package com.sma.mobile.favourite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fintechviet.android.sdk.model.Favourite;
import com.sma.mobile.R;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by longtran on 17/01/2017.
 */

public class ConversationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Favourite> itemList;
    private Context context;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    /***
     * @param context
     * @param itemList
     */
    public ConversationAdapter(Context context, List<Favourite> itemList,
                               RecyclerViewOnItemClickListener
                                       recyclerViewOnItemClickListener) {
        this.itemList = itemList;
        this.context = context;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card_small, parent, false);
            ConversationViewHolder viewHolder = new ConversationViewHolder(view, recyclerViewOnItemClickListener);
            return viewHolder;
        } else if (viewType == VIEW_TYPE_LOADING) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_progress_bar, parent, false);
//            ProgressViewHolder progressViewHolder = new ProgressViewHolder(view);
            return null;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof ConversationViewHolder) {
//            final ConversationViewHolder conversationViewHolder
//                    = (ConversationViewHolder) holder;
//            final Favourite favourite = itemList.get(position);
//            if (null != favourite) {
//                conversationViewHolder.appCompatTextViewSubject.setText("");
//                conversationViewHolder.appCompatTextViewContent.setText(favourite.getTitle());
//                conversationViewHolder.appCompatTextViewTime.setText(String.format("%d likes", new Random().nextInt(200)));
//                RequestOptions options = new RequestOptions();
//                options.centerCrop();
//                if(!StringUtils.isBlank(favourite.getUrlToImage())){
//                    Glide.with(context)
//                            .load(favourite.getUrlToImage())
//                            .apply(options)
//                            .into(conversationViewHolder.appCompatImageViewSender);
//
//                }
//            }
//        } else {
//
//        }


    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }
}
