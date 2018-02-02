package com.wallpapers.unsplash.about.view.holder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.interfaces.model.AboutModel;
import com.wallpapers.unsplash.common.ui.adapter.AboutAdapter;
import com.wallpapers.unsplash.common.ui.dialog.TotalDialog;
import com.wallpapers.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;
import com.wallpapers.unsplash.common.utils.DisplayUtils;
import com.wallpapers.unsplash.common.utils.helper.ImageHelper;
import com.wallpapers.unsplash.common.utils.manager.ThemeManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Header holder.
 * <p>
 * This PhotoViewHolder class is used to show header for {@link AboutAdapter}.
 */

public class HeaderHolder extends AboutAdapter.ViewHolder {

    @BindView(R.id.item_about_header_appIcon)
    ImageView appIcon;

    public HeaderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        ImageButton backBtn = ButterKnife.findById(itemView, R.id.item_about_header_backButton);
        ThemeManager.setImageResource(
                backBtn, R.drawable.ic_toolbar_back_light, R.drawable.ic_toolbar_back_dark);

        TextView version = (TextView) itemView.findViewById(R.id.item_about_header_versionCode);
        DisplayUtils.setTypeface(itemView.getContext(), version);

        TextView unsplashTitle = (TextView) itemView.findViewById(R.id.item_about_header_unsplashTitle);
        unsplashTitle.setText(itemView.getContext().getString(R.string.unsplash));
        DisplayUtils.setTypeface(itemView.getContext(), unsplashTitle);

        TextView unsplashContent = (TextView) itemView.findViewById(R.id.item_about_header_unsplashContent);
        unsplashContent.setText(itemView.getContext().getString(R.string.about_unsplash));
        DisplayUtils.setTypeface(itemView.getContext(), unsplashContent);
    }

    @Override
    protected void onBindView(BaseActivity a, AboutModel model) {
        ImageHelper.loadResourceImage(a, appIcon, R.drawable.ic_launcher);
    }

    @Override
    protected void onRecycled() {
        ImageHelper.releaseImageView(appIcon);
    }

    @OnClick(R.id.item_about_header_backButton)
    void close() {
        BaseActivity activity = UnsplashApplication.getInstance()
                .getTopActivity();
        if (activity != null) {
            activity.finishActivity(SwipeBackCoordinatorLayout.DOWN_DIR);
        }
    }

    @OnClick(R.id.item_about_header_unsplashContainer)
    void checkTotal() {
        BaseActivity activity = UnsplashApplication.getInstance()
                .getTopActivity();
        if (activity != null) {
            TotalDialog dialog = new TotalDialog();
            dialog.show(activity.getFragmentManager(), null);
        }
    }
}
