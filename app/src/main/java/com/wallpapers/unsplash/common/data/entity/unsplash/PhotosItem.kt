package com.wallpapers.unsplash.common.data.entity.unsplash

import com.google.gson.annotations.SerializedName

data class PhotosItem(

	@field:SerializedName("urls")
	val urls: Urls? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("description")
	val description: Any? = null,

	@field:SerializedName("links")
	val links: Links? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("categories")
	val categories: List<Any?>? = null,

	@field:SerializedName("liked_by_user")
	val likedByUser: Boolean? = null,

	@field:SerializedName("height")
	val height: Int? = null,

	@field:SerializedName("likes")
	val likes: Int? = null
)