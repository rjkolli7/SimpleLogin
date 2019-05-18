package com.app.simplelogin.ui.main.user.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.app.simplelogin.network.model.User
import com.app.simplelogin.testing.OpenForTesting
import com.app.simplelogin.ui.base.BaseViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

@OpenForTesting
class UserDetailsViewModel @Inject constructor() : BaseViewModel() {

    private val userDetails: MutableLiveData<User> = MutableLiveData()
    val address: MutableLiveData<String> = MutableLiveData()
    val company: MutableLiveData<String> = MutableLiveData()
    val geo: MutableLiveData<String> = MutableLiveData()

    fun bind(user: User) {
        userDetails.value = user
        setAddress()
        setCompany()
        setGeo()
    }

    fun getUserDetails(): MutableLiveData<User> {
        return userDetails
    }

    fun onItemClickListener() = View.OnClickListener { v ->
        navigateUserDetails(v, v.tag as User)
    }

    private fun setAddress() {
        val user = userDetails.value
        user?.let { u ->
            u.address?.let { a ->
                address.value = "${a.street}, ${a.suite}, ${a.city}, ${a.zipcode}"
            }
        }
    }

    private fun setCompany() {
        val user = userDetails.value
        user?.let { u ->
            u.company?.let { c ->
                company.value = "${c.name}\n${c.catchPhrase}\n${c.bs}"
            }
        }
    }

    private fun setGeo() {
        val user = userDetails.value
        user?.let { u ->
            u.address?.let { a ->
                a.geo?.let { g ->
                    geo.value = "${g.lat},${g.lng}"
                }
            }
        }
    }

    fun addMarker() : MarkerOptions? {
        var markerOptions: MarkerOptions? = null
        userDetails.value?.let {u ->
            u.address?.let { a ->
                a.geo?.let { g ->
                    val lat = g.lat?.toDouble()
                    val lng = g.lng?.toDouble()
                    markerOptions = MarkerOptions()
                    markerOptions?.position(LatLng(lat!!, lng!!))
                    return markerOptions
                }
            }
        }
        return markerOptions
    }


}