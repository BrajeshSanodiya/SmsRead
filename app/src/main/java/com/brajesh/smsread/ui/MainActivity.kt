package com.brajesh.smsread.ui

import android.app.ActivityManager
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brajesh.smsread.R
import com.brajesh.smsread.Util
import com.brajesh.smsread.appcoredatabase.dagger.DaggerDBComponent
import com.brajesh.smsread.appcoredatabase.dagger.DbModule
import com.brajesh.smsread.dagger.DaggerMainComponent
import com.brajesh.smsread.dagger.MainModule
import com.brajesh.smsread.service.SmsService
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val mainViewModel: MainViewModel by viewModels { viewModelFactory }

    private val smsTransAdapter = SmsTransAdapter(arrayListOf())
    private val TAG: String = "DEBUGLOG-MainActivity"
    private val RECEIVE_SMS_REQUEST = 1001
    private val IGNORE_BATTERY_OPTIMIZATION_REQUEST = 1002

    private val localReceiveSms: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d(TAG, "LocalBroadcastReceive")
            mainViewModel.getSmsTrans()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDagger()

        recycler_view_sms_list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = smsTransAdapter
        }

        observeViewModel()
    }



    private fun initDagger() {
        DaggerMainComponent.builder()
            .mainModule(MainModule(this.application))
            .dBComponent(DaggerDBComponent.builder().dbModule(DbModule(this)).build())
            .build()
            .inject(this)
    }

    private fun observeViewModel() {
        mainViewModel.smsTransList.observe(this, Observer {
            Log.d(
                TAG,
                "ViewModel List Observer updating list: ${it.toString()}"
            )
            Toast.makeText(applicationContext, "List Refresh", Toast.LENGTH_LONG).show()
            smsTransAdapter.updateList(it)
        })
    }


    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Requesting for permission")
            requestPermissions(
                arrayOf(android.Manifest.permission.RECEIVE_SMS),
                RECEIVE_SMS_REQUEST
            )
        } else if (Util.isBatteryOptimized(this)) {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            intent.data = Uri.parse("package:$packageName")
            startActivityForResult(intent, IGNORE_BATTERY_OPTIMIZATION_REQUEST)
        } else {
            if (!Util.isMyServiceRunning(this,SmsService::class.java)) {
                Log.d(TAG, "service started")
                val smsServiceIntent = Intent(this@MainActivity, SmsService::class.java)
                this.startService(smsServiceIntent)
            }

            LocalBroadcastManager.getInstance(this).registerReceiver(
                localReceiveSms,
                IntentFilter("localsmsreceiver")
            )

            mainViewModel.getSmsTrans()
        }
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localReceiveSms)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == RECEIVE_SMS_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "permission granted!")
            } else {
                Log.d(TAG, "permission not granted!")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.help_menu->
            {
                Util.callDailog(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}