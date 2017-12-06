package com.wallpapers.unsplash.common.data.entity.unsplash;

import android.os.Parcel;
import android.os.Parcelable;

import com.wallpapers.unsplash.UnsplashApplication;

/**
 * Category.
 * */

public class Category implements com.wallpapers.unsplash.common.basic.Tag, Parcelable {

    /**
     * id : 2
     * title : Buildings
     * photo_count : 3428
     * links : {"self":"https://api.unsplash.com/categories/2","photos":"https://api.unsplash.com/categories/2/photos"}
     */
    public int id;
    public String title;
    public int photo_count;

    public CategoryLinks links;

    private String getRawCoverUrl() {
        switch (id) {
            case UnsplashApplication.CATEGORY_BUILDINGS_ID:
                return "https://images.unsplash.com/photo-1481205009193-0b6b42cc81ac";

            case UnsplashApplication.CATEGORY_FOOD_DRINK_ID:
                return "https://images.unsplash.com/photo-1453831362806-3d5577f014a4";

            case UnsplashApplication.CATEGORY_NATURE_ID:
                return "https://images.unsplash.com/photo-1433351120803-a29aeee7d1e7";

            case UnsplashApplication.CATEGORY_OBJECTS_ID:
                return "https://images.unsplash.com/photo-1444881421460-d838c3b98f95";

            case UnsplashApplication.CATEGORY_PEOPLE_ID:
                return "https://images.unsplash.com/photo-1482028655172-fa4270a17164";

            case UnsplashApplication.CATEGORY_TECHNOLOGY_ID:
                return "https://images.unsplash.com/photo-1445620466293-d6316372ab59";

            default:
                return "https://images.unsplash.com/photo-1485282569499-bc24811e75ce";
        }
    }

    // parcel.

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeInt(this.photo_count);
        dest.writeParcelable(this.links, flags);
    }

    public Category() {
    }

    protected Category(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.photo_count = in.readInt();
        this.links = in.readParcelable(CategoryLinks.class.getClassLoader());
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    // interface.

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getRegularUrl() {
        return getRawCoverUrl() + "?fm=jpg&w=720&fit=max";
    }

    @Override
    public String getThumbnailUrl() {
        return getRawCoverUrl() + "?fm=jpg&w=360&fit=max";
    }
}
