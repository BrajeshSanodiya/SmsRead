package com.brajesh.smsread.data.model

import java.util.*


data class SmsTransInfo (

    val smsSender: String,

    val amount: String,

    val currency: String = "INR",

    val transType: String = "UNKNOWN",

    val transDate: Long
)