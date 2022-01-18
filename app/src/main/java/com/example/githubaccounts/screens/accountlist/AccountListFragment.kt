package com.example.githubaccounts.screens.accountlist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubaccounts.R
import com.example.githubaccounts.data.Account
import com.example.githubaccounts.databinding.FragmentAccountListBinding
import com.example.githubaccounts.utils.Result
import com.example.githubaccounts.utils.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountListFragment : Fragment(R.layout.fragment_account_list) {

    private val binding: FragmentAccountListBinding by viewBinding()
    private val viewModel: AccountListViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AccountsAdapter()
        binding.accountsRecyclerView.adapter = adapter

        binding.error.errorButton.setOnClickListener {
            viewModel.loadAccounts()
        }

        binding.accountsRecyclerView.setOnClickListener {
            view.findNavController().navigate(R.id.action_accountListFragment_to_accountDetailsFragment)
        }

        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: AccountsAdapter) {
        viewModel.accountsLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success ->{
                    binding.accountsRecyclerView.isVisible = true
                    toLoadingState(false)
                    toErrorState(false)
                    adapter.submitList(result.data)
                }
                is Result.Error<List<Account>> -> {
                    binding.accountsRecyclerView.isVisible = false
                    toLoadingState(false)
                    toErrorState(true)
                    //Todo загружать кеш, если кеш пуст, то показывать кнопку новой попытки
                    Log.v(TAG, "error: ${result.t.message}")
                    Toast.makeText(requireContext(), R.string.error_toast_text, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    binding.accountsRecyclerView.isVisible = false
                    toErrorState(false)
                    toLoadingState(true)
                }
            }
        }
    }

    private fun toLoadingState(isVisible: Boolean) {
        binding.loading.root.isVisible  = isVisible
    }

    private fun toErrorState(isVisible: Boolean) {
        binding.error.root.isVisible = isVisible
    }

}