package com.submission.dicodingstory.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.submission.dicodingstory.R
import com.submission.dicodingstory.databinding.FragmentPostTimelineBinding

class PostTimelineFragment : Fragment() {

    private lateinit var binding: FragmentPostTimelineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_timeline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPostTimelineBinding.bind(view)

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.homeFragment -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, TimelineFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}