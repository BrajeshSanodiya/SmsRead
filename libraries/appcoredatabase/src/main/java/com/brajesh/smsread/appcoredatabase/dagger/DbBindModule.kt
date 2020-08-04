package com.brajesh.smsread.appcoredatabase.dagger

import android.content.Context
import com.brajesh.smsread.appcoredatabase.AppCoreDatabase
import com.brajesh.smsread.appcoredatabase.IDatabase
import dagger.Binds
import dagger.Module

@Module
abstract class DbBindModule(val context: Context) {
    @Binds
    abstract fun provideIDatabase(appCoreDatabase: AppCoreDatabase): IDatabase
}
