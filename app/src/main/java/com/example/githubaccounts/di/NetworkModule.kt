package com.example.githubaccounts.di

import com.example.githubaccounts.api.GithubAccountsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGithubAccountService(): GithubAccountsService {
        return GithubAccountsService.create()
    }
}