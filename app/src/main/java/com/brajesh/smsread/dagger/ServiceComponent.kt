
package com.brajesh.smsread.dagger

import com.brajesh.smsread.appcoredatabase.dagger.DBComponent
import com.brajesh.smsread.dagger.scope.FeatureScope
import com.brajesh.smsread.service.SmsService
import com.brajesh.smsread.ui.MainActivity
import dagger.Component

@FeatureScope
@Component(
    dependencies = [DBComponent::class],
    modules = [MainModule::class, ServiceModule::class]
)
interface ServiceComponent {
    fun inject(smsService: SmsService)
}

