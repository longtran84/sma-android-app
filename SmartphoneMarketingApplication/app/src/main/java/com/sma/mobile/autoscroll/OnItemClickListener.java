package com.sma.mobile.autoscroll;

import com.fintechviet.android.sdk.model.ArticlesItem;

public interface OnItemClickListener {
    void onItemClick(ArticlesItem item);
    void onChildItemClick(ArticlesItem item);
}