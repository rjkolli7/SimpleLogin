package com.app.simplelogin.di.module

import com.app.simplelogin.ui.auth.AuthActivity
import com.app.simplelogin.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("unused")
abstract class ActivityBindings {

    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    abstract fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
