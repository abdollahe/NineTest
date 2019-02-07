package com.boundlesssystems.ninetest.model.api

/** Top level class of the API response **/
data class NewStories(
	val displayName: String? = null,
	val relatedImages: List<Any?>? = null,
	val sponsored: Boolean? = null,
	val url: String? = null,
	val assetType: String? = null,
	val timeStamp: Long? = null,
	val onTime: Long? = null,
	val assets: List<AssetsItem?>? = null,
	val relatedAssets: List<Any?>? = null,
	val id: Int? = null,
	val categories: List<Any?>? = null,
	val lastModified: Long? = null,
	val authors: List<Any?>? = null
)
