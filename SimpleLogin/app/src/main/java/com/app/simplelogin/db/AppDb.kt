package com.app.simplelogin.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Main database description.
 */
@Database(
    entities = [
        Login::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun loginDao(): LoginDao
}
