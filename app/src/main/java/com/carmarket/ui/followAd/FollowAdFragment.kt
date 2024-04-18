package com.carmarket.ui.followAd

import android.os.Bundle
import android.util.Base64
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
import com.carmarket.databinding.AllAdsCardBinding
import com.carmarket.databinding.FragmentFollowAdBinding
import com.carmarket.stateClasses.AdUIState
import com.carmarket.stateClasses.FollowAdUIState
import com.carmarket.ui.allAds.AllAdsAdapter
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FollowAdFragment : Fragment() {

    private var binding: FragmentFollowAdBinding? = null
    private val viewModel: FollowAdViewModel by sharedViewModel()
    private var adapter: FollowAdsAdapter? = null
    private var accessToken: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFollowAdBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding?.followingAdsByUserRecyclerView

        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled
        }

        adapter = FollowAdsAdapter(emptyList()) { adId, _ ->
            val bundle = Bundle()
            bundle.putLong("adId", adId)
            findNavController().navigate(R.id.action_followAdFragment_to_adDetailsFragment, bundle)
        }

        recyclerView?.adapter = adapter

        arguments?.getString("accessToken")?.let { token ->
            accessToken = token
            val id = getUserIdFromToken(token)
            if (id != null) {
                viewModel.getFollowingAds(id, token)
            } else {
                showTokenErrorDialog("Molimo vas da se ulogujete!")
            }
        } ?: run {
            showTokenErrorDialog("Molimo vas da se ulogujete!")
        }

        observeAdData()
    }

    private fun observeAdData() {
        lifecycleScope.launch {
            viewModel.followAdUiDataState.collect { state ->
                when (state) {
                    is FollowAdUIState.Loading -> binding?.followingAdsByUserProgressBar?.visibility = View.VISIBLE

                    is FollowAdUIState.Success -> {
                        binding?.followingAdsByUserProgressBar?.visibility = View.GONE
                        adapter?.setAdData(state.followAd)
                        adapter?.notifyDataSetChanged()
                    }

                    is FollowAdUIState.Error -> {
                        binding?.followingAdsByUserProgressBar?.visibility = View.GONE
                        Log.d("Greška", state.message)
                        showErrorDialog("Ne pratite ni jedan oglas.")
                    }
                }
            }
        }
    }

    private fun getUserIdFromToken(token: String): Long? {
        val tokenParts = token.split(".")
        if (tokenParts.size != 3) {
            return null
        }
        val body = tokenParts[1]
        val decodedBody = String(Base64.decode(body, Base64.URL_SAFE), Charsets.UTF_8)
        try {
            val jsonBody = JSONObject(decodedBody)
            return jsonBody.optLong("id")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    private fun showErrorDialog(message: String) {
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.error)
            setMessage(message)
            setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            create()
        }.show()
    }

    private fun showTokenErrorDialog(message: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.error)
            setMessage(message)
            setPositiveButton(R.string.ok) { _, _ ->
                val action = R.id.action_followAdFragment_to_loginFragment
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