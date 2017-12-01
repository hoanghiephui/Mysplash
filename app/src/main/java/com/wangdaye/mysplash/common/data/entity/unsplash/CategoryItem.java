package com.wangdaye.mysplash.common.data.entity.unsplash;

import com.google.gson.annotations.SerializedName;

public class CategoryItem {

    @SerializedName("image")
    private String image;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}