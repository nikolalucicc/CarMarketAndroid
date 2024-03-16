package com.carmarket.ui.changeUser

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
import com.carmarket.databinding.FragmentChangeUserBinding
import com.carmarket.model.request.UserRequest
import com.carmarket.model.responseBody.UserResponseBody
import com.carmarket.stateClasses.UserUIState
import com.carmarket.ui.userProfile.UserProfileViewModel
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChangeUserFragment : Fragment() {

    //TODO Zasto ne radi izmena username i namestiti izmenu passworda, kada se menjaju ova dva da se obrise token i korisnik mora ponovo da se uloguje

    private var binding: FragmentChangeUserBinding? = null
    private var accessToken: String? = null
    private val userProfileViewModel: UserProfileViewModel by sharedViewModel()
    private val changeUserViewModelViewModel: ChangeUserViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentChangeUserBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accessToken = arguments?.getString("accessToken")

        accessToken?.let { token ->
            val username = getUsernameFromToken(token)
            if (username != null) {
                userProfileViewModel.getUserDetails(username)
                userDetails()
            } else {
                showErrorDialog("Invalid token.")
            }
        }

        binding?.saveChangesButton?.setOnClickListener {
            val email = binding?.emailEditText?.text.toString()
            val password = binding?.passwordEditText?.text.toString()
            val newUsername = binding?.usernameEditText?.text.toString()
            val firstName = binding?.firstNameEditText?.text.toString()
            val lastName = binding?.lastNameEditText?.text.toString()

            val user = UserRequest(email, password, newUsername, firstName, lastName)
            accessToken?.let { token ->
                val username = getUsernameFromToken(token)
                if (username != null) {
                    changeUserViewModelViewModel.updateUser(user, username, token)
                } else {
                    showErrorDialog("Invalid token.")
                }
            }
        }
    }

    private fun userDetails(){
        lifecycleScope.launch {
            userProfileViewModel.userDataState.collect {state ->
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

    private fun FragmentChangeUserBinding.render(data: UserResponseBody) {
        firstNameEditText.setText(data.firstName)
        lastNameEditText.setText(data.lastName)
        usernameEditText.setText(data.username)
        emailEditText.setText(data.email)
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
