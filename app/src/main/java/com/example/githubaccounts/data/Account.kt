package com.example.githubaccounts.data

import com.google.gson.annotations.SerializedName

data class Account(
    @field:SerializedName("login") val login: String,
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("avatar_url") val avatarUrl: String,
)
