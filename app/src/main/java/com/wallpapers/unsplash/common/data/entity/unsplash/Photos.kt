package com.wallpapers.unsplash.common.data.entity.unsplash

import com.google.gson.annotations.SerializedName

data class Photos(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<Photo?>? = null
)