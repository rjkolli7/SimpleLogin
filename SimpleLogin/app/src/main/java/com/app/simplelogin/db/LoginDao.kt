package com.app.simplelogin.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Interface for database access for login related operations.
 */
@Dao
abstract class LoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(login: Login)

    @Query("SELECT * FROM Login WHERE email = :email")
    abstract fun findByEmail(email: String): Login?

    @Query("SELECT * FROM Login")
    abstract fun allUsers(): List<Login>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createLoginIfNotExists(login: Login): Long
}