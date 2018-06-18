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
import com.fintechviet.android.sdk.model.VoucherResponse;
import com.sma.mobile.R;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.utils.viewholder.ProgressViewHolder;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by longtran on 17/01/2017.
 */

public class VoucherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<VoucherResponse> itemList;
    private Context context;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    /***
     * @param context
     * @param itemList
     */
    public VoucherAdapter(Context context, List<VoucherResponse> itemList,
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_voucher, parent, false);
            VoucherViewHolder viewHolder = new VoucherViewHolder(view, recyclerViewOnItemClickListener);
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
        if (holder instanceof VoucherViewHolder) {
            final VoucherViewHolder voucherViewHolder
                    = (VoucherViewHolder) holder;
            final VoucherResponse voucherResponse = itemList.get(position);
            if (null != voucherResponse) {
                final DecimalFormat formatter = new DecimalFormat("###,###,###.##");
                voucherViewHolder.textViewDescription.setText(voucherResponse.getName());
                voucherViewHolder.pointExchangeText.setText(voucherResponse.getPointExchangeText() + " điểm");
                voucherViewHolder.textViewPrice.setText(
                        String.format(
                                "%s đồng", formatter.format(voucherResponse.getPrice())
                        )
                );
                voucherViewHolder.textViewMarketPrice.setText(
                        String.format(
                                "%s đồng", formatter.format(voucherResponse.getMarketPrice())
                        )
                );
                BigDecimal discountTempSubtract = voucherResponse.getMarketPrice().subtract(voucherResponse.getPrice());
                BigDecimal discountTempDivide = discountTempSubtract.divide(voucherResponse.getMarketPrice(), 2, RoundingMode.FLOOR);
                BigDecimal discountTempMultiply = discountTempDivide.multiply(new BigDecimal(100)).setScale(0, RoundingMode.FLOOR);
                voucherViewHolder.textViewDiscount.setText("Giảm tới " + discountTempMultiply.toPlainString() + "%");
                if (!StringUtils.isBlank(voucherResponse.getPicture())) {
                    Glide.with(context)
                            .load(voucherResponse.getPicture())
                            .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
                                    new RoundedCornersTransformation(1, 0, RoundedCornersTransformation.CornerType.ALL))))
                            .into(voucherViewHolder.imageViewAppIcon);

                }
                if (voucherResponse.getQuantity() <= 0) {
                    voucherViewHolder.text_view_oversell.setText("HẾT");
                    voucherViewHolder.text_view_oversell.setVisibility(View.VISIBLE);
                } else {
                    voucherViewHolder.text_view_oversell.setText("Mua Ngay");
                    voucherViewHolder.text_view_oversell.setVisibility(View.VISIBLE);
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
