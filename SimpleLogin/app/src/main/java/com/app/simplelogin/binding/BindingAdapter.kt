package com.app.simplelogin.binding

import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("textChangedListener")
fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher) {
    editText.addTextChangedListener(textWatcher)
}

@BindingAdapter("onViewClickListener")
fun bindClickListener(view: View, clickListener: View.OnClickListener) {
    view.setOnClickListener(clickListener)
}