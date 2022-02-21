package com.example.githubaccounts.data

import android.util.Log
import com.example.githubaccounts.utils.Result
import com.example.githubaccounts.utils.TAG
import javax.inject.Inject

class AccountsLocalDataSource @Inject constructor(private val accountDao: AccountDao) {

    suspend fun getAccounts(): Result<List<Account>> {
        return try {
            Result.Success(accountDao.getAll())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun insertAll(accounts: List<Account>) {
        try {
            accountDao.insertAll(accounts)
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
        }
    }
}