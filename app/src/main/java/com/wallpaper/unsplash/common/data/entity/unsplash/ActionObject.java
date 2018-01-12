package com.wallpaper.unsplash.common.data.entity.unsplash;

import java.util.ArrayList;
import java.util.List;

/**
 * Action object.
 * */

public class ActionObject {
    // comment part.
    public boolean hasFadedIn = false;
    public String id;
    public int downloads;
    public PhotoLinks links;

    // photo part.
    public boolean loadPhotoSuccess = false;
    public boolean settingLike = false;
    public String created_at;
    public String updated_at;
    public int width;
    public int height;
    public String color;
    public int likes;
    public boolean liked_by_user;
    public Exif exif;
    public PhotoUrls urls;
    public User user;
    public List<Collection> current_user_collections;
    public List<Category> categories;

    // user part.
    public String username;
    public String name;
    public String first_name;
    public String last_name;
    public String portfolio_url;
    public String bio;
    public int total_likes;
    public int total_photos;
    public int total_collections;
    public boolean followed_by_user;
    public ProfileImage profile_image;
    public Badge badge;

    public ActionObject(Photo photo) {
        loadPhotoSuccess = photo.loadPhotoSuccess;
        hasFadedIn = photo.hasFadedIn;
        settingLike = photo.settingLike;
        id = photo.id;
        created_at = photo.created_at;
        updated_at = photo.updated_at;
        width = photo.width;
        height = photo.height;
        color = photo.color;
        downloads = photo.downloads;
        likes = photo.likes;
        liked_by_user = photo.liked_by_user;
        exif = photo.exif;
        urls = photo.urls;
        links = photo.links;
        user = photo.user;
        current_user_collections = new ArrayList<>(photo.current_user_collections);
        categories = new ArrayList<>(photo.categories);
    }

    public ActionObject(User user) {
        hasFadedIn = user.hasFadedIn;
        id = user.id;
        downloads = user.downloads;
        username = user.username;
        name = user.name;
        first_name = user.first_name;
        last_name = user.last_name;
        portfolio_url = user.portfolio_url;
        bio = user.bio;
        total_likes = user.total_likes;
        total_photos = user.total_photos;
        total_collections = user.total_collections;
        followed_by_user = user.followed_by_user;
        profile_image = user.profile_image;
        badge = user.badge;
    }

    public Photo castToPhoto() {
        Photo photo = new Photo();
        photo.loadPhotoSuccess = loadPhotoSuccess;
        photo.hasFadedIn = hasFadedIn;
        photo.settingLike = settingLike;
        photo.id = id;
        photo.created_at = created_at;
        photo.updated_at = updated_at;
        photo.width = width;
        photo.height = height;
        photo.color = color;
        photo.downloads = downloads;
        photo.likes = likes;
        photo.liked_by_user = liked_by_user;
        photo.exif = exif;
        photo.urls = urls;
        photo.links = links;
        photo.user = user;
        photo.current_user_collections = new ArrayList<>(current_user_collections);
        photo.categories = new ArrayList<>(categories);
        return photo;
    }

    public User castToUser() {
        User user = new User();
        user.id = id;
        user.username = username;
        user.name = name;
        user.first_name = first_name;
        user.last_name = last_name;
        user.portfolio_url = portfolio_url;
        user.bio = bio;
        user.total_likes = total_likes;
        user.total_photos = total_photos;
        user.total_collections = total_collections;
        user.followed_by_user = followed_by_user;
        user.downloads = downloads;
        user.profile_image = profile_image;
        user.badge = badge;
        return user;
    }
}
