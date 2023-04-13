package com.submission.dicodingstory.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.submission.dicodingstory.R
import com.submission.dicodingstory.data.factory.ApiStoryRepositoryFactory
import com.submission.dicodingstory.data.remote.body.RegisterBody
import com.submission.dicodingstory.databinding.FragmentRegisterBinding
import com.submission.dicodingstory.utils.Resource
import com.submission.dicodingstory.viewmodel.ApiStoryViewModel


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)
        binding.loading.visibility = View.GONE

        val factory: ApiStoryRepositoryFactory =
            ApiStoryRepositoryFactory.getInstance(requireActivity())
        val viewModel: ApiStoryViewModel by lazy {
            ViewModelProvider(this, factory)[ApiStoryViewModel::class.java]
        }

        binding.btnSignin.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, LoginFragment())
                .commit()
        }

        binding.btnSignup.setOnClickListener {
            val name = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.register(
                RegisterBody(
                    name = name,
                    email = email,
                    password = password
                )
            )
        }

        viewModel.registerData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.parent.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.loading.visibility = View.GONE
                    binding.parent.visibility = View.VISIBLE

                    if(it.data?.error == false) {
                        Toast.makeText(requireContext(), "Berhasil mendaftar", Toast.LENGTH_SHORT)
                            .show()

                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_container, LoginFragment())
                            .commit()
                    }else{
                        val message = it.data?.message

                        Toast.makeText(requireContext(), "Terjadi Kesalahan : $message", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
                is Resource.Error -> {
                    binding.loading.visibility = View.GONE
                    binding.parent.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}