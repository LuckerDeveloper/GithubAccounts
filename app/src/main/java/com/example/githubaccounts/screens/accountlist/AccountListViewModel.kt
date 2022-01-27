package com.example.githubaccounts.screens.accountlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubaccounts.data.AccountsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AccountListViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : ViewModel() {
    val accountsLiveData = accountsRepository.accountListFlow.asLiveData()

    init {
        loadAccounts()
    }

    fun loadAccounts() {
        viewModelScope.launch {
            accountsRepository.loadAccountList()
        }
    }
}