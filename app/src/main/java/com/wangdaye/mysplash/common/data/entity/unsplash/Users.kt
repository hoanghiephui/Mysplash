package com.wangdaye.mysplash.common.data.entity.unsplash

import com.google.gson.annotations.SerializedName

data class Users(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)