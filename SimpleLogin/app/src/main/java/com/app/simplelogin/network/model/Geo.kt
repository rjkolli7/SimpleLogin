package com.app.simplelogin.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//{
//    "lat": "-37.3159",
//    "lng": "81.1496"
//}
data class Geo(
    @SerializedName("lat")
    @Expose
    val lat: String? = null,

    @SerializedName("lng")
    @Expose
    val lng: String? = null
)