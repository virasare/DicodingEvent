package com.dicoding.dicodingevent.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.dicodingevent.R

class DetailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val eventId = intent.getIntExtra("event_id", -1)
        fetchEventDetails(eventId)
    }
    private fun fetchEventDetails(eventId: Int) {
        // Panggil API dan ambil detail berdasarkan eventId
    }
}