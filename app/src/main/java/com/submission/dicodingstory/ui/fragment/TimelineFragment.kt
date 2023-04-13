package com.submission.dicodingstory.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.dicodingstory.R
import com.submission.dicodingstory.data.factory.ApiStoryRepositoryFactory
import com.submission.dicodingstory.data.factory.AuthPreferencesFactory
import com.submission.dicodingstory.data.preferences.AuthPreferences
import com.submission.dicodingstory.databinding.FragmentTimelineBinding
import com.submission.dicodingstory.ui.adapter.TimelinePostAdapter
import com.submission.dicodingstory.utils.Constants.Companion.dataStore
import com.submission.dicodingstory.viewmodel.ApiStoryViewModel
import com.submission.dicodingstory.viewmodel.AuthPreferencesViewModel
import com.submission.dicodingstory.utils.Resource


class TimelineFragment : Fragment() {

    private lateinit var binding: FragmentTimelineBinding
    private lateinit var userName: String
    private lateinit var timelineAdapter: TimelinePostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimelineBinding.bind(view)

        val pref = AuthPreferences.getInstance(requireActivity().dataStore)
        val authViewModel = ViewModelProvider(
            this, AuthPreferencesFactory(pref)
        )[AuthPreferencesViewModel::class.java]

        val factory: ApiStoryRepositoryFactory =
            ApiStoryRepositoryFactory.getInstance(requireActivity())
        val viewModel: ApiStoryViewModel by lazy {
            ViewModelProvider(this, factory)[ApiStoryViewModel::class.java]
        }

        authViewModel.getUserData().observe(viewLifecycleOwner) {

            if (it != null && it.name != "" && it.token != "") {
                userName = it.name!!

                binding.helloUser.text = "Hello, $userName"
                viewModel.getStory("Bearer ${it.token}")
            }

        }

        setupRecyclerView()

        viewModel.storyData.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
//                        binding.parent.visibility = View.GONE
//                        binding.loading.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
//                        binding.parent.visibility = View.VISIBLE
//                        binding.loading.visibility = View.GONE

                        timelineAdapter.differ.submitList(it.data?.listStory)
                    }
                    is Resource.Error -> {
//                        binding.parent.visibility = View.VISIBLE
//                        binding.loading.visibility = View.GONE

                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.addFragment -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, PostTimelineFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }


    private fun setupRecyclerView() {
        timelineAdapter = TimelinePostAdapter()
        binding.rvTimeline.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = timelineAdapter
        }

    }

}