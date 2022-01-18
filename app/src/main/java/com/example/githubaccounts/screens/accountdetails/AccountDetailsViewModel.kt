package com.example.githubaccounts.screens.accountdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.githubaccounts.data.AccountsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
): ViewModel() {

    val accountsLiveData = accountsRepository.accountListFlow.asLiveData()
}