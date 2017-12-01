package com.wangdaye.mysplash.common.data.entity.unsplash

import com.google.gson.annotations.SerializedName

data class PreviewPhotosItem(

	@field:SerializedName("urls")
	val urls: Urls? = null,

	@field:SerializedName("id")
	val id: Int? = null
)