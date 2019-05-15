package com.app.simplelogin.ui.auth.viewmodel

import android.content.Intent
import android.view.View
import com.app.simplelogin.ui.base.BaseViewModel
import com.app.simplelogin.ui.main.MainActivity

class LoginViewModel : BaseViewModel() {

    fun onLoginButtonClick(): View.OnClickListener = View.OnClickListener { v ->
        v.context.startActivity(Intent(v.context, MainActivity::class.java))
    }

}