package com.wallpapers.unsplash.common.data.entity.unsplash;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.basic.Previewable;
import com.wallpapers.unsplash.common.utils.DisplayUtils;
import com.wallpapers.unsplash.common.utils.manager.SettingsOptionManager;

import java.util.List;

/**
 * Photo.
 */

public class Photo
        implements Parcelable, Previewable {
    // data
    public boolean loadPhotoSuccess = false;
    public boolean hasFadedIn = false;
    public boolean settingLike = false;
    public boolean complete = false;
    public String id;
    public String created_at;
    public String updated_at;
    public int width = 0;
    public int height = 0;
    public String color;
    public int views;
    public int downloads;
    public int likes;
    public boolean liked_by_user;

    public String description;
    public Exif exif;
    public Location location;
    public PhotoUrls urls;
    public PhotoLinks links;
    public Story story;
    public Stats stats;
    public User user;
    public List<Collection> current_user_collections;
    public List<Category> categories;
    public List<Tag> tags;
    public RelatedPhotos related_photos;
    public RelatedCollections related_collections;

    public static class RelatedPhotos implements Parcelable {
        public int total;
        public String type;
        public List<Photo> results;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.total);
            dest.writeString(this.type);
            dest.writeTypedList(this.results);
        }

        public RelatedPhotos() {
        }

        protected RelatedPhotos(Parcel in) {
            this.total = in.readInt();
            this.type = in.readString();
            this.results = in.createTypedArrayList(Photo.CREATOR);
        }

        public static final Creator<RelatedPhotos> CREATOR = new Creator<RelatedPhotos>() {
            @Override
            public RelatedPhotos createFromParcel(Parcel source) {
                return new RelatedPhotos(source);
            }

            @Override
            public RelatedPhotos[] newArray(int size) {
                return new RelatedPhotos[size];
            }
        };
    }

    public static class RelatedCollections implements Parcelable {
        public int total;
        public String type;
        public List<Collection> results;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.total);
            dest.writeString(this.type);
            dest.writeTypedList(this.results);
        }

        public RelatedCollections() {
        }

        protected RelatedCollections(Parcel in) {
            this.total = in.readInt();
            this.type = in.readString();
            this.results = in.createTypedArrayList(Collection.CREATOR);
        }

        public static final Creator<RelatedCollections> CREATOR = new Creator<RelatedCollections>() {
            @Override
            public RelatedCollections createFromParcel(Parcel source) {
                return new RelatedCollections(source);
            }

            @Override
            public RelatedCollections[] newArray(int size) {
                return new RelatedCollections[size];
            }
        };
    }

    /**
     * <br> data.
     */

    public int getRegularWidth() {
        try {
            int w = Integer.parseInt(Uri.parse(urls.regular).getQueryParameter("w"));
            return w == 0 ? 1080 : w;
        } catch (NumberFormatException e) {
            return 1080;
        }
    }

    public int getRegularHeight() {
        return (int) (1.0 * height * getRegularWidth() / width);
    }

    public String getWallpaperSizeUrl(Context context) {
        double scaleRatio = 0.7
                * Math.max(
                context.getResources().getDisplayMetrics().widthPixels,
                context.getResources().getDisplayMetrics().heightPixels)
                / Math.min(width, height);
        int w = (int) (scaleRatio * width);
        int h = (int) (scaleRatio * height);
        return urls.raw + "?q=50&fm=jpg&w=" + w + "&h=" + h + "&fit=crop";
    }

    public String getRegularSizeUrl(Context context) {
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        if (DisplayUtils.isLandscape(context)) {
            return urls.raw
                    + "?q=75&fm=jpg&w="
                    + (int) (screenWidth * 0.6)
                    + "&fit=max";
        } else {
            boolean landPhoto = 1.0 * height / width * screenWidth <= screenHeight
                    - context.getResources().getDimensionPixelSize(R.dimen.photo_info_base_view_height);
            if (DisplayUtils.isTabletDevice(context)) {
                if (landPhoto) {
                    return urls.raw
                            + "?q=75&fm=jpg&w="
                            + screenWidth
                            + "&fit=max";
                } else {
                    return urls.raw
                            + "?q=75&fm=jpg&w="
                            + (int) (screenWidth * 0.6)
                            + "&fit=max";
                }
            } else {
                if (landPhoto) {
                    return urls.raw
                            + "?q=75&fm=jpg&w="
                            + (int) (screenWidth * 0.9)
                            + "&fit=max";
                } else {
                    return urls.raw
                            + "?q=75&fm=jpg&w="
                            + (int) (screenWidth * 0.6)
                            + "&fit=max";
                }
            }
        }
        /*
        return urls.raw
                + "?q=75&fm=jpg&w="
                + ((DisplayUtils.isTabletDevice(context) || DisplayUtils.isLandscape(context)) ? 1080 : 960)
                + "&fit=max";*/
    }

    // parcel.

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.loadPhotoSuccess ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasFadedIn ? (byte) 1 : (byte) 0);
        dest.writeByte(this.settingLike ? (byte) 1 : (byte) 0);
        dest.writeByte(this.complete ? (byte) 1 : (byte) 0);
        dest.writeString(this.id);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.color);
        dest.writeInt(this.views);
        dest.writeInt(this.downloads);
        dest.writeInt(this.likes);
        dest.writeByte(this.liked_by_user ? (byte) 1 : (byte) 0);
        dest.writeString(this.description);
        dest.writeParcelable(this.exif, flags);
        dest.writeParcelable(this.location, flags);
        dest.writeParcelable(this.urls, flags);
        dest.writeParcelable(this.links, flags);
        dest.writeParcelable(this.story, flags);
        dest.writeParcelable(this.stats, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeTypedList(this.current_user_collections);
        dest.writeTypedList(this.categories);
        dest.writeTypedList(this.tags);
        dest.writeParcelable(this.related_photos, flags);
        dest.writeParcelable(this.related_collections, flags);
    }

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.loadPhotoSuccess = in.readByte() != 0;
        this.hasFadedIn = in.readByte() != 0;
        this.settingLike = in.readByte() != 0;
        this.complete = in.readByte() != 0;
        this.id = in.readString();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.color = in.readString();
        this.views = in.readInt();
        this.downloads = in.readInt();
        this.likes = in.readInt();
        this.liked_by_user = in.readByte() != 0;
        this.description = in.readString();
        this.exif = in.readParcelable(Exif.class.getClassLoader());
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.urls = in.readParcelable(PhotoUrls.class.getClassLoader());
        this.links = in.readParcelable(PhotoLinks.class.getClassLoader());
        this.story = in.readParcelable(Story.class.getClassLoader());
        this.stats = in.readParcelable(Stats.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.current_user_collections = in.createTypedArrayList(Collection.CREATOR);
        this.categories = in.createTypedArrayList(Category.CREATOR);
        this.tags = in.createTypedArrayList(Tag.CREATOR);
        this.related_photos = in.readParcelable(RelatedPhotos.class.getClassLoader());
        this.related_collections = in.readParcelable(RelatedCollections.class.getClassLoader());
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    // interface.

    @Override
    public String getRegularUrl() {
        return urls.regular;
    }

    @Override
    public String getFullUrl() {
        return urls.full;
    }

    @Override
    public String getDownloadUrl() {
        if (SettingsOptionManager.getInstance(UnsplashApplication.getInstance())
                .getDownloadScale().equals("compact")) {
            return urls.full;
        } else {
            return urls.raw;
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Update load information for photo.
     *
     * @return Return true when load information has been changed. Otherwise return false.
     */
    public boolean updateLoadInformation(Photo photo) {
        this.loadPhotoSuccess = photo.loadPhotoSuccess;
        if (this.hasFadedIn != photo.hasFadedIn) {
            this.hasFadedIn = photo.hasFadedIn;
            return true;
        } else {
            return false;
        }
    }
}
