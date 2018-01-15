package com.wallpaper.unsplash.common.ui.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.data.entity.unsplash.Photo;
import com.wallpaper.unsplash.common.ui.dialog.SelectCollectionDialog;
import com.wallpaper.unsplash.common.ui.widget.freedomSizeView.FreedomImageView;
import com.wallpaper.unsplash.common.utils.DisplayUtils;
import com.wallpaper.unsplash.photo.view.activity.PhotoActivity;
import com.wallpaper.unsplash.photo.view.holder.AdsViewHolder;
import com.wallpaper.unsplash.photo.view.holder.BaseHolder;
import com.wallpaper.unsplash.photo.view.holder.BaseLandscapeHolder;
import com.wallpaper.unsplash.photo.view.holder.ExifHolder;
import com.wallpaper.unsplash.photo.view.holder.FeaturedCollectionsHolder;
import com.wallpaper.unsplash.photo.view.holder.MoreHolder;
import com.wallpaper.unsplash.photo.view.holder.MoreLandscapeHolder;
import com.wallpaper.unsplash.photo.view.holder.ProgressHolder;
import com.wallpaper.unsplash.photo.view.holder.RelatedPhotoHolder;
import com.wallpaper.unsplash.photo.view.holder.StoryHolder;
import com.wallpaper.unsplash.photo.view.holder.TagHolder;
import com.wallpaper.unsplash.photo.view.holder.TouchHolder;
import com.wallpaper.unsplash.photo.view.holder.TouchLandscapeHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Photo info adapter.
 * <p>
 * Adapter for {@link RecyclerView} to show details of photoActivity photo.
 */

public class PhotoInfoAdapter extends RecyclerView.Adapter<PhotoInfoAdapter.ViewHolder> {

    private PhotoActivity photoActivity;
    private OnScrollListener tagListener, moreListener, collectionListener;

    private Photo photo;
    private List<Integer> typeList; // information of view holder.
    private List<Photo> photoListRelated;

    private boolean complete; // if true, means the photo object is completely. (has data like exif)
    private boolean needShowInitAnim; // need do the initialize animation when first bind basic view.
    private ViewHolder holder;

    private MoreHolder.MoreHolderModel moreHolderModel;
    private SelectCollectionDialog.OnCollectionsChangedListener collectionsChangedListener;

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        protected void onBindView(PhotoActivity activity, Photo photo) {
        }

        public void onBindView(PhotoActivity activity, List<Photo> photo) {
        }

        protected void updateData(Photo photo) {
        }

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

    public PhotoInfoAdapter(PhotoActivity photoActivity, Photo photo, List<Photo> photoListRelated) {
        this.photoActivity = photoActivity;
        this.tagListener = new OnScrollListener();
        this.collectionListener = new OnScrollListener();
        this.moreListener = new OnScrollListener();
        this.photo = photo;
        this.photoListRelated = photoListRelated;
        this.complete = photo != null && photo.complete;
        resetNeedShowInitAnimFlag();
        this.moreHolderModel = null;
        buildTypeList(photoListRelated);
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
            case FeaturedCollectionsHolder.TYPE_COLLECTION:
                return new FeaturedCollectionsHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_featuerd_collection, parent, false),
                        photoActivity);
            case RelatedPhotoHolder.TYPE_PHOTO:
                return new RelatedPhotoHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_featuerd_collection, parent, false),
                        photoActivity);
            case AdsViewHolder.TYPE_ADS:
                return new AdsViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_ads_banner, parent, false),
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
        if (getItemViewType(position) == RelatedPhotoHolder.TYPE_PHOTO) {
            holder.onBindView(photoActivity,
                    photoListRelated);
            ((RelatedPhotoHolder) holder).scrollTo(collectionListener.scrollX, 0);
            ((RelatedPhotoHolder) holder).setScrollListener(collectionListener);
        } else {
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
            } else if (getItemViewType(position) == FeaturedCollectionsHolder.TYPE_COLLECTION) {
                ((FeaturedCollectionsHolder) holder).scrollTo(collectionListener.scrollX, 0);
                ((FeaturedCollectionsHolder) holder).setScrollListener(collectionListener);
            }
        }
    }

    /*@Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.onRecycled();
        if (holder instanceof MoreHolder) {
            this.moreHolderModel = ((MoreHolder) holder).saveModel();
        }
    }*/

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
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

    private void buildTypeList(List<Photo> photoListRelated) {
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
            typeList.add(AdsViewHolder.TYPE_ADS);
            if (photoListRelated != null && photoListRelated.size() > 0){
                typeList.add(RelatedPhotoHolder.TYPE_PHOTO);
            }

            typeList.add(FeaturedCollectionsHolder.TYPE_COLLECTION);
            if (photo.story != null
                    && !TextUtils.isEmpty(photo.story.title)
                    && !TextUtils.isEmpty(photo.story.description)) {
                typeList.add(StoryHolder.TYPE_STORY);
            }
            for (int i = 0; i < 8; i++) {
                typeList.add(ExifHolder.TYPE_EXIF + i);
            }
            typeList.add(AdsViewHolder.TYPE_ADS);
            if (photo.tags != null && photo.tags.size() > 0) {
                typeList.add(TagHolder.TYPE_TAG);
            }
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
        updatePhoto(photo, null);
        resetNeedShowInitAnimFlag();
        this.moreHolderModel = null;
    }

    public void updatePhoto(Photo photo, List<Photo> list) {
        this.photo = photo;
        this.complete = photo.exif != null;
        this.photoListRelated = list;
        buildTypeList(list);
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
