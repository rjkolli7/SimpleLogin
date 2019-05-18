package com.app.simplelogin.ui.auth.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.app.simplelogin.R
import com.app.simplelogin.db.LoginDao
import com.app.simplelogin.sharedpref.SessionManager
import com.app.simplelogin.testing.OpenForTesting
import com.app.simplelogin.ui.base.BaseViewModel
import com.app.simplelogin.ui.main.MainActivity
import com.app.simplelogin.utils.dismissKeyboard
import com.app.simplelogin.utils.isValidEmail
import com.app.simplelogin.utils.isValidPassword
import javax.inject.Inject

@OpenForTesting
class LoginViewModel @Inject constructor(private val loginDao: LoginDao) : BaseViewModel() {

    var password: String? = null
    var email: String? = null

    var countryValue: MutableLiveData<String> = MutableLiveData()

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
        if (isValid(v.context)) {
            doLogin(v.context, false)
        }
    }

    fun doLogin(context: Context, isTesting: Boolean) {
        email?.let { e ->
            val login = loginDao.findByEmail(e)
            login?.let {
                if (password == it.password) {
                    if (!isTesting) {
                        showLoader(context)
                        Handler().postDelayed({
                            Toast.makeText(context, R.string.success_login, Toast.LENGTH_LONG).show()
                            dismissDialog()
                            openMain(context)
                        }, 500)
                    }
                    SessionManager.getInstance(context).saveLogin(true)
                } else {
                    Toast.makeText(context, R.string.error_incorrect_password, Toast.LENGTH_LONG).show()
                }
            } ?: run {
                Toast.makeText(context, R.string.error_incorrect_email, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun isValid(context: Context): Boolean {
        if (email.isNullOrBlank()) {
            Toast.makeText(context, R.string.enter_email_address, Toast.LENGTH_LONG).show()
            return false
        }

        if (!isValidEmail(email!!)) {
            Toast.makeText(context, R.string.error_invalid_email, Toast.LENGTH_LONG).show()
            return false
        }

        if (password.isNullOrBlank()) {
            Toast.makeText(context, R.string.enter_password, Toast.LENGTH_LONG).show()
            return false
        }

        if (!isValidPassword(password!!)) {
            Toast.makeText(context, R.string.error_invalid_password, Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun openMain(context: Context) {
        context.startActivity(Intent(context, MainActivity::class.java))
        (context as AppCompatActivity).finish()
    }


    fun showCountryList(v: View) {
        dismissDialog()
        val dialogBuilder = AlertDialog.Builder(v.context)
        val statesList = v.resources.getStringArray(R.array.countries)
        dialogBuilder.setTitle(R.string.select)
        dialogBuilder.setItems(
            statesList
        ) { _, which ->
            countryValue.value = statesList[which]
        }
        dialog = dialogBuilder.create()
        dialog?.show()
    }
}