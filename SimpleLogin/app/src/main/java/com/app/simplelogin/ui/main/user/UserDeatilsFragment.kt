package com.app.simplelogin.ui.main.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.simplelogin.R
import com.app.simplelogin.databinding.FragmentUserDetailsBinding

class UserDeatilsFragment : Fragment() {

    lateinit var binding: FragmentUserDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false)
        return binding.root
    }
}