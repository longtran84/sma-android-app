package com.sma.mobile.cashout.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.sma.mobile.R;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by QLong on 12/7/2017.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView> {

    private List<String> list;
    private Context context;


    public class MyView extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public MyView(View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.image_view_slide);

        }
    }


    public RecyclerViewAdapter(Context context, List<String> horizontalList) {
        this.context = context;
        this.list = horizontalList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slideshow_horizontal_item, parent, false);

        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {

        if (!StringUtils.isBlank(list.get(position))) {
            Glide.with(context)
                    .load(list.get(position))
                    .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.ALL))))
                    .into(holder.imageView);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
