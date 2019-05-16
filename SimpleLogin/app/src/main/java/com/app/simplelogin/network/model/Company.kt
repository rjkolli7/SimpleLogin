package com.app.simplelogin.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//{
//    "name": "Romaguera-Crona",
//    "catchPhrase": "Multi-layered client-server neural-net",
//    "bs": "harness real-time e-markets"
//}
data class Company(
    @SerializedName("name")
    @Expose
    private val name: String? = null,

    @SerializedName("catchPhrase")
    @Expose
    private val catchPhrase: String? = null,

    @SerializedName("bs")
    @Expose
    private val bs: String? = null
)