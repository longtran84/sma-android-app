package com.sma.mobile.article.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.fintechviet.android.sdk.model.ArticlesItem;
import com.sma.mobile.R;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.utils.viewholder.ProgressViewHolder;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by longtran on 17/01/2017.
 */

public class NewsArticleViewerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ArticlesItem> itemList;
    private Context context;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    /***
     * @param context
     * @param itemList
     */
    public NewsArticleViewerAdapter(Context context, List<ArticlesItem> itemList,
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_news_article_viewer, parent, false);
            NewsArticleViewerViewHolder viewHolder = new NewsArticleViewerViewHolder(view, recyclerViewOnItemClickListener);
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
        if (holder instanceof NewsArticleViewerViewHolder) {
            final NewsArticleViewerViewHolder conversationViewHolder
                    = (NewsArticleViewerViewHolder) holder;
            final ArticlesItem articlesItem = itemList.get(position);
            if (null != articlesItem) {
                if (!StringUtils.isBlank(articlesItem.getTitle())) {
                    conversationViewHolder.textViewTitle.setText(articlesItem.getTitle().trim());
                } else {
                    conversationViewHolder.textViewTitle.setText("");
                }
                if (!StringUtils.isBlank(articlesItem.getPublishDate())) {
                    conversationViewHolder.textViewPublishData.setText(articlesItem.getPublishDate().trim());
                } else {
                    conversationViewHolder.textViewPublishData.setText("");
                }
                if (!StringUtils.isBlank(articlesItem.getUrlToImage())) {
//                    RequestOptions requestOptions = new RequestOptions();
//                    requestOptions.override(250, 250);
//                    requestOptions.skipMemoryCache(true);
//                    requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
//                    requestOptions.error(R.drawable.loading_throbber);
//                    Glide.with(context)
//                            .load(articlesItem.getUrlToImage())
//                            .thumbnail( 0.5f )
//                            .apply(requestOptions)
//                            .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
//                                    new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL))))
//                            .into(conversationViewHolder.imageViewAppIcon);
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
