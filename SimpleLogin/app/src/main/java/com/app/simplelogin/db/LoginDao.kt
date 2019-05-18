package com.app.simplelogin.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.simplelogin.testing.OpenForTesting

/**
 * Interface for database access for login related operations.
 */
@Dao
@OpenForTesting
abstract class LoginDao {

    @Query("SELECT * FROM Login WHERE email = :email")
    abstract fun findByEmail(email: String): Login?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createLoginIfNotExists(login: Login): Long
}