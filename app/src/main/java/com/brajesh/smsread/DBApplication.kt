package com.brajesh.smsread

import com.brajesh.smsread.dagger.AppComponent
import com.brajesh.smsread.dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class DBApplication : DaggerApplication() {

    private lateinit var appComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder()
            .baseApplication(this)
            .build()

        return appComponent
    }
}
