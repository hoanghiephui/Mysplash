package com.wangdaye.mysplash.common.data.entity.unsplash

import com.google.gson.annotations.SerializedName

data class PhotorResponse(

	@field:SerializedName("collections")
	val collections: Collections? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null,

	@field:SerializedName("related_searches")
	val relatedSearches: List<RelatedSearchesItem?>? = null,

	@field:SerializedName("photos")
	val photos: Photos? = null,

	@field:SerializedName("users")
	val users: Users? = null
)