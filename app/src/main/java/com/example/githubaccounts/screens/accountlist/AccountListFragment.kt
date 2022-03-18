package com.example.githubaccounts.screens.accountlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubaccounts.R
import com.example.githubaccounts.databinding.FragmentAccountListBinding
import com.example.githubaccounts.utils.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountListFragment : Fragment(R.layout.fragment_account_list) {

    private val binding: FragmentAccountListBinding by viewBinding()
    private val viewModel: AccountListViewModel by viewModels()
    private val adapter = AccountsAdapter { account ->
        viewModel.deleteAccount(account)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadAccounts()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: AccountsAdapter) {
        viewModel.accountsLiveData.observe(viewLifecycleOwner) { accounts ->
            if (accounts.isNotEmpty()) {
                adapter.submitList(accounts)
                toLoadingState(false)
                toErrorState(false)
            }
        }
        viewModel.accountsListNetworkState.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.SUCCESS -> {
                    binding.swipeContainer.isRefreshing = false
                }
                State.LOADING -> {
                    toErrorState(false)
                    if (adapter.itemCount == 0) {
                        toLoadingState(true)
                    } else {
                        binding.swipeContainer.isRefreshing = true
                    }
                }
                State.ERROR -> {
                    toLoadingState(false)
                    Toast.makeText(requireContext(), "Network error", Toast.LENGTH_LONG).show()
                    binding.swipeContainer.isRefreshing = false
                    if (adapter.itemCount == 0) toErrorState(true)
                }
            }
        }
    }

    private fun setupUI(view: View) {
        binding.apply {
            accountsRecyclerView.adapter = adapter
            accountsRecyclerView.setOnClickListener {
                view.findNavController()
                    .navigate(R.id.action_accountListFragment_to_accountDetailsFragment)
            }
            error.errorButton.setOnClickListener {
                viewModel.loadAccounts()
            }
            val navController = findNavController(this@AccountListFragment)
            toolbar
                .setupWithNavController(navController, AppBarConfiguration(navController.graph))
            swipeContainer.setOnRefreshListener {
                viewModel.loadAccounts()
            }
        }
    }

    private fun toLoadingState(isVisible: Boolean) {
        binding.loading.root.isVisible = isVisible
    }

    private fun toErrorState(isVisible: Boolean) {
        binding.error.root.isVisible = isVisible
    }
}