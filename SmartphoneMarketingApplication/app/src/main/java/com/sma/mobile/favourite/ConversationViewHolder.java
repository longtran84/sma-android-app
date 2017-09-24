package com.sma.mobile.favourite;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sma.mobile.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longtran on 17/01/2017.
 */

public class ConversationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;


    @BindView(R.id.text_view_article_published_at)
    public TextView appCompatTextViewArticlePublishedAt;

    @BindView(R.id.text_view_article_title)
    public TextView appCompatTextViewTitle;

    @BindView(R.id.text_view_article_description)
    public HtmlTextView appCompatTextViewDescription;

    @BindView(R.id.image_view_article_image)
    public ImageView imageViewArticleImage;

    @BindView(R.id.text_view_article_comment)
    public TextView appCompatTextViewComment;

    public ConversationViewHolder(View itemView,
                                  RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
    }
}
