package com.carmarket.ui.allAds

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.carmarket.R
import com.carmarket.databinding.FragmentAllAdsBinding
import com.carmarket.stateClasses.AdUIState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AllAdsFragment : Fragment() {

    private var binding: FragmentAllAdsBinding? = null
    private val viewModel: AllAdsViewModel by sharedViewModel()
    private var adapter: AllAdsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAllAdsBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding?.airPollutionRecyclerView

        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled
        }

        adapter = AllAdsAdapter(emptyList()) { id ->
            val bundle = Bundle()
            bundle.putLong("adId", id)
            findNavController().navigate(R.id.action_allAdsFragment_to_adDetailsFragment, bundle)
        }
        recyclerView?.adapter = adapter

        setHasOptionsMenu(true)

        lifecycleScope.launch {
            viewModel.getAllAds()
            allAds()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.basicSort -> {
                viewModel.getAllAds()
                true
            }
            R.id.sortByYearDesc -> {
                viewModel.sortByYearDesc()
                true
            }
            R.id.sortByYearAsc -> {
                viewModel.sortByYearAsc()
                true
            }
            R.id.sortByPriceDesc -> {
                viewModel.sortByPriceDesc()
                true
            }
            R.id.sortByPriceAsc -> {
                viewModel.sortByPriceAsc()
                true
            }
            R.id.sortByMileageDesc -> {
                viewModel.sortByMileageDesc()
                true
            }R.id.sortByMileageAsc -> {
                viewModel.sortByMileageAsc()
                true
            }
            R.id.sortByPostedDateDesc -> {
                viewModel.sortByPostedDateDesc()
                true
            }
            R.id.sortByPostedDateAsc -> {
                viewModel.sortByPostedDateAsc()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun allAds() {
        lifecycleScope.launch {
            viewModel.adUiDataState.collect { state ->
                when (state) {
                    is AdUIState.Loading -> binding?.allAdsProgressBar?.visibility = View.VISIBLE

                    is AdUIState.Success -> {
                        binding?.allAdsProgressBar?.visibility = View.GONE
                        adapter?.setAdData(state.ads)
                        adapter?.notifyDataSetChanged()
                    }

                    is AdUIState.Error -> {
                        Log.d("Error", state.message)
                        showErrorDialog()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.error)
            setMessage(R.string.errorMessage)
            setPositiveButton(R.string.ok) { _, _ ->
                requireActivity().finishAffinity()
            }
            create().show()
        }
    }
}
