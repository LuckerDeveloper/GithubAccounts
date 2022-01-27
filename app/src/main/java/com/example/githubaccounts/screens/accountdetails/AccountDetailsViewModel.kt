package com.example.githubaccounts.screens.accountdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubaccounts.data.AccountsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountsRepository: AccountsRepository
) : ViewModel() {

    val login = savedStateHandle.get<String>(ACCOUNT_ID_SAVED_STATE_KEY)!!
    val accountDetailsLiveData = accountsRepository.accountDetailsFlow.asLiveData()

    fun loadAccountDetails() {
        viewModelScope.launch {
            accountsRepository.loadAccountDetails(login)
        }
    }

    companion object {
        private const val ACCOUNT_ID_SAVED_STATE_KEY = "accountId"
    }
}