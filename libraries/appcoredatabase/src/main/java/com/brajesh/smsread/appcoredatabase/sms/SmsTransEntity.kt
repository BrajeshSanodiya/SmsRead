package com.brajesh.smsread.appcoredatabase.sms

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity(
    tableName = "smstransaction"
)
data class SmsTransEntity (
    val smsSender: String,
    val amount: String,
    val currency: String = "INR",
    val transType: String = "UNKNOWN",
    val transDate: Long
) {
    @PrimaryKey(autoGenerate = true)
    var rowIndex: Long = 0
}