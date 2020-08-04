package com.brajesh.smsread.dagger

import com.brajesh.smsread.DBApplication
import com.brajesh.smsread.dagger.scope.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(modules = [AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<DBApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun baseApplication(application: DBApplication): Builder
        fun build(): AppComponent
    }
}
