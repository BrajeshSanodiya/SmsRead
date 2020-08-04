package com.brajesh.smsread.dagger

import com.brajesh.smsread.service.SmsService
import dagger.Module
import dagger.Provides


@Module
class ServiceModule(private val smsService: SmsService) {

    @Provides
    fun provideSmsService(): SmsService =smsService

}