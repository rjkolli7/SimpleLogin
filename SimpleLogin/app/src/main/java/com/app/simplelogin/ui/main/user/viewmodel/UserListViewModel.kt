package com.app.simplelogin.ui.main.user.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.app.simplelogin.R
import com.app.simplelogin.network.ApiInterface
import com.app.simplelogin.network.model.User
import com.app.simplelogin.testing.OpenForTesting
import com.app.simplelogin.ui.base.BaseViewModel
import com.app.simplelogin.ui.main.user.UsersListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@OpenForTesting
class UserListViewModel @Inject constructor(private val apiInterface: ApiInterface) : BaseViewModel() {

    var usersList: MutableList<User> = ArrayList()
    var usersListAdapter: UsersListAdapter? = null

    var noUsers: MutableLiveData<Boolean> = MutableLiveData()

    fun loadUsers(context: Context, showLoader: Boolean = true) {
        isTablet = context.resources.getBoolean(R.bool.isTablet)
        disposable = CompositeDisposable()
        disposable.add(apiInterface.users()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if(showLoader)
                    showLoader(context)
            }
            .subscribe(
                { response ->
                    if(!response.isNullOrEmpty()) {
                        usersList.clear()
                        usersList.addAll(response)
                        noUsers.value = false
                        usersListAdapter?.notifyDataSetChanged()
                    } else {
                        noUsers.value = true
                    }
                    dispose()
                },
                {
                    noUsers.value = true
                    dispose()
                }
            ))
    }
}