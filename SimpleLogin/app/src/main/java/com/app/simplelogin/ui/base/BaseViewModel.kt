package com.app.simplelogin.ui.base

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import com.app.simplelogin.utils.dismissKeyboard
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    var dialog: Dialog? = null

    var disposable: CompositeDisposable = CompositeDisposable()

    fun closeKeyboard(): View.OnClickListener = View.OnClickListener { v ->
        dismissKeyboard(v.context, v.windowToken)
    }

    fun showLoader(context: Context) {
        dismissDialog()
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(ProgressBar(context))
        dialogBuilder.setCancelable(false)
        dialog = dialogBuilder.create()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.show()
    }


    fun dismissDialog() {
        dialog?.let { dialog ->
            dialog.isShowing.let {
                dialog.dismiss()
            }
            null
        }
    }

    fun dispose() {
        dismissDialog()
        disposable.dispose()
    }
}