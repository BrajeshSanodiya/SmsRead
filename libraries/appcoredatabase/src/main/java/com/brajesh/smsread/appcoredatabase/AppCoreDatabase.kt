package com.brajesh.smsread.appcoredatabase


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import com.brajesh.smsread.appcoredatabase.sms.SmsTransDao
import com.brajesh.smsread.appcoredatabase.sms.SmsTransEntity

@Database(
    entities = [
        SmsTransEntity::class
    ],
    version = AppCoreDatabase.DATABASE_VERSION,
    exportSchema = false
)
abstract class AppCoreDatabase : RoomDatabase(), IDatabase {
    abstract fun smsTransDao(): SmsTransDao

    companion object {

        private const val DATABASE_NAME = "sms-read-db"
        const val DATABASE_VERSION = 2

        // For Singleton instantiation
        @Volatile
        private var instance: AppCoreDatabase? = null

        fun getInstance(context: Context): AppCoreDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppCoreDatabase {
            return Room.databaseBuilder(
                context, AppCoreDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    override suspend fun <R> runInTransaction(block: suspend () -> R): R = withTransaction(block)
}
