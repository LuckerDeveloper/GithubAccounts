package com.example.githubaccounts.data

import android.util.Log
import com.example.githubaccounts.utils.TAG
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class AccountsLocalDataSource @Inject constructor(private val accountDao: AccountDao) {

    val accountsFlow: Flow<List<Account>>
        get() = accountDao.getAccountsFlow()

    suspend fun insertAll(accounts: List<Account>) {
        try {
            accountDao.insertAll(accounts)
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
        }
    }

    suspend fun delete(account: Account) {
        try {
            accountDao.delete(account)
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
        }
    }
}