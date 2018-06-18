/*
 *
 */
package cn.trinea.android.view.autoscrollviewpager.adapter;

/**
 * Created by QLong on 10/6/2017.
 */

import java.util.List;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.fintechviet.android.sdk.model.ArticlesItem;
import com.floatingwidget.R;
import com.jakewharton.salvage.RecyclingPagerAdapter;

import org.apache.commons.lang3.StringUtils;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * ImagePagerAdapter
 *
 * @author longtq
 */
public class NewsArticleViewerPagerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private List<ArticlesItem> imageIdList;
    private LayoutInflater inflater;

    private int size;
    private boolean isInfiniteLoop;

    public NewsArticleViewerPagerAdapter(Context context, List<ArticlesItem> imageIdList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.imageIdList = imageIdList;
        this.size = imageIdList.size();
        isInfiniteLoop = true;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    /**
     * @param context
     */
    private void redirectArticlesActivity(Context context) {
//        Intent intent = new Intent();
//        intent.setClassName(context, "com.sma.mobile.home.HomeActivity");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
////        intent.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED +
////                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD +
////                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON +
////                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
//        intent.putExtra("KEY_NAME_RESULT", 2);
//        context.startActivity(intent);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup container) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(com.floatingwidget.R.layout.view_pager_news_article_viewer_row, null);
            holder = new ViewHolder();
            holder.imageViewAppIcon = (ImageView) convertView.findViewById(com.floatingwidget.R.id.image_view_app_icon_id);
            holder.textViewTitle = (TextView) convertView.findViewById(com.floatingwidget.R.id.text_view_title);
            //holder.textViewDescription = (TextView) convertView.findViewById(com.floatingwidget.R.id.text_view_description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (imageIdList.size() > 0 && position < imageIdList.size()) {

            final ArticlesItem articlesItem = imageIdList.get(getPosition(position));
            if (null != articlesItem) {
                holder.textViewTitle.setText(articlesItem.getTitle().trim());
                //holder.textViewDescription.setText(articlesItem.getDescription());
                if (!StringUtils.isBlank(articlesItem.getUrlToImage())) {
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.override(600, 600);
                    requestOptions.skipMemoryCache(true);
                    requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
                    requestOptions.error(R.drawable.news_icon);
                    Glide.with(context)
                            .load(articlesItem.getUrlToImage())
                            .apply(requestOptions)
                            .into(holder.imageViewAppIcon);

                }
            }
        } else {

        }
        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                redirectArticlesActivity(context);
                Log.i("TAG", "This page was clicked: " + position);
            }
        });
        return convertView;
    }

    private static class ViewHolder {

        public ImageView imageViewAppIcon;

        public TextView textViewTitle;

        public TextView textViewDescription;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public NewsArticleViewerPagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
