package com.example.githubaccounts.screens.accountdetails

import androidx.annotation.StringRes
import com.example.githubaccounts.R

enum class AccountDetailsFields(@StringRes val res: Int) {
    ID(R.string.account_details_id),
    URL(R.string.account_details_url),
    NAME(R.string.account_details_name),
    COMPANY(R.string.account_details_company),
    LOCATION(R.string.account_details_location),
    FOLLOWERS(R.string.account_details_followers),
    CREATED_AT(R.string.account_details_created_at)
}