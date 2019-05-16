package com.app.simplelogin.utils

import android.text.TextUtils
import android.util.Patterns

fun isValidEmail(target: String): Boolean =
    !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()

fun isValidPassword(phone: String): Boolean = phone.length > 5