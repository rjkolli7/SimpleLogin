package com.app.simplelogin.di.module

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.simplelogin.App
import com.app.simplelogin.R
import com.app.simplelogin.db.AppDb
import com.app.simplelogin.db.Login
import com.app.simplelogin.db.LoginDao
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RoomModule {

    lateinit var appDb: AppDb

    private fun getDb(@Named("app") app: App): AppDb {
        appDb = Room
            .databaseBuilder(app, AppDb::class.java, "app.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        return appDb
    }

    @Singleton
    @Provides
    fun provideDb(@Named("app") app: App) : AppDb {
        getDb(app)
        val password = "abc@12345"
        app.resources
            .getStringArray(R.array.emails)
            .map { email ->
                appDb.runInTransaction {
                    provideLoginDao(appDb).createLoginIfNotExists(
                        Login(email = email, password = password)
                    )
                    provideLoginDao(appDb).insert(Login(email = email, password = password))
                }
            }
        return appDb
    }

    @Singleton
    @Provides
    fun provideLoginDao(db: AppDb): LoginDao {
        return db.loginDao()
    }
}