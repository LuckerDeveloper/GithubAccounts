package com.example.githubaccounts.screens.accountdetails

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubaccounts.R
import com.example.githubaccounts.databinding.FragmentAccountDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountDetailsFragment : Fragment(R.layout.fragment_account_details) {

    private val binding: FragmentAccountDetailsBinding by viewBinding()
    private val viewModel: AccountDetailsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fields = listOf(
            Pair("a", "a"),
            Pair("a", "a"),
            Pair("a", "a"),
            Pair("a", "a"),
            Pair("a", "a"),
            Pair("a", "a"),
            Pair("a", "a"),
            Pair("a", "a"),
            )
        val adapter = AccountsDetailsAdapter(createFields())
        binding.recyclerView.adapter = adapter
    }

    private fun createFields(): List<Pair<String, String>> {
        //TODO 
        return listOf(
            Pair("a", "a"),
            Pair("a", "a"),
            Pair("a", "a"),
            Pair("a", "a"),
            Pair("a", "a"),
            Pair("a", "a"),
            Pair("a", "a"),
            Pair("a", "a"),
        )
    }
}