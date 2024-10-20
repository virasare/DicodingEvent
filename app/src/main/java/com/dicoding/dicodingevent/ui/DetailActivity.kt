package com.dicoding.dicodingevent.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.data.local.FavoriteEventRepository
import com.dicoding.dicodingevent.data.remote.response.Event
import com.dicoding.dicodingevent.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    private var isFavorite: Boolean = false
    private lateinit var currentEvent: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = FavoriteEventRepository(application)
        val factory = DetailFactory(repository)

        detailViewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val eventId = intent.getIntExtra("event_id", -1)

        detailViewModel.fetchEventDetails(eventId)

        detailViewModel.eventDetail.observe(this) { eventDetail ->
            if (eventDetail != null) {
                updateUI(eventDetail)
                currentEvent = eventDetail
            }
        }

        detailViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        detailViewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
            }
        }

        setupFabFavorite(eventId)
    }

    private fun setupFabFavorite(eventId: Int) {
        detailViewModel.checkFavoriteStatus(eventId.toString())

        detailViewModel.isFavorite.observe(this) { isFav ->
            isFavorite = isFav
            updateFabIcon(isFavorite)
        }

        binding.fabFav.setOnClickListener {
            if (isFavorite) {
                detailViewModel.deleteFromFavorite(currentEvent.id.toString())
                Snackbar.make(binding.root, "${currentEvent.name} dihapus dari favorit", Snackbar.LENGTH_SHORT).show()
            } else {
                detailViewModel.addToFavorite(currentEvent)
                Snackbar.make(binding.root, "${currentEvent.name} ditambahkan ke favorit", Snackbar.LENGTH_SHORT).show()
            }
            isFavorite = !isFavorite
            updateFabIcon(isFavorite)
        }
    }

    private fun updateFabIcon(isFavorite: Boolean) {
        binding.fabFav.setImageResource(
            if (isFavorite) R.drawable.baseline_favorite_24
            else R.drawable.baseline_favorite_border_24
        )
    }

    private fun updateUI(eventDetail: Event) {
        binding.includeEventDetail.tvEventName.text = eventDetail.name
        binding.includeEventDetail.tvOwnerName.text = eventDetail.ownerName
        val remainingQuota = (eventDetail.quota ?: 0) - (eventDetail.registrants ?: 0)
        binding.includeEventDetail.tvQuotaRemaining.text = String.format(
            Locale.getDefault(),
            "%d Peserta",
            remainingQuota
        )
        binding.includeEventDetail.tvBeginTime.text = eventDetail.beginTime
        binding.includeEventDetail.tvEventSummary.text = eventDetail.summary
        binding.includeEventDetail.tvEventDescription.text = eventDetail.description?.let {
            HtmlCompat.fromHtml(
                it,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
        Glide.with(this)
            .load(eventDetail.mediaCover)
            .into(binding.imgEventPhoto)

        binding.btnRegister.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(eventDetail.link))
            startActivity(intent)
        }
    }
}
