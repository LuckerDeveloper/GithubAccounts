package com.example.githubaccounts.screens.accountdetails

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.githubaccounts.R
import com.example.githubaccounts.data.AccountDetails
import com.example.githubaccounts.databinding.FragmentAccountDetailsBinding
import com.example.githubaccounts.utils.Result
import com.example.githubaccounts.utils.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountDetailsFragment : Fragment(R.layout.fragment_account_details) {

    private val binding: FragmentAccountDetailsBinding by viewBinding()
    private val viewModel: AccountDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadAccountDetails()

        val login =
            AccountDetailsFragmentArgs.fromBundle(requireArguments()).accountId
        binding.collapsingToolbar.title = login

        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.accountDetailsLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    binding.accountDetails.isVisible = true
                    binding.appBarLayout.isVisible = true
                    toLoadingState(false)
                    toErrorState(false)
                    val accountDetails = result.data
                    val adapter = AccountsDetailsAdapter(createFields(result.data))
                    binding.accountDetails.adapter = adapter
                    Glide
                        .with(requireContext())
                        .load(accountDetails.avatarUrl)
                        .placeholder(R.color.light_gray)
                        .into(binding.avatar)
                }
                is Result.Error -> {
                    binding.accountDetails.isVisible = false
                    binding.appBarLayout.isVisible = false
                    toLoadingState(false)
                    toErrorState(true)
                    Log.v(TAG, "error: ${result.t.message}")
                    Toast.makeText(requireContext(), R.string.error_toast_text, Toast.LENGTH_SHORT)
                        .show()
                }
                is Result.Loading -> {
                    binding.accountDetails.isVisible = false
                    binding.appBarLayout.isVisible = false
                    toErrorState(false)
                    toLoadingState(true)
                }
            }
        }
    }


    private fun toLoadingState(isVisible: Boolean) {
        binding.loading.root.isVisible = isVisible
    }

    private fun toErrorState(isVisible: Boolean) {
        binding.error.root.isVisible = isVisible
    }

    private fun createFields(accountDetails: AccountDetails): List<Pair<String, String>> {
        return listOf(
            Pair(getString(AccountDetailsFields.LOGIN.res), accountDetails.login),
            Pair(getString(AccountDetailsFields.ID.res), accountDetails.id.toString()),
            Pair(getString(AccountDetailsFields.URL.res), accountDetails.url),
            Pair(getString(AccountDetailsFields.NAME.res), accountDetails.name),
            Pair(getString(AccountDetailsFields.COMPANY.res), accountDetails.company),
            Pair(getString(AccountDetailsFields.LOCATION.res), accountDetails.location),
            Pair(
                getString(AccountDetailsFields.FOLLOWERS.res),
                accountDetails.followers.toString()
            ),
            Pair(getString(AccountDetailsFields.CREATED_AT.res), accountDetails.createdAt),
        )
    }
}