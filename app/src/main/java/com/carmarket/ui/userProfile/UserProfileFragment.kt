package com.carmarket.ui.userProfile

import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.carmarket.R
import com.carmarket.databinding.FragmentUserProfileBinding
import com.carmarket.model.responseBody.UserResponseBody
import com.carmarket.stateClasses.OneAdUIState
import com.carmarket.stateClasses.UserUIState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserProfileFragment : Fragment() {

    private var binding: FragmentUserProfileBinding? = null
    private val viewModel: UserProfileViewModel by sharedViewModel()
    private var accessToken: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentUserProfileBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("accessToken")?.let { token ->
            accessToken = token
            val username = getUsernameFromToken(token)
            if (username != null) {
                viewModel.getUserDetails(username)
            } else {
                showErrorDialog("Invalid token.")
            }
        } ?: run {
            showErrorDialog("Access token not found.")
        }

        userDetails()
    }

    private fun userDetails(){
        lifecycleScope.launch {
            viewModel.userDataState.collect {state ->
                when(state) {
                    is UserUIState.Loading -> {}

                    is UserUIState.Success -> {
                        val user = state.user
                        binding?.render(user)
                    }

                    is UserUIState.Error -> {
                        Log.d("Error", state.message)
                        showErrorDialog(state.message)
                    }
                }

            }
        }
    }

    private fun getUsernameFromToken(token: String): String? {
        val tokenParts = token.split(".")
        if (tokenParts.size != 3) {
            return null
        }
        val body = tokenParts[1]
        val decodedBody = String(Base64.decode(body, Base64.URL_SAFE), Charsets.UTF_8)
        try {
            val jsonBody = JSONObject(decodedBody)
            return jsonBody.getString("sub")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    private fun FragmentUserProfileBinding.render(data: UserResponseBody) {
        firstNameTextView.text = getString(R.string.firstNameTextView, data.firstName)
        lastNameTextView.text = getString(R.string.lastNameTextView, data.lastName)
        emailTextView.text = getString(R.string.emailTextView, data.email)
        usernameTextView.text = getString(R.string.usernameTextView, data.username)
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.error)
            setMessage(message)
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

}