package com.sma.mobile.messages.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fintechviet.android.sdk.ad.AdMob;
import com.fintechviet.android.sdk.model.Message;
import com.sma.mobile.R;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.utils.viewholder.ProgressViewHolder;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by longtran on 17/01/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> itemList;
    private Context context;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    /***
     * @param context
     * @param itemList
     */
    public MessageAdapter(Context context, List<Message> itemList,
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_message, parent, false);
            MessageViewHolder viewHolder = new MessageViewHolder(view, recyclerViewOnItemClickListener);
            return viewHolder;
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_progress_bar, parent, false);
            ProgressViewHolder progressViewHolder = new ProgressViewHolder(view);
            return progressViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MessageViewHolder) {
            final MessageViewHolder conversationViewHolder
                    = (MessageViewHolder) holder;
            final Message message = itemList.get(position);
            if (null != message) {
                conversationViewHolder.textViewDescription.setText(message.getSubject());
                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                String dateText = df2.format(new Date(message.getCreatedDate()));
                conversationViewHolder.textViewDate.setText(dateText);
                if (message.getRead() == 0) {
                    conversationViewHolder.textViewDescription.setTypeface(null, Typeface.BOLD);
                    conversationViewHolder.textViewDate.setTypeface(null, Typeface.BOLD);
                    conversationViewHolder.textViewDescription.setTextColor(0xFF000000);
                    conversationViewHolder.textViewDate.setTextColor(0xFF000000);
                    conversationViewHolder.viewBulletPoints.setVisibility(View.VISIBLE);
                } else {
                    conversationViewHolder.textViewDescription.setTypeface(null, Typeface.NORMAL);
                    conversationViewHolder.textViewDate.setTypeface(null, Typeface.NORMAL);
                    conversationViewHolder.textViewDescription.setTextColor(0x66000000);
                    conversationViewHolder.textViewDate.setTextColor(0x66000000);
                    conversationViewHolder.viewBulletPoints.setVisibility(View.INVISIBLE);
                }
            }
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }
}
