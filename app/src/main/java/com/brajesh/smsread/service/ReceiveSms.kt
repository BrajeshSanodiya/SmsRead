package com.brajesh.smsread.service

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.brajesh.smsread.Util
import com.brajesh.smsread.data.model.SmsTransInfo
import com.brajesh.smsread.data.repository.SmsRepository
import com.brajesh.smsread.ui.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject
import kotlin.collections.ArrayList


class ReceiveSms @Inject constructor() : BroadcastReceiver() {
    private val TAG: String = "DEBUGLOG-ReceiveSms"

    @Inject
    lateinit var smsUseCase: SmsUseCase

    override fun onReceive(context: Context?, intent: Intent) {
        // Toast.makeText(context,"Sms Received!", Toast.LENGTH_LONG).show()
        Log.d(
            TAG,
            "Sms Received!"
        )
        val bundle = intent.extras
        try {
            if (bundle != null) {
                val pdusObj = bundle["pdus"] as Array<Any>?
                val listSmsTransInfo = ArrayList<SmsTransInfo>()
                for (i in pdusObj!!.indices) {
                    val currentMessage = getIncomingMsg(pdusObj[i], bundle)
                    val smsSender = getValidSender(currentMessage)
                    if (smsSender.isNotEmpty()) {
                        if (isValidMessage(currentMessage) && !isOTPMessage(currentMessage)) {
                            val amt = getValidAmount(currentMessage)
                            val date = getValidDate(currentMessage)
                            val transType = getTransactionType(currentMessage)

                            if (amt.isNotEmpty() && date.isNotEmpty()) {
                                val dateInLong = Util.covertDateInLong(date)
                                val smsTransInfo = SmsTransInfo(
                                    smsSender = smsSender,
                                    amount = amt,
                                    currency = "INR",
                                    transType = transType,
                                    transDate = dateInLong
                                )
                                listSmsTransInfo.add(smsTransInfo)
                                Log.d(
                                    TAG,
                                    "SmSTransInfo : $smsTransInfo"
                                )
                            }
                        }
                    }
                }

                if (listSmsTransInfo.isNotEmpty()) {
                    GlobalScope.launch {
                        smsUseCase.insertSmsTrans(listSmsTransInfo)
                        LocalBroadcastManager.getInstance(context!!.applicationContext)
                            .sendBroadcast(Intent("localsmsreceiver"))
                    }

                }

            }
        } catch (e: Exception) {
            Log.d(TAG, "Exception smsReceiver$e")
        }
    }


    private fun getTransactionType(currentMessage: SmsMessage): String {
        val regExTransType: Pattern = Pattern.compile("[Cc]redit|[Dd]ebit")
        val matcherTransType: Matcher = regExTransType.matcher(currentMessage.displayMessageBody)
        if (matcherTransType.find()) {
            return try {
                val transType: String = matcherTransType.group(0)
                Log.d(
                    TAG,
                    "Transaction Type: $transType"
                )
                transType
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "Transaction Type: UNKNOWN")
                "UNKNOWN"
            }
        } else {
            Log.d(TAG, "Transaction Type: UNKNOWN")
            return "UNKNOWN"
        }
    }

    private fun isOTPMessage(currentMessage: SmsMessage): Boolean {
        val regExOTPMsg = Pattern.compile("(?=.*[Oo][tT][pP].*|.*[Pp][Ii][Nn].*)")
        val matcherOTPMsg: Matcher = regExOTPMsg.matcher(currentMessage.displayMessageBody)
        if (matcherOTPMsg.find()) {
            Log.d(TAG, "Is OTP/PIN MSG: True")
            return true
        } else {
            Log.d(TAG, "Is OTP/PIN MSG: False")
            return false
        }
    }

    private fun getValidDate(currentMessage: SmsMessage): String {
        val regExAmount: Pattern =
            Pattern.compile("(\\d{2}-\\d{2}-(\\d{4}|\\d{2}))|(\\d{2}-([Jj][aA][nN]|[Ff][eE][bB]|[Mm][aA][rR]|[Aa][pP][rR]|[Mm][aA][yY]|[Jj][uU][nN]|[Jj][uU][lL]|[Aa][uU][gG]|[Ss][eE][pP][tT]|[Oo][cC][tT]|[Nn][oO][vV]|[Dd][eE][cC])-(\\d{4}|\\d{2}))")
        val matcherAmount: Matcher = regExAmount.matcher(currentMessage.displayMessageBody)
        if (matcherAmount.find()) {
            return try {
                var dateString: String = matcherAmount.group(0)
                Log.d(
                    TAG,
                    "Transaction Date: $dateString"
                )

                getvalidDateFormat(dateString)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        } else {
            Log.d(TAG, "Transaction Date: NOT FOUND")
            return ""
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getvalidDateFormat(dateString: String): String {
        var dateValue = dateString
        var recieveFormat = "dd-MM-yyyy"
        val returnFormat = "dd-MM-yyyy"
        when (dateValue.length) {
            8 -> {
                recieveFormat = "dd-MM-yy"
            }
            9 -> {
                recieveFormat = "dd-MMM-yy"
            }
            11 -> {
                recieveFormat = "dd-MMM-yyyy"
            }
        }
        val date = SimpleDateFormat(recieveFormat).parse(dateValue)
        dateValue = SimpleDateFormat(returnFormat).format(date)
        Log.d(
            TAG,
            "Formatted Transaction Date: $dateValue"
        )
        return dateValue
    }

    private fun getValidAmount(currentMessage: SmsMessage): String {
        val regExAmount: Pattern =
            Pattern.compile("[rR][sS.]\\.?\\s*[,\\d]+\\.?\\d{0,2}|[iI][nN][rR.]\\.?\\s*[,\\d]+\\.?\\d{0,2}")
        val matcherAmount: Matcher = regExAmount.matcher(currentMessage.displayMessageBody)
        if (matcherAmount.find()) {
            return try {
                var amount: String = matcherAmount.group(0)
                Log.d(
                    TAG,
                    "Transaction Amount: $amount"
                )
                amount = amount.replace("[rR][sS][.\\s]".toRegex(), "")
                amount = amount.replace("[Ii][Nn][Rr][.\\s]".toRegex(), "")
                amount = amount.replace(" ".toRegex(), "")
                amount = amount.replace(",".toRegex(), "")
                Log.d(
                    TAG,
                    "Formatted Transaction Amount: $amount"
                )
                amount
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(
                    TAG,
                    "Transaction Amount: NOT FOUND"
                )
                ""
            }
        } else {
            Log.d(TAG, "Transaction Amount: NOT FOUND")
            return ""
        }
    }

    private fun isValidMessage(currentMessage: SmsMessage): Boolean {
        val regExTransactinalMsg =
            Pattern.compile("(?=.*[Aa]ccount.*|.*[Aa]/[Cc].*|.*[Cc][Aa][Rr][Dd].*)(?=.*[Cc]redit.*|.*[Dd]ebit.*)(?=.*[Ii][Nn][Rr].*|.*[Rr][Ss].*)")
        val matcherTransactinalMsg: Matcher =
            regExTransactinalMsg.matcher(currentMessage.displayMessageBody)
        if (matcherTransactinalMsg.find()) {
            return try {
                Log.d(TAG, "Is Transactional Msg : True")
                true
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "Is Transactional Msg : False")
                false
            }
        } else {
            Log.d(TAG, "Is Transactional Msg : False")
            return false
        }
    }

    private fun getValidSender(currentMessage: SmsMessage): String {
        val regExSender: Pattern = Pattern.compile("[a-zA-Z0-9]{2}-[a-zA-Z0-9]{6}")
        val m: Matcher = regExSender.matcher(currentMessage.displayOriginatingAddress)
        if (m.find()) {
            return try {
                val phoneNumber: String = m.group(0)
                Log.d(
                    TAG,
                    "Sender: $phoneNumber"
                )
                phoneNumber
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "Sender: UNKNOWN")
                "UN-SENDER"
            }
        } else {
            Log.d(TAG, "Sender: UNKNOWN")
            return "UN-SENDER"
        }

    }


    private fun getIncomingMsg(pdusObj: Any, bundle: Bundle): SmsMessage {
        val currentMessage: SmsMessage
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val format = bundle.getString("format")
            currentMessage = SmsMessage.createFromPdu(
                pdusObj as ByteArray,
                format
            )
            Log.d(
                TAG,
                "Current Message ->format:" + format + " ->OriginatingAddress:" + currentMessage.displayOriginatingAddress + " ->MsgBody:" + currentMessage.messageBody
            )
        } else {
            currentMessage =
                SmsMessage.createFromPdu(pdusObj as ByteArray)
        }
        return currentMessage
    }
}