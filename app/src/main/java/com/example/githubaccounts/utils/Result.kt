package com.example.githubaccounts.utils

sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error<out T>(val t: Throwable, val data: T? = null): Result<T>()
    object Loading : Result<Nothing>()
}