package com.boundlesssystems.ninetest.di

import com.boundlesssystems.ninetest.base.BaseTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, RepositoryModule::class, ViewModelModule::class, TestRxJavaModule::class])
interface TestAppComponent {
    fun inject(baseTest: BaseTest)
}