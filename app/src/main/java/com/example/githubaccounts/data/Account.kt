package com.example.githubaccounts.data

import com.google.gson.annotations.SerializedName

data class Account(
    val login: String,
    val id: Int,
    @field:SerializedName("avatar_url") val avatarUrl: String,
)
