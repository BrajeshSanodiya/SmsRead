package com.brajesh.smsread.data.localdatasource

import com.brajesh.smsread.appcoredatabase.sms.SmsTransDao
import com.brajesh.smsread.appcoredatabase.sms.SmsTransEntity
import com.brajesh.smsread.data.model.SmsTransInfo
import javax.inject.Inject

class SmsLocalDataSource @Inject constructor(
    private val smsTransDao: SmsTransDao
) {
    fun getSmsTrans()=smsTransDao.getSmsTrans().map { it.toSmsTransInfo() }

   suspend fun insertSmsTrans(listSmsTransInfo: List<SmsTransInfo>){
        smsTransDao.insertSmsTrans(listSmsTransInfo.map { it.toSmsTransEntity() })
    }

    private fun SmsTransInfo.toSmsTransEntity(): SmsTransEntity {
        return SmsTransEntity(smsSender,amount,currency,transType,transDate)
    }

    private fun SmsTransEntity.toSmsTransInfo(): SmsTransInfo {
        return SmsTransInfo(smsSender,amount,currency,transType,transDate)
    }

}