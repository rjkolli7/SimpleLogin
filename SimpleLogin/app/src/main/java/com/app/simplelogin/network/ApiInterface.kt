package com.app.simplelogin.network

import com.app.simplelogin.network.model.User
import io.reactivex.Single
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface ApiInterface {

    @GET("/users/")
    fun users(): Single<User>

}