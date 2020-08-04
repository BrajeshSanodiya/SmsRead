package com.brajesh.smsread.dagger

import com.brajesh.smsread.appcoredatabase.dagger.DBComponent
import com.brajesh.smsread.dagger.scope.FeatureScope
import com.brajesh.smsread.ui.MainActivity
import dagger.Component

@FeatureScope
@Component(
    dependencies = [DBComponent::class],
    modules = [MainModule::class, MainBindsModule::class]
)
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}
