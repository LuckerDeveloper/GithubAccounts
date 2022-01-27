package com.example.githubaccounts.screens.accountlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubaccounts.R
import com.example.githubaccounts.data.Account
import com.example.githubaccounts.databinding.ItemAccountBinding

class AccountsAdapter :
    ListAdapter<Account, AccountsAdapter.AccountsViewHolder>(AccountItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        val binding = ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        val account = getItem(position)
        holder.viewBinding.apply {
            login.text = account.login
            id.text = account.id.toString()
            Glide
                .with(this.root.context)
                .load(account.avatarUrl)
                .placeholder(R.color.light_gray)
                .into(avatar)
        }
        holder.viewBinding.root.setOnClickListener { view ->
            val direction =
                AccountListFragmentDirections.actionAccountListFragmentToAccountDetailsFragment(
                    account.login
                )
            view.findNavController().navigate(direction)
        }
    }

    class AccountsViewHolder(val viewBinding: ItemAccountBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}

private class AccountItemDiffCallback : DiffUtil.ItemCallback<Account>() {

    override fun areItemsTheSame(oldItem: Account, newItem: Account) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Account, newItem: Account) = oldItem == newItem
}