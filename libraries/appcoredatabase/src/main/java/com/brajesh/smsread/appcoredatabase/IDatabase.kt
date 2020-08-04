package com.brajesh.smsread.appcoredatabase

interface IDatabase {
    suspend fun <R> runInTransaction(block: suspend () -> R): R
}
