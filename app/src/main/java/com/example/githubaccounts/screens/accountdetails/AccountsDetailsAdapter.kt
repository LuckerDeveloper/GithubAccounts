package com.example.githubaccounts.screens.accountdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubaccounts.databinding.ItemAcountDetailsBinding

class AccountsDetailsAdapter(
    private val fields: List<Pair<String, String>>
) : RecyclerView.Adapter<AccountsDetailsAdapter.AccountDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountDetailsViewHolder {
        val binding =
            ItemAcountDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountDetailsViewHolder, position: Int) {
        val field = fields[position]
        holder.binding.apply {
            param.text = field.first
            value.text = field.second
        }
    }

    override fun getItemCount() = fields.size

    class AccountDetailsViewHolder(val binding: ItemAcountDetailsBinding) :
        RecyclerView.ViewHolder(binding.root)
}