package com.carmarket.ui.adDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.carmarket.R
import com.carmarket.databinding.FragmentAdDetailsBinding
import com.carmarket.model.responseBody.AdResponseBody
import com.carmarket.stateClasses.AdDetailsUIState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AdDetailsFragment : Fragment() {

    private var binding: FragmentAdDetailsBinding? = null
    private val viewModel: AdDetailsViewModel by sharedViewModel()
    private var imageViewPager: ViewPager2? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAdDetailsBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageViewPager = binding?.imageViewPager

        val adId = arguments?.getLong("adId") ?: -1
        if (adId != -1L) {
            lifecycleScope.launch {
                viewModel.getAdDetails(adId)
            }
        } else {
            showErrorDialog()
        }

        adDetails()
    }

    private fun adDetails() {
        lifecycleScope.launch {
            viewModel.adDetailsDataState.collect { state ->
                when (state) {
                    is AdDetailsUIState.Loading -> binding?.adDetailsProgressBar?.visibility = View.VISIBLE

                    is AdDetailsUIState.Success -> {
                        binding?.adDetailsProgressBar?.visibility = View.GONE
                        val ad = state.ad
                        binding?.render(ad)
                        Log.d("id", ad.id.toString())
                    }

                    is AdDetailsUIState.Error -> {
                        Log.d("Error", state.message)
                        showErrorDialog()
                    }
                }
            }
        }
    }

    private fun showErrorDialog() {
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
        adDetailBrandTextView.text = data.brand
        adDetailModelTextView.text = data.model
        adDetailYearTextView.text = getString(R.string.year, data.year)
        adDetailPriceTextView.text = getString(R.string.price, data.price)
        adDetailConditionTextView.text = data.condition
        adDetailMileageTextView.text = getString(R.string.km, data.mileage)
        adEngineTypeDetailTextView.text = data.engineType
        adHpDetailTextView.text = getString(R.string.ks, data.engineHp)
        adKwDetailTextView.text = getString(R.string.kw, data.engineKw)
        adCubicDetailTextView.text = getString(R.string.cubic, data.engineCubic)
        adTransmissionDetailTextView.text = data.transmission
        adCityDetailTextView.text = data.city
        adCountryDetailTextView.text = data.country
        adDetailDescriptionTextView.text = data.description

        val imageUrls = data.images
        val adapter = ImagePagerAdapter(imageUrls)
        imageViewPager.adapter = adapter
    }


}
