package com.brajesh.smsread.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brajesh.smsread.data.model.SmsTransInfo
import com.brajesh.smsread.data.repository.SmsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SmsUseCase @Inject constructor(private val smsRepository: SmsRepository) {


    suspend fun insertSmsTrans(listSmsTransInfo: List<SmsTransInfo>){
                smsRepository.insertSmsTrans(listSmsTransInfo)
    }

    suspend fun getSmsTrans(){
            val list = smsRepository.getSmsTrans()
    }
}