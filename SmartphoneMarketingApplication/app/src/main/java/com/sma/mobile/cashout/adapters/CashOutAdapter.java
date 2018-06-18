package com.sma.mobile.cashout.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.sma.mobile.R;
import com.sma.mobile.cashout.models.CashOut;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.utils.viewholder.ProgressViewHolder;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by longtran on 17/01/2017.
 */

public class CashOutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CashOut> itemList;
    private Context context;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    /***
     * @param context
     * @param itemList
     */
    public CashOutAdapter(Context context, List<CashOut> itemList,
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_cash_out, parent, false);
            CashOutViewHolder viewHolder = new CashOutViewHolder(view, recyclerViewOnItemClickListener);
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
        if (holder instanceof CashOutViewHolder) {
            final CashOutViewHolder conversationViewHolder
                    = (CashOutViewHolder) holder;
            final CashOut cashOut = itemList.get(position);
            if (null != cashOut) {
                conversationViewHolder.textViewDescription.setText(cashOut.getName() + " " + cashOut.getDesc());
                if (!StringUtils.isBlank(cashOut.getIcon())) {
                    Glide.with(context)
                            .load(cashOut.getIcon())
                            .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
                                    new RoundedCornersTransformation(1, 0, RoundedCornersTransformation.CornerType.ALL))))
                            .into(conversationViewHolder.imageViewAppIcon);

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
