package com.submission.dicodingstory.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.submission.dicodingstory.R
import com.submission.dicodingstory.data.factory.ApiStoryRepositoryFactory
import com.submission.dicodingstory.data.factory.AuthPreferencesFactory
import com.submission.dicodingstory.data.preferences.AuthPreferences
import com.submission.dicodingstory.data.preferences.UserData
import com.submission.dicodingstory.data.remote.body.LoginBody
import com.submission.dicodingstory.databinding.FragmentLoginBinding
import com.submission.dicodingstory.utils.Constants.Companion.dataStore
import com.submission.dicodingstory.utils.Resource
import com.submission.dicodingstory.viewmodel.ApiStoryViewModel
import com.submission.dicodingstory.viewmodel.AuthPreferencesViewModel


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding.loading.visibility = View.GONE

        binding.btnSignup.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, RegisterFragment())
                .commit()
        }

        val factory: ApiStoryRepositoryFactory =
            ApiStoryRepositoryFactory.getInstance(requireActivity())
        val viewModel: ApiStoryViewModel by lazy {
            ViewModelProvider(this, factory)[ApiStoryViewModel::class.java]
        }

        val pref = AuthPreferences.getInstance(requireActivity().dataStore)
        val authViewModel = ViewModelProvider(
            this,
            AuthPreferencesFactory(pref)
        )[AuthPreferencesViewModel::class.java]

        binding.btnSignin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.login(
                LoginBody(
                    email = email,
                    password = password
                )
            )
        }

        viewModel.loginData.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        binding.parent.visibility = View.GONE
                        binding.loading.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.parent.visibility = View.VISIBLE
                        binding.loading.visibility = View.GONE

                        if (it.data?.error == false) {

                            Log.d("LoginFragment", "onViewCreated: ${it.data.loginResult?.name}")
                            Log.d("LoginFragment", "onViewCreated: ${it.data.loginResult?.token}")

                            authViewModel.saveUserData(
                                UserData(
                                    name = it.data.loginResult?.name,
                                    token = it.data.loginResult?.token
                                )
                            )
                            Toast.makeText(
                                requireContext(),
                                "Berhasil Login",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val message = it.data?.message

                            Toast.makeText(
                                requireContext(),
                                "Gagal Login : $message",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is Resource.Error -> {
                        binding.loading.visibility = View.GONE
                        binding.parent.visibility = View.VISIBLE

                        Toast.makeText(requireContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }


    }
}