package com.brajesh.smsread.dagger

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class MainModule(private val application: Application) {

    @Provides
    fun provideApplication(): Application =
        application


}
