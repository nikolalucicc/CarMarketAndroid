package com.carmarket.ui.login

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.carmarket.R
import com.carmarket.databinding.FragmentLoginBinding
import com.carmarket.model.request.LoginRequest
import com.carmarket.stateClasses.LoginUIState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import android.content.Context
import android.content.SharedPreferences

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private val viewModel: LoginViewModel by sharedViewModel()

    private val _accessToken = MutableLiveData<String>()
    val accessToken: LiveData<String>
        get() = _accessToken

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoginBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.registrationRedirectTextView?.setOnClickListener {
            registrationRedirect()
        }

        binding?.loginButton?.setOnClickListener {
            val username = binding?.usernameLoginEditText?.text.toString()
            val password = binding?.passwordLoginEditText?.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                login()
            } else {
                Toast.makeText(requireContext(), "Molimo unesite korisničko ime i lozinku", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login() {
        val username = binding?.usernameLoginEditText?.text.toString()
        val password = binding?.passwordLoginEditText?.text.toString()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            viewModel.login(LoginRequest(username, password))

            lifecycleScope.launch {
                viewModel.loginUiDataState.collect { state ->
                    when (state) {
                        is LoginUIState.Loading -> {
                            hideKeyboard()
                        }

                        is LoginUIState.Success -> {
                            val response = state.response
                            _accessToken.value = response.accessToken
                            saveAccessTokenToSharedPreferences(response.accessToken)
                            Toast.makeText(requireContext(), "Uspešno ste se prijavili", Toast.LENGTH_SHORT).show()
                            navigateToNextScreen(response.accessToken, response.expiresIn)
                            Log.d("Token", response.accessToken)
                        }

                        is LoginUIState.Error -> {
                            displayErrorDialog("Pogresni kredencijali")
                        }
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "Molimo unesite korisničko ime i lozinku", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveAccessTokenToSharedPreferences(accessToken: String) {
        val sharedPreferences = requireContext().getSharedPreferences("com.carmarket", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("accessToken", accessToken).apply()
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = requireActivity().currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun navigateToNextScreen(accessToken: String, expiresIn: Long) {
        val bundle = Bundle().apply {
            putString("accessToken", accessToken)
            putLong("expiresIn", expiresIn)
        }

        val navController = findNavController()
        navController.navigate(R.id.action_loginFragment_to_allAdsFragment, bundle)
    }

    private fun registrationRedirect(){
        val navController = findNavController()
        navController.navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    private fun displayErrorDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Login Greska")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun getAccessToken(): String {
        return _accessToken.value ?: ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}