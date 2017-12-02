package com.wallpapers.unsplash.common.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wallpapers.unsplash.Unsplash;
import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.collection.view.activity.CollectionActivity;
import com.wallpapers.unsplash.common._basic.FooterAdapter;
import com.wallpapers.unsplash.common._basic.activity.LoadableActivity;
import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;
import com.wallpapers.unsplash.common.data.entity.unsplash.CategoryItem;
import com.wallpapers.unsplash.common.data.entity.unsplash.ChangeCollectionPhotoResult;
import com.wallpapers.unsplash.common.data.entity.unsplash.LikePhotoResult;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.data.service.PhotoService;
import com.wallpapers.unsplash.common.ui.dialog.DeleteCollectionPhotoDialog;
import com.wallpapers.unsplash.common.ui.dialog.DownloadRepeatDialog;
import com.wallpapers.unsplash.common.ui.dialog.SelectCollectionDialog;
import com.wallpapers.unsplash.common.ui.widget.CircleImageView;
import com.wallpapers.unsplash.common.ui.widget.CircularProgressIcon;
import com.wallpapers.unsplash.common.ui.widget.freedomSizeView.FreedomImageView;
import com.wallpapers.unsplash.common.utils.DisplayUtils;
import com.wallpapers.unsplash.common.utils.FileUtils;
import com.wallpapers.unsplash.common.utils.helper.DatabaseHelper;
import com.wallpapers.unsplash.common.utils.helper.ImageHelper;
import com.wallpapers.unsplash.common.utils.helper.IntentHelper;
import com.wallpapers.unsplash.common.utils.helper.NotificationHelper;
import com.wallpapers.unsplash.common.utils.manager.AuthManager;
import com.wallpapers.unsplash.main.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

import static com.wallpapers.unsplash.Unsplash.SEARCH_QUERY_ID;

/**
 * Photo adapter.
 * <p>
 * Adapter for {@link RecyclerView} to show photos.
 */

public class PhotoAdapter extends FooterAdapter<RecyclerView.ViewHolder>
        implements DeleteCollectionPhotoDialog.OnDeleteCollectionListener,
        DownloadRepeatDialog.OnCheckOrDownloadListener, CategoryHomeHolder.CallBackAdapter {

    private Context a;
    private RecyclerView recyclerView;

    private SelectCollectionDialog.OnCollectionsChangedListener collectionsChangedListener;
    private OnDownloadPhotoListener downloadPhotoListener;

    private List<Photo> itemList;
    private PhotoService service;
    private List<CategoryItem> categoryItems = new ArrayList<>();

    public void setCategoryItems(List<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
        notifyItemChanged(0);
    }

    private int columnCount;

    // if set true, it means these photos is in a collection that was created by user.
    private boolean inMyCollection = false;

    /**
     * @param title
     * @method callback click item category home
     */
    @Override
    public void onClickItem(String title) {
        ((MainActivity) a).changeFragment(SEARCH_QUERY_ID, title);
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements ImageHelper.OnLoadImageListener<Photo>, View.OnClickListener {

        @BindView(R.id.item_photo)
        CardView card;

        @BindView(R.id.item_photo_image)
        FreedomImageView image;

        @BindView(R.id.item_photo_title)
        TextView title;

        @BindView(R.id.item_photo_deleteButton)
        ImageButton deleteButton;

        @BindView(R.id.item_photo_collectionButton)
        ImageButton collectionButton;

        @BindView(R.id.item_photo_likeButton)
        CircularProgressIcon likeButton;
        @BindView(R.id.item_photo_downloadButton)
        AppCompatImageButton downloadImage;
        @BindView(R.id.item_photo_base_avatar)
        CircleImageView avatar;
        @BindView(R.id.item_photo_desc)
        TextView desc;

        private Photo photo;
        private int mPosition;
        private ViewHolder mHolder = null;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            DisplayUtils.setTypeface(itemView.getContext(), title);
        }

        void onBindView(ViewHolder viewHolder, final int position) {
            this.photo = itemList.get(position);

            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) card.getLayoutParams();
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

            image.setSize(photo.width, photo.height);

            title.setText("");
            image.setShowShadow(false);

            // ImageHelper.loadFullPhoto(a, image, photo, position, this);
            ImageHelper.loadRegularPhoto(a, image, photo, position, this);
            ImageHelper.loadAvatar(a, avatar, itemList.get(position).user);

            if (inMyCollection) {
                deleteButton.setVisibility(View.VISIBLE);
            } else {
                deleteButton.setVisibility(View.GONE);
            }
            if (photo.current_user_collections.size() != 0) {
                collectionButton.setImageResource(R.drawable.ic_item_collected);
            } else {
                collectionButton.setImageResource(R.drawable.ic_item_collect);
            }

            if (photo.settingLike) {
                likeButton.forceSetProgressState();
            } else {
                likeButton.forceSetResultState(photo.liked_by_user ?
                        R.drawable.ic_item_heart_red : R.drawable.ic_item_heart_outline);
            }
            desc.setVisibility(itemList.get(position).user.location != null ? View.VISIBLE : View.GONE);
            if (photo.user.name == null || photo.user.name.equals("")) {
                title.setVisibility(View.GONE);
            } else {
                title.setText(photo.user.name);
                title.setVisibility(View.VISIBLE);
            }

            if (itemList.get(position).user.location != null)
                desc.setText(itemList.get(position).user.location);
            card.setCardBackgroundColor(ImageHelper.computeCardBackgroundColor(a, photo.color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                image.setTransitionName(photo.id + "-cover");
                card.setTransitionName(photo.id + "-background");
            }

            setOnClick(viewHolder, position);
        }

        void onRecycled() {
            ImageHelper.releaseImageView(image);
            likeButton.recycleImageView();
        }

        private void setOnClick(ViewHolder holder, final int position) {
            this.mPosition = position;
            this.mHolder = holder;
            card.setOnClickListener(this);
            avatar.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            likeButton.setOnClickListener(this);
            collectionButton.setOnClickListener(this);
            downloadImage.setOnClickListener(this);
        }

        // interface.

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_photo:
                    clickItem();
                    break;
                case R.id.item_photo_base_avatar:
                    clickUser();
                    break;
                case R.id.item_photo_deleteButton:
                    deletePhoto();
                    break;
                case R.id.item_photo_likeButton:
                    likePhoto();
                    break;
                case R.id.item_photo_collectionButton:
                    collectPhoto();
                    break;
                default:
                    downloadPhoto();
                    break;
            }
        }

        void clickUser() {

        }

        void clickItem() {
            if (a instanceof MysplashActivity && mPosition < itemList.size()) {

                ArrayList<Photo> list = new ArrayList<>();
                int headIndex = mPosition - 2;
                int size = 5;
                if (headIndex < 0) {
                    headIndex = 0;
                }
                if (headIndex + size - 1 > itemList.size() - 1) {
                    size = itemList.size() - headIndex;
                }
                for (int i = 0; i < size; i++) {
                    list.add(itemList.get(headIndex + i));
                }

                IntentHelper.startPhotoActivity(
                        (MysplashActivity) a, image, card,
                        list, mPosition, headIndex,
                        a instanceof LoadableActivity ? ((LoadableActivity) a).getBundleOfList() : new Bundle());
            }
        }

        void deletePhoto() {
            if (a instanceof CollectionActivity) {
                DeleteCollectionPhotoDialog dialog = new DeleteCollectionPhotoDialog();
                dialog.setDeleteInfo(
                        ((CollectionActivity) a).getCollection(),
                        itemList.get(mPosition),
                        mPosition);
                dialog.setOnDeleteCollectionListener(PhotoAdapter.this);
                dialog.show(((CollectionActivity) a).getFragmentManager(), null);
            }
        }

        void likePhoto() {
            if (AuthManager.getInstance().isAuthorized()) {
                if (likeButton.isUsable()) {
                    likeButton.setProgressState();
                    setLikeForAPhoto(
                            !itemList.get(mPosition).liked_by_user,
                            mPosition);
                }
            } else {
                IntentHelper.startLoginActivity((MysplashActivity) a);
            }
        }

        void collectPhoto() {
            if (a instanceof MysplashActivity) {
                if (!AuthManager.getInstance().isAuthorized()) {
                    IntentHelper.startLoginActivity((MysplashActivity) a);
                } else {
                    SelectCollectionDialog dialog = new SelectCollectionDialog();
                    dialog.setPhotoAndListener(itemList.get(mPosition), collectionsChangedListener);
                    dialog.show(((MysplashActivity) a).getFragmentManager(), null);
                }
            }
        }

        //@OnClick(R.id.item_photo_downloadButton)
        void downloadPhoto() {
            Photo p = itemList.get(mPosition);
            if (DatabaseHelper.getInstance(a).readDownloadingEntityCount(p.id) > 0) {
                NotificationHelper.showSnackbar(a.getString(R.string.feedback_download_repeat));
            } else if (FileUtils.isPhotoExists(a, p.id)) {
                MysplashActivity activity = Unsplash.getInstance().getTopActivity();
                if (activity != null) {
                    DownloadRepeatDialog dialog = new DownloadRepeatDialog();
                    dialog.setDownloadKey(p);
                    dialog.setOnCheckOrDownloadListener(PhotoAdapter.this);
                    dialog.show(activity.getFragmentManager(), null);
                }
            } else {
                if (downloadPhotoListener != null) {
                    downloadPhotoListener.onDownload(p);
                }
            }
        }

        @Override
        public void onLoadImageSucceed(Photo newT, int index) {
            if (photo.updateLoadInformation(newT)) {
                Photo p = itemList.get(index);
                p.updateLoadInformation(newT);
                itemList.set(index, p);
            }

            image.setShowShadow(true);
        }

        @Override
        public void onLoadImageFailed(Photo originalT, int index) {
            // do nothing.
        }
    }

    public PhotoAdapter(Context a, List<Photo> list,
                        SelectCollectionDialog.OnCollectionsChangedListener sl,
                        OnDownloadPhotoListener dl, boolean isHeader) {
        this(a, list, DisplayUtils.getGirdColumnCount(a), sl, dl);
        isHasHeader = isHeader;
    }

    public PhotoAdapter(Context a, List<Photo> list, int columnCount,
                        SelectCollectionDialog.OnCollectionsChangedListener sl,
                        OnDownloadPhotoListener dl) {
        this.a = a;
        this.itemList = list;
        this.columnCount = columnCount;
        this.collectionsChangedListener = sl;
        this.downloadPhotoListener = dl;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER_TYPE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
                return new CategoryHomeHolder(view, a, this);
            case CONTENT_TYPE:
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_photo, parent, false);
                return new ViewHolder(v);
            default:
                return FooterHolder.buildInstance(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case HEADER_TYPE:
                ((CategoryHomeHolder) holder).onBindView(a, categoryItems);
                break;
            case CONTENT_TYPE:
                ViewHolder holderContent = (ViewHolder) holder;
                holderContent.onBindView(holderContent, isHasHeader ? position - 1 : position);
                break;
            default:
                //update fooder
                break;

        }/*
        if (holder instanceof ViewHolder && position < getRealItemCount()) {
            ((ViewHolder) holder).onBindView(position);
        }*/
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

    public void setActivity(MysplashActivity a) {
        this.a = a;
    }

    public void setRecyclerView(RecyclerView v) {
        this.recyclerView = v;
    }

    public void insertItem(Photo item) {
        if (item.width != 0 && item.height != 0) {
            itemList.add(item);
            notifyItemInserted(itemList.size() - 1);
        }
    }

    public void insertItemToFirst(Photo item) {
        if (item.width != 0 && item.height != 0) {
            itemList.add(0, item);
            notifyItemInserted(0);
        }
    }

    public void removeItem(Photo item) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).id.equals(item.id)) {
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

    private void setLikeForAPhoto(boolean like, int position) {
        if (service == null) {
            service = PhotoService.getService();
        }
        itemList.get(position).settingLike = true;
        service.setLikeForAPhoto(
                itemList.get(position).id,
                like,
                new OnSetLikeListener(itemList.get(position).id, position));
    }

    public void setInMyCollection(boolean in) {
        this.inMyCollection = in;
    }

    public void updatePhoto(Photo p, boolean refreshView, boolean probablyRepeat) {
        for (int i = 0; i < getRealItemCount(); i++) {
            if (itemList.get(i).id.equals(p.id)) {
                p.loadPhotoSuccess = itemList.get(i).loadPhotoSuccess;
                p.hasFadedIn = itemList.get(i).hasFadedIn;
                p.settingLike = itemList.get(i).settingLike;
                itemList.set(i, p);
                if (refreshView) {
                    notifyItemChanged(i);
                }
                if (!probablyRepeat) {
                    return;
                }
            }
        }
    }

    public void setPhotoData(List<Photo> list) {
        itemList.clear();
        itemList.addAll(list);
        notifyDataSetChanged();
    }

    public List<Photo> getPhotoData() {
        List<Photo> list = new ArrayList<>();
        list.addAll(itemList);
        return list;
    }

    private void dispatchUpdate(int position) {
        if (a instanceof LoadableActivity) {
            Unsplash.getInstance().dispatchPhotoUpdate(
                    (LoadableActivity<Photo>) a,
                    itemList.get(position));
        }
    }

    // interface.

    // on download photo listener.

    public interface OnDownloadPhotoListener {
        void onDownload(Photo photo);
    }

    public void setOnDownloadPhotoListener(OnDownloadPhotoListener l) {
        this.downloadPhotoListener = l;
    }

    // on set like listener.

    private class OnSetLikeListener implements PhotoService.OnSetLikeListener {

        private String id;
        private int position;

        OnSetLikeListener(String id, int position) {
            this.id = id;
            this.position = position;
        }

        // interface.

        @Override
        public void onSetLikeSuccess(Call<LikePhotoResult> call, Response<LikePhotoResult> response) {
            if (itemList.size() > position && itemList.get(position).id.equals(id)) {
                itemList.get(position).settingLike = false;

                if (response.isSuccessful() && response.body() != null) {
                    itemList.get(position).liked_by_user = response.body().photo.liked_by_user;
                    itemList.get(position).likes = response.body().photo.likes;
                }

                updateView(itemList.get(position).liked_by_user);
                dispatchUpdate(position);
            }
        }

        @Override
        public void onSetLikeFailed(Call<LikePhotoResult> call, Throwable t) {
            if (itemList.size() > position
                    && itemList.get(position).id.equals(id)) {
                itemList.get(position).settingLike = false;
                updateView(itemList.get(position).liked_by_user);
                NotificationHelper.showSnackbar(
                        itemList.get(position).liked_by_user ?
                                a.getString(R.string.feedback_unlike_failed)
                                :
                                a.getString(R.string.feedback_like_failed));
            }
        }

        private void updateView(boolean to) {
            if (recyclerView != null) {
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] firstPositions = layoutManager.findFirstVisibleItemPositions(null);
                int[] lastPositions = layoutManager.findLastVisibleItemPositions(null);
                int mPosition = isHasHeader ? position + 1 : position;
                if (firstPositions[0] <= position && position <= lastPositions[lastPositions.length - 1]) {
                    ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(mPosition);
                    holder.likeButton.setResultState(
                            to ? R.drawable.ic_item_heart_red : R.drawable.ic_item_heart_outline);
                }
            }
        }
    }

    // on delete collection photo listener.

    @Override
    public void onDeletePhotoSuccess(ChangeCollectionPhotoResult result, int position) {
        if (itemList.size() > position && itemList.get(position).id.equals(result.photo.id)) {
            itemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    // on check or download listener. (download repeat dialog)

    @Override
    public void onCheck(Object obj) {
        IntentHelper.startCheckPhotoActivity(a, ((Photo) obj).id);
    }

    @Override
    public void onDownload(Object obj) {
        if (downloadPhotoListener != null) {
            downloadPhotoListener.onDownload((Photo) obj);
        }
    }
}
