package se.emilsjolander.flipview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.ad.Content;
import com.fintechviet.android.sdk.ad.Decision;
import com.fintechviet.android.sdk.ad.DecisionResponse;
import com.fintechviet.android.sdk.model.ArticlesItem;
import com.google.gson.Gson;
import com.sma.mobile.R;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.thefinestartist.finestwebview.FinestWebView;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.List;

public class FlipAdapter extends BaseAdapter implements OnClickListener {

    private Context context;

    public interface Callback {
        public void onPageRequested(int page);
    }


    private LayoutInflater inflater;
    private Callback callback;
    private List<ArticlesItem> items;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public FlipAdapter(Context context, List<ArticlesItem> items, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.items = items;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.page, parent, false);
            holder.textViewArticleTitle = (AppCompatTextView) convertView.findViewById(R.id.text_view_article_title);
            holder.textViewArticleDescription = (AppCompatTextView) convertView.findViewById(R.id.text_view_article_description);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.view_click_listener_id);
            holder.textViewEarn = (AppCompatTextView) convertView.findViewById(R.id.text_view_earn);
            holder.imageViewAdmob = (ImageView) convertView.findViewById(R.id.image_view_admob);
            holder.textViewPublishDate = (AppCompatTextView) convertView.findViewById(R.id.text_view_publish_date);
            holder.textViewAdvertising = (AppCompatTextView) convertView.findViewById(R.id.text_view_publish_advertising);
            holder.relativeLayoutAdvertising = (RelativeLayout) convertView.findViewById(R.id.layout_advertising);
            holder.imageViewCloseAdmob = (ImageView) convertView.findViewById(R.id.image_view_close_advertising_id);
            holder.textViewAdvertisingClick = (AppCompatTextView) convertView.findViewById(R.id.text_view_publish_advertising);
            holder.imageViewMobileAdvertisingImageFull = (ImageView) convertView.findViewById(R.id.image_view_mobile_advertising_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ArticlesItem articlesItem = items.get(position);
        holder.imageViewCloseAdmob.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.relativeLayoutAdvertising.setVisibility(View.GONE);
            }
        });
        holder.textViewArticleTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewOnItemClickListener.onClick(v, position);
            }
        });
        holder.textViewArticleDescription.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewOnItemClickListener.onClick(v, position);
            }
        });
        if (!StringUtils.isBlank(articlesItem.getTitle())) {
            holder.textViewArticleTitle.setText(articlesItem.getTitle().trim());
        } else {
            holder.textViewArticleTitle.setText("");
        }
        if (!StringUtils.isBlank(articlesItem.getDescription())) {
            holder.textViewArticleDescription.setVisibility(View.VISIBLE);
            holder.textViewArticleDescription.setText(articlesItem.getDescription().trim());
        } else {
            holder.textViewArticleDescription.setText("");
            holder.textViewArticleDescription.setVisibility(View.GONE);
        }
        if (!StringUtils.isBlank(articlesItem.getPublishDate())) {
            holder.textViewPublishDate.setText(articlesItem.getPublishDate().trim());
            holder.textViewPublishDate.setVisibility(View.VISIBLE);
        } else {
            holder.textViewPublishDate.setVisibility(View.GONE);
            holder.textViewPublishDate.setText("");
        }
        //final DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        //holder.textViewEarn.setText(String.format("%s đ", articlesItem.getEarning()/*formatter.format(items.get(position).getEarning())*/));
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .dontAnimate()
                .dontTransform()
                .skipMemoryCache(true)
                .error(R.drawable.unchecked_drawable)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(articlesItem.getUrlToImage())
                .thumbnail(0.5f)
                .apply(options)
                .into(holder.imageView);
        try {
            final DecisionResponse decisionResponse = articlesItem.getDecisionResponse();
            final Decision decision = decisionResponse.getDecision();
            final Content content = decision.getContent();
            holder.textViewAdvertising.setText(content.getBody());
            Glide.with(context)
                    .load(content.getImageUrl())
                    .apply(options)
                    .into(holder.imageViewAdmob);
            Glide.with(context)
                    .load(content.getImageAdUrl())
                    .apply(options)
                    .into(holder.imageViewMobileAdvertisingImageFull);
            holder.imageViewMobileAdvertisingImageFull.setVisibility(View.GONE);
            if (!StringUtils.isBlank(content.getImageAdUrl())) {
                holder.imageViewMobileAdvertisingImageFull.setVisibility(View.VISIBLE);
                holder.imageViewMobileAdvertisingImageFull.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new FinestWebView.Builder(context).theme(R.style.FinestWebViewTheme)
                                .titleDefault("SMA")
                                .toolbarScrollFlags(0)
                                .statusBarColorRes(R.color.blackPrimaryDark)
                                .toolbarColorRes(R.color.blackPrimary)
                                .titleColorRes(R.color.finestWhite)
                                .urlColorRes(R.color.blackPrimaryLight)
                                .iconDefaultColorRes(R.color.finestWhite)
                                .progressBarColorRes(R.color.finestWhite)
                                .swipeRefreshColorRes(R.color.blackPrimaryDark)
                                .menuSelector(R.drawable.selector_light_theme)
                                .menuTextGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT)
                                .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                                .dividerHeight(0)
                                .gradientDivider(false)
                                .setCustomAnimations(R.anim.slide_left_in, R.anim.hold, R.anim.hold, R.anim.slide_right_out)
                                .disableIconBack(false)
                                .disableIconClose(false)
                                .disableIconForward(true)
                                .disableIconMenu(true)
                                .show(decision.getClickUrl());
                    }
                });
            } else {
                holder.imageViewMobileAdvertisingImageFull.setOnClickListener(null);
                holder.imageViewMobileAdvertisingImageFull.setVisibility(View.GONE);
            }
            holder.textViewAdvertisingClick.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FinestWebView.Builder(context).theme(R.style.FinestWebViewTheme)
                            .titleDefault("SMA")
                            .toolbarScrollFlags(0)
                            .statusBarColorRes(R.color.blackPrimaryDark)
                            .toolbarColorRes(R.color.blackPrimary)
                            .titleColorRes(R.color.finestWhite)
                            .urlColorRes(R.color.blackPrimaryLight)
                            .iconDefaultColorRes(R.color.finestWhite)
                            .progressBarColorRes(R.color.finestWhite)
                            .swipeRefreshColorRes(R.color.blackPrimaryDark)
                            .menuSelector(R.drawable.selector_light_theme)
                            .menuTextGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT)
                            .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                            .dividerHeight(0)
                            .gradientDivider(false)
                            .setCustomAnimations(R.anim.slide_left_in, R.anim.hold, R.anim.hold,
                                    R.anim.slide_right_out)
                            .disableIconBack(false)
                            .disableIconClose(false)
                            .disableIconForward(true)
                            .disableIconMenu(true)
                            .show(decision.getClickUrl());
                    FintechvietSdk.getInstance().saveAdActivity(decision.getTrackingUrl());
                }
            });
            holder.imageViewAdmob.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FinestWebView.Builder(context).theme(R.style.FinestWebViewTheme)
                            .titleDefault("SMA")
                            .toolbarScrollFlags(0)
                            .statusBarColorRes(R.color.blackPrimaryDark)
                            .toolbarColorRes(R.color.blackPrimary)
                            .titleColorRes(R.color.finestWhite)
                            .urlColorRes(R.color.blackPrimaryLight)
                            .iconDefaultColorRes(R.color.finestWhite)
                            .progressBarColorRes(R.color.finestWhite)
                            .swipeRefreshColorRes(R.color.blackPrimaryDark)
                            .menuSelector(R.drawable.selector_light_theme)
                            .menuTextGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT)
                            .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                            .dividerHeight(0)
                            .gradientDivider(false)
                            .setCustomAnimations(R.anim.slide_left_in, R.anim.hold, R.anim.hold,
                                    R.anim.slide_right_out)
                            .disableIconBack(false)
                            .disableIconClose(false)
                            .disableIconForward(true)
                            .disableIconMenu(true)
                            .show(decision.getClickUrl());
                    FintechvietSdk.getInstance().saveAdActivity(decision.getTrackingUrl());
                }
            });
            holder.relativeLayoutAdvertising.setVisibility(View.VISIBLE);
        } catch (Exception exception) {
            holder.relativeLayoutAdvertising.setVisibility(View.GONE);
            holder.imageViewMobileAdvertisingImageFull.setOnClickListener(null);
            holder.imageViewMobileAdvertisingImageFull.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        AppCompatTextView textViewArticleTitle;
        AppCompatTextView textViewArticleDescription;
        ImageView imageView;
        LinearLayout linearLayout;
        AppCompatTextView textViewEarn;
        ImageView imageViewAdmob;
        TextView textViewAdvertisingClick;
        TextView textViewAdvertising;
        AppCompatTextView textViewPublishDate;
        RelativeLayout relativeLayoutAdvertising;
        ImageView imageViewCloseAdmob;
        ImageView imageViewMobileAdvertisingImageFull;
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.first_page:
//                if (callback != null) {
//                    callback.onPageRequested(0);
//                }
//                break;
//            case R.id.last_page:
//                if (callback != null) {
//                    callback.onPageRequested(getCount() - 1);
//                }
//                break;
//        }
    }

//    public void addItems(int amount) {
//        for (int i = 0; i < amount; i++) {
//            items.add(new Item());
//        }
//        notifyDataSetChanged();
//    }
//
//    public void addItemsBefore(int amount) {
//        for (int i = 0; i < amount; i++) {
//            items.add(0, new Item());
//        }
//        notifyDataSetChanged();
//    }

}
