package com.brajesh.smsread.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brajesh.smsread.data.model.SmsTransInfo
import com.brajesh.smsread.data.repository.SmsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val smsRepository: SmsRepository) : ViewModel() {

    private val _smsTransList = MutableLiveData<List<SmsTransInfo>>()
    val smsTransList: LiveData<List<SmsTransInfo>> = _smsTransList


     fun insertSmsTrans(listSmsTransInfo: List<SmsTransInfo>){
         viewModelScope.launch(Dispatchers.IO) {
                smsRepository.insertSmsTrans(listSmsTransInfo)
                _smsTransList.postValue(smsRepository.getSmsTrans())
         }
    }

    fun getSmsTrans(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = smsRepository.getSmsTrans()
            _smsTransList.postValue(list)
        }
    }
}