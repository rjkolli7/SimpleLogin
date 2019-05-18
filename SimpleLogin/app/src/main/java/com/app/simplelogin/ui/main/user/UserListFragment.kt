package com.app.simplelogin.ui.main.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.simplelogin.R
import com.app.simplelogin.databinding.FragmentUsersBinding
import com.app.simplelogin.di.injector.Injectable
import com.app.simplelogin.ui.main.user.viewmodel.UserListViewModel
import kotlinx.android.synthetic.main.fragment_users.*
import javax.inject.Inject

class UserListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentUsersBinding

    lateinit var model: UserListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_users, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProviders.of(this, viewModelFactory)
            .get(UserListViewModel::class.java)

        model.childFragmentManager = childFragmentManager
        model.usersListAdapter = UsersListAdapter(model.usersList, childFragmentManager)

        users_list_rv.layoutManager = LinearLayoutManager(activity)
        users_list_rv.setHasFixedSize(true)
        users_list_rv.adapter = model.usersListAdapter
        users_list_rv.addItemDecoration(DividerItemDecoration(activity,
            DividerItemDecoration.VERTICAL)
        )
        if(model.usersList.isEmpty())
            model.loadUsers(activity!!)
        else
            model.loadUsers(activity!!, false)

        binding.model = model
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.user_list)
    }

    override fun onDestroy() {
        super.onDestroy()
        model.dispose()
    }
}