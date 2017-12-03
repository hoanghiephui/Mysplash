package com.wallpapers.unsplash.common._basic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallpapers.unsplash.R;

/**
 * Footer adapter.
 *
 * A RecyclerView.Adapter class with a footer view holder. By extending this adapter, child can
 * adapt footer view for RecyclerView more easily.
 *
 * */

public abstract class FooterAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public static final int HEADER_TYPE = 0;
    public static final int CONTENT_TYPE = 1;
    public static final int FOODER_TYPE = 2;
    public static final int ADS = 3;
    protected boolean isHasFooder = true;
    protected boolean isHasHeader = true;

    protected abstract boolean hasFooter();

    protected boolean isFooter(int position) {
        return hasFooter() && position == getItemCount() - 1;
    }


    public boolean isHasFooder() {
        return isHasFooder;
    }

    public void setHasFooder(boolean hasFooder) {
        if (isHasFooder == hasFooder) {
            return;
        }
        isHasFooder = hasFooder;
        notifyDataSetChanged();
    }

    public boolean isHasHeader() {
        return isHasHeader;
    }

    public void setHasHeader(boolean hasHeader) {
        if (isHasHeader == hasHeader) {
            return;
        }
        isHasHeader = hasHeader;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return getRealItemCount() + (isHasFooder ? 1 : 0) + (isHasHeader ? 1 : 0);
    }

    public abstract int getRealItemCount();

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && isHasHeader) {
            return HEADER_TYPE;
        }
        if (position == getItemCount() - 1 && isHasFooder) {
            return FOODER_TYPE;
        }
        return CONTENT_TYPE;
    }

    /**
     * Basic ViewHolder for {@link FooterAdapter}. This holder is used to fill the location of
     * navigation bar.
     */
    protected static class FooterHolder extends RecyclerView.ViewHolder {

        private FooterHolder(View itemView) {
            super(itemView);
        }

        public static FooterHolder buildInstance(ViewGroup parent) {
            return new FooterHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false));
        }
    }
}

