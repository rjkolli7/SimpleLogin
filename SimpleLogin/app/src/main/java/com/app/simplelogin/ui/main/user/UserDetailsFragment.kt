package com.app.simplelogin.ui.main.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.app.simplelogin.R
import com.app.simplelogin.databinding.FragmentUserDetailsBinding
import com.app.simplelogin.di.injector.Injectable
import com.app.simplelogin.network.model.User
import com.app.simplelogin.testing.OpenForTesting
import com.app.simplelogin.ui.main.user.viewmodel.UserDetailsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_user_details.*
import javax.inject.Inject

@OpenForTesting
class UserDetailsFragment : Fragment(), Injectable, OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentUserDetailsBinding

    lateinit var model: UserDetailsViewModel

    var mapFragment: SupportMapFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.user_detail)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model = ViewModelProviders.of(this, viewModelFactory)
            .get(UserDetailsViewModel::class.java)

        val user = arguments?.getSerializable("details")

        user?.let {
            model.bind(user as User)
        }

        binding.model = model
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        mapFragment = childFragmentManager.findFragmentById(R.id.ud_location_map) as SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            val builder = LatLngBounds.Builder()

            val mop = model.addMarker()
            map.addMarker(mop)
            builder.include(mop?.position)

            val bounds = builder.build()

            val width = resources.displayMetrics.widthPixels
            val height = resources.displayMetrics.heightPixels
            val padding = (width * 0.30).toInt()

            val cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding)
            map.moveCamera(cu)
        }
    }

    /**
     * Created to be able to override in tests
     */
    fun navController() = findNavController()
}