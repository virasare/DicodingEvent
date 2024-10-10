package com.dicoding.dicodingevent.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.databinding.FragmentHomeBinding
import com.dicoding.dicodingevent.ui.DetailActivity
import com.dicoding.dicodingevent.ui.EventAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: EventAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvUpcomingEvent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = EventAdapter { event ->
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("event_id", event.id)
            }
            startActivity(intent)
        }
        binding.rvUpcomingEvent.adapter = adapter

        homeViewModel.isUpcomingLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoadingUpcoming(isLoading)
        }
        homeViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            val limitedEvents = events.take(5)
            adapter.submitList(limitedEvents)
        }

        binding.rvFinishedEvent.layoutManager = LinearLayoutManager(context)
        adapter = EventAdapter { event ->
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("event_id", event.id)
            }
            startActivity(intent)
        }
        binding.rvFinishedEvent.adapter = adapter

        homeViewModel.fetchUpcomingEvents()
        homeViewModel.fetchFinishedEvents()

        homeViewModel.isFinishedLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoadingFinished(isLoading)
        }
        homeViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            val limitedEvents = events.take(5)
            adapter.submitList(limitedEvents)
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
