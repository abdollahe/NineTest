package com.boundlesssystems.ninetest.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.boundlesssystems.ninetest.viewmodel.NewsViewModel
import com.boundlesssystems.ninetest.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.github.philippeboisney.retrokotlin.di.ViewModelKey

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    internal abstract fun postMainViewModel(viewModel: NewsViewModel): ViewModel
}