package com.wallpapers.unsplash.common.ui.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.ui.widget.freedomSizeView.FreedomImageView;
import com.wallpapers.unsplash.common.utils.DisplayUtils;
import com.wallpapers.unsplash.photo.view.activity.PhotoActivity;
import com.wallpapers.unsplash.photo.view.holder.BaseHolder;
import com.wallpapers.unsplash.photo.view.holder.BaseLandscapeHolder;
import com.wallpapers.unsplash.photo.view.holder.ExifHolder;
import com.wallpapers.unsplash.photo.view.holder.MoreHolder;
import com.wallpapers.unsplash.photo.view.holder.MoreLandscapeHolder;
import com.wallpapers.unsplash.photo.view.holder.ProgressHolder;
import com.wallpapers.unsplash.photo.view.holder.StoryHolder;
import com.wallpapers.unsplash.photo.view.holder.TagHolder;
import com.wallpapers.unsplash.photo.view.holder.TouchHolder;
import com.wallpapers.unsplash.photo.view.holder.TouchLandscapeHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Photo info adapter.
 * <p>
 * Adapter for {@link RecyclerView} to show details of photoActivity photo.
 */

public class PhotoInfoAdapter extends RecyclerView.Adapter<PhotoInfoAdapter.ViewHolder> {

    private PhotoActivity photoActivity;
    private OnScrollListener tagListener, moreListener;

    private Photo photo;
    private List<Integer> typeList; // information of view holder.

    private boolean complete; // if true, means the photo object is completely. (has data like exif)
    private boolean needShowInitAnim; // need do the initialize animation when first bind basic view.
    private ViewHolder holder;

    private MoreHolder.MoreHolderModel moreHolderModel;

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        protected abstract void onBindView(PhotoActivity a, Photo photo);

        protected void updateData(Photo photo){}

        protected abstract void onRecycled();
    }

    public static class SpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

        private PhotoInfoAdapter adapter;
        private int columnCount;

        public SpanSizeLookup(PhotoInfoAdapter adapter, int columnCount) {
            this.adapter = adapter;
            this.columnCount = columnCount;
        }

        @Override
        public int getSpanSize(int position) {
            if (adapter.typeList.get(position) >= ExifHolder.TYPE_EXIF) {
                return 1;
            } else {
                return columnCount;
            }
        }
    }

    public PhotoInfoAdapter(PhotoActivity photoActivity, Photo photo) {
        this.photoActivity = photoActivity;
        this.tagListener = new OnScrollListener();
        this.moreListener = new OnScrollListener();
        this.photo = photo;
        this.complete = photo != null && photo.complete;
        resetNeedShowInitAnimFlag();
        this.moreHolderModel = null;
        buildTypeList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TouchHolder.TYPE_TOUCH:
                return new TouchHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_photo_touch, parent, false));

            case TouchLandscapeHolder.TYPE_TOUCH_LANDSCAPE:
                return new TouchLandscapeHolder(
                        photoActivity,
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_photo_touch_landscape, parent, false));

            case BaseHolder.TYPE_BASE:
                return new BaseHolder(
                        photoActivity,
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_photo_base, parent, false));

            case BaseLandscapeHolder.TYPE_BASE_LANDSCAPE:
                return new BaseLandscapeHolder(
                        photoActivity,
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_photo_base_landscape, parent, false));

            case ProgressHolder.TYPE_PROGRESS:
                return new ProgressHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_photo_progress, parent, false));

            case StoryHolder.TYPE_STORY:
                return new StoryHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_photo_story, parent, false));

            case TagHolder.TYPE_TAG:
                return new TagHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_photo_tag, parent, false),
                        photoActivity);

            case MoreHolder.TYPE_MORE:
                return new MoreHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_photo_more, parent, false),
                        photo,
                        moreHolderModel);

            case MoreLandscapeHolder.TYPE_MORE_LANDSCAPE:
                return new MoreLandscapeHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_photo_more_landscape, parent, false),
                        photoActivity);

            default:
                return new ExifHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_photo_exif, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        this.holder = holder;
        holder.onBindView(photoActivity, photo);
        if (needShowInitAnim && getItemViewType(position) == BaseHolder.TYPE_BASE) {
            needShowInitAnim = false;
            ((BaseHolder) holder).showInitAnim();
        } else if (getItemViewType(position) >= ExifHolder.TYPE_EXIF) {
            ((ExifHolder) holder).drawExif(photoActivity, getItemViewType(position), photo);
        } else if (getItemViewType(position) == TagHolder.TYPE_TAG) {
            ((TagHolder) holder).scrollTo(tagListener.scrollX, 0);
            ((TagHolder) holder).setScrollListener(tagListener);
        } else if (getItemViewType(position) == MoreLandscapeHolder.TYPE_MORE_LANDSCAPE) {
            ((MoreLandscapeHolder) holder).scrollTo(moreListener.scrollX, 0);
            ((MoreLandscapeHolder) holder).setScrollListener(moreListener);
        }
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.onRecycled();
        if (holder instanceof MoreHolder) {
            this.moreHolderModel = ((MoreHolder) holder).saveModel();
        }
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return typeList.get(position);
    }

    private void buildTypeList() {
        typeList = new ArrayList<>();
        /*if (DisplayUtils.isLandscape(photoActivity)) {
            typeList.add(TouchLandscapeHolder.TYPE_TOUCH_LANDSCAPE);
            typeList.add(BaseLandscapeHolder.TYPE_BASE_LANDSCAPE);
        } else {
            typeList.add(TouchHolder.TYPE_TOUCH);
            typeList.add(BaseHolder.TYPE_BASE);
        }*/
        typeList.add(TouchLandscapeHolder.TYPE_TOUCH_LANDSCAPE);
        typeList.add(BaseLandscapeHolder.TYPE_BASE_LANDSCAPE);
        if (complete) {
            if (photo.story != null
                    && !TextUtils.isEmpty(photo.story.title)
                    && !TextUtils.isEmpty(photo.story.description)) {
                typeList.add(StoryHolder.TYPE_STORY);
            }
            for (int i = 0; i < 8; i ++) {
                typeList.add(ExifHolder.TYPE_EXIF + i);
            }
            typeList.add(TagHolder.TYPE_TAG);
            if (photo.related_collections != null && photo.related_collections.results.size() > 0) {
                if (DisplayUtils.isLandscape(photoActivity)) {
                    typeList.add(MoreLandscapeHolder.TYPE_MORE_LANDSCAPE);
                } else {
                    typeList.add(MoreHolder.TYPE_MORE);
                }
            }
        } else {
            typeList.add(ProgressHolder.TYPE_PROGRESS);
        }
    }

    public void reset(Photo photo) {
        updatePhoto(photo);
        resetNeedShowInitAnimFlag();
        this.moreHolderModel = null;
    }

    public void updatePhoto(Photo photo) {
        this.photo = photo;
        this.complete = photo.exif != null;
        buildTypeList();
        holder.updateData(photo);
        notifyItemChanged(0);
    }

    private void resetNeedShowInitAnimFlag() {
        this.needShowInitAnim = photo == null
                || FreedomImageView.getMeasureSize(
                photoActivity,
                photoActivity.getResources().getDisplayMetrics().widthPixels,
                photo.width,
                photo.height,
                true)[1] < photoActivity.getResources().getDisplayMetrics().heightPixels;
    }

    public boolean isComplete() {
        return complete;
    }

    // interface.

    // on scroll swipeListener.

    /**
     * A scroll listener to saved scroll position of the {@link TagHolder}.
     */
    private class OnScrollListener extends RecyclerView.OnScrollListener {

        int scrollX;

        OnScrollListener() {
            reset();
        }

        void reset() {
            scrollX = 0;
        }

        // interface.

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            scrollX += dx;
        }
    }
}
