package com.app.simplelogin

import android.app.Activity
import android.app.Application
import com.app.simplelogin.di.injector.AppInjector
import com.app.simplelogin.testing.OpenForTesting
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

@OpenForTesting
class App: Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = activityInjector

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }
}