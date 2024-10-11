package com.dicoding.dicodingevent.fragments.finished

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.databinding.FragmentFinishedEventBinding
import com.dicoding.dicodingevent.ui.DetailActivity
import com.dicoding.dicodingevent.ui.EventAdapter

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedEventBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: EventAdapter
    private val finishedViewModel: FinishedViewModel by viewModels() // Ambil instance ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFinishedEvent.layoutManager = LinearLayoutManager(context)
        adapter = EventAdapter { event ->
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("event_id", event.id)
            }
            startActivity(intent)
        }
        binding.rvFinishedEvent.adapter = adapter


        finishedViewModel.events.observe(viewLifecycleOwner) { events ->
            adapter.submitList(events)
        }

        finishedViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        finishedViewModel.fetchEvents()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}