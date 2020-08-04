package com.brajesh.smsread.appcoredatabase.dagger

import android.content.Context
import com.brajesh.smsread.appcoredatabase.AppCoreDatabase
import dagger.Module
import dagger.Provides

@Module
class DbModule(val context: Context) {
    @Provides // scope is not necessary for parameters stored within the module
    fun context(): Context {
        return context
    }

    @Provides
    fun provideWeatherDB(context: Context): AppCoreDatabase = AppCoreDatabase.getInstance(context)

    @Provides
    fun provideSmsTransDao(appCoreDatabase: AppCoreDatabase) =
        appCoreDatabase.smsTransDao()

}
