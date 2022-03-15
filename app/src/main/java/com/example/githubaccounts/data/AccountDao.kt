package com.example.githubaccounts.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    fun getAccountsFlow(): Flow<List<Account>>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(users: List<Account>)
}