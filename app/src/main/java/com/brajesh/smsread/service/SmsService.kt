package com.brajesh.smsread.service

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import com.brajesh.smsread.appcoredatabase.dagger.DaggerDBComponent
import com.brajesh.smsread.appcoredatabase.dagger.DbModule
import com.brajesh.smsread.dagger.DaggerServiceComponent
import javax.inject.Inject


class SmsService : Service() {

    @Inject
    lateinit var  receiveSms:ReceiveSms

    init {
        DaggerServiceComponent.builder()
            .dBComponent(DaggerDBComponent.builder().dbModule(DbModule(this)).build())
            .build()
            .inject(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("SmsService","OnBind")
       intent?.let {  receiveSms.onReceive(applicationContext,intent)
       }
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        registerReceiver(receiveSms, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiveSms)
    }

}