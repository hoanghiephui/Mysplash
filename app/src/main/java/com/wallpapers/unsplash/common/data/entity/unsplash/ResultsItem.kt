package com.wallpapers.unsplash.common.data.entity.unsplash

import com.google.gson.annotations.SerializedName

data class ResultsItem(

        @field:SerializedName("total_photos")
        val totalPhotos: Int? = null,

        @field:SerializedName("followed_by_user")
        val followedByUser: Boolean? = null,

        @field:SerializedName("twitter_username")
        val twitterUsername: Any? = null,

        @field:SerializedName("last_name")
        val lastName: String? = null,

        @field:SerializedName("bio")
        val bio: Any? = null,

        @field:SerializedName("total_likes")
        val totalLikes: Int? = null,

        @field:SerializedName("photos")
        val photos: List<PhotosItem?>? = null,

        @field:SerializedName("portfolio_url")
        val portfolioUrl: Any? = null,

        @field:SerializedName("profile_image")
        val profileImage: ProfileImage? = null,

        @field:SerializedName("updated_at")
        val updatedAt: String? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("location")
        val location: Any? = null,

        @field:SerializedName("links")
        val links: Links? = null,

        @field:SerializedName("total_collections")
        val totalCollections: Int? = null,

        @field:SerializedName("id")
        val id: String? = null,

        @field:SerializedName("first_name")
        val firstName: String? = null,

        @field:SerializedName("username")
        val username: String? = null
)