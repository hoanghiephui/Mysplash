package com.wallpapers.unsplash.about.view.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.interfaces.model.AboutModel;
import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;
import com.wallpapers.unsplash.common.ui.adapter.AboutAdapter;
import com.wallpapers.unsplash.common.ui.widget.CircleImageView;
import com.wallpapers.unsplash.common.utils.DisplayUtils;
import com.wallpapers.unsplash.common.utils.helper.ImageHelper;
import com.wallpapers.unsplash.common.utils.helper.IntentHelper;
import com.wallpapers.unsplash.about.model.TranslatorObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Translator holder.
 *
 * This ViewHolder class is used to show translator for {@link AboutAdapter}.
 *
 * */

public class TranslatorHolder extends AboutAdapter.ViewHolder {

    @BindView(R.id.item_about_translator_avatar)
    CircleImageView avatar;

    @BindView(R.id.item_about_translator_flag)
    ImageView flag;

    @BindView(R.id.item_about_translator_title)
    TextView title;

    @BindView(R.id.item_about_translator_subtitle)
    TextView subtitle;

    private String url;

    public TranslatorHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        DisplayUtils.setTypeface(itemView.getContext(), subtitle);
    }

    @Override
    protected void onBindView(MysplashActivity a, AboutModel model) {
        TranslatorObject object = (TranslatorObject) model;

        ImageHelper.loadAvatar(a, avatar, object.avatarUrl);
        ImageHelper.loadResourceImage(a, flag, object.flagId);

        title.setText(object.title);
        subtitle.setText(object.subtitle);
        url = object.subtitle;
    }

    @Override
    protected void onRecycled() {
        ImageHelper.releaseImageView(avatar);
        ImageHelper.releaseImageView(flag);
    }

    @OnClick(R.id.item_about_translator_container) void clickItem() {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(url);
        IntentHelper.startWebActivity(
                itemView.getContext(),
                matcher.matches() ? "mailto:" + url : url);
    }
}
