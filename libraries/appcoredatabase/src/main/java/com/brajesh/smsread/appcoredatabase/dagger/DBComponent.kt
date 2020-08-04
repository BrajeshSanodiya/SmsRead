package com.brajesh.smsread.appcoredatabase.dagger

import com.brajesh.smsread.appcoredatabase.sms.SmsTransDao
import dagger.Component

/**
 * Component providing application wide singletons.
 * To call this make use of DBApplication.coreComponent or the
 * Activity.coreComponent extension function.
 */
@Component(modules = [DbModule::class, DbBindModule::class])
// @Singleton//ToDO uncomment
interface DBComponent {
    fun provideSmsTransDao(): SmsTransDao
}
