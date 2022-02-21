package com.example.githubaccounts.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Account(
    val login: String,
    @PrimaryKey val id: Int,
    @field:SerializedName("avatar_url") val avatarUrl: String,
)
