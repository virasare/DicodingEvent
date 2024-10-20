package com.dicoding.dicodingevent.ui.fragments.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.databinding.FragmentFavoriteBinding
import com.dicoding.dicodingevent.ui.DetailActivity

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel = ViewModelProvider(this, FavoriteFactory.getInstance(requireContext()))[FavoriteViewModel::class.java]

        binding.rvFavorite.layoutManager = LinearLayoutManager(context)
        adapter = FavoriteAdapter { favoriteEvent ->
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("event_id", favoriteEvent.id)
            }
            startActivity(intent)
        }
        binding.rvFavorite.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvFavorite.adapter = adapter

        observeViewModel()
    }

    private fun observeViewModel() {
        favoriteViewModel.favoriteEvent.observe(viewLifecycleOwner) { favoriteEvents ->
            if (favoriteEvents == null ||favoriteEvents.isEmpty()) {
                Toast.makeText(requireContext(), "Tidak ada acara favorit", Toast.LENGTH_SHORT).show()
                binding.rvFavorite.visibility = View.GONE
            } else {
                adapter.submitList(favoriteEvents)
            }
        }

        favoriteViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}