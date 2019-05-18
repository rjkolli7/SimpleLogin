package com.app.simplelogin.ui.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.app.simplelogin.R
import com.app.simplelogin.network.model.User
import com.app.simplelogin.utils.dismissKeyboard
import io.reactivex.disposables.CompositeDisposable
import java.lang.NullPointerException

open class BaseViewModel : ViewModel() {

    var dialog: Dialog? = null

    var disposable: CompositeDisposable = CompositeDisposable()

    var isTablet: Boolean = false

    var childFragmentManager: FragmentManager? = null

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

    fun navigateUserDetails(v: View, user: User) {
        isTablet = v.context.resources.getBoolean(R.bool.isTablet)
        val bundle = bundleOf("details" to user)
        if(!isTablet) {
            Navigation.findNavController(v).navigate(
                R.id.userDetails,
                bundle
            )
        } else {
            navMasterUserDetails(bundle)
        }
    }

    private fun navMasterUserDetails(bundle: Bundle) {
        childFragmentManager?.let {
            val navHostFragment =
                it.findFragmentById(R.id.nav_user_details_graph) as NavHostFragment
            navHostFragment.navController.navigate(R.id.userDetailsFragment, bundle)
        } ?: run {
            throw NullPointerException("FragmentManager should be assigned with ChildFragmentManager")
        }
    }
}