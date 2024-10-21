package com.dicoding.dicodingevent.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.databinding.FragmentHomeBinding
import com.dicoding.dicodingevent.ui.fragments.finished.FinishedAdapter
import com.dicoding.dicodingevent.ui.activity.detail.DetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var upcomingAdapter: HomeAdapter
    private lateinit var finishedAdapter: FinishedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        upcomingAdapter = HomeAdapter { event ->
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("event_id", event.id)
            }
            startActivity(intent)
        }
        finishedAdapter = FinishedAdapter { event ->
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("event_id", event.id)
            }
            startActivity(intent)
        }

        binding.rvUpcomingEvent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvUpcomingEvent.adapter = upcomingAdapter
        binding.rvFinishedEvent.isNestedScrollingEnabled = true

        binding.rvFinishedEvent.layoutManager = LinearLayoutManager(context)
        binding.rvFinishedEvent.adapter = finishedAdapter
        binding.rvFinishedEvent.isNestedScrollingEnabled = false

        homeViewModel.fetchUpcomingEvents()
        homeViewModel.fetchFinishedEvents()

        observeViewModel()
    }

    private fun observeViewModel() {
        homeViewModel.isUpcomingLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoadingUpcoming(isLoading)
        }

        homeViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            upcomingAdapter.submitList(events.take(5))
        }

        homeViewModel.isFinishedLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoadingFinished(isLoading)
        }

        homeViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            finishedAdapter.submitList(events.take(5))
        }
    }

    private fun showLoadingUpcoming(isLoading: Boolean) {
        binding.progressBarUpcoming.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showLoadingFinished(isLoading: Boolean) {
        binding.progressBarFinished.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
