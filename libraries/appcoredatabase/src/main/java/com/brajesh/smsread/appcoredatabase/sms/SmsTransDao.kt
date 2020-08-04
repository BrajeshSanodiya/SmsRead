package com.brajesh.smsread.appcoredatabase.sms

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.brajesh.smsread.appcoredatabase.sms.SmsTransEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SmsTransDao {
    @Query("SELECT * FROM smstransaction ORDER BY transDate DESC,rowIndex  DESC")
    fun getSmsTrans(): List<SmsTransEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSmsTrans(smsTrans: List<SmsTransEntity>): List<Long>

    @Query("DELETE FROM smstransaction")
    suspend fun deleteAllSmsTrans()

    @Transaction
    suspend fun clearAndInsertSmsTrans(
        smsTrans: List<SmsTransEntity>,
        clearPreviousData: Boolean
    ): List<Long> {
        if (clearPreviousData) {
            deleteAllSmsTrans()
        }
        return insertSmsTrans(smsTrans)
    }
}