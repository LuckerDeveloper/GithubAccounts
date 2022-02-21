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
class AccountsRepository @Inject constructor(
    private val accountsService: GithubAccountsService,
    private val remoteDataSource: AccountsRemoteDataSource,
    private val localDataSource: AccountsLocalDataSource,
) {

    private val _accountDetailsFlow = MutableStateFlow<Result<AccountDetails>>(Result.Loading)
    val accountDetailsFlow: StateFlow<Result<AccountDetails>> = _accountDetailsFlow

    private val _accountListFlow = MutableStateFlow<Result<List<Account>>>(Result.Loading)
    val accountListFlow: StateFlow<Result<List<Account>>> = _accountListFlow

    suspend fun loadAccountList() {
        _accountListFlow.emit(Result.Loading)
        val localAccountList = localDataSource.getAccounts()
        if (localAccountList is Result.Success && localAccountList.data.isNotEmpty()) {
            _accountListFlow.emit(localAccountList)
        }
        when (val remoteRequestResult = remoteDataSource.getAccounts()) {
            is Result.Success -> {
                localDataSource.insertAll(remoteRequestResult.data)
                val updatedLocalAccountList = localDataSource.getAccounts()
                if (updatedLocalAccountList is Result.Success) {
                    _accountListFlow.emit(updatedLocalAccountList)
                } else if (updatedLocalAccountList is Result.Error) {
                    _accountListFlow.emit(updatedLocalAccountList)
                }
            }
            is Result.Error -> _accountListFlow.emit(remoteRequestResult)
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