package com.example.githubaccounts.data

import android.util.Log
import com.example.githubaccounts.api.GithubAccountsService
import com.example.githubaccounts.utils.Result
import com.example.githubaccounts.utils.TAG
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Singleton
class AccountsRepository @Inject constructor(private val accountsService: GithubAccountsService) {

    private val _accountDetailsFlow = MutableStateFlow<Result<AccountDetails>>(Result.Loading)
    val accountDetailsFlow: StateFlow<Result<AccountDetails>> = _accountDetailsFlow

    private val _accountListFlow = MutableStateFlow<Result<List<Account>>>(Result.Loading)
    val accountListFlow: StateFlow<Result<List<Account>>> = _accountListFlow

    suspend fun loadAccountList() {
        _accountListFlow.emit(Result.Loading)
        try {
            val accountList = accountsService.getAccounts()
            _accountListFlow.emit(Result.Success(accountList))
        } catch (t: Throwable) {
            _accountListFlow.emit(Result.Error(t))
            Log.e(TAG, "net error: ${t.message}")
        }
    }

    suspend fun loadAccountDetails(login: String) {
        _accountDetailsFlow.emit(Result.Loading)
        try {
            val accountDetails = accountsService.getAccountDetails(login)
            _accountDetailsFlow.emit(Result.Success(accountDetails))
        } catch (t: Throwable) {
            _accountDetailsFlow.emit(Result.Error(t))
            Log.e(TAG, "net error: ${t.message}")
        }
    }
}