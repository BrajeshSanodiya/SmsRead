package com.brajesh.smsread.dagger


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brajesh.smsread.service.ReceiveSms
import com.brajesh.smsread.ui.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainBindsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(homeViewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindDaggerViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

}

