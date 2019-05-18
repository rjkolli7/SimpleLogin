package com.app.simplelogin.ui.di

import com.app.simplelogin.di.module.AppModule
class MockAppModule : AppModule() {

    fun getApiInterface() = providesApiInterface(provideRetrofit(providesOkHttpClient()))
}