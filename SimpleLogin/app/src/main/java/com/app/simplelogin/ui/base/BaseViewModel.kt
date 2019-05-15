package com.app.simplelogin.ui.base

import android.view.View
import androidx.lifecycle.ViewModel
import com.app.simplelogin.utils.dismissKeyboard

open class BaseViewModel : ViewModel() {

    fun closeKeyboard(): View.OnClickListener = View.OnClickListener { v ->
        dismissKeyboard(v.context, v.windowToken)
    }
}