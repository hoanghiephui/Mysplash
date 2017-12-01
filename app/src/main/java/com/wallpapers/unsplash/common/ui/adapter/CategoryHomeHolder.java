package com.wallpapers.unsplash.common.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.data.entity.unsplash.CategoryItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hoanghiep on 11/27/17.
 */

public class CategoryHomeHolder extends BaseViewHolder<CategoryItem> implements CategoryHomeAdapter.CallBackAdapter {
    @BindView(R.id.item_photo_tag)
    RecyclerView recyclerView;
    @BindView(R.id.viewTag)
    RelativeLayout viewTag;

    private CallBackAdapter callBackAdapter;

    public CategoryHomeHolder(View itemView, Context context, CallBackAdapter callBackAdapter) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false));
        this.callBackAdapter = callBackAdapter;
    }

    @Override
    protected void onBindView(Context context, List<CategoryItem> categoryItems) {
        if (categoryItems != null && categoryItems.size() > 0) {
            recyclerView.setAdapter(new CategoryHomeAdapter(context, categoryItems, this));
            viewTag.setVisibility(View.VISIBLE);
        } else {
            viewTag.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onRecycled() {
        // do nothing.
    }

    public void setScrollListener(RecyclerView.OnScrollListener listener) {
        recyclerView.clearOnScrollListeners();
        recyclerView.addOnScrollListener(listener);
    }

    public void scrollTo(int x, int y) {
        recyclerView.scrollTo(x, y);
    }

    @Override
    public void onClickItem(String title) {
        callBackAdapter.onClickItem(title);
    }

    public interface CallBackAdapter {
        void onClickItem(String title);
    }
}
