package com.carmarket.ui.createAd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.carmarket.R
import com.carmarket.databinding.FragmentCreateAdBinding
import com.carmarket.model.request.AdRequest
import com.carmarket.utils.SpinnerAdapters
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CreateAdFragment : Fragment() {

    private var binding: FragmentCreateAdBinding? = null
    private val viewModel: CreateAdViewModel by sharedViewModel()
    private var bearerToken: String? = null
    private val selectedImages: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCreateAdBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagesRecyclerView = binding?.imagesCreateAdRecyclerView
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        imagesRecyclerView?.layoutManager = layoutManager
        val imageAdapter = ImageAdapter(requireContext(), selectedImages)
        imagesRecyclerView?.adapter = imageAdapter

        binding?.createAdButton?.setOnClickListener {
            createAd()
        }

        binding?.addImageButton?.setOnClickListener {
            openImagePicker()
        }

        binding?.brandCreateAdSpinner?.adapter = SpinnerAdapters.brandAdapter(requireContext())
        binding?.carBodyCreateAdSpinner?.adapter = SpinnerAdapters.carBodyAdapter(requireContext())
        binding?.yearCreateAdSpinner?.adapter = SpinnerAdapters.yearAdapter(requireContext())
        binding?.conditionCreateAdSpinner?.adapter = SpinnerAdapters.conditionAdapter(requireContext())
        binding?.fuelCreateAdSpinner?.adapter = SpinnerAdapters.fuelAdapter(requireContext())
        binding?.transmissionCreateAdSpinner?.adapter = SpinnerAdapters.transmissionAdapter(requireContext())
        binding?.driveCreateAdSpinner?.adapter = SpinnerAdapters.driveAdapter(requireContext())
        binding?.doorsNumberCreateAdSpinner?.adapter = SpinnerAdapters.doorsNumberAdapter(requireContext())
        binding?.seatsNumberCreateAdSpinner?.adapter = SpinnerAdapters.seatsNumberAdapter(requireContext())
        binding?.wheelSideCreateAdSpinner?.adapter = SpinnerAdapters.wheelSideAdapter(requireContext())
        binding?.airConditioningCreateAdSpinner?.adapter = SpinnerAdapters.airConditioningAdapter(requireContext())
        binding?.colorCreateAdSpinner?.adapter = SpinnerAdapters.colorAdapter(requireContext())
        binding?.interiorColorCreateAdSpinner?.adapter = SpinnerAdapters.interiorColorAdapter(requireContext())

        arguments?.getString("accessToken")?.let { token ->
            if (token.isEmpty()) {
                showErrorDialog("Molimo vas da se ulogujete!")
            } else {
                bearerToken = token
            }
        } ?: showErrorDialog("Molimo vas da se ulogujete!")

    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let { uri ->
                val imageUrl = uri.toString()
                selectedImages.add(imageUrl)
                binding?.imagesCreateAdRecyclerView?.adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun createAd() {
        val description = binding?.descriptionAdEditText?.text.toString()
        val brand = binding?.brandCreateAdSpinner?.selectedItem?.toString() ?: ""
        val model = binding?.modelCreateAdEditText?.text.toString()
        val carBody = binding?.carBodyCreateAdSpinner?.selectedItem?.toString() ?: ""
        val year = binding?.yearCreateAdSpinner?.selectedItem?.toString()?.toLongOrNull() ?: 0L
        val price = binding?.priceCreateAdEditText?.text.toString().toLongOrNull() ?: 0L
        val condition = binding?.conditionCreateAdSpinner?.selectedItem?.toString() ?: ""
        val mileage = binding?.mileageCreateAdEditText?.text.toString().toLongOrNull() ?: 0L
        val fuel = binding?.fuelCreateAdSpinner?.selectedItem?.toString() ?: ""
        val engineHp = binding?.engineHpCreateAdEditText?.text.toString().toLongOrNull() ?: 0L
        val engineKw = binding?.engineKwCreateAdEditText?.text.toString().toLongOrNull() ?: 0L
        val engineCubic = binding?.engineCubicCreateAdEditText?.text.toString().toLongOrNull() ?: 0L
        val transmission = binding?.transmissionCreateAdSpinner?.selectedItem?.toString() ?: ""
        val drive = binding?.driveCreateAdSpinner?.selectedItem?.toString() ?: ""
        val doorNumber = binding?.doorsNumberCreateAdSpinner?.selectedItem?.toString() ?: ""
        val seatsNumber = binding?.seatsNumberCreateAdSpinner?.selectedItem?.toString() ?: ""
        val wheelSide = binding?.wheelSideCreateAdSpinner?.selectedItem?.toString() ?: ""
        val airConditioning = binding?.airConditioningCreateAdSpinner?.selectedItem?.toString() ?: ""
        val color = binding?.colorCreateAdSpinner?.selectedItem?.toString() ?: ""
        val interiorColor = binding?.interiorColorCreateAdSpinner?.selectedItem?.toString() ?: ""
        val registeredUntil = binding?.registeredUntilCreateAdEditText?.text.toString()
        val city = binding?.cityCreateAdEditText?.text.toString()
        val country = binding?.countryCreateAdEditText?.text.toString()
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

        viewModel.createAd(adRequest, bearerToken)
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.error)
            setMessage(message)
            setPositiveButton(R.string.ok) { _, _ ->
                val action = R.id.action_createAdFragment_to_loginFragment
                findNavController().navigate(action)
            }
            create().show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}