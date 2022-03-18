package com.example.githubaccounts.data

import android.util.Log
import com.example.githubaccounts.api.GithubAccountsService
import com.example.githubaccounts.utils.Result
import com.example.githubaccounts.utils.State
import com.example.githubaccounts.utils.TAG
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
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

    val accountListFlow: Flow<List<Account>> = localDataSource.accountsFlow
    private val _accountListNetworkState: MutableStateFlow<State> = MutableStateFlow(State.LOADING)
    val accountListNetworkState: StateFlow<State> = _accountListNetworkState

    suspend fun loadAccountList() {
        _accountListNetworkState.value = State.LOADING
        when (val remoteRequestResult = remoteDataSource.getAccounts()) {
            is Result.Success -> {
                localDataSource.insertAll(remoteRequestResult.data)
                _accountListNetworkState.value = State.SUCCESS
            }
            is Result.Error -> _accountListNetworkState.value = State.ERROR
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

    suspend fun deleteAccount(account: Account) {
        localDataSource.delete(account)
    }
}