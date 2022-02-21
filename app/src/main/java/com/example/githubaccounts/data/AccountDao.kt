package com.example.githubaccounts.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    suspend fun getAll(): List<Account>

    @Insert
    suspend fun insertAll(users: List<Account>)
}