package com.example.githubaccounts.data

import android.util.Log
import com.example.githubaccounts.api.GithubAccountsService
import com.example.githubaccounts.utils.Result
import com.example.githubaccounts.utils.TAG
import javax.inject.Inject

class AccountsRemoteDataSource @Inject constructor(
    private val accountsService: GithubAccountsService,
) {
    suspend fun getAccounts(): Result<List<Account>> {
        return try {
            val accounts = accountsService.getAccounts()
            Result.Success(accounts)
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            Result.Error(e)
        }
    }
}