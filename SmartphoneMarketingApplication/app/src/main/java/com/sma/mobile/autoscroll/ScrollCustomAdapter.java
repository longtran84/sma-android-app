package com.sma.mobile.autoscroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fintechviet.android.sdk.model.ArticlesItem;
import com.sma.mobile.R;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

/**
 * Created by BS058 on 12/18/2016.
 */

public abstract class ScrollCustomAdapter extends RecyclerView.Adapter<ScrollCustomAdapter.CustomViewHolder> {
    public List<ArticlesItem> stockListModels;
    private OnItemClickListener onItemClickListener;
    Context mContext;

    Random randomGenerator = new Random();
    float min = -0.5f;
    float max = 0.5f;
    DecimalFormat df = new DecimalFormat();

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ScrollCustomAdapter(Context context, List<ArticlesItem> stockListModels) {
        this.stockListModels = stockListModels;
        this.mContext = context;
    }

    public abstract void load();

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_histories, null);
        // RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //view.setLayoutParams(lp);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        if ((position >= getItemCount() - 1)) {
            load();
        } else {
            df.setMaximumFractionDigits(3);
            float percantage = randomGenerator.nextFloat() * (max - min) + min;
            holder.bind(stockListModels.get(position), onItemClickListener);
//            holder.txt_percantage_price.setText(df.format(percantage) + " %");
            holder.txt_stock_name.setText(stockListModels.get(position).getTitle().trim());

            if (percantage < 0) {
//                Picasso.with(mContext).load(R.mipmap.down).into(holder.img_up);
//                holder.txt_percantage_price.setTextColor(Color.parseColor("#EC3030"));
            } else {
//                Picasso.with(mContext).load(R.mipmap.up).into(holder.img_up);
//                holder.txt_percantage_price.setTextColor(Color.parseColor("#8DCF5F"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return stockListModels.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView txt_stock_name;


        public CustomViewHolder(View itemView) {
            super(itemView);
            this.txt_stock_name = (TextView) itemView.findViewById(R.id.txt_stock_name);
        }

        public void bind(final ArticlesItem item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
            this.txt_stock_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
