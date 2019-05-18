package com.app.simplelogin.di.module

import com.app.simplelogin.ui.auth.LoginFragment
import com.app.simplelogin.ui.main.user.UserDetailsFragment
import com.app.simplelogin.ui.main.user.UserListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("unused")
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeUserListFragment(): UserListFragment

    @ContributesAndroidInjector
    abstract fun contributeUserDetailsFragment(): UserDetailsFragment

}