package com.boundlesssystems.ninetest.di

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Singleton

@Module
class PicassoModule(private val context : Context) {

    private val cacheSize : Long =  15 * 1024 * 1024

    @Provides
    fun providesCacheDirectory() : File {
        return File(context.cacheDir, "picasso-cache")
    }

    @Provides
    fun providesCache(cacheDirectory : File) : Cache {
        return Cache(cacheDirectory, cacheSize)
    }

    @Provides
    fun provideHttpClientBuilder(cache : Cache) : OkHttpClient.Builder {
        return OkHttpClient.Builder().cache(cache)
    }

    @Provides
    @Singleton
    fun providesPicassoWithCache(okHttpClientBuilder : OkHttpClient.Builder) : Picasso {
        return Picasso.Builder(context).downloader(OkHttp3Downloader(okHttpClientBuilder.build())).build()
    }





}
