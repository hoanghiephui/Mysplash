package com.wallpaper.unsplash.common.data.entity.unsplash;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryModel {

    @SerializedName("category")
    private List<CategoryItem> category;

    public List<CategoryItem> getCategory() {
        return category;
    }
}