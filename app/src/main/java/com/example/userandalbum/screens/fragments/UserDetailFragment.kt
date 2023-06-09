package com.example.userandalbum.screens.fragments

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.userandalbum.databinding.FragmentUserDetailBinding
import com.example.userandalbum.util.hide
import com.example.userandalbum.util.logI
import com.example.userandalbum.util.show
import java.io.IOException
import java.util.Locale

class UserDetailFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailBinding

    private val args: UserDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        val userDetails = args.UserDetails
        binding.apply {
            tvName.text = "${userDetails.name}"
            tvUserName.text = "${userDetails.username}"
            tvEmail.text = "${userDetails.email}"
            tvPhone.text = "${userDetails.phone}"
            tvAddress.text =
                "${userDetails.address?.suite}, ${userDetails.address?.street}, ${userDetails.address?.city} - ${userDetails.address?.zipcode},"
            tvCompanyName.text = "${userDetails.company?.name}"
            tvWebsite.text = "${userDetails.website}"
            if (userDetails.address?.geo?.lat != null) {
                val location = getLocation(
                    userDetails.address.geo.lat.toDouble(),
                    userDetails.address.geo.lng.toDouble()
                )
                if (!location.isNullOrEmpty()) {
                    liGeoLocation.show()
                    tvLocation.text = location
                }
                else liGeoLocation.hide()
            } else {
                liGeoLocation.hide()
            }
        }
    }

    private fun getLocation(latitude: Double, longitude: Double): String? {
        val geocoder = context?.let { Geocoder(it, Locale.getDefault()) }
        val addresses: List<Address>?
        return try {
            addresses = geocoder?.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                logI("GeoAddress", addresses[0].toString())
                return addresses[0].getAddressLine(0)
            } else {
                logI("GeoAddress", "NULL FROM FETCHING")
                null
            }
        } catch (e: IOException) {
            logI("GeoAddress", "ERROR: $e")
            null
        }
    }
}