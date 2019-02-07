package com.boundlesssystems.ninetest.di

import com.boundlesssystems.ninetest.base.BaseActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, RepositoryModule::class, ViewModelModule::class, RxJavaModule::class , PicassoModule::class])
interface AppComponent {
    fun inject(baseActivity: BaseActivity)
}