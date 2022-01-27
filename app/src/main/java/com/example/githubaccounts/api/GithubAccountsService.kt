package com.example.githubaccounts.api

import com.example.githubaccounts.data.Account
import com.example.githubaccounts.data.AccountDetails
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubAccountsService {

    @GET("/users")
    suspend fun getAccounts(): List<Account>

    @GET("/users/{username}")
    suspend fun getAccountDetails(@Path("username") login: String): AccountDetails

    companion object {
        private const val BASE_URL = "https://api.github.com"

        fun create(): GithubAccountsService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubAccountsService::class.java)
        }
    }
}