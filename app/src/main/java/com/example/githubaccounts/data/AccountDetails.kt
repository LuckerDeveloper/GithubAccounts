package com.example.githubaccounts.data

import com.google.gson.annotations.SerializedName

data class AccountDetails(
    val login: String,
    val id: Int,
    @field:SerializedName("avatar_url") val avatarUrl: String,
    @field:SerializedName("html_url") val url: String,
    val name: String,
    val company: String,
    val location: String,
    val followers: Long,
    @field:SerializedName("created_at") val createdAt: String,
)