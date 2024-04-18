package com.carmarket.ui.searchResult

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.carmarket.R
import com.carmarket.databinding.FragmentSearchResultBinding
import com.carmarket.stateClasses.AdUIState
import com.carmarket.ui.allAds.AllAdsAdapter
import com.carmarket.ui.searchAd.SearchAdViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchResultFragment : Fragment() {

    private var binding: FragmentSearchResultBinding? = null
    private var adapter: AllAdsAdapter? = null
    private val viewModel: SearchAdViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchResultBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding?.searchResultRecyclerView

        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled
        }

        adapter = AllAdsAdapter(emptyList()) { id ->
            val bundle = Bundle()
            bundle.putLong("adId", id)
            bundle.putString("jwt", getAccessToken())
            findNavController().navigate(R.id.action_searchResultFragment_to_adDetailsFragment, bundle)
        }
        recyclerView?.adapter = adapter

        lifecycleScope.launch {
            getResults()
        }
    }

    private fun getResults() {
        lifecycleScope.launch {
            viewModel.adUiDataState.collect {state ->
                when(state) {
                    is AdUIState.Loading -> binding?.searchResultProgressBar?.visibility = View.VISIBLE

                    is AdUIState.Success -> {
                        binding?.searchResultProgressBar?.visibility = View.GONE
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

    private fun showErrorDialog() {
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.error)
            setMessage("Došlo je do greške tokom pretrage. Pokušajte ponovo.")
            setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            create()
        }.show()
    }

    private fun getAccessToken(): String {
        val sharedPreferences = requireContext().getSharedPreferences("com.carmarket", Context.MODE_PRIVATE)
        return sharedPreferences.getString("accessToken", "") ?: ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}