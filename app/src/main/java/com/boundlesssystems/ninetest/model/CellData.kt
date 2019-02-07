package com.boundlesssystems.ninetest.model


/** This data class holds the data is to be shown and processed by the recyclerview. Data from the network response (NewsStories class) are processed
 * and the data in this data class are extracted from it */
data class CellData(    val header : String?
                      , val abstract : String?
                      , val byLine : String?
                      , val imageUrl : String?
                      , val timeStamp : Long?
                      , val newsUrl : String? )