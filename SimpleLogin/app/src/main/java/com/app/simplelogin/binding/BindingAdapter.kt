package com.app.simplelogin.binding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("onViewClickListener")
fun bindClickListener(view: View, clickListener: View.OnClickListener) {
    view.setOnClickListener(clickListener)
}