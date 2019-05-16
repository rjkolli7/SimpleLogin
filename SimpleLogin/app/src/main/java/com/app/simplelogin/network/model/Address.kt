package com.app.simplelogin.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//{
//    "street": "Kulas Light",
//    "suite": "Apt. 556",
//    "city": "Gwenborough",
//    "zipcode": "92998-3874",
//    "geo": {
//    "lat": "-37.3159",
//    "lng": "81.1496"
//}
data class Address(

    @SerializedName("street")
    @Expose
    private val street: String? = null,

    @SerializedName("suite")
    @Expose
    private val suite: String? = null,

    @SerializedName("city")
    @Expose
    private val city: String? = null,

    @SerializedName("zipcode")
    @Expose
    private val zipcode: String? = null,

    @SerializedName("geo")
    @Expose
    private val geo: Geo? = null
)