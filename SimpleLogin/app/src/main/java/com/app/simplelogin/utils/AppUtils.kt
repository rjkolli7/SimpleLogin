package com.app.simplelogin.utils

import android.text.TextUtils
import java.util.regex.Pattern

fun isValidEmail(target: String): Boolean =
    !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()

fun isValidPassword(phone: String): Boolean = phone.length > 5

object Patterns {
    private const val EMAIL_PATTERN =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    val EMAIL_ADDRESS = Pattern.compile(EMAIL_PATTERN)!!
}