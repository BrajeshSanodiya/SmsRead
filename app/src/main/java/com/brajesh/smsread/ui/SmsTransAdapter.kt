package com.brajesh.smsread.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brajesh.smsread.R
import com.brajesh.smsread.Util
import com.brajesh.smsread.data.model.SmsTransInfo
import kotlinx.android.synthetic.main.item_sms_list.view.*

class SmsTransAdapter(private val smsTransList: ArrayList<SmsTransInfo>): RecyclerView.Adapter<SmsTransAdapter.SmsTransHolder>() {

    fun updateList(newSmsTransList: List<SmsTransInfo>) {
        smsTransList.clear()
        smsTransList.addAll(newSmsTransList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsTransAdapter.SmsTransHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_sms_list,parent, false)
        return SmsTransHolder(inflatedView)
    }

    override fun getItemCount()=smsTransList.size

    override fun onBindViewHolder(holder: SmsTransAdapter.SmsTransHolder, position: Int) {
        holder.bind(smsTransList[position])
    }

    class SmsTransHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private val txtDate = view.text_date
        private val txtType = view.text_transType
        private val txtAmount = view.text_amount
        private val txtSender = view.text_sender
        fun bind(smsTrasInfo: SmsTransInfo) {
            txtDate.text = Util.covertDateInString(smsTrasInfo.transDate)
            txtType.text = smsTrasInfo.transType
            txtAmount.text=smsTrasInfo.amount
            txtSender.text=smsTrasInfo.smsSender
        }
    }
}
