package com.dicoding.dicodingevent.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.data.response.DetailEventResponse
import com.dicoding.dicodingevent.data.response.Event
import com.dicoding.dicodingevent.data.retrofit.ApiConfig
import com.dicoding.dicodingevent.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId = intent.getIntExtra("event_id", -1)
        fetchEventDetails(eventId)
    }
    private fun fetchEventDetails(eventId: Int) {
        binding.progressBarDetail.visibility = View.VISIBLE

        val apiService = ApiConfig.getApiService()
        apiService.getDetailEvent(eventId.toString()).enqueue(object: Callback<DetailEventResponse> {
            override fun onResponse(call: Call<DetailEventResponse>, response: Response<DetailEventResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val eventDetail = response.body()?.event
                    if (eventDetail != null) {
                        updateUI(eventDetail)
                    }
                }
                binding.progressBarDetail.visibility = View.GONE
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                binding.progressBarDetail.visibility = View.GONE
            }
        })
    }

    private fun updateUI(eventDetail: Event) {
        binding.includeEventDetail.tvEventName.text = eventDetail.name
        binding.includeEventDetail.tvOwnerName.text = eventDetail.ownerName
        binding.includeEventDetail.tvQuota.text = eventDetail.quota.toString()
        binding.includeEventDetail.tvBeginTime.text = eventDetail.beginTime
        binding.includeEventDetail.tvEventSummary.text = eventDetail.summary
        binding.includeEventDetail.tvEventDescription.text = eventDetail.description
        Glide.with(this)
            .load(eventDetail.mediaCover)
            .into(binding.imgEventPhoto)

        binding.btnRegister.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(eventDetail.link))
            startActivity(intent)
        }
    }
}