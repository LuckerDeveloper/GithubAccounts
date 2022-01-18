package com.example.githubaccounts.data

import com.example.githubaccounts.api.GithubAccountsService
import com.example.githubaccounts.utils.Result
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Singleton
class AccountsRepository @Inject constructor(private val accountsService: GithubAccountsService) {

    private val _accountsListFlow = MutableStateFlow<Result<List<Account>>>(Result.Loading)
    val accountListFlow: StateFlow<Result<List<Account>>> = _accountsListFlow

    suspend fun loadAccounts() {
        _accountsListFlow.value = try {
            val accountList = accountsService.getAccounts()
            Result.Success(accountList)
        } catch (t: Throwable) {
            Result.Error(t)
        }
    }

}