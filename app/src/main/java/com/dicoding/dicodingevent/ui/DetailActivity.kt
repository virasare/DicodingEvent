package com.dicoding.dicodingevent.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.data.response.Event
import com.dicoding.dicodingevent.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId = intent.getIntExtra("event_id", -1)

        // Fetch event details
        detailViewModel.fetchEventDetails(eventId)

        // Observe event details
        detailViewModel.eventDetail.observe(this) { eventDetail ->
            if (eventDetail != null) {
                updateUI(eventDetail)
            }
        }

        // Observe loading status
        detailViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observe error messages
        detailViewModel.errorMessage.observe(this) { errorMessage ->
            // You can show a Snackbar or Toast with the error message
            if (errorMessage != null) {
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
            }
        }
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
