package com.brajesh.smsread

import android.app.ActivityManager
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.PowerManager
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object{
        private val TAG: String = "DEBUGLOG-Util"

        fun isBatteryOptimized(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
                return !powerManager.isIgnoringBatteryOptimizations(context.packageName)
            }
            return false
        }

        fun isMyServiceRunning(context: Context,serviceClass: Class<*>): Boolean {
            val manager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Int.MAX_VALUE)) {
                if (serviceClass.name == service.service.className) {
                    Log.d(TAG, "Service status Running")
                    return true
                }
            }
            Log.d(TAG, "Service status Not running")
            return false
        }

        fun callDailog(context: Context) {
            val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle("Allow App to Run in Background")
            alertDialog.setMessage(
                "Please perform below steps Manually\n\n" +
                        "\t->Go to Device 'Setting'\n" +
                        "\t->Go to 'Battery optimization' / 'Optimise battery usage'\n" +
                        "\t->then 'All apps'\n" +
                        "\t->then find app '${context.resources.getString(R.string.app_name)}'\n" +
                        "\t->then click 'Don't optimize' / 'toggle off'\n\n" +
                        "after perform above steps app will run in Background"
            )
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "Okay",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
            alertDialog.show()
        }

        fun covertDateInLong(date: String): Long {
            var dateValue=date
            var recieveFormat="dd-MM-yyyy"
            val date= SimpleDateFormat(recieveFormat).parse(dateValue)
            return date.time
        }

        fun covertDateInString(date: Long): String {
            var dateValue=date
            val date=Date(dateValue)
            var returnFormat="dd-MM-yyyy"
            val formater= SimpleDateFormat(returnFormat)
            return formater.format(date)
        }

    }
}