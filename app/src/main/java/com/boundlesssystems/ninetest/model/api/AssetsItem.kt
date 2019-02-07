package com.boundlesssystems.ninetest.model.api

data class AssetsItem(
	val identifier: String? = null,
	val byLine: String? = null,
	val acceptComments: Boolean? = null,
	val sources: List<SourcesItem?>? = null,
	val relatedImages: List<RelatedImagesItem?>? = null,
	val theAbstract: String? = null,
	val legalStatus: String? = null,
	val sponsored: Boolean? = null,
	val overrides: Overrides? = null,
	val url: String? = null,
	val numberOfComments: Int? = null,
	val assetType: String? = null,
	val timeStamp: Long? = null,
	val companies: List<Any?>? = null,
	val onTime: Long? = null,
	val relatedAssets: List<Any?>? = null,
	val id: Int? = null,
	val categories: List<CategoriesItem?>? = null,
	val lastModified: Long? = null,
	val tabletHeadline: String? = null,
	val indexHeadline: String? = null,
	val headline: String? = null,
	val authors: List<Any?>? = null
)
