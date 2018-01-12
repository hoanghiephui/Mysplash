package com.wallpaper.unsplash.common.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.data.entity.item.MyFollowUser;
import com.wallpaper.unsplash.common.data.entity.unsplash.User;
import com.wallpaper.unsplash.common.data.service.FeedService;
import com.wallpaper.unsplash.common.ui.widget.CircleImageView;
import com.wallpaper.unsplash.common.ui.widget.rippleButton.RippleButton;
import com.wallpaper.unsplash.common.utils.helper.NotificationHelper;
import com.wallpaper.unsplash.common.utils.helper.ImageHelper;
import com.wallpaper.unsplash.common.utils.helper.IntentHelper;
import com.wallpaper.unsplash.user.view.activity.UserActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * My follow adapter.
 *
 * Adapter for {@link RecyclerView} to show follow information.
 *
 * */

public class MyFollowAdapter extends RecyclerView.Adapter<MyFollowAdapter.ViewHolder> {

    private Context context;
    private List<MyFollowUser> itemList;
    private OnFollowStateChangedListener listener;

    private FeedService service;

    public class ViewHolder extends RecyclerView.ViewHolder
            implements RippleButton.OnSwitchListener {

        @BindView(R.id.item_my_follow_user_background)
        RelativeLayout background;

        @BindView(R.id.item_my_follow_user_avatar)
        CircleImageView avatar;

        @BindView(R.id.item_my_follow_user_title)
        TextView title;

        @BindView(R.id.item_my_follow_user_button)
        RippleButton rippleButton;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void onBindView(int position) {
            ImageHelper.loadAvatar(context, avatar, itemList.get(position).user, getAdapterPosition(), null);

            title.setText(itemList.get(position).user.name);

            if (itemList.get(position).requesting) {
                rippleButton.forceProgress(itemList.get(position).switchTo);
            } else {
                rippleButton.forceSwitch(itemList.get(position).user.followed_by_user);
            }
            rippleButton.setOnSwitchListener(this);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                avatar.setTransitionName(itemList.get(position).user.username + "-avatar");
                background.setTransitionName(itemList.get(position).user.username + "-background");
            }
        }

        void onRecycled() {
            ImageHelper.releaseImageView(avatar);
        }

        public void setSwitchResult(boolean result) {
            rippleButton.setSwitchResult(result);
        }

        // interface.

        @OnClick(R.id.item_my_follow_user_background) void clickItem() {
            if (context instanceof BaseActivity) {
                IntentHelper.startUserActivity(
                        (BaseActivity) context,
                        avatar,
                        itemList.get(getAdapterPosition()).user,
                        UserActivity.PAGE_PHOTO);
            }
        }

        @Override
        public void onSwitch(boolean switchTo) {
            MyFollowUser myFollowUser = itemList.get(getAdapterPosition());
            myFollowUser.requesting = true;
            myFollowUser.switchTo = switchTo;
            itemList.set(getAdapterPosition(), myFollowUser);
            service.setFollowUser(context,
                    itemList.get(getAdapterPosition()).user.username,
                    switchTo,
                    new OnSetFollowListener(
                            itemList.get(getAdapterPosition()).user.username,
                            switchTo));
        }
    }

    public MyFollowAdapter(Context context, List<MyFollowUser> list, OnFollowStateChangedListener l) {
        this.context = context;
        this.itemList = list;
        this.listener = l;
        this.service = FeedService.getService();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_follow_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBindView(position);
    }

    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.onRecycled();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setActivity(BaseActivity a) {
        this.context = a;
    }

    public void insertItem(User u, int position) {
        if (position <= itemList.size()) {
            itemList.add(position, new MyFollowUser(u));
            notifyItemInserted(position);
        }
    }

    public void clearItem() {
        itemList.clear();
        notifyDataSetChanged();
    }

    // interface.

    // on follow state changed listener.

    public interface OnFollowStateChangedListener {
        void onFollowStateChanged(String username, int position, boolean switchTo, boolean succeed);
    }

    // on set follow listener.

    private class OnSetFollowListener implements FeedService.OnFollowListener {
        // data
        private String username;
        private boolean switchTo;

        // life cycle.

        OnSetFollowListener(String username, boolean switchTo) {
            this.username = username;
            this.switchTo = switchTo;
        }

        // data.

        @Override
        public void onFollowSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (UnsplashApplication.getInstance() != null && UnsplashApplication.getInstance().getTopActivity() != null) {
                for (int i = 0; i < itemList.size(); i ++) {
                    if (itemList.get(i).user.username.equals(username)) {
                        User user = itemList.get(i).user;
                        user.followed_by_user = true;
                        itemList.set(i, new MyFollowUser(user));
                        if (listener != null) {
                            listener.onFollowStateChanged(username, i, switchTo, true);
                        }
                    }
                }
            }
        }

        @Override
        public void onCancelFollowSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (UnsplashApplication.getInstance() != null && UnsplashApplication.getInstance().getTopActivity() != null) {
                for (int i = 0; i < itemList.size(); i ++) {
                    if (itemList.get(i).user.username.equals(username)) {
                        User user = itemList.get(i).user;
                        user.followed_by_user = false;
                        itemList.set(i, new MyFollowUser(user));
                        if (listener != null) {
                            listener.onFollowStateChanged(username, i, switchTo, true);
                        }
                    }
                }
            }
        }

        @Override
        public void onFollowFailed(Call<ResponseBody> call, Throwable t) {
            if (UnsplashApplication.getInstance() != null && UnsplashApplication.getInstance().getTopActivity() != null) {
                NotificationHelper.showSnackbar(context.getString(R.string.feedback_follow_failed));
                for (int i = 0; i < itemList.size(); i ++) {
                    if (itemList.get(i).user.username.equals(username)) {
                        User user = itemList.get(i).user;
                        itemList.set(i, new MyFollowUser(user));
                        if (listener != null) {
                            listener.onFollowStateChanged(username, i, switchTo, false);
                        }
                    }
                }
            }
        }

        @Override
        public void onCancelFollowFailed(Call<ResponseBody> call, Throwable t) {
            if (UnsplashApplication.getInstance() != null && UnsplashApplication.getInstance().getTopActivity() != null) {
                NotificationHelper.showSnackbar(context.getString(R.string.feedback_cancel_follow_failed));
                for (int i = 0; i < itemList.size(); i ++) {
                    if (itemList.get(i).user.username.equals(username)) {
                        User user = itemList.get(i).user;
                        itemList.set(i, new MyFollowUser(user));
                        if (listener != null) {
                            listener.onFollowStateChanged(username, i, switchTo, false);
                        }
                    }
                }
            }
        }
    }
}