package com.carmarket.ui.changeAd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.carmarket.R
import com.carmarket.databinding.FragmentChangeAdBinding
import com.carmarket.model.request.AdRequest
import com.carmarket.model.responseBody.AdResponseBody
import com.carmarket.stateClasses.OneAdUIState
import com.carmarket.ui.adDetails.AdDetailsViewModel
import com.carmarket.ui.createAd.ImageAdapter
import com.carmarket.utils.SpinnerAdapters
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChangeAdFragment : Fragment() {

    private var binding: FragmentChangeAdBinding? = null
    private val adViewModel: AdDetailsViewModel by sharedViewModel()
    private val changeAdViewModel: ChangeAdViewModel by sharedViewModel()
    private val selectedImages: MutableList<String> = mutableListOf()
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentChangeAdBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagesRecyclerView = binding?.imagesChangeAdRecyclerView
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        imagesRecyclerView?.layoutManager = layoutManager
        imageAdapter = ImageAdapter(requireContext(), selectedImages)
        imagesRecyclerView?.adapter = imageAdapter

        val accessToken = arguments?.getString("jwt") ?: ""
        if (accessToken.isBlank()) {
            showErrorDialog("Access token is missing.")
            return
        }

        arguments?.let { args ->
            val id = args.getLong("adId")
            if (id == 0L) {
                showErrorDialog("Ad ID is missing.")
                return
            }
        } ?: showErrorDialog("Required arguments missing.")

        setupSpinners()

        binding?.saveChangeAdButton?.setOnClickListener{
            val adId = arguments?.getLong("adId") ?: 0L
            saveChanges(adId, accessToken)
        }

        binding?.addImageChangeButton?.setOnClickListener {
            openImagePicker()
        }

        adDetails()
    }

    private fun setupSpinners() {
        binding?.brandChangeAdSpinner?.adapter = SpinnerAdapters.brandAdapter(requireContext())
        binding?.carBodyChangeAdSpinner?.adapter = SpinnerAdapters.carBodyAdapter(requireContext())
        binding?.yearChangeAdSpinner?.adapter = SpinnerAdapters.yearAdapter(requireContext())
        binding?.conditionChangeAdSpinner?.adapter = SpinnerAdapters.conditionAdapter(requireContext())
        binding?.fuelChangeAdSpinner?.adapter = SpinnerAdapters.fuelAdapter(requireContext())
        binding?.transmissionChangeAdSpinner?.adapter = SpinnerAdapters.transmissionAdapter(requireContext())
        binding?.driveChangeAdSpinner?.adapter = SpinnerAdapters.driveAdapter(requireContext())
        binding?.doorsNumberChangeAdSpinner?.adapter = SpinnerAdapters.doorsNumberAdapter(requireContext())
        binding?.seatsNumberChangeAdSpinner?.adapter = SpinnerAdapters.seatsNumberAdapter(requireContext())
        binding?.wheelSideChangeAdSpinner?.adapter = SpinnerAdapters.wheelSideAdapter(requireContext())
        binding?.airConditioningChangeAdSpinner?.adapter = SpinnerAdapters.airConditioningAdapter(requireContext())
        binding?.colorChangeAdSpinner?.adapter = SpinnerAdapters.colorAdapter(requireContext())
        binding?.interiorColorChangeAdSpinner?.adapter = SpinnerAdapters.interiorColorAdapter(requireContext())
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let { uri ->
                val imageUrl = uri.toString()
                selectedImages.add(imageUrl)
                binding?.imagesChangeAdRecyclerView?.adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun saveChanges(id: Long, accessToken: String) {
        val description = binding?.descriptionAdEditText?.text.toString()
        val brand = binding?.brandChangeAdSpinner?.selectedItem?.toString() ?: ""
        val model = binding?.modelChangeAdEditText?.text.toString()
        val carBody = binding?.carBodyChangeAdSpinner?.selectedItem?.toString() ?: ""
        val year = binding?.yearChangeAdSpinner?.selectedItem?.toString()?.toLongOrNull() ?: 0L
        val price = binding?.priceChangeAdEditText?.text.toString().toLongOrNull() ?: 0L
        val condition = binding?.conditionChangeAdSpinner?.selectedItem?.toString() ?: ""
        val mileage = binding?.mileageChangeAdEditText?.text.toString().toLongOrNull() ?: 0L
        val fuel = binding?.fuelChangeAdSpinner?.selectedItem?.toString() ?: ""
        val engineHp = binding?.engineHpChangeAdEditText?.text.toString().toLongOrNull() ?: 0L
        val engineKw = binding?.engineKwChangeAdEditText?.text.toString().toLongOrNull() ?: 0L
        val engineCubic = binding?.engineCubicChangeAdEditText?.text.toString().toLongOrNull() ?: 0L
        val transmission = binding?.transmissionChangeAdSpinner?.selectedItem?.toString() ?: ""
        val drive = binding?.driveChangeAdSpinner?.selectedItem?.toString() ?: ""
        val doorNumber = binding?.doorsNumberChangeAdSpinner?.selectedItem?.toString() ?: ""
        val seatsNumber = binding?.seatsNumberChangeAdSpinner?.selectedItem?.toString() ?: ""
        val wheelSide = binding?.wheelSideChangeAdSpinner?.selectedItem?.toString() ?: ""
        val airConditioning = binding?.airConditioningChangeAdSpinner?.selectedItem?.toString() ?: ""
        val color = binding?.colorChangeAdSpinner?.selectedItem?.toString() ?: ""
        val interiorColor = binding?.interiorColorChangeAdSpinner?.selectedItem?.toString() ?: ""
        val registeredUntil = binding?.registeredUntilChangeAdEditText?.text.toString()
        val city = binding?.cityChangeAdEditText?.text.toString()
        val country = binding?.countryChangeAdEditText?.text.toString()
        val phoneNumber = binding?.phoneNumberAdEditText?.text.toString()

        if (description.isBlank() || brand.isBlank() || model.isBlank() || carBody.isBlank() || year == null || price == null ||
            condition.isBlank() || mileage == null || fuel.isBlank() || engineHp == null || engineKw == null ||
            engineCubic == null || transmission.isBlank() || drive.isBlank() || doorNumber.isBlank() ||
            seatsNumber.isBlank() || wheelSide.isBlank() || airConditioning.isBlank() || color.isBlank() ||
            interiorColor.isBlank() || registeredUntil.isBlank() || city.isBlank() || country.isBlank() ||
            phoneNumber.isBlank() || selectedImages.isEmpty()
        ) {
            Toast.makeText(requireContext(), "Sva polja moraju biti popunjena.", Toast.LENGTH_SHORT).show()
            return
        }

        val adRequest = AdRequest(
            description = description,
            brand = brand,
            model = model,
            carBody = carBody,
            year = year,
            price = price,
            condition = condition,
            mileage = mileage,
            fuel = fuel,
            engineHp = engineHp,
            engineKw = engineKw,
            engineCubic = engineCubic,
            transmission = transmission,
            drive = drive,
            doorNumber = doorNumber,
            seatsNumber = seatsNumber,
            wheelSide = wheelSide,
            airConditioning = airConditioning,
            color = color,
            interiorColor = interiorColor,
            registeredUntil = registeredUntil,
            city = city,
            country = country,
            phoneNumber = phoneNumber,
            imageUrls = selectedImages
        )

        changeAdViewModel.changeAd(adRequest, id, accessToken)
    }

    private fun adDetails() {
        lifecycleScope.launch {
            adViewModel.adDetailsDataState.collect { state ->
                when (state) {
                    is OneAdUIState.Loading -> {}

                    is OneAdUIState.Success -> {
                        val ad = state.ad
                        binding?.render(ad)
                        val adImages = ad.images.map { it.url }
                        selectedImages.addAll(adImages)
                        imageAdapter.notifyDataSetChanged()
                    }

                    is OneAdUIState.Error -> {
                        Log.d("Error", state.message)
                        showErrorDialog("Invalid token.")
                    }
                }
            }
        }
    }

    private fun FragmentChangeAdBinding.render(data: AdResponseBody) {
        changeAdIdTextView.text = data.id.toString()

        val brandIndex = findItemIndexInSpinner(brandChangeAdSpinner, data.brand)
        brandChangeAdSpinner.setSelection(brandIndex)

        modelChangeAdEditText.setText(data.model)

        val carBodyIndex = findItemIndexInSpinner(carBodyChangeAdSpinner, data.carBody)
        carBodyChangeAdSpinner.setSelection(carBodyIndex)

        val yearIndex = findItemIndexInSpinner(yearChangeAdSpinner, data.year.toString())
        yearChangeAdSpinner.setSelection(yearIndex)

        priceChangeAdEditText.setText(data.price.toString())

        val conditionIndex = findItemIndexInSpinner(conditionChangeAdSpinner, data.condition)
        conditionChangeAdSpinner.setSelection(conditionIndex)

        mileageChangeAdEditText.setText(data.mileage.toString())

        val fuelTypeIndex = findItemIndexInSpinner(fuelChangeAdSpinner, data.fuel)
        fuelChangeAdSpinner.setSelection(fuelTypeIndex)

        engineCubicChangeAdEditText.setText(data.engineCubic.toString())

        engineHpChangeAdEditText.setText(data.engineHp.toString())

        engineKwChangeAdEditText.setText(data.engineKw.toString())

        val transmissionIndex = findItemIndexInSpinner(transmissionChangeAdSpinner, data.transmission)
        transmissionChangeAdSpinner.setSelection(transmissionIndex)

        val driveIndex = findItemIndexInSpinner(driveChangeAdSpinner, data.drive)
        driveChangeAdSpinner.setSelection(driveIndex)

        val doorsNumberIndex = findItemIndexInSpinner(doorsNumberChangeAdSpinner, data.doorNumber)
        doorsNumberChangeAdSpinner.setSelection(doorsNumberIndex)

        val seatsNumberIndex = findItemIndexInSpinner(seatsNumberChangeAdSpinner, data.seatsNumber)
        seatsNumberChangeAdSpinner.setSelection(seatsNumberIndex)

        val wheelSideIndex = findItemIndexInSpinner(wheelSideChangeAdSpinner, data.wheelSide)
        wheelSideChangeAdSpinner.setSelection(wheelSideIndex)

        val airConditioningIndex = findItemIndexInSpinner(airConditioningChangeAdSpinner, data.airConditioning)
        airConditioningChangeAdSpinner.setSelection(airConditioningIndex)

        val colorIndex = findItemIndexInSpinner(colorChangeAdSpinner, data.color)
        colorChangeAdSpinner.setSelection(colorIndex)

        val interiorColorIndex = findItemIndexInSpinner(interiorColorChangeAdSpinner, data.interiorColor)
        interiorColorChangeAdSpinner.setSelection(interiorColorIndex)

        registeredUntilChangeAdEditText.setText(data.registeredUntil)

        cityChangeAdEditText.setText(data.city)

        countryChangeAdEditText.setText(data.country)

        phoneNumberAdEditText.setText(data.phoneNumber)

        descriptionAdEditText.setText(data.description)
    }

    private fun findItemIndexInSpinner(spinner: Spinner, item: Any): Int {
        val adapter = spinner.adapter
        for (i in 0 until adapter.count) {
            if (adapter.getItem(i) == item) {
                return i
            }
        }
        return 0
    }

    private fun showErrorDialog(message: String) {
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.error)
            setMessage(message)
            setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            create().show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}