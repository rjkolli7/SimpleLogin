package com.app.simplelogin.network.model

import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//{
//    "id": 1,
//    "name": "Leanne Graham",
//    "username": "Bret",
//    "email": "Sincere@april.biz",
//    "address": {
//    "street": "Kulas Light",
//    "suite": "Apt. 556",
//    "city": "Gwenborough",
//    "zipcode": "92998-3874",
//    "geo": {
//    "lat": "-37.3159",
//    "lng": "81.1496"
//}
//},
//    "phone": "1-770-736-8031 x56442",
//    "website": "hildegard.org",
//    "company": {
//    "name": "Romaguera-Crona",
//    "catchPhrase": "Multi-layered client-server neural-net",
//    "bs": "harness real-time e-markets"
//}
//}
data class User(
    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("username")
    @Expose
    val username: String? = null,

    @SerializedName("email")
    @Expose
    val email: String? = null,

    @SerializedName("address")
    @Expose
    val address: Address? = null,

    @SerializedName("phone")
    @Expose
    val phone: String? = null,

    @SerializedName("website")
    @Expose
    val website: String? = null,

    @SerializedName("company")
    @Expose
    val company: Company? = null
) : Serializable