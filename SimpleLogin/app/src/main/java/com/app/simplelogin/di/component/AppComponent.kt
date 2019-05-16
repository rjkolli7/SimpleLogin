package com.app.simplelogin.di.component

import com.app.simplelogin.App
import com.app.simplelogin.di.module.ActivityBindings
import com.app.simplelogin.di.module.AppModule
import com.app.simplelogin.di.module.RoomModule
import com.app.simplelogin.network.ApiInterface
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        AppModule::class,
        RoomModule::class,
        ActivityBindings::class]
)
interface AppComponent {

    fun getAPiInterface(): ApiInterface

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(@Named("app")application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}