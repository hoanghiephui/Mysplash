package com.wallpaper.unsplash.common.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.FooterAdapter;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.data.entity.unsplash.Collection;
import com.wallpaper.unsplash.common.data.entity.unsplash.Photo;
import com.wallpaper.unsplash.common.ui.widget.CircleImageView;
import com.wallpaper.unsplash.common.ui.widget.freedomSizeView.FreedomImageView;
import com.wallpaper.unsplash.common.utils.DisplayUtils;
import com.wallpaper.unsplash.common.utils.helper.ImageHelper;
import com.wallpaper.unsplash.common.utils.helper.IntentHelper;
import com.wallpaper.unsplash.user.view.activity.UserActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Collection adapter.
 * <p>
 * Adapter for {@link RecyclerView} to show {@link Collection}.
 */

public class CollectionAdapter extends FooterAdapter<RecyclerView.ViewHolder> {

    private Context a;
    private List<Collection> itemList;

    private int columnCount;

    class ViewHolder extends RecyclerView.ViewHolder
            implements ImageHelper.OnLoadImageListener<Photo>, View.OnClickListener {

        @BindView(R.id.item_collection)
        CardView card;

        @BindView(R.id.item_collection_cover)
        FreedomImageView image;

        @BindView(R.id.item_collection_title)
        TextView title;

        @BindView(R.id.item_collection_subtitle)
        TextView subtitle;

        @BindView(R.id.item_collection_avatar)
        CircleImageView avatar;

        @BindView(R.id.item_collection_name)
        TextView name;
        @BindView(R.id.viewAvatar)
        View viewAvatar;

        private Collection collection;
        private int mPosition;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            DisplayUtils.setTypeface(itemView.getContext(), subtitle);
            DisplayUtils.setTypeface(itemView.getContext(), name);
        }

        @SuppressLint("SetTextI18n")
        public void onBindView(RecyclerView.ViewHolder holder, final Collection collection, final int position) {
            this.collection = collection;

            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) card.getLayoutParams();
            viewAvatar.setVisibility(columnCount > 1 ? View.GONE: View.VISIBLE);
            if (columnCount > 1) {
                int margin = a.getResources().getDimensionPixelSize(R.dimen.little_margin);
                params.setMargins(0, 0, margin, margin);
                card.setLayoutParams(params);
                card.setRadius(new DisplayUtils(a).dpToPx(2));
            } else {
                params.setMargins(0, 0, 0, 0);
                card.setLayoutParams(params);
                card.setRadius(0);
            }

            if (collection.cover_photo != null
                    && collection.cover_photo.width != 0
                    && collection.cover_photo.height != 0) {
                image.setSize(
                        collection.cover_photo.width,
                        collection.cover_photo.height);
            }

            title.setText(collection.title.toUpperCase());
            int photoNum = collection.total_photos;
            subtitle.setText(
                    photoNum + " " + a.getResources().getStringArray(R.array.user_tabs)[0]);
            name.setText(a.getString(R.string.curated_by, collection.user.name));
            image.setShowShadow(true);
            image.setShowShadow(false);

            if (collection.cover_photo != null) {
                ImageHelper.loadCollectionCover(a, image, collection, getAdapterPosition(), this);
                card.setCardBackgroundColor(
                        ImageHelper.computeCardBackgroundColor(
                                a,
                                collection.cover_photo.color));
            } else {
                ImageHelper.loadResourceImage(a, image, R.drawable.default_collection_cover);
            }

            ImageHelper.loadAvatar(a, avatar, collection.user, getAdapterPosition(), null);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                card.setTransitionName(collection.id + "-background");
                avatar.setTransitionName(collection.user.username + "-avatar");
            }
            setOnClick((ViewHolder) holder, position);
        }

        private void setOnClick(ViewHolder holder, final int position) {
            this.mPosition = position;
            card.setOnClickListener(this);
            avatar.setOnClickListener(this);
        }

        public void onRecycled() {
            ImageHelper.releaseImageView(image);
            ImageHelper.releaseImageView(avatar);
        }

        // interface.

        void clickItem() {
            if (a instanceof BaseActivity) {
                IntentHelper.startCollectionActivity(
                        (BaseActivity) a,
                        avatar,
                        card,
                        collection);
            }
        }

        void checkAuthor() {
            if (a instanceof BaseActivity) {
                IntentHelper.startUserActivity(
                        (BaseActivity) a,
                        avatar,
                        collection.user,
                        UserActivity.PAGE_PHOTO);
            }
        }

        // on load image listener.

        @SuppressLint("SetTextI18n")
        @Override
        public void onLoadImageSucceed(Photo newT, int index) {
            if (collection.cover_photo.updateLoadInformation(newT)) {
                Collection c = itemList.get(index);
                c.cover_photo.updateLoadInformation(newT);
                itemList.set(index, c);
            }
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onLoadImageFailed(Photo originalT, int index) {
            title.setText(collection.title.toUpperCase());
            int photoNum = collection.total_photos;
            subtitle.setText(
                    photoNum + " " + a.getResources().getStringArray(R.array.user_tabs)[0]);
            name.setText(collection.user.name);
            image.setShowShadow(true);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_collection_avatar:
                    checkAuthor();
                    break;
                case R.id.item_collection:
                    clickItem();
                    break;
            }
        }
    }

    public CollectionAdapter(Context a, List<Collection> list, boolean isHeader) {
        this(a, list, DisplayUtils.getGirdColumnCount(a));
        isHasHeader = isHeader;
    }

    public CollectionAdapter(Context a, List<Collection> list, boolean isHeader, int columnCount) {
        this(a, list, columnCount);
        isHasHeader = isHeader;
    }

    public CollectionAdapter(Context a, List<Collection> list, int columnCount) {
        this.a = a;
        this.itemList = list;
        this.columnCount = columnCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case CONTENT_TYPE:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_collection, parent, false);
                return new ViewHolder(view);
            default:
                return FooterHolder.buildInstance(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case CONTENT_TYPE:
                ((ViewHolder) holder).onBindView(holder, itemList.get(isHasHeader ? position - 1 : position),
                        isHasHeader ? position - 1 : position);
                break;
            default:
                //update fooder
                break;

        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).onRecycled();
        }
    }

    @Override
    public int getRealItemCount() {
        return itemList.size();
    }


    @Override
    protected boolean hasFooter() {
        return !DisplayUtils.isLandscape(a)
                && DisplayUtils.getNavigationBarHeight(a.getResources()) != 0;
    }

    public void setActivity(BaseActivity a) {
        this.a = a;
    }

    public void insertItem(Collection c, int position) {
        if (position <= itemList.size()) {
            itemList.add(position, c);
            notifyItemInserted(position);
        }
    }

    public void removeItem(Collection c) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).id == c.id) {
                itemList.remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
    }

    public void clearItem() {
        itemList.clear();
        notifyDataSetChanged();
    }

    public List<Collection> getItemList() {
        return itemList;
    }

    public void updateCollection(Collection c, boolean refreshView, boolean probablyRepeat) {
        for (int i = 0; i < getRealItemCount(); i++) {
            if (itemList.get(i).id == c.id) {
                c.editing = itemList.get(i).editing;
                if (c.cover_photo != null && itemList.get(i).cover_photo != null) {
                    c.cover_photo.loadPhotoSuccess = itemList.get(i).cover_photo.loadPhotoSuccess;
                    c.cover_photo.hasFadedIn = itemList.get(i).cover_photo.hasFadedIn;
                    c.cover_photo.settingLike = itemList.get(i).cover_photo.settingLike;
                }
                itemList.set(i, c);
                if (refreshView) {
                    notifyItemChanged(i);
                }
                if (!probablyRepeat) {
                    return;
                }
            }
        }
    }

    public void setCollectionData(List<Collection> list) {
        itemList.clear();
        itemList.addAll(list);
        notifyDataSetChanged();
    }

    public List<Collection> getCollectionData() {
        List<Collection> list = new ArrayList<>();
        list.addAll(itemList);
        return list;
    }
}