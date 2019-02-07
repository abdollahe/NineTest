package com.boundlesssystems.ninetest.di

import com.boundlesssystems.ninetest.api.ApiService
import com.boundlesssystems.ninetest.repository.NewsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService) = NewsRepository(apiService)

}