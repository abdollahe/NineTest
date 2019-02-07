package com.boundlesssystems.ninetest.base

import android.app.Application
import com.boundlesssystems.ninetest.BASE_URL
import com.boundlesssystems.ninetest.di.*

/** Application context that holds the Dagger instantiation  **/
open class BaseApplication : Application() {

    // FOR DATA
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        this.appComponent = this.initDagger()
    }

    // --- Dependencies injection ---

    protected open fun initDagger(): AppComponent
            = DaggerAppComponent.builder()
            .netModule(NetModule(BASE_URL))
            .repositoryModule(RepositoryModule())
            .rxJavaModule(RxJavaModule())
            .picassoModule(PicassoModule(this))
            .build()
}