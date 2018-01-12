package com.wallpaper.unsplash.about.view.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.interfaces.model.AboutModel;
import com.wallpaper.unsplash.common.ui.activity.IntroduceActivity;
import com.wallpaper.unsplash.common.ui.adapter.AboutAdapter;
import com.wallpaper.unsplash.common.utils.helper.DonateHelper;
import com.wallpaper.unsplash.common.utils.helper.IntentHelper;
import com.wallpaper.unsplash.about.model.AppObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * App holder.
 *
 * This ViewHolder class is used to show app information for {@link AboutAdapter}.
 *
 * */

public class AppHolder extends AboutAdapter.ViewHolder {

    @BindView(R.id.item_about_app_icon)
    ImageView icon;

    @BindView(R.id.item_about_app_title)
    TextView text;

    private int id;

    public AppHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindView(BaseActivity a, AboutModel model) {
        AppObject object = (AppObject) model;

        icon.setImageResource(object.iconId);
        text.setText(object.text);
        id = object.id;
    }

    @Override
    protected void onRecycled() {
        // do nothing.
    }

    @OnClick(R.id.item_about_app_container) void clickItem() {
        switch (id) {
            case 1:
                IntroduceActivity.watchAllIntroduce(UnsplashApplication.getInstance().getTopActivity());
                break;

            case 2:
                IntentHelper.startWebActivity(itemView.getContext(), "https://github.com/WangDaYeeeeee");
                break;

            case 3:
                IntentHelper.startWebActivity(itemView.getContext(), "mailto:wangdayeeeeee@gmail.com");
                break;

            case 4:
                IntentHelper.startWebActivity(itemView.getContext(), "https://github.com/WangDaYeeeeee/MySplash");
                break;

            case 5:
                DonateHelper.donateByAlipay(UnsplashApplication.getInstance().getTopActivity());
                break;
        }
    }
}
