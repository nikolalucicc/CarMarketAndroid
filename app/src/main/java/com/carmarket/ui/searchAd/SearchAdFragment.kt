package com.carmarket.ui.searchAd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.carmarket.R
import com.carmarket.databinding.FragmentSearchAdBinding
import com.carmarket.ui.searchResult.SearchResultFragment
import com.carmarket.utils.SpinnerAdapters
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchAdFragment : Fragment() {

    private var binding: FragmentSearchAdBinding? = null
    private val viewModel: SearchAdViewModel by sharedViewModel()
    private lateinit var bearerToken: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchAdBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinners()
        setupSearchButton()

        arguments?.getString("accessToken")?.let { token ->
            bearerToken = token
        }
    }

    private fun setupSearchButton() {
        binding?.searchButton?.setOnClickListener {
            val brand = binding?.brandSearchSpinner?.selectedItem?.toString()
            val model = binding?.modelSearchEditText?.text?.toString()
            val carBody = binding?.carBodySearchSpinner?.selectedItem?.toString()
            val minYear = binding?.minYearSearchSpinner?.selectedItem as? Int
            val maxYear = binding?.maxYearSearchSpinner?.selectedItem as? Int
            val minPrice = binding?.minPriceSearchEditText?.text?.toString()?.toIntOrNull()
            val maxPrice = binding?.maxPriceSearchEditText?.text?.toString()?.toIntOrNull()
            val condition = binding?.conditionSearchSpinner?.selectedItem?.toString()
            val minMileage = binding?.minMileageSearchEditText?.text?.toString()?.toIntOrNull()
            val maxMileage = binding?.maxMileageSearchEditText?.text?.toString()?.toIntOrNull()
            val fuel = binding?.fuelSearchSpinner?.selectedItem?.toString()
            val minEngineHp = binding?.minHPSearchEditText?.text?.toString()?.toIntOrNull()
            val maxEngineHp = binding?.maxHPSearchEditText?.text?.toString()?.toIntOrNull()
            val minEngineKw = binding?.minKwSearchEditText?.text?.toString()?.toIntOrNull()
            val maxEngineKw = binding?.maxKwSearchEditText?.text?.toString()?.toIntOrNull()
            val minEngineCubic = binding?.minEngineCubicSearchEditText?.text?.toString()?.toIntOrNull()
            val maxEngineCubic = binding?.maxEngineCubicSearchEditText?.text?.toString()?.toIntOrNull()
            val transmission = binding?.transmissionSearchSpinner?.selectedItem?.toString()
            val city = binding?.citySearchEditText?.text?.toString()
            val country = binding?.countrySearchEditText?.text?.toString()
            val drive = binding?.driveSearchSpinner?.selectedItem?.toString()
            val doorNumber = binding?.doorsNumberSearchSpinner?.selectedItem?.toString()
            val seatsNumber = binding?.seatsNumberSearchSpinner?.selectedItem?.toString()
            val wheelSide = binding?.wheelSideSearchSpinner?.selectedItem?.toString()
            val airConditioning = binding?.airConditioningSearchSpinner?.selectedItem?.toString()
            val color = binding?.colorSearchSpinner?.selectedItem?.toString()
            val interiorColor = binding?.interiorColorSearchSpinner?.selectedItem?.toString()
            val registeredUntil = binding?.registrationSearchEditText?.text?.toString()

            val action = SearchAdFragmentDirections.actionSearchAdFragmentToSearchResultFragment()
            findNavController().navigate(action)

            viewModel.searchAd(
                brand,
                model,
                carBody,
                minYear,
                maxYear,
                minPrice,
                maxPrice,
                condition,
                minMileage,
                maxMileage,
                fuel,
                minEngineHp,
                maxEngineHp,
                minEngineKw,
                maxEngineKw,
                minEngineCubic,
                maxEngineCubic,
                transmission,
                city,
                country,
                drive,
                doorNumber,
                seatsNumber,
                wheelSide,
                airConditioning,
                color,
                interiorColor,
                registeredUntil,
                phoneNumber = null,
                bearerToken
            )
        }
    }

    private fun setupSpinners() {
        binding?.brandSearchSpinner?.adapter = SpinnerAdapters.brandAdapter(requireContext())
        binding?.carBodySearchSpinner?.adapter = SpinnerAdapters.carBodyAdapter(requireContext())
        binding?.maxYearSearchSpinner?.adapter = SpinnerAdapters.yearAdapter(requireContext())
        binding?.minYearSearchSpinner?.adapter = SpinnerAdapters.yearAdapter(requireContext())
        binding?.conditionSearchSpinner?.adapter = SpinnerAdapters.conditionAdapter(requireContext())
        binding?.fuelSearchSpinner?.adapter = SpinnerAdapters.fuelAdapter(requireContext())
        binding?.transmissionSearchSpinner?.adapter = SpinnerAdapters.transmissionAdapter(requireContext())
        binding?.driveSearchSpinner?.adapter = SpinnerAdapters.driveAdapter(requireContext())
        binding?.doorsNumberSearchSpinner?.adapter = SpinnerAdapters.doorsNumberAdapter(requireContext())
        binding?.seatsNumberSearchSpinner?.adapter = SpinnerAdapters.seatsNumberAdapter(requireContext())
        binding?.wheelSideSearchSpinner?.adapter = SpinnerAdapters.wheelSideAdapter(requireContext())
        binding?.airConditioningSearchSpinner?.adapter = SpinnerAdapters.airConditioningAdapter(requireContext())
        binding?.colorSearchSpinner?.adapter = SpinnerAdapters.colorAdapter(requireContext())
        binding?.interiorColorSearchSpinner?.adapter = SpinnerAdapters.interiorColorAdapter(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}