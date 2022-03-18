package com.example.githubaccounts.screens.accountlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubaccounts.data.Account
import com.example.githubaccounts.data.AccountsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AccountListViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : ViewModel() {
    val accountsLiveData = accountsRepository.accountListFlow.asLiveData()
    val accountsListNetworkState = accountsRepository.accountListNetworkState.asLiveData()

    fun loadAccounts() {
        viewModelScope.launch {
            accountsRepository.loadAccountList()
        }
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            accountsRepository.deleteAccount(account)
        }
    }
}