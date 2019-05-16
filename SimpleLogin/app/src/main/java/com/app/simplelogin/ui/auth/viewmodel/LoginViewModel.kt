package com.app.simplelogin.ui.auth.viewmodel

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.app.simplelogin.R
import com.app.simplelogin.db.LoginDao
import com.app.simplelogin.ui.base.BaseViewModel
import com.app.simplelogin.ui.main.MainActivity
import com.app.simplelogin.utils.dismissKeyboard
import com.app.simplelogin.utils.isValidEmail
import com.app.simplelogin.utils.isValidPassword
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val loginDao: LoginDao) : BaseViewModel() {

    var password: String? = null
    var email: String? = null

    fun passwordTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                password = s.toString()
            }
        }
    }

    fun emailTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                email = s.toString()
            }
        }
    }

    fun onLoginButtonClick(): View.OnClickListener = View.OnClickListener { v ->
        dismissKeyboard(v.context, v.windowToken)
        if (email.isNullOrBlank()) {
            Toast.makeText(v.context, R.string.enter_email_address, Toast.LENGTH_LONG).show()
            return@OnClickListener
        }

        if (!isValidEmail(email!!)) {
            Toast.makeText(v.context, R.string.error_invalid_email, Toast.LENGTH_LONG).show()
            return@OnClickListener
        }

        if (password.isNullOrBlank()) {
            Toast.makeText(v.context, R.string.enter_password, Toast.LENGTH_LONG).show()
            return@OnClickListener
        }

        if (!isValidPassword(password!!)) {
            Toast.makeText(v.context, R.string.error_invalid_password, Toast.LENGTH_LONG).show()
            return@OnClickListener
        }

        email?.let { e ->
            val login = loginDao.findByEmail(e)
            login?.let {
                if (password == it.password) {
                    showLoader(v.context)
                    v.postDelayed({
                        Toast.makeText(v.context, R.string.success_login, Toast.LENGTH_LONG).show()
                        dismissDialog()
                        openMain(v.context)
                    }, 500)
                } else {
                    Toast.makeText(v.context, R.string.error_incorrect_password, Toast.LENGTH_LONG).show()
                    return@OnClickListener
                }
            } ?: run {
                Toast.makeText(v.context, R.string.error_incorrect_email, Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
        }
    }

    private fun openMain(context: Context) {
        context.startActivity(Intent(context, MainActivity::class.java))
        (context as AppCompatActivity).finish()
    }
}