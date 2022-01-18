package com.example.githubaccounts.api

import com.example.githubaccounts.data.Account
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GithubAccountsService {

    @GET("/users")
    suspend fun getAccounts(): List<Account>

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