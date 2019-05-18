package com.app.simplelogin.sharedpref

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SessionManager private constructor() {

    var mSecureSharedPreferences: SharedPreferences?

    val isLogin: Boolean
        get() = getBooleanValue(LOGIN)

    /**
     * Single point for the app to get the secure prefs object
     *
     * @return SecureSharedPreferences
     */
    private val sharedPreferences: SharedPreferences
        get() {
            if (mSecureSharedPreferences == null) {
                mSecureSharedPreferences = SecurePreferences(mContext, "abc@12345", PREF)
                SecurePreferences.setLoggingEnabled(false)
            }
            return mSecureSharedPreferences!!
        }

    init {
        mSecureSharedPreferences = sharedPreferences
    }

    fun saveLogin(isLogin: Boolean) {
        saveBooleanValue(LOGIN, isLogin)
    }

    fun saveStringValue(key: String, value: String) {
        sharedPreferences
        mSecureSharedPreferences!!.edit().putString(key, value).apply()
    }

    fun getStringValue(key: String): String? {
        sharedPreferences
        return mSecureSharedPreferences!!.getString(key, "")
    }

    fun saveIntValue(key: String, value: Int) {
        sharedPreferences
        mSecureSharedPreferences!!.edit().putInt(key, value).apply()
    }

    fun getIntValue(key: String): Int? {
        sharedPreferences
        return mSecureSharedPreferences!!.getInt(key, 0)
    }

    fun saveBooleanValue(key: String, value: Boolean) {
        sharedPreferences
        mSecureSharedPreferences!!.edit().putBoolean(key, value).apply()
    }

    fun getBooleanValue(key: String): Boolean {
        sharedPreferences
        return mSecureSharedPreferences!!.getBoolean(key, false)
    }

    fun clearAllSharedPreferences() {
        sharedPreferences
        try {
            (mSecureSharedPreferences as SecurePreferences).handlePasswordChange("", mContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        INSTANCE = null
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: SessionManager? = null

        @SuppressLint("StaticFieldLeak")
        private var mContext: Context? = null

        private val PREF = "sample_login_prefs.xml"

        private val LOGIN = "login"

        fun getInstance(context: Context): SessionManager {
            mContext = context
            if (INSTANCE == null)
                INSTANCE = SessionManager()

            return INSTANCE as SessionManager
        }
    }
}
