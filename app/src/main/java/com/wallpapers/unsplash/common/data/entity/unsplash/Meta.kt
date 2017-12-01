package com.wallpapers.unsplash.common.data.entity.unsplash

import com.google.gson.annotations.SerializedName

data class Meta(

	@field:SerializedName("photo_id")
	val photoId: Int? = null,

	@field:SerializedName("description")
	val description: Any? = null,

	@field:SerializedName("index")
	val index: Boolean? = null,

	@field:SerializedName("text")
	val text: Any? = null,

	@field:SerializedName("canonical")
	val canonical: Any? = null,

	@field:SerializedName("keyword")
	val keyword: String? = null,

	@field:SerializedName("title")
	val title: Any? = null,

	@field:SerializedName("suffix")
	val suffix: String? = null
)