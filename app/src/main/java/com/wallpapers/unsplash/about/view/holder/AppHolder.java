package com.wallpapers.unsplash.about.view.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallpapers.unsplash.Unsplash;
import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.interfaces.model.AboutModel;
import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;
import com.wallpapers.unsplash.common.ui.activity.IntroduceActivity;
import com.wallpapers.unsplash.common.ui.adapter.AboutAdapter;
import com.wallpapers.unsplash.common.utils.helper.DonateHelper;
import com.wallpapers.unsplash.common.utils.helper.IntentHelper;
import com.wallpapers.unsplash.about.model.AppObject;

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
    public void onBindView(MysplashActivity a, AboutModel model) {
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
                IntroduceActivity.watchAllIntroduce(Unsplash.getInstance().getTopActivity());
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
                DonateHelper.donateByAlipay(Unsplash.getInstance().getTopActivity());
                break;
        }
    }
}
