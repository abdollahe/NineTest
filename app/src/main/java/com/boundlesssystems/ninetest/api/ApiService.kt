package com.boundlesssystems.ninetest.api

import com.boundlesssystems.ninetest.model.api.NewStories
import io.reactivex.Observable
import retrofit2.http.GET


interface ApiService {

    @GET("/1/coding_test/13ZZQX/full")
    fun getNewsStories(): Observable<NewStories>

}