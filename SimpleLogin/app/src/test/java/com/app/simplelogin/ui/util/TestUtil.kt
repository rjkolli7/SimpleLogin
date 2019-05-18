package com.app.simplelogin.ui.util

import com.app.simplelogin.network.model.Address
import com.app.simplelogin.network.model.Company
import com.app.simplelogin.network.model.Geo
import com.app.simplelogin.network.model.User

object TestUtil {

    fun createMockUser() =
        User(
            id = 1,
            name = "Leanne Graham",
            username = "Bret",
            email = "Sincere@april.biz",
            phone = "1-770-736-8031 x56442",
            website = "hildegard.org",
            address = createAddress(),
            company = createCompany()
        )

    private fun createAddress() = Address(
        street = "Victor Plains", suite = "Suite 879", city = "Wisokyburgh", zipcode = "90566-7771",
        geo = createGeo()
    )

    private fun createGeo() = Geo(
        lat = "-43.9509", lng = "-34.4618"
    )

    private fun createCompany() = Company(
        name = "Romaguera-Crona",
        catchPhrase = "Multi-layered client-server neural-net",
        bs = "harness real-time e-markets"
    )
}