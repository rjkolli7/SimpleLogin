package com.app.simplelogin.ui.auth

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.app.simplelogin.R
import com.app.simplelogin.databinding.FragmentLoginBinding
import com.app.simplelogin.di.injector.Injectable
import com.app.simplelogin.ui.auth.viewmodel.LoginViewModel
import javax.inject.Inject

class LoginFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentLoginBinding

    lateinit var model: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProviders.of(this, viewModelFactory)
            .get(LoginViewModel::class.java)
        binding.model = model
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}