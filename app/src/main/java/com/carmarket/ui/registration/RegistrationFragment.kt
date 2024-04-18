package com.carmarket.ui.registration

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresExtension
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.carmarket.R
import com.carmarket.databinding.FragmentRegistrationBinding
import com.carmarket.model.request.UserRequest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import retrofit2.HttpException

class RegistrationFragment : Fragment() {

    private var binding: FragmentRegistrationBinding? = null
    private val viewModel: RegistrationViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRegistrationBinding.inflate(inflater, container, false).also { binding = it }.root

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.loginRedirectTextView?.setOnClickListener {
            loginRedirect()
        }

        binding?.registrationButton?.setOnClickListener {
            registerUser()
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun registerUser() {
        val email = binding?.emailEditText?.text.toString().trim()
        val password = binding?.passwordEditText?.text.toString().trim()
        val repeatedPassword = binding?.repeatedPasswordEditText?.text.toString().trim()
        val username = binding?.usernameEditText?.text.toString().trim()
        val firstName = binding?.firstNameEditText?.text.toString().trim()
        val lastName = binding?.lastNameEditText?.text.toString().trim()

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
            TextUtils.isEmpty(repeatedPassword) || TextUtils.isEmpty(username) ||
            TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
            displayErrorDialog("Molimo popunite sva polja")
            return
        }

        if (password != repeatedPassword) {
            displayErrorDialog("Sifre se ne poklapaju. Unesite iste sifre.")
            return
        }

        lifecycleScope.launch {
            val userRequest = UserRequest(
                email, password, username, firstName, lastName
            )
            try {
                viewModel.registration(userRequest)
                clearFields()
                loginRedirect()
            } catch (e: HttpException) {
                when (e.code()) {
                    409 -> displayErrorDialog("Korisnicko ime ili email vec postoje")
                    else -> displayErrorDialog(R.string.error.toString())
                }
            } catch (e: Exception) {
                displayErrorDialog(R.string.error.toString())
            }
        }
    }

    private fun loginRedirect(){
        val navController = findNavController()
        navController.navigate(R.id.action_registrationFragment_to_loginFragment)
    }

    private fun clearFields() {
        binding?.firstNameEditText?.text?.clear()
        binding?.lastNameEditText?.text?.clear()
        binding?.usernameEditText?.text?.clear()
        binding?.emailEditText?.text?.clear()
        binding?.passwordEditText?.text?.clear()
        binding?.repeatedPasswordEditText?.text?.clear()
    }

    private fun displayErrorDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Greška pri registraciji")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
