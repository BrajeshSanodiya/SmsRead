package com.brajesh.smsread.data.repository

import android.util.Log
import com.brajesh.smsread.data.localdatasource.SmsLocalDataSource
import com.brajesh.smsread.data.model.SmsTransInfo
import javax.inject.Inject

class SmsRepository @Inject constructor(
    private val smsLocalDataSource: SmsLocalDataSource
) {
    suspend  fun insertSmsTrans(listSmsTransInfo: List<SmsTransInfo>){
        Log.d(
            "DEBUGLOG-Database",
            "Data Inserted"
        )
        smsLocalDataSource.insertSmsTrans(listSmsTransInfo)
    }
    fun getSmsTrans()=smsLocalDataSource.getSmsTrans()

}