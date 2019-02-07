package com.boundlesssystems.ninetest.repository

import com.boundlesssystems.ninetest.api.ApiService

/** Repository class that is responsible for the different io processing. Including Networking and database (if present)
 * The synchronization of data between the database (if present) and the data coming from the network is done here. Caching
 * capabilities can also be implemented in this space*/

class NewsRepository(private val apiService: ApiService) {

    // Function to request an api call for fetching news Stories
    fun getNewsStories() = this.apiService.getNewsStories()
}