package com.carmarket.ui.adDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.carmarket.R
import com.carmarket.databinding.FragmentAdDetailsBinding
import com.carmarket.model.request.FollowAdRequest
import com.carmarket.model.responseBody.AdResponseBody
import com.carmarket.stateClasses.FollowAdUIState
import com.carmarket.stateClasses.OneAdUIState
import com.carmarket.ui.changeAd.ChangeAdFragment
import com.carmarket.ui.followAd.FollowAdViewModel
import com.carmarket.ui.removeAd.RemoveAdViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AdDetailsFragment : Fragment() {

    private var binding: FragmentAdDetailsBinding? = null
    private val viewModel: AdDetailsViewModel by sharedViewModel()
    private val followAdViewModel: FollowAdViewModel by sharedViewModel()
    private val removeAdViewModel: RemoveAdViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAdDetailsBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adId = arguments?.getLong("adId") ?: -1
        val followedAdId = arguments?.getLong("followedAdId") ?: -1
        val jwt = arguments?.getString("jwt") ?: ""

        if (adId != -1L) {
            lifecycleScope.launch {
                viewModel.getAdDetails(adId)
            }

            arguments?.getString("jwt")?.let {
                checkIfAdIsFollowed(adId, jwt)
            }
        } else {
            showErrorDialog("Invalid token.")
        }

        requireActivity().findViewById<ImageButton>(R.id.followAdButton)?.setOnClickListener {
            val jwt = arguments?.getString("jwt", "")
            if (adId != -1L && !jwt.isNullOrEmpty()) {
                lifecycleScope.launch {
                    val isAdFollowed = followAdViewModel.isAdFollowed(adId, getUserIdFromJWT(jwt))

                    if (isAdFollowed) {
                        followAdViewModel.deleteFromFavorites(followedAdId, getUserIdFromJWT(jwt))
                        Toast.makeText(requireContext(), "Oglas je uspešno otpraćen!", Toast.LENGTH_SHORT).show()
                    } else {
                        val followAdRequest = FollowAdRequest(getUserIdFromJWT(jwt), adId)
                        followAdViewModel.followAd(followAdRequest, jwt)
                        Toast.makeText(requireContext(), "Oglas je uspešno zapraćen!", Toast.LENGTH_SHORT).show()
                    }

                    updateFollowButtonState(!isAdFollowed)
                }
            }
        }

        requireActivity().findViewById<ImageButton>(R.id.changeAdButton)?.setOnClickListener {
            val bundle = Bundle().apply {
                putLong("adId", adId)
                putString("jwt", jwt)
            }
            val navController = findNavController()
            navController.navigate(R.id.action_adDetailsFragment_to_changeAdFragment, bundle)
        }

        requireActivity().findViewById<ImageButton>(R.id.removeAdButton)?.setOnClickListener {
            val jwt = arguments?.getString("jwt", "")
            if (adId != -1L && !jwt.isNullOrEmpty()) {
                lifecycleScope.launch {
                    try {
                        removeAdViewModel.removeAd(adId, jwt)
                        val bundle = Bundle().apply {
                            putString("accessToken", jwt)
                        }
                        findNavController().navigate(R.id.action_adDetailsFragment_to_adsByUserFragment, bundle)
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Greška prilikom brisanja oglasa.", Toast.LENGTH_SHORT).show()
                        Log.e("AdDetailsFragment", "Greška prilikom brisanja oglasa: ${e.message}")
                    }
                }
            }
        }

        adDetails()
    }

    private fun checkIfAdIsFollowed(adId: Long, jwt: String) {
        val followAdViewModel: FollowAdViewModel by sharedViewModel()

        lifecycleScope.launch {
            followAdViewModel.followAdUiDataState.collect { state ->
                when (state) {
                    is FollowAdUIState.Success -> {
                        val followedAds = state.followAd
                        val isAdFollowed = followedAds.any { it.ad.id == adId }
                        updateFollowButtonState(isAdFollowed)
                    }
                    is FollowAdUIState.Error -> {
                        Log.d("FollowAdFragment", "Error: ${state.message}")
                    }
                    else -> {
                        // Ignore other states
                    }
                }
            }
        }

        val id = getUserIdFromJWT(jwt)
        if (id != null) {
            followAdViewModel.getFollowingAds(id.toLong(), jwt)
        } else {
            showErrorDialog("Invalid token.")
        }
    }

    private fun updateFollowButtonState(isAdFollowed: Boolean) {
        val followAdButton = requireActivity().findViewById<ImageButton>(R.id.followAdButton)
        val followedImageResource = if (isAdFollowed) {
            R.drawable.baseline_favorite_24
        } else {
            R.drawable.baseline_favorite_border_24
        }
        followAdButton.setImageResource(followedImageResource)
    }

    private fun adDetails() {
        lifecycleScope.launch {
            viewModel.adDetailsDataState.collect { state ->
                when (state) {
                    is OneAdUIState.Loading -> binding?.adDetailsProgressBar?.visibility = View.VISIBLE

                    is OneAdUIState.Success -> {
                        binding?.adDetailsProgressBar?.visibility = View.GONE
                        val ad = state.ad
                        binding?.render(ad)
                        Log.d("id", ad.id.toString())
                    }

                    is OneAdUIState.Error -> {
                        Log.d("Error", state.message)
                        showErrorDialog("Invalid token.")
                    }
                }
            }
        }
    }

    private fun getUserIdFromJWT(jwt: String): String {
        val parts = jwt.split(".")
        if (parts.size == 3) {
            val decodedPayload = android.util.Base64.decode(parts[1], android.util.Base64.DEFAULT)
            val payload = String(decodedPayload)
            val jsonObject = JSONObject(payload)
            return jsonObject.optString("id", "")
        }
        return ""
    }

    private fun showErrorDialog(s: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle(R.string.error)
            setMessage(R.string.errorMessage)
            setPositiveButton(R.string.ok) { _, _ ->
                requireActivity().finishAffinity()
            }
            create().show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun FragmentAdDetailsBinding.render(data: AdResponseBody) {
        adIdTextView.text = data.id.toString()
        adDetailBrandTextView.text = data.brand
        adDetailModelTextView.text = data.model
        adDetailYearTextView.text = getString(R.string.year, data.year)
        adDetailPriceTextView.text = getString(R.string.price, data.price)
        adDetailConditionTextView.text = data.condition
        adDetailMileageTextView.text = getString(R.string.km, data.mileage)
        adFuelDetailTextView.text = data.fuel
        adHpDetailTextView.text = getString(R.string.ks, data.engineHp)
        adKwDetailTextView.text = getString(R.string.kw, data.engineKw)
        adCubicDetailTextView.text = getString(R.string.cubic, data.engineCubic)
        adTransmissionDetailTextView.text = data.transmission
        adCityDetailTextView.text = data.city
        adCountryDetailTextView.text = data.country
        adDetailDescriptionTextView.text = data.description
        adDriveDetailTextView.text = data.drive
        adDoorNumberDetailTextView.text = data.doorNumber
        adSeatsNumberDetailTextView.text = data.seatsNumber
        adWheelSideDetailTextView.text = data.wheelSide
        adAirConditioningDetailTextView.text = data.airConditioning
        adColorDetailTextView.text = data.color
        adInteriorColorDetailTextView.text = data.interiorColor
        adRegisteredUntilDetailTextView.text = data.registeredUntil
        adTelephoneNumberDetailTextView.text = data.phoneNumber

        val imageUrls = data.images
        val adapter = ImagePagerAdapter(imageUrls)
        imageViewPager.adapter = adapter
    }

}
